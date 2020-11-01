package com.igorf.contacts.network

import com.igorf.contacts.data.ContactDetailVo
import com.igorf.contacts.data.RequestDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactsApi {

    @GET("user")
    fun getUsersList() : Call<RequestDto>

    @GET("user/{id}")
    fun getUserDetail(@Path("id") id: Int): Call<ContactDetailVo>
}