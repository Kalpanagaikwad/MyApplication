package com.myapplication.map.Orders;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myapplication.map.MapsActivity;
import com.myapplication.map.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.Manifest.permission.CALL_PHONE;
import static androidx.core.app.ActivityCompat.requestPermissions;
import static com.myapplication.map.MainActivity.REQUEST_CODE;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder> {
    private ArrayList<Orders> mDataset;
    public  Context context;
  //  public String BASE_URL = "http://157.245.96.191";
    // public String BASE_URL="http://nodetest.trumonitor.tech";
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView,mtv_info,warning_data;
        public Button ok_button;
        public ImageButton phonecall,direction;
        String phn_no;

        public MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.iv_text);
            mtv_info=(TextView) v.findViewById(R.id.tv_infos);
           // warning_data=(TextView) v.findViewById(R.id.warning_data);
            ok_button=(Button) v.findViewById(R.id.ok_button);
            phonecall=(ImageButton) v.findViewById(R.id.phonecall);
            direction=(ImageButton) v.findViewById(R.id.direction);
            ok_button.setVisibility(View.GONE);
            direction.setVisibility(View.INVISIBLE);
            phonecall.setVisibility(View.INVISIBLE);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OrdersListAdapter(ArrayList<Orders> myDataset, Context con) {
        mDataset = myDataset;
        context=con;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrdersListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(holder.getAdapterPosition()).getName());
        holder.mtv_info.setText(mDataset.get(holder.getAdapterPosition()).getAddress());
       final  int  phn_no = mDataset.get(holder.getAdapterPosition()).getPhone();
       final double lat=mDataset.get(holder.getAdapterPosition()).getLocation().getLat();
        final double longi=mDataset.get(holder.getAdapterPosition()).getLocation().getLong();
       // holder.warning_data.setText(mDataset.get(holder.getAdapterPosition()).sensor_name);
        holder.ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                warningResponse posToRemove = mDataset.get(holder.getAdapterPosition());
//                String warn_id=posToRemove.getWarning_id();
//                callApiToRemoveWarning(warn_id);
//                mDataset.remove(holder.getAdapterPosition());
//                notifyDataSetChanged();
            }
        });
        //google map Location
        holder.direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // try {
                    Intent my_mapIntent = new Intent(context, MapsActivity.class);
                    my_mapIntent.putExtra("lat",lat);
                    my_mapIntent.putExtra("longi",longi);

                    v.getContext().startActivity(my_mapIntent);


//                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//                        //here the word 'tel' is important for making a call...
//                        v.getContext().startActivity(my_callIntent);
//                    }
//
//
//                    else {
//                        /* Exibe a tela para o usuário dar a permissão. */
//                        ActivityCompat.requestPermissions(
//                                (Activity) context,
//                                new String[]{Manifest.permission.CALL_PHONE},
//                                REQUEST_CODE);
//                    }
//
//                } catch (ActivityNotFoundException e) {
//                    Toast.makeText(context, "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
//                }
            }
        });

        //make a phone call
        holder.phonecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent my_callIntent = new Intent(Intent.ACTION_DIAL);
                    Log.e("tel:",""+phn_no);
                    my_callIntent.setData(Uri.parse("tel:"+phn_no));



                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        //here the word 'tel' is important for making a call...
                        v.getContext().startActivity(my_callIntent);
                    }


                    else {
                        /* Exibe a tela para o usuário dar a permissão. */
                        ActivityCompat.requestPermissions(
                                (Activity) context,
                                new String[]{Manifest.permission.CALL_PHONE},
                                REQUEST_CODE);
                    }

                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        //.concat( mDataset.get(position).getSensor_name()));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ok_button.setVisibility(View.VISIBLE);
               holder.direction.setVisibility(View.VISIBLE);
               holder.phonecall.setVisibility(View.VISIBLE);
                //  String currentValue = mDataset[position];
                //  Log.d("CardView", "CardView Clicked: " + currentValue);



            }
        });
    }




