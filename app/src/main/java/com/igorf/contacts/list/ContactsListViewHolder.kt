package com.igorf.contacts.list

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.igorf.contacts.R
import com.igorf.contacts.data.ContactVo
import kotlinx.android.synthetic.main.contact_list_item.view.checkedImageView
import kotlinx.android.synthetic.main.contact_list_item.view.itemEmailTextView
import kotlinx.android.synthetic.main.contact_list_item.view.itemFullNameTextView
import kotlinx.android.synthetic.main.contact_list_item.view.itemImageView

class ContactsListViewHolder(itemView: View, private val listener: (ContactVo) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    private val itemImageView = itemView.itemImageView
    private val fullNameText = itemView.itemFullNameTextView
    private val emailText = itemView.itemEmailTextView
    private val isChecked = itemView.checkedImageView

    fun bindView(contactVo: ContactVo) {
        Glide
            .with(itemView)
            .load(contactVo.photo)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.circle_placeholder)
            .into(itemImageView)

        fullNameText.text = contactVo.name
        emailText.text = contactVo.email

        val visibility = if(contactVo.isVerified) VISIBLE else GONE
        isChecked.visibility = visibility

        itemView.setOnClickListener {
            listener.invoke(contactVo)
        }
    }
}