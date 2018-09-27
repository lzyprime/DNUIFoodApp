package com.prime.case01_16110100617.fragments


import android.content.Context
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
import com.prime.case01_16110100617.adapters.UsrCollectionList
import com.prime.case01_16110100617.beans.CollectionshopfoodBean
import kotlinx.android.synthetic.main.fragment_collect_food.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CollectFoodFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collect_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var usr_id = context!!.getSharedPreferences("case01_16110100617", Context.MODE_PRIVATE).getString("usrid","")
        RV_collect_food.layoutManager = GridLayoutManager(context,1)
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                .create(RetrofitInterfaces::class.java).getusrcollectionlist(usr_id,"1")
                .enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        var foodlist = Gson().fromJson<List<CollectionshopfoodBean>>(String(response.body()?.bytes()!!),object : TypeToken<List<CollectionshopfoodBean>>(){}.type)
                        RV_collect_food.adapter = UsrCollectionList(foodlist)
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })
    }
}
