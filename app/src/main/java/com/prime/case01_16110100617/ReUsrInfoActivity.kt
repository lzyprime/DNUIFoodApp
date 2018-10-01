package com.prime.case01_16110100617

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.prime.case01_16110100617.beans.SignInBean
import kotlinx.android.synthetic.main.activity_re_usr_info.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ReUsrInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_usr_info)

        var share = getSharedPreferences("case01_16110100617",Context.MODE_PRIVATE)
        et_usrname.append(share.getString("usrname",""))
        et_passwd.append(share.getString("passwd",""))
        et_phone.append(share.getString("phone",""))
        et_addr.append(share.getString("addr",""))

        bt_commit.setOnClickListener {
            var usrid = share.getString("usrid","")
          var  usrname = et_usrname.text.toString()
          var  passwd = et_passwd.text.toString()
          var  phone = et_phone.text.toString()
          var  addr = et_addr.text.toString()
            Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                    .create(RetrofitInterfaces::class.java).updateusr(usrid,usrname,passwd,phone,addr)
                    .enqueue(object : Callback<ResponseBody>{
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if(Gson().fromJson(String(response.body()?.bytes()!!),SignInBean::class.java).success.equals("1"))
                            {
                                share.edit().putString("usrname",usrname).putString("passwd",passwd).putString("phone",phone).putString("addr",addr)
                                        .commit()
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        }
                    })

        }

    }
}
