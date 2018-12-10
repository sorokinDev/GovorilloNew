package com.vsquad.projects.govorillo.model.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface AnalizerApi {

    @FormUrlEncoded
    @POST("v1/recognize")
    fun getSubject(@Field("text") text: String): Call<ResponseBody>
}
