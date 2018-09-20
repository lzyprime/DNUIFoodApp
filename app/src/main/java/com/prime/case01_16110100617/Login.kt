package com.prime.case01_16110100617

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bt_signIn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@Login,SignIn::class.java))
        })
        bt_login.setOnClickListener(View.OnClickListener {
            val usrname : String = eT_usrname.text.toString()
            val passwd : String = eT_passwd.text.toString()
            if(!usrname.equals("") && !passwd.equals(""))
                login(usrname,passwd)
            else
                Toast.makeText(this@Login,"用户名和密码不能为空",Toast.LENGTH_SHORT).show()
        })
    }

    fun login(usrname : String,passwd : String){
            val retrofit = Retrofit.Builder().baseUrl("http://172.24.10.175:8080/").build().create(RetrofitInterfaces::class.java).login(usrname,passwd)
        retrofit.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                data class Login(var userid : String)
                var s = Gson().fromJson(response.body().toString(),Login::class.java)
                if(s.userid.equals("0"))
                    Toast.makeText(this@Login,"登录失败",Toast.LENGTH_SHORT).show()
                else
                    startActivity(Intent(this@Login,MainActivity::class.java))
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable){
                Toast.makeText(this@Login,"网络连接出错",Toast.LENGTH_SHORT).show()
            }
        })

    }


}
