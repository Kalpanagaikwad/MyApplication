package com.myapplication.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.myapplication.map.APIService.ApiServices;
import com.myapplication.map.NetworkUtil.Networkservice;
import com.myapplication.map.Orders.Orders;
import com.myapplication.map.Orders.OrdersListAdapter;
import com.myapplication.map.Orders.OrdersLoc;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    RecyclerView rv;
    public String BASE_URL = "http://demo8360259.mockable.io";
    ArrayList<Orders> data;
    OrdersListAdapter adapter;
    public static final String BROADCAST = "checkinternet";
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        setTitle("Orders");
        intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST);
        //check internet
        Intent serviceIntent = new Intent(this, Networkservice.class);
        startService(serviceIntent);
        if (Networkservice.isOnline(getApplicationContext())){
            Toast.makeText(getApplicationContext(),"Internet Connected",Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(getApplicationContext(),"Internet DisConnected",Toast.LENGTH_SHORT).show();
        getOrders();

    }
    public BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BROADCAST)){
                if (intent.getStringExtra("online_status").equals("true")){
                    Toast.makeText(getApplicationContext(),"Internet Connected",Toast.LENGTH_SHORT).show();
                    Log.d("data","true");
                }else {
                    Toast.makeText(getApplicationContext(), "Internet DisConnected", Toast.LENGTH_SHORT).show();
                    Log.d("data", "false");
                }
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver,intentFilter);
    }

    //Call API to get order List
    private void getOrders() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        ApiServices apiService = retrofit.create(ApiServices.class);
        Observable<OrdersLoc> observable = apiService.getOrders().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading....");
        // progressDoalog.setTitle("ProgressDialog bar example");
        //  progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        observable.subscribe(new Observer<OrdersLoc>() {


            @Override
            public void onCompleted() {
                progressDoalog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "check Server Connection..", Toast.LENGTH_LONG).show();
                //dismiss progress
                progressDoalog.dismiss();
            }


            @Override
            public void onNext(OrdersLoc ordersLoc) {
                Log.e("response", ordersLoc.toString());
                //dismiss progress
                progressDoalog.dismiss();
                //orderResponses.get

                data = new ArrayList<Orders>(ordersLoc.getOrders());
                if (data.size() != 0) {
                    // adapter.clear();
                    //   data.addAll(orderResponses);
                    //  rv.setAdapter(adapter);
                    adapter = new OrdersListAdapter(data, MainActivity.this);
                    rv.setAdapter(adapter);


                } else {
                    Toast.makeText(MainActivity.this, "Orders not Available", Toast.LENGTH_LONG).show();

                }

            }
        });
//    } else {
//        Toast.makeText(SitesActivity.this, "check Server Connection..", Toast.LENGTH_LONG).show();
//
//    }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}