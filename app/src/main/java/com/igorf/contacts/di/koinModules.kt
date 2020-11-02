package com.igorf.contacts.di

import com.igorf.contacts.detail.ContactDetailViewModel
import com.igorf.contacts.list.ContactsListViewModel
import com.igorf.contacts.network.ContactsApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://processo-seletivo-mobile-api.usemobile.com.br/api/"

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ContactsApi> {
        get<Retrofit>().create(ContactsApi::class.java)
    }
}

val viewModelModule = module {
    viewModel {
        ContactsListViewModel(get())
    }

    viewModel {
        ContactDetailViewModel(get())
    }
}