package com.myapplication.map.Orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersLoc  implements Parcelable {
    @SerializedName("orders")

        private List<Orders> orders;

    protected OrdersLoc(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<OrdersLoc> CREATOR = new Creator<OrdersLoc>() {
        @Override
        public OrdersLoc createFromParcel(Parcel in) {
            return new OrdersLoc(in);
        }

        @Override
        public OrdersLoc[] newArray(int size) {
            return new OrdersLoc[size];
        }
    };


    public void setOrders(List<Orders> orders){
            this.orders = orders;
        }
        public List<Orders> getOrders(){
            return this.orders;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(orders);

    }
    private void readFromParcel(Parcel in) {
        orders = in.readArrayList(OrdersLoc.class.getClassLoader());
    }
}
