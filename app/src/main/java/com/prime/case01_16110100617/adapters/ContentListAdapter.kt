package com.prime.case01_16110100617.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.prime.case01_16110100617.CommentActivity
import com.prime.case01_16110100617.R
import com.prime.case01_16110100617.RetrofitInterfaces
import com.prime.case01_16110100617.beans.ContentBean
import com.prime.case01_16110100617.beans.SignInBean
import kotlinx.android.synthetic.main.content_item.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ContentListAdapter(var contentlist : List<ContentBean>,var usr_id : String) : RecyclerView.Adapter<ContentListAdapter.ViewHolder>(){

    override fun getItemCount(): Int = contentlist.size
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContentListAdapter.ViewHolder =
           ViewHolder(View.inflate(p0.context, R.layout.content_item,null))


    override fun onBindViewHolder(holder: ContentListAdapter.ViewHolder, positon: Int) {
        var show = contentlist[positon]
        with(holder.itemView){
            tv_usrname.text = show.user_id
            tv_date.text = show.comment_time
            tv_content.text = show.content
            tv_foodname.text = show.foodname
            if(usr_id.equals(show.user_id)) {
                bt_deletecomment.visibility = View.VISIBLE
                bt_deletecomment.setOnClickListener {
                    Retrofit.Builder().baseUrl("http://172.24.10.175:8080/foodService/").build()
                            .create(RetrofitInterfaces::class.java).deletecomment(show.item_id).enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                    if(Gson().fromJson(String(response.body()?.bytes()!!), SignInBean::class.java).success.equals("1")){
                                        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show()
                                        holder.itemView.visibility = View.INVISIBLE
                                    }
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                                }
                            })
                }
                bt_resetcomment.visibility = View.VISIBLE
                bt_resetcomment.setOnClickListener {context.startActivity(Intent(context,CommentActivity::class.java)
                        .putExtra("mod","reset").putExtra("item_id",show.item_id).putExtra("content",show.content))}
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}