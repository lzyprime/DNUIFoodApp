package com.prime.dnuifood

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_usrname.setText(share.getString("usrname", ""))
        et_passwd.setText(share.getString("passwd", ""))

        bt_login.onClick {
            if (usrname == "" || passwd == "")
                toast("用户名密码不能为空")
            else
                login()
        }
        tv_toregister.onClick { startActivity<RegisterActivity>() }
    }

    private fun login() = doAsync {
        val result = Server.login(usrname, passwd)
        uiThread {
            if (result.userid == "0")
                toast("登录失败")
            else {
                shareEditor.putString("usr_id", result.userid).commit()
                startActivity<MainActivity>()
                finish()
            }
        }
    }

    private val usrname get() = et_usrname.text.toString()
    private val passwd get() = et_passwd.text.toString()
    private val share get() = getSharedPreferences("DNUIFood", Activity.MODE_PRIVATE)
    private val shareEditor get() = share.edit()

}
