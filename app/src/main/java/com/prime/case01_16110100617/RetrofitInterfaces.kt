package com.prime.case01_16110100617

import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.*



interface RetrofitInterfaces {
    // url :http://172.24.10.175:8080/foodService/userLogin.do?username=lnn&userpass=11
    @GET("foodService/userLogin.do")
        fun login(@Query("username") username : String,
                  @Query("userpass") userpass : String) : Call<ResponseBody>

  // http://172.24.10.175:8080/foodService/userRegister.do?username=lnn&userpass=11&mobilenum=13476543211&address=大连&comment=老师
    @GET("foodService/userRegister.do")
        fun signIn(@Query("username") username: String,
                   @Query("userpass") userpass: String,
                   @Query("mobilenum") mobilenum: String,
                   @Query("address") address: String,
                   @Query("comment") comment : String) : Call<ResponseBody>

}