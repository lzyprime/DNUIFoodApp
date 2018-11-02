package com.prime.dnuifood.Fragments

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import com.prime.dnuifood.Beans.UsrBean
import com.prime.dnuifood.R
import com.prime.dnuifood.Server
import kotlinx.android.synthetic.main.activity_register.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class ResetUsrInfoAlert(context: Context, var usr: UsrBean) : AlertDialog.Builder(context) {
    init {
        setTitle("修改用户信息")
        val view = View.inflate(context, R.layout.activity_register,null)
        with(view){
            et_usrname.setText(usr.username)
            et_passwd.setText(usr.userpass)
            et_addr.setText(usr.userpass)
            et_phone.setText(usr.mobilenum)
            et_comment.visibility = View.INVISIBLE
            bt_register.visibility = View.INVISIBLE
        }
        setView(view)
        setPositiveButton("提交修改") {_,_ ->
            usr.username = view.et_usrname.text.toString()
            usr.address = view.et_addr.text.toString()
            usr.mobilenum = view.et_phone.text.toString()
            usr.userpass = view.et_passwd.text.toString()
            doAsync {
                var re = Server.updateUserById(usr.user_id,usr.username,usr.userpass,usr.mobilenum,usr.address)
                uiThread {
                    if (re.success == "1")
                        context.toast("修改成功")
                    else
                        context.toast("修改失败")
                }
            }
        }
        setNegativeButton("取消", null)
    }
}