package com.prime.case01_16110100617

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
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

class OneShopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_shop)

        val shop_id = intent.getStringExtra("shop_id")
        val usr_id :String = getSharedPreferences("case01_16110100617", Context.MODE_PRIVATE).getString("usrid", "")
        shop_toolbar.title = intent.getStringExtra("shopname")
        shop_toolbar.inflateMenu(R.menu.collectmenu)
        shop_toolbar.setNavigationIcon(R.drawable.ic_leftback)
        shop_toolbar.setNavigationOnClickListener { finish() }
        shop_toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener{
            override fun onMenuItemClick(p0: MenuItem?): Boolean {
                Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                        .create(RetrofitInterfaces::class.java).collectShop(usr_id,shop_id)
                        .enqueue(object : Callback<ResponseBody>{
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                if(Gson().fromJson(String(response.body()?.bytes()!!),SignInBean::class.java).success.equals("1"))
                                {
                                    shop_toolbar.menu.findItem(R.id.item_collected).isVisible = !shop_toolbar.menu.findItem(R.id.item_collected).isVisible
                                    shop_toolbar.menu.findItem(R.id.item_notcollected).isVisible = !shop_toolbar.menu.findItem(R.id.item_notcollected).isVisible
                                }
                            }
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            }
                        })

                return true
            }
        })
        getCollected(usr_id, shop_id)
        RV_food.layoutManager = GridLayoutManager(this@OneShopActivity, 1)
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
                        {
                          shop_toolbar.menu.findItem(R.id.item_collected).isVisible = true
                            shop_toolbar.menu.findItem(R.id.item_notcollected).isVisible = false
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    }
                })
    }
}
