package com.prime.case01_16110100617.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.prime.case01_16110100617.MainActivity

import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.RetrofitInterfaces
import com.prime.case01_16110100617.beans.LoginBean
import com.prime.case01_16110100617.beans.SignInBean
import kotlinx.android.synthetic.main.fragment_start_sign_in.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class StartSignIn : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_signin.setOnClickListener {
            var usrname = et_usrname.text.toString()
            var passwd = et_passwd.text.toString()
            var phone = et_phone.text.toString()
            var addr = et_address.text.toString()
            var comment = et_comment.text.toString()
            if(usrname.equals("")||passwd.equals("")||phone.equals("")||addr.equals("")||comment.equals(""))
                Toast.makeText(this@StartSignIn.context,"信息填写有误",Toast.LENGTH_SHORT).show()
            else
            {
                Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                        .create(RetrofitInterfaces::class.java).signIn(usrname,passwd,phone,addr,comment)
                        .enqueue(object : Callback<ResponseBody>{
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                var s = Gson().fromJson(String(response.body()?.bytes()!!),SignInBean::class.java)
                                if(s!=null && s.success.equals("1")) {
                                    Toast.makeText(this@StartSignIn.context,"注册成功",Toast.LENGTH_SHORT).show()
                                    activity?.getSharedPreferences("case01_16110100617",Context.MODE_PRIVATE)?.edit()
                                            ?.putString("addr",addr)?.putString("phone",phone)?.commit()
                                    login(usrname,passwd)
                                }
                                else
                                Toast.makeText(this@StartSignIn.context,"注册失败",Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Toast.makeText(this@StartSignIn.context,"网络错误",Toast.LENGTH_SHORT).show()
                            }
                        })
            }
        }
    }
    fun login(usrname : String,passwd : String){
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                .create<RetrofitInterfaces>(RetrofitInterfaces::class.java).login(usrname,passwd)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        var s = Gson().fromJson(String(response.body()?.bytes()!!), LoginBean::class.java).userid
                        if(s.equals("0"))
                            Toast.makeText(this@StartSignIn.context,"用户名或密码错误", Toast.LENGTH_SHORT).show()
                        else
                        {
                            var share = this@StartSignIn.activity?.getSharedPreferences("case01_16110100617", Context.MODE_PRIVATE)
                            share?.edit()?.putString("usrname",usrname)?.putString("passwd",passwd)?.putString("usrid",s)
                                    ?.commit()
                            startActivity(Intent(this@StartSignIn.context, MainActivity::class.java))
                            this@StartSignIn.activity?.finish()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable){
                        Toast.makeText(this@StartSignIn.context,"网络连接出错", Toast.LENGTH_SHORT).show()
                    }
                })

    }
}
