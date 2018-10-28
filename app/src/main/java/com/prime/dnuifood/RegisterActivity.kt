package com.prime.dnuifood

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        bt_register.setOnClickListener {
            if (usrname != "" && passwd != "" && phone != "" && addr != "" && comment != "")
                register()
            else
                toast("信息不能为空")
        }
    }

    private fun register() = doAsync {
        val result = Server.register(usrname,passwd,phone,addr,comment)
        uiThread {
            if (result.success == "1") {
                shareEditor.putString("usrname",usrname).putString("passwd",passwd).commit()
                toast("注册成功").also { finish() }
            } else
                toast("注册失败")
        }
    }

    private val share get() = getSharedPreferences("DNUIFood", Activity.MODE_PRIVATE)
    private val shareEditor get() = share.edit()
    private val usrname get() = et_usrname.text.toString()
    private val passwd get() = et_passwd.text.toString()
    private val phone get() = et_phone.text.toString()
    private val addr get() = et_addr.text.toString()
    private val comment get() = et_comment.text.toString()
}
