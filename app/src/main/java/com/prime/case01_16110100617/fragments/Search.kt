package com.prime.case01_16110100617.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.RetrofitInterfaces
import com.prime.case01_16110100617.adapters.FoodListAdapter
import com.prime.case01_16110100617.adapters.SearchLogAdapter
import com.prime.case01_16110100617.beans.Foodbean
import kotlinx.android.synthetic.main.fragment_search.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class  Search : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    @SuppressLint("ApplySharedPref")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RV_search.layoutManager = GridLayoutManager(context,1)
        var share = context!!.getSharedPreferences("case01_16110100617",Context.MODE_PRIVATE)
        var logs = share.getStringSet("searchlog", mutableSetOf())?.toList()!!
        RV_search.adapter = SearchLogAdapter(logs)

        bt_search.setOnClickListener {
            var input = et_search.text.toString()
            if(!input.equals("")){
                var logset = share.getStringSet("searchlog", mutableSetOf())!!
                logset.add(input)
                share.edit().putStringSet("searchlog",logset).commit()
                Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                        .create(RetrofitInterfaces::class.java).search(input)
                        .enqueue(object : Callback<ResponseBody>{
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                var foodlist: List<Foodbean> = Gson().fromJson<List<Foodbean>>(String(response.body()?.bytes()!!), object : TypeToken<List<Foodbean>>() {}.type)
                                RV_search.adapter = FoodListAdapter(foodlist)
                                chtv_clearlog.visibility = View.INVISIBLE
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                            }
                        })
            }
        }

        et_search.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                logs = share.getStringSet("searchlog", mutableSetOf())?.toList()!!
                RV_search.adapter = SearchLogAdapter(logs)
                chtv_clearlog.visibility = View.VISIBLE
            }
        })

        chtv_clearlog.setOnClickListener {
            share.edit().putStringSet("searchlog", mutableSetOf()).commit()

            RV_search.adapter = null
        }


    }
}
