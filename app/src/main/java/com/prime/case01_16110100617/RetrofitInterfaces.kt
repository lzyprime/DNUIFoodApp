package com.prime.case01_16110100617

import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterfaces {
    @GET("foodService/userLogin.do")
        fun login(@Query("username") username : String,
                  @Query("userpass") userpass : String) : Call<ResponseBody>

    @GET("foodService/userRegister.do")
        fun signIn(@Query("username") username: String) : Call<ResponseBody>
}