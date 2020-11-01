package com.igorf.contacts.network

import com.igorf.contacts.data.ContactDetailVo
import com.igorf.contacts.data.ContactListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactsApi {

    @GET("user")
    fun getUsersList() : Call<ContactListDto>

    @GET("user/{id}")
    fun getUserDetail(@Path("id") id: Int): Call<ContactDetailVo>
}