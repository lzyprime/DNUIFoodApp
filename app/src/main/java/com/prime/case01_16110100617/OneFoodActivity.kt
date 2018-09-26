package com.prime.case01_16110100617

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.prime.case01_16110100617.beans.CollectedBean
import com.prime.case01_16110100617.beans.Foodbean
import com.prime.case01_16110100617.beans.SignInBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_one_food.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class OneFoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_food)
        var usr_id = getSharedPreferences("case01_16110100617",Context.MODE_PRIVATE).getString("usrid","")!!
        var food_id = intent.getStringExtra("food_id")

        food_toolbar.setNavigationIcon(R.drawable.ic_leftback)
        food_toolbar.setNavigationOnClickListener { finish() }

        food_toolbar.inflateMenu(R.menu.collectmenu)
        food_toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener{
            override fun onMenuItemClick(p0: MenuItem?): Boolean {
                Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                        .create(RetrofitInterfaces::class.java).collectFood(usr_id, food_id)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                if (Gson().fromJson(String(response.body()?.bytes()!!), SignInBean::class.java).success.equals("1")) {
                                    food_toolbar.menu.findItem(R.id.item_collected).isVisible = !food_toolbar.menu.findItem(R.id.item_collected).isVisible
                                    food_toolbar.menu.findItem(R.id.item_notcollected).isVisible = !food_toolbar.menu.findItem(R.id.item_notcollected).isVisible
                                }
                            }
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            }
                        })
                return true
            }
        })

        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                .create(RetrofitInterfaces::class.java).iscollected(usr_id, food_id, "1")
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        var collected = Gson().fromJson(String(response.body()?.bytes()!!), CollectedBean::class.java)
                        if (collected!=null && collected.collected.equals("1"))
                        {
                            food_toolbar.menu.findItem(R.id.item_collected).isVisible = true
                            food_toolbar.menu.findItem(R.id.item_notcollected).isVisible = false
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    }
                })

    }
}
