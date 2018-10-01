package com.prime.case01_16110100617

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prime.case01_16110100617.adapters.ContentListAdapter
import com.prime.case01_16110100617.beans.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_one_food.*
import kotlinx.android.synthetic.main.activity_re_usr_info.*
import kotlinx.android.synthetic.main.usrcardtoolbar.view.*
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
        RV_foodcontents.layoutManager = GridLayoutManager(this@OneFoodActivity,1)
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                .create(RetrofitInterfaces::class.java).getfoodbyid(food_id)
                .enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        var foodinfo = Gson().fromJson(String(response.body()?.bytes()!!),Foodbean::class.java)
                        FoodInfoBar.card_tv_usrname.text = foodinfo?.foodname
                        FoodInfoBar.card_tv_phone.text = "￥${foodinfo?.price}"
                        FoodInfoBar.card_tv_addr.text = foodinfo?.intro
                        Picasso.get().load("http://172.24.10.175:8080/foodService${foodinfo?.pic}").into(FoodInfoBar.im_usravtar)

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                .create(RetrofitInterfaces::class.java).getfoodcomments(food_id)
                .enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        var comments = Gson().fromJson<List<ContentBean>>(String(response.body()?.bytes()!!),object : TypeToken<List<ContentBean>>(){}.type)
                        RV_foodcontents.adapter = ContentListAdapter(comments,usr_id)
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })


        foodbar.inflateMenu(R.menu.foodmenu)
        foodbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.addcart -> {
                    Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                            .create(RetrofitInterfaces::class.java).addCart(usr_id,food_id,"1")
                            .enqueue(object : Callback<ResponseBody>{
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    if(Gson().fromJson(String(response.body()?.bytes()!!),SignInBean::class.java).success.equals("1"))
                                        Toast.makeText(this@OneFoodActivity,"加入成功",Toast.LENGTH_SHORT).show()
                                    else
                                        Toast.makeText(this@OneFoodActivity,"加入购物车失败",Toast.LENGTH_SHORT).show()
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    Toast.makeText(this@OneFoodActivity,"加入购物车失败",Toast.LENGTH_SHORT).show()
                                }
                            })

                }
                R.id.pay -> {
                    startActivity(Intent(this@OneFoodActivity,PayMoneyActivity::class.java).putExtra("usrid",usr_id)
                            .putExtra("food_id",food_id).putExtra("price",intent.getStringExtra("price")))
                }
            }
            true
        }

        getcllected(usr_id,food_id)

       foodbar.setNavigationOnClickListener {
           Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                   .create(RetrofitInterfaces::class.java).collectFood(usr_id,food_id)
                   .enqueue(object : Callback<ResponseBody>{
                       override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                           if(Gson().fromJson(String(response.body()?.bytes()!!),SignInBean::class.java).success.equals("1"))
                               getcllected(usr_id,food_id)
                       }

                       override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                       }
                   })
       }
    }

    fun getcllected(usr_id : String,food_id : String){
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                .create(RetrofitInterfaces::class.java).iscollected(usr_id,food_id,"1")
                .enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if(Gson().fromJson(String(response.body()?.bytes()!!),CollectedBean::class.java).collected.equals("1"))
                            foodbar.setNavigationIcon(R.drawable.ic_favorite)
                        else
                            foodbar.setNavigationIcon(R.drawable.ic_favorite_border)

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }
                })

    }

}
