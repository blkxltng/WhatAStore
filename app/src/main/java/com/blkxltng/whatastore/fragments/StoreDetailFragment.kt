package com.blkxltng.whatastore.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.blkxltng.whatastore.Constants
import com.blkxltng.whatastore.R
import com.blkxltng.whatastore.objects.Store
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_store_detail.view.*

class StoreDetailFragment : Fragment() {

    private var currentStore: Store? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<Store>(Constants.STORE_TO_LOAD_KEY)?.let {
            currentStore = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_store_detail, container, false)

        view.fragmentStoreDetail_nameTextView.text = currentStore?.name
        view.fragmentStoreDetail_iDTextView.text = currentStore?.storeId
        view.fragmentStoreDetail_addressTextView.text = "${currentStore?.address}\n${currentStore?.city}, ${currentStore?.state} ${currentStore?.zipCode}"
        view.fragmentStoreDetail_phoneTextView.text = currentStore?.phone
        Glide.with(view.fragmentStoreDetail_imageView.context).load(currentStore?.logoURL).into(view.fragmentStoreDetail_imageView)

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