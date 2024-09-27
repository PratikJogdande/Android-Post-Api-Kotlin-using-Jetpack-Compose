package com.androidpostapikotlinjetpackcompose

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAPI {
    @POST("users")
    fun  // on below line we are creating a method to post our data.
            postData(@Body dataModel: DataModel?): Call<DataModel?>?
}