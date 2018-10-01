package com.prime.case01_16110100617

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prime.case01_16110100617.adapters.CartAdapter
import com.prime.case01_16110100617.beans.CartBean
import com.prime.case01_16110100617.beans.SignInBean
import kotlinx.android.synthetic.main.activity_cart.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        var usr_id = getSharedPreferences("case01_16110100617",Context.MODE_PRIVATE).getString("usrid","")
        RV_cart.layoutManager = GridLayoutManager(this@CartActivity,1)
        var adapter : CartAdapter = CartAdapter(listOf<CartBean>())
        RV_cart.adapter = adapter
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                .create(RetrofitInterfaces::class.java).getcart(usr_id)
                .enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        var list = Gson().fromJson<List<CartBean>>(String(response.body()?.bytes()!!), object : TypeToken<List<CartBean>>() {}.type)
                        adapter.showlist = list
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })
        bt_cart_commit.setOnClickListener {
            var ids = adapter.ids
            var list = adapter.showlist
            var items = list[ids.first()].item_id
            var sum = list[ids.first()].num.toInt() * list[ids.first()].price.toDouble()
            var tmp = ids.first()
            ids.remove(ids.first())
            for(i in ids) {
                items = "${items},${list[i].item_id}"
                sum += list[i].num.toInt() * list[i].price.toDouble()
            }
            ids.add(tmp)
            Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                    .create(RetrofitInterfaces::class.java).buyCart(usr_id,et_cart_addr.text.toString(),sum.toString(),"now",items)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if(!Gson().fromJson(String(response.body()?.bytes()!!),SignInBean::class.java).success.equals("0")) {
                                Toast.makeText(this@CartActivity,"购买成功",Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                        }
                    })

           // Toast.makeText(this@CartActivity,items,Toast.LENGTH_SHORT).show()
        }
    }
}
