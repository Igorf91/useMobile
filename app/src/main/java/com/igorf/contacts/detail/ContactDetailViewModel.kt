package com.igorf.contacts.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.igorf.contacts.data.ContactDetailVo
import com.igorf.contacts.data.ContactVo
import com.igorf.contacts.network.ContactsApi
import com.igorf.contacts.util.RetrofitFactory
import retrofit2.Callback
import retrofit2.Response

class ContactDetailViewModel(
    private val contactVo: ContactVo,
    private val api: ContactsApi
) : ViewModel() {

    companion object {
        private const val TAG = "ContactDetailViewModel"
        private const val EMPTY_RESPONSE = "empty error result"
    }

    private val _contactDetailLiveData = MutableLiveData<ContactDetailVo>()
    val contactDetailLiveData: LiveData<ContactDetailVo> = _contactDetailLiveData

    fun fetchContactData() {
        api.getUserDetail(contactVo.id).enqueue(object : Callback<ContactDetailVo> {
            override fun onResponse(
                call: retrofit2.Call<ContactDetailVo>,
                response: Response<ContactDetailVo>
            ) {
                response.body()?.let {
                    _contactDetailLiveData.postValue(it)
                }
            }

            override fun onFailure(call: retrofit2.Call<ContactDetailVo>, t: Throwable) {
                Log.e(TAG, t.message ?: EMPTY_RESPONSE)
            }
        })
    }

    fun getToolbarTitle() = contactVo.name
}

class ContactDetailViewModelFactory(private val contactVo: ContactVo) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        ContactDetailViewModel(
            contactVo,
            RetrofitFactory().getRetrofit().create(ContactsApi::class.java)
        ) as T
}