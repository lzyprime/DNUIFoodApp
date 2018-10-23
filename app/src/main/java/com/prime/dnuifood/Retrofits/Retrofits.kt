package com.prime.dnuifood.Retrofits

import com.prime.dnuifood.Beans.LoginBean
import com.prime.dnuifood.Beans.ShopBean
import com.prime.dnuifood.Beans.SuccessBean
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofits {
    val BaseUrl = "http://172.24.10.175:8080/foodService/"
    val retrofit = Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    val server = retrofit.create(RetrofitInterfaces::class.java)

    fun doLogin (usrname : String, passwd : String, callBack: Callback<LoginBean>) {
            server.login(usrname, passwd).enqueue(callBack)
    }

    fun doRegister(usrname:String,passwd:String,phone:String,addr:String,comment:String,callback: Callback<SuccessBean>){
            server.register(usrname,passwd,phone,addr,comment).enqueue(callback)
    }
    fun doGetAllShops(callback: Callback<List<ShopBean>>) {
        server.getAllShops().enqueue(callback)
    }
}