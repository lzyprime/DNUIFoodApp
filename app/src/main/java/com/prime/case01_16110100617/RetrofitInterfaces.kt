package com.prime.case01_16110100617

import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.*



interface RetrofitInterfaces {
    // url :http://172.24.10.175:8080/foodService/userLogin.do?username=lnn&userpass=11
    @GET("userLogin.do")
        fun login(@Query("username") username : String,
                  @Query("userpass") userpass : String) : Call<ResponseBody>

  // http://172.24.10.175:8080/foodService/userRegister.do?username=lnn&userpass=11&mobilenum=13476543211&address=大连&comment=老师
    @GET("userRegister.do")
        fun signIn(@Query("username") username: String,
                   @Query("userpass") userpass: String,
                   @Query("mobilenum") mobilenum: String,
                   @Query("address") address: String,
                   @Query("comment") comment : String) : Call<ResponseBody>

    @GET("getAllShops.do")
        fun getAllShops() : Call<ResponseBody>

    @GET("isCollected.do")
        fun iscollected(@Query("user_id") user_id : String,
                        @Query("shop_food_id") shop_food_id : String,
                        @Query("flag") flag : String) : Call<ResponseBody>

    @GET("userCollectShop.do")
        fun collectShop(@Query("user_id") userid : String,
                        @Query("shop_id") shop_id : String) : Call<ResponseBody>

    @GET("userCollectFood.do")
        fun collectFood(@Query("user_id") user_id : String,
                        @Query("food_id") food_id : String) : Call<ResponseBody>

    @GET("getFoodByShop.do")
        fun getfoodlist(@Query("shop_id") shop_id: String) : Call<ResponseBody>

    @GET("getFoodById.do")
        fun getfoodinfo(@Query("food_id") food_id : String) :Call<ResponseBody>

    @GET("getAllUserFoodOrder.do")
        fun getcontents(@Query("food_id") food_id : String) : Call<ResponseBody>

    @GET("getAllUserCollection.do")
        fun getusrcollectionlist(@Query("user_id") user_id: String,
                                 @Query("flag") flag: String) : Call<ResponseBody>

    @GET("getFoodBySearch.do")
        fun search(@Query("search") search : String) : Call<ResponseBody>

}