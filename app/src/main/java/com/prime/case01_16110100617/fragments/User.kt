package com.prime.case01_16110100617.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prime.case01_16110100617.CartActivity

import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.ReUsrInfoActivity
import com.prime.case01_16110100617.RetrofitInterfaces
import com.prime.case01_16110100617.adapters.ContentListAdapter
import com.prime.case01_16110100617.adapters.UsrOrderAdapter
import com.prime.case01_16110100617.beans.ContentBean
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.usrcardtoolbar.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class User : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initcard()
        var usr_id = context?.getSharedPreferences("case01_16110100617", Context.MODE_PRIVATE)!!.getString("usrid", "")
        RV_usr_O_C_list.layoutManager = GridLayoutManager(context, 1)
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                .create(RetrofitInterfaces::class.java).getallorder(usr_id)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        var comments = Gson().fromJson<List<ContentBean>>(String(response.body()?.bytes()!!), object : TypeToken<List<ContentBean>>() {}.type)
                        RV_usr_O_C_list.adapter = UsrOrderAdapter(comments)
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })
        NV_usrbar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.usrallorder -> {
                    Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                            .create(RetrofitInterfaces::class.java).getallorder(usr_id)
                            .enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    var comments = Gson().fromJson<List<ContentBean>>(String(response.body()?.bytes()!!), object : TypeToken<List<ContentBean>>() {}.type)
                                    RV_usr_O_C_list.adapter = UsrOrderAdapter(comments)
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                                }
                            })
                }
                R.id.usrallcontent -> {
                    Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                            .create(RetrofitInterfaces::class.java).getallcomments(usr_id)
                            .enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    var comments = Gson().fromJson<List<ContentBean>>(String(response.body()?.bytes()!!), object : TypeToken<List<ContentBean>>() {}.type)
                                    RV_usr_O_C_list.adapter = ContentListAdapter(comments, usr_id)
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                                }
                            })
                }
                R.id.cart -> {
                        context!!.startActivity(Intent(context,CartActivity::class.java))
                }
            }
            true
        }
    }

    fun initcard() {
        var share = context!!.getSharedPreferences("case01_16110100617", Context.MODE_PRIVATE)
        card_tv_usrname.text = share.getString("usrname", "")
        card_tv_phone.text = "phone : ${share.getString("phone", "")}"
        card_tv_addr.text = "地址 : ${share.getString("addr", "")}"
        usrbar.setOnClickListener {
            startActivity(Intent(context, ReUsrInfoActivity::class.java))
        }
    }

}
