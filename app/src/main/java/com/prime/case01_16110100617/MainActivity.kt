package com.prime.case01_16110100617

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_signIn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MainActivity,SignIn::class.java))
        })
        bt_login.setOnClickListener(View.OnClickListener {
            val usrname : String = eT_usrname.text.toString()
            val passwd : String = eT_passwd.text.toString()
            if(!usrname.equals("") && !passwd.equals(""))
                login(usrname,passwd)
            else
                Toast.makeText(this@MainActivity,"用户名和密码不能为空",Toast.LENGTH_SHORT).show()
        })
    }
    fun login(usrname : String,passwd : String){
        Toast.makeText(this@MainActivity,"login",Toast.LENGTH_SHORT).show()
    }
}
