package com.igorf.contacts.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.igorf.contacts.R
import com.igorf.contacts.data.ContactVo
import kotlinx.android.synthetic.main.fragment_contacts_list.contactsListRecyclerView

class ContactsListFragment : Fragment(R.layout.fragment_contacts_list) {

    private lateinit var contactsListAdapter: ContactsListAdapter
    private val listViewModel by lazy {
        ViewModelProvider(this, ContactsListViewModelFactory())
            .get(ContactsListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupView()
        setupListeners()

        listViewModel.fetchListContacts()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity)
            .supportActionBar?.apply {
                setTitle(R.string.toolbar_contacts_list_title)
                setDisplayHomeAsUpEnabled(false)
                setDisplayShowHomeEnabled(false)
            }
    }

    private fun setupView() {
        contactsListAdapter = ContactsListAdapter(::onItemClick)

        contactsListRecyclerView.adapter = contactsListAdapter
    }

    private fun setupListeners() {
        listViewModel.listContacts.observe(
            viewLifecycleOwner,
            {
                contactsListAdapter.loadContactList(it)
            }
        )
    }

    private fun onItemClick(contactVo: ContactVo) {
        val detailFragment = ContactsListFragmentDirections.contactsListToContactDetail(contactVo)
        findNavController().navigate(detailFragment)
    }
}