//    private void callApiToRemoveWarning(String warn_id) {
//        userSharedPref userpref = new userSharedPref();
//        LoginResponse userDataPref = userpref.readDataFromPrefAllData(context);
//
//        final warningResponse war_remove=new warningResponse();
//        war_remove.setWarning_id(warn_id);//warning_id
//        war_remove.setUser_id(String.valueOf(userDataPref.getUser_info().get(0).getUser_id()));//user_id
//
//
//
//        // for retrofit Api Service calling with refresh token
//        MyServiceHolder myServiceHolder = new MyServiceHolder();
//
//        OkHttpClient okHttpClient = new OkHttpClientInstance.Builder(context, myServiceHolder)
//                //  .addHeader("Authorization", token)
//                .build();
//
//        APIService apiService = new retrofit2.Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .client(okHttpClient)
//                .build()
//                .create(APIService.class);
//
//        myServiceHolder.setApiService(apiService);
//
//
//
//        //make  API call to Server
//   /*     Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//
//        APIService apiService = retrofit.create(APIService.class); */
//
////        userSharedPref userpref = new userSharedPref();
////        LoginResponse userDataPref ;
//        //= userpref.readDataFromPrefAllData(SensorGraphActivity.this);
//
//        userDataPref=  userpref.readDataFromPrefAllData(context);
//        String uid= String.valueOf(userDataPref.getUser_info().get(0).getUser_id());
//        String orgid= String.valueOf(userDataPref.getUser_info().get(0).getOrg_id());
//        String token=userDataPref.getidToken();
//        final  String Levelstr = userDataPref.getUser_info().get(0).getLevel();
//
//        //Make Api Call and get response
//        Observable<ackWarningRespose> observable = apiService.AckWarningToRemove(token,uid,orgid,Levelstr,war_remove).subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread());
//
//        // Set up progress before call
//        final ProgressDialog progressDoalog;
//        progressDoalog = new ProgressDialog(context);
//        progressDoalog.setMax(100);
//        progressDoalog.setMessage("Loading....");
//        // progressDoalog.setTitle("ProgressDialog bar example");
//        //  progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//
//        // show it
//        progressDoalog.show();
//
//        observable.subscribe(new Observer<ackWarningRespose>() {
//
//            @Override
//            public void onCompleted() {
//                progressDoalog.dismiss();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(ackWarningRespose ackWarningRespose) {
//
//
//                ackWarningRespose res=ackWarningRespose;
//                if(res.getAkn_status().equals("acknowledged")){
//                    Toast.makeText(context, "Warning Removed", Toast.LENGTH_LONG).show();
//                    //when we get Acknowleged status that warning removed from server database then we
//                    // remove it from sharedprefence and write new list to shared preference
//
//                    userSharedPref userPref=new userSharedPref();
//                    ArrayList<warningResponse>    dataAlarm= (ArrayList<warningResponse>) userPref.readWarningData(context);
//                    String warid=  war_remove.getWarning_id();
//                    for(int i=0;i<dataAlarm.size();i++){
//
//                        if(dataAlarm.get(i).getWarning_id().equals(warid)){
//                            dataAlarm.remove(i);
//                            break;
//                        }
//                    }
//                    userPref.WriteWarningData(context,dataAlarm);
//
//
//
//                }else {
//                    if (res.getAkn_status().equals("already acknowledged")){
//                        //     Toast.makeText(context, "Warning  Not Removed", Toast.LENGTH_LONG).show();
//
//                    }else{
//                        Toast.makeText(context, "Warning  Not Removed", Toast.LENGTH_LONG).show();
//                    }
//                    progressDoalog.dismiss();
//                }
//
//
//            }
//        });
//    }




  /*  @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTextView.setText(mDataset[position]);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = mDataset[position];
                Log.d("CardView", "CardView Clicked: " + currentValue);
            }
        });
    }*/

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}