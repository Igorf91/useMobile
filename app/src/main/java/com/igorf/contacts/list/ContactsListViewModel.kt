package com.igorf.contacts.list

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igorf.contacts.data.ContactVo
import com.igorf.contacts.data.RequestDto
import com.igorf.contacts.network.ContactsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactsListViewModel(private val api: ContactsApi) : ViewModel() {

    companion object {
        private const val TAG = "ContactsListViewModel"
    }

    private val mutableListContacts = mutableListOf<ContactVo>()
    private val _listContacts = MutableLiveData<List<ContactVo>>()
    val listContacts: LiveData<List<ContactVo>> = _listContacts

    fun fetchListContacts() {
        api.getUsersList().enqueue(object : Callback<RequestDto> {
            override fun onResponse(
                call: Call<RequestDto>,
                response: Response<RequestDto>
            ) {
                response.body()?.let {
                    mutableListContacts.addAll(it.result)
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

    private fun filter(search: String) {
        _listContacts.postValue(
            mutableListContacts.filter { it.name.contains(search, true) }
        )
    }

    fun listenerSearch() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // empty
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // empty
        }
        override fun afterTextChanged(s: Editable?) {
            filter(s.toString())
        }
    }
}