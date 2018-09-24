package com.prime.case01_16110100617

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prime.case01_16110100617.adapters.FoodListAdapter
import com.prime.case01_16110100617.beans.CollectedBean
import com.prime.case01_16110100617.beans.Foodbean
import com.prime.case01_16110100617.beans.SignInBean
import kotlinx.android.synthetic.main.activity_one_shop.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class OneShopActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_shop)
        val shop_id = intent.getStringExtra("shop_id")
        val usr_id :String = getSharedPreferences("case01_16110100617", Activity.MODE_PRIVATE).getString("usrid", "")
        tv_shopname.text = intent.getStringExtra("shopname")
        getCollected(usr_id, shop_id)
        switchbt_collect.setOnClickListener {
            Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                    .create(RetrofitInterfaces::class.java).collectShop(usr_id, shop_id)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            var s = Gson().fromJson(String(response.body()?.bytes()!!), SignInBean::class.java)
                            Toast.makeText(this@OneShopActivity, s.success, Toast.LENGTH_SHORT).show()
                            if (s == null || s.success.equals("0")) {
                                Toast.makeText(this@OneShopActivity, "操作失败", Toast.LENGTH_SHORT).show()
                                switchbt_collect.isChecked = !switchbt_collect.isChecked
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(this@OneShopActivity, "操作失败", Toast.LENGTH_SHORT).show()
                            switchbt_collect.isChecked = !switchbt_collect.isChecked
                        }
                    })
        }
        RV_food.layoutManager = GridLayoutManager(this, 1)
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                .create(RetrofitInterfaces::class.java).getfoodlist(shop_id)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        var foodlist: List<Foodbean> = Gson().fromJson<List<Foodbean>>(String(response.body()?.bytes()!!), object : TypeToken<List<Foodbean>>() {}.type)
                        RV_food.adapter = FoodListAdapter(foodlist)
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {}
                })

    }

    fun getCollected(usr_id: String, shop_id: String) {
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                .create(RetrofitInterfaces::class.java).iscollected(usr_id, shop_id, "0")
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        var collected = Gson().fromJson(String(response.body()?.bytes()!!), CollectedBean::class.java)
                        if (collected.collected.equals("1"))
                            switchbt_collect.isChecked = true
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    }
                })
    }
}
