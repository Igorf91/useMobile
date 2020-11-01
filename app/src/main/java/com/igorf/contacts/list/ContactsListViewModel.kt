package com.igorf.contacts.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.igorf.contacts.data.RequestDto
import com.igorf.contacts.data.ContactVo
import com.igorf.contacts.network.ContactsApi
import com.igorf.contacts.util.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactsListViewModel(private val api: ContactsApi) : ViewModel() {

    companion object {
        private const val TAG = "ContactsListViewModel"
    }

    private val _listContacts = MutableLiveData<List<ContactVo>>()
    val listContacts: LiveData<List<ContactVo>> = _listContacts

    fun fetchListContacts() {
        api.getUsersList().enqueue(object : Callback<RequestDto> {
            override fun onResponse(
                call: Call<RequestDto>,
                response: Response<RequestDto>
            ) {
                response.body()?.let {
                    _listContacts.postValue(it.result)
                }
            }

            override fun onFailure(call: Call<RequestDto>, t: Throwable) {
                t.message?.let {
                    Log.e(TAG, it)
                }
            }
        })
    }
}

class ContactsListViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        ContactsListViewModel(RetrofitFactory().getRetrofit().create(ContactsApi::class.java)) as T
}