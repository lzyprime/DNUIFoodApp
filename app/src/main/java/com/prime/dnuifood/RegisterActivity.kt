package com.prime.dnuifood

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.prime.dnuifood.Beans.LoginBean
import com.prime.dnuifood.Beans.SuccessBean
import com.prime.dnuifood.Retrofits.Retrofits
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        bt_register.setOnClickListener {
            if (usrname != "" && passwd != "" && phone != "" && addr != "" && comment != "")
                Retrofits.doRegister(usrname, passwd, phone, addr, comment, callback)
        }
    }


    private val callback = object : Callback<SuccessBean> {
        override fun onResponse(call: Call<SuccessBean>, response: Response<SuccessBean>) {
            if (response.isSuccessful && response.body()!!.success == "1") {
                show("注册成功")
                shareEditor.putString("usrname",usrname).putString("passwd",passwd).commit()
                finish()
            }
            else
                show("注册失败")
        }

        override fun onFailure(call: Call<SuccessBean>, t: Throwable) {
            show("网络出错")
        }
    }

    private val share get() = getSharedPreferences("DNUIFood", Activity.MODE_PRIVATE)
    private val shareEditor get() = share.edit()
    private val usrname get() = et_usrname.text.toString()
    private val passwd get() = et_passwd.text.toString()
    private val phone get() = et_phone.text.toString()
    private val addr get() = et_addr.text.toString()
    private val comment get() = et_comment.text.toString()

    fun show(text: String = "") {
        Toast.makeText(this@RegisterActivity, text, Activity.MODE_PRIVATE).show()
    }
}
