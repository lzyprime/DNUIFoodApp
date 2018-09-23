package com.prime.case01_16110100617.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import kotlinx.android.synthetic.main.fragment_start_login.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class StartLogin : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_start_login, container, false)
    }

    private var share : SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        share = this@StartLogin.activity?.getSharedPreferences("case01_16110100617",Context.MODE_PRIVATE)
        var usrname = share?.getString("usrname","")!!
        var passwd = share?.getString("passwd","")!!
        if(!usrname.equals("") && !passwd.equals(""))
            login(usrname,passwd)
        else
        {
            eT_usrname.text.append(usrname)
            eT_passwd.text.append(passwd)
        }
        bt_login.setOnClickListener {
            usrname = eT_usrname.text.toString()
            passwd = eT_passwd.text.toString()
            if(!usrname.equals("") && !passwd.equals(""))
                login(usrname,passwd)
            else
                Toast.makeText(this@StartLogin.context,"用户名密码不能为空",Toast.LENGTH_SHORT).show()
        }
    }

    fun login(usrname : String,passwd : String){
        Retrofit.Builder().baseUrl("http://172.24.10.175:8080/").build()
                .create<RetrofitInterfaces>(RetrofitInterfaces::class.java).login(usrname,passwd)
                .enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var s = Gson().fromJson(String(response.body()?.bytes()!!), LoginBean::class.java).userid
                if(s.equals("0"))
                    Toast.makeText(this@StartLogin.context,"用户名或密码错误", Toast.LENGTH_SHORT).show()
                else
                {
                   share?.edit()?.putString("usrname",usrname)?.putString("passwd",passwd)?.putString("usrid",s)
                           ?.commit()
                    startActivity(Intent(this@StartLogin.context, MainActivity::class.java))
                    this@StartLogin.activity?.finish()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable){
                Toast.makeText(this@StartLogin.context,"网络连接出错", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
