package com.prime.dnuifood.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.prime.dnuifood.Beans.CommentBean
import com.prime.dnuifood.Fragments.CommentAlert
import com.prime.dnuifood.R
import com.prime.dnuifood.Server
import kotlinx.android.synthetic.main.comment_item.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class CommentListAdaper(val usr_id: String, var comments: List<CommentBean>, val food_id : String) :
    RecyclerView.Adapter<CommentListAdaper.ViewHolder>() {
    override fun getItemCount(): Int = comments.size
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(View.inflate(p0.context, R.layout.comment_item, null))

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val comment = comments[p1]
        with(p0.itemView) {
            tv_comment_foodname.text = comment.foodname
            tv_comment_usrname.text = comment.username
            tv_comment_time.text = comment.comment_time
            tv_comment_content.text = comment.content
            if (comment.user_id == usr_id) {
                tv_reset_comment.visibility = View.VISIBLE
                tv_reset_comment.setOnClickListener {
                    CommentAlert(context, comment.item_id, comment.content, false).create().show()
                    commentList()
                }
                tv_delete_comment.visibility = View.VISIBLE
                tv_delete_comment.setOnClickListener {
                    context.alert {
                        title = "删除评论"
                        positiveButton("确认") {
                            Server.deleteComment(comment.item_id)
                            commentList()
                        }
                        negativeButton("取消"){}
                    }.show()
                }
            }
        }
    }
    fun commentList() = doAsync {
        val commentlist = Server.getAllCommentsByFood(food_id)
        uiThread {
            comments = commentlist
            notifyDataSetChanged()
        }
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}