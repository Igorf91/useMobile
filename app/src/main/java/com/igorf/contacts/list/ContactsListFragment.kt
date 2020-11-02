package com.igorf.contacts.list

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.igorf.contacts.R
import com.igorf.contacts.data.ContactVo
import kotlinx.android.synthetic.main.fragment_contacts_list.contactsListRecyclerView
import kotlinx.android.synthetic.main.fragment_contacts_list.searchContactListEdit
import kotlinx.android.synthetic.main.fragment_contacts_list.voiceSearchIcon
import java.util.Locale

class ContactsListFragment : Fragment(R.layout.fragment_contacts_list) {

    private lateinit var contactsListAdapter: ContactsListAdapter
    private val listViewModel by lazy {
        ViewModelProvider(this, ContactsListViewModelFactory())
            .get(ContactsListViewModel::class.java)
    }

    companion object {
        private const val VOICE_REQUEST_CODE = 9874
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

        voiceSearchIcon.setOnClickListener {
            val voiceIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).also {
                it.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                it.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            }

            startActivityForResult(voiceIntent, VOICE_REQUEST_CODE)
        }
    }

    private fun setupListeners() {
        listViewModel.listContacts.observe(
            viewLifecycleOwner,
            {
                contactsListAdapter.loadContactList(it)
            }
        )

        searchContactListEdit.addTextChangedListener(listViewModel.listenerSearch())
    }

    private fun onItemClick(contactVo: ContactVo) {
        val detailFragment = ContactsListFragmentDirections.contactsListToContactDetail(contactVo)
        findNavController().navigate(detailFragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VOICE_REQUEST_CODE && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            searchContactListEdit.setText(result?.first())
        }
    }
}