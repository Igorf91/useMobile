package com.igorf.contacts.detail

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.igorf.contacts.R
import kotlinx.android.synthetic.main.fragment_contact_detail.detailAboutContactTextView
import kotlinx.android.synthetic.main.fragment_contact_detail.detailEmailTextView
import kotlinx.android.synthetic.main.fragment_contact_detail.detailFullNameTextView
import kotlinx.android.synthetic.main.fragment_contact_detail.detailGroup
import kotlinx.android.synthetic.main.fragment_contact_detail.detailImageView
import kotlinx.android.synthetic.main.fragment_contact_detail.loadingDetail
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactDetailFragment : Fragment(R.layout.fragment_contact_detail) {

    private val args: ContactDetailFragmentArgs by navArgs()
    private val detailViewModel by viewModel<ContactDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.init(args.contactVo)
        setupToolbar()
        setupListener()

        detailViewModel.fetchContactData()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity)
            .supportActionBar?.apply {
                title = detailViewModel.getToolbarTitle()
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
    }

    private fun setupListener() {
        detailViewModel.contactDetailLiveData.observe(
            viewLifecycleOwner,
            {
                loadingDetail.visibility = GONE
                detailGroup.visibility = VISIBLE

                Glide
                    .with(this)
                    .load(it.photo)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.circle_placeholder)
                    .into(detailImageView)

                detailFullNameTextView.text = it.name
                detailEmailTextView.text = it.email
                detailAboutContactTextView.text = it.about
            }
        )
    }
}