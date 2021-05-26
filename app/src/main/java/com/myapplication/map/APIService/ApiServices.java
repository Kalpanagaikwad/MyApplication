package com.myapplication.map.APIService;

import com.myapplication.map.Orders.OrdersLoc;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;

public interface  ApiServices {
//Api call
    @GET("/clients")
   Observable<OrdersLoc> getOrders();


}
