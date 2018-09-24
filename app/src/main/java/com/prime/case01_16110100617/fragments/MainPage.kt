package com.prime.case01_16110100617.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.RetrofitInterfaces
import com.prime.case01_16110100617.ShowActivity
import com.prime.case01_16110100617.adapters.ShopListAdapter
import com.prime.case01_16110100617.beans.ShopBean
import kotlinx.android.synthetic.main.fragment_main_page.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainPage : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
        .create(RetrofitInterfaces::class.java).getAllShops()
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        var shoplist = Gson().fromJson<List<ShopBean>>(String(response.body()?.bytes()!!), object : TypeToken<List<ShopBean>>(){}.type)
                        RV_shop.layoutManager = GridLayoutManager(context,1)
                        RV_shop.adapter = ShopListAdapter(shoplist)
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })
    }

}
