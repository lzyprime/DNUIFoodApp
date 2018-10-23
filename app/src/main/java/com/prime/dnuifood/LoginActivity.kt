package com.prime.dnuifood

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.prime.dnuifood.Beans.LoginBean
import com.prime.dnuifood.Retrofits.Retrofits
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_usrname.setText(share.getString("usrname", ""))
        et_passwd.setText(share.getString("passwd", ""))
        bt_login.setOnClickListener {
            if (usrname == "" || passwd == "")
                show("用户名密码不能为空")
            else
                Retrofits.doLogin(usrname, passwd, callback)
        }

        tv_toregister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

     private val usrname get() = et_usrname.text.toString()
     private val passwd get() = et_passwd.text.toString()

     private val share get() = getSharedPreferences("DNUIFood",Activity.MODE_PRIVATE)
     private val shareEditor get() = share.edit()

    private var callback = object : Callback<LoginBean> {

        override fun onResponse(call: Call<LoginBean>, response: Response<LoginBean>) {
            if (response.isSuccessful && response.body()!!.userid != "0") {
                shareEditor.putString("usr_id",response.body()!!.userid).putString("usrname",usrname).putString("passwd",passwd).commit()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            else
                show("登录失败")
        }

        override fun onFailure(call: Call<LoginBean>, t: Throwable) {
            show("网络出错")
        }
    }

    fun show(text: String = "") {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}
