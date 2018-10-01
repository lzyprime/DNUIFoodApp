package com.prime.case01_16110100617

import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.*


interface RetrofitInterfaces {
    // url :http://172.24.10.175:8080/foodService/userLogin.do?username=lnn&userpass=11
    @GET("userLogin.do")
    fun login(@Query("username") username: String,
              @Query("userpass") userpass: String): Call<ResponseBody>

    // http://172.24.10.175:8080/foodService/userRegister.do?username=lnn&userpass=11&mobilenum=13476543211&address=大连&comment=老师
    @GET("userRegister.do")
    fun signIn(@Query("username") username: String,
               @Query("userpass") userpass: String,
               @Query("mobilenum") mobilenum: String,
               @Query("address") address: String,
               @Query("comment") comment: String): Call<ResponseBody>

    @GET("getAllShops.do")
    fun getAllShops(): Call<ResponseBody>

    @GET("isCollected.do")
    fun iscollected(@Query("user_id") user_id: String,
                    @Query("shop_food_id") shop_food_id: String,
                    @Query("flag") flag: String): Call<ResponseBody>

    @GET("userCollectShop.do")
    fun collectShop(@Query("user_id") userid: String,
                    @Query("shop_id") shop_id: String): Call<ResponseBody>

    @GET("userCollectFood.do")
    fun collectFood(@Query("user_id") user_id: String,
                    @Query("food_id") food_id: String): Call<ResponseBody>

    @GET("getFoodByShop.do")
    fun getfoodlist(@Query("shop_id") shop_id: String): Call<ResponseBody>

    @GET("getAllCommentsByFood.do")
    fun getfoodcomments(@Query("food_id") food_id: String): Call<ResponseBody>

    @GET("getAllUserCollection.do")
    fun getusrcollectionlist(@Query("user_id") user_id: String,
                             @Query("flag") flag: String): Call<ResponseBody>

    @GET("getFoodBySearch.do")
    fun search(@Query("search") search: String): Call<ResponseBody>

    @GET("updateUserById.do")
    fun updateusr(@Query("user_id") usr_id: String,
                  @Query("username") usrname: String,
                  @Query("userpass") passwd: String,
                  @Query("mobilenum") phone: String,
                  @Query("address") addr: String): Call<ResponseBody>

    @GET("addCart.do")
    fun addCart(@Query("user_id") usr_id: String,
                @Query("food_id") food_id: String,
                @Query("num") num: String): Call<ResponseBody>

    @GET("getAllOrdersByUser.do")
    fun getallorder(@Query("user_id") usr_id: String): Call<ResponseBody>

    @GET("getAllCommentsByUser.do")
    fun getallcomments(@Query("user_id") usr_id: String): Call<ResponseBody>

    @GET("insertOrder.do")
    fun insertOrder(@Query("user_id") usr_id: String,
                    @Query("food_id") food_id: String,
                    @Query("num") num: String,
                    @Query("sum") sum: Double,
                    @Query("suggesttime") suggesttime: String,
                    @Query("address") addr: String): Call<ResponseBody>

    @GET("getMyCartByUser.do")
    fun getcart(@Query("user_id") usr_id: String): Call<ResponseBody>

    @GET("getFoodById.do")
    fun getfoodbyid(@Query("food_id") food_id: String): Call<ResponseBody>

    @GET("deleteComment.do")
    fun deletecomment(@Query("item_id") item_id: String): Call<ResponseBody>

    @GET("insertComment.do")
    fun insetcomment(@Query("item_id") item_id: String,
                     @Query("content") content: String): Call<ResponseBody>

    @GET("updateComment.do")
        fun updatecomment(@Query("item_id") item_id: String,
                          @Query("content") content: String): Call<ResponseBody>

    @GET("insertOrder2.do")
        fun buyCart(@Query("user_id") usr_id : String,
                    @Query("address") addr : String,
                    @Query("sum") sum : String,
                    @Query("suggesttime") suggesttime: String,
                    @Query("items") items : String): Call<ResponseBody>

    @GET("deleteCartItem.do")
        fun deleteCart(@Query("item_id") item_id: String): Call<ResponseBody>
}