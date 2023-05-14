package com.example.anotherurlshortener

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ShortenUrlService {
    @FormUrlEncoded
    @POST("shorten")
    fun shortenUrl(@Field("url") longUrl: String): Call<ShortenUrlResponse>
}
