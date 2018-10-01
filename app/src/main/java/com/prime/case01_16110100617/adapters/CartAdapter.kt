package com.prime.case01_16110100617.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.RetrofitInterfaces
import com.prime.case01_16110100617.beans.CartBean
import com.prime.case01_16110100617.beans.SignInBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_item.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CartAdapter(var showlist: List<CartBean>) : RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    override fun getItemCount(): Int = showlist.size
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(View.inflate(p0.context, R.layout.cart_item,null))
    }

    var ids = mutableSetOf<Int>()
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
            with(p0.itemView) {
                cart_tv_foodname.text = showlist[p1].foodname
                cart_tv_shopname.text = showlist[p1].shopname
                cart_tv_money.text = (showlist[p1].num.toInt() * showlist[p1].price.toDouble()).toString()
                Picasso.get().load("http://172.24.10.175:8080/foodService${showlist[p1].pic}").into(cart_iv_food)
                cart_checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    when(isChecked){
                        true -> ids.add(p1)
                        else -> ids.remove(p1)
                    }
                }
                bt_delete.setOnClickListener {
                    Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                            .create(RetrofitInterfaces::class.java).deleteCart(showlist[p1].item_id)
                            .enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    if(!Gson().fromJson(String(response.body()?.bytes()!!), SignInBean::class.java).success.equals("0")) {
                                        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show()
                                        visibility = View.INVISIBLE
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                                }
                            })
                }
            }

    }
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
}