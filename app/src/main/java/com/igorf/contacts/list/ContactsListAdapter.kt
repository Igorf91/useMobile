package com.igorf.contacts.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.igorf.contacts.R
import com.igorf.contacts.data.ContactVo

class ContactsListAdapter(private val listener: (ContactVo) -> Unit) :
    RecyclerView.Adapter<ContactsListViewHolder>() {

    private val contactsMutableList = mutableListOf<ContactVo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_list_item, parent, false)
        return ContactsListViewHolder(view, listener)
    }

    override fun onBindViewHolder(viewHolder: ContactsListViewHolder, position: Int) {
        viewHolder.bindView(
            contactsMutableList[position]
        )
    }

    override fun getItemCount() = contactsMutableList.size

    fun loadContactList(newItems: List<ContactVo>){
        contactsMutableList.addAll(newItems)
        notifyDataSetChanged()
    }
}