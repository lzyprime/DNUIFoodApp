package com.prime.case01_16110100617

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.prime.case01_16110100617.beans.SignInBean
import kotlinx.android.synthetic.main.activity_pay_money.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PayMoneyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_money)
        var usr_id = intent.getStringExtra("usrid")
        var food_id = intent.getStringExtra("food_id")
        var price = intent.getStringExtra("price")
        bt_pay.setOnClickListener {
            var num = et_num.text.toString()
            var addr= et_addr.text.toString()
            if(!num.equals("") && !addr.equals("")) {
                var sum =  price.toDouble() * num.toInt()
                Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                        .create(RetrofitInterfaces::class.java)
                        .insertOrder(usr_id,food_id,num,sum,"11:00-11:30",addr)
                        .enqueue(object : Callback<ResponseBody>{
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                if(Gson().fromJson(String(response.body()?.bytes()!!),SignInBean::class.java).success.equals("1"))
                                {
                                    Toast.makeText(this@PayMoneyActivity,"下单成功",Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                            }
                        })
            }
        }
    }
}
