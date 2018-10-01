package com.prime.case01_16110100617

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.prime.case01_16110100617.beans.SignInBean
import kotlinx.android.synthetic.main.activity_comment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CommentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        var mod = intent.getStringExtra("mod");
        var item_id = intent.getStringExtra("item_id")
        var share = Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build().create(RetrofitInterfaces::class.java)
        if(mod.equals("add")) {
            bt_upcomment.setOnClickListener {
                var content = et_content.text.toString()
                if(!content.equals("")) {
                    share.insetcomment(item_id,content).enqueue(object : Callback<ResponseBody>{
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if(Gson().fromJson(String(response.body()?.bytes()!!),SignInBean::class.java).success.equals("1")){
                    Toast.makeText(this@CommentActivity, "评论成功", Toast.LENGTH_SHORT).show()
                    finish()
                            }
                        }
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        }
                    })
                }
            }
        }else{
            var content = intent.getStringExtra("content")
            et_content.append(content)
            bt_upcomment.text = "修改"
            bt_upcomment.setOnClickListener {
                content = et_content.text.toString()
                if(!content.equals("")){
                    share.updatecomment(item_id,content).enqueue(object : Callback<ResponseBody>{
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if(Gson().fromJson(String(response.body()?.bytes()!!),SignInBean::class.java).success.equals("1")){
                                Toast.makeText(this@CommentActivity,"评论成功",Toast.LENGTH_SHORT).show()
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
}
