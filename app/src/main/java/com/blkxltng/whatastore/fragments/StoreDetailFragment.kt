package com.blkxltng.whatastore.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blkxltng.whatastore.Constants
import com.blkxltng.whatastore.R
import com.blkxltng.whatastore.objects.Store
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_store_detail.view.*
import kotlinx.android.synthetic.main.fragment_store_detail.view.fragmentStoreDetail_phoneTextView

class StoreDetailFragment : Fragment() {

    private var currentStore: Store? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<Store>(Constants.STORE_TO_LOAD_KEY).let {
            currentStore = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_store_detail, container, false)

        view.fragmentStoreDetail_nameTextView.text = currentStore?.name
        view.fragmentStoreDetail_iDTextView.text = currentStore?.storeId
//        view.fragmentStoreDetail_addressTextView.text = "${currentStore?.address}\n${currentStore?.city}, ${currentStore?.state} ${currentStore?.zipCode}"
        view.fragmentStoreDetail_addressTextView.text = getString(R.string.fragmentStoreDetail_addressTemplate,
            currentStore?.address, currentStore?.city, currentStore?.state, currentStore?.zipCode)
        view.fragmentStoreDetail_phoneTextView.text = currentStore?.phone
        Glide.with(view.fragmentStoreDetail_imageView.context).load(currentStore?.logoURL).into(view.fragmentStoreDetail_imageView)

        view.fragmentStoreDetail_phoneTextView.setOnClickListener { v ->
            val callIntent: Intent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:${currentStore?.phone}")
            callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(callIntent)
        }

        view.fragmentStoreDetail_addressTextView.setOnClickListener { v ->
//            val mapUri: Uri = Uri.parse("geo:0,0?q=${currentStore?.latitude},${currentStore?.longitude}(${currentStore?.name})")
            val mapUri: Uri = Uri.parse("geo:0,0?q=${getString(R.string.fragmentStoreDetail_addressTemplate,
                currentStore?.address, currentStore?.city, currentStore?.state, currentStore?.zipCode)})")
            val mapIntent: Intent = Intent(Intent.ACTION_VIEW, mapUri)
//            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(store: Store?) = StoreDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.STORE_TO_LOAD_KEY, store)
            }
        }
    }

}