package com.blkxltng.whatastore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blkxltng.whatastore.R
import com.blkxltng.whatastore.objects.Store
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_store.view.*

class StoreAdapter(private val storeList: List<Store>?, val clickListener: (Store?) -> Unit) : RecyclerView.Adapter<StoreAdapter.StoreHolder>() {


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class StoreHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(store: Store?, clickListener: (Store?) -> Unit) {
            view.listItemStore_nameTextView.text = store?.name
            view.listItemStore_addressTextView.text = store?.address
            view.listItemStore_stateTextView.text = "${store?.city}, ${store?.state} ${store?.zipCode}"
            view.listItemStore_phoneTextView.text = store?.phone
            Glide.with(view.listItemStore_imageView.context).load(store?.logoURL).into(view.listItemStore_imageView)

            view.setOnClickListener{clickListener(store)}
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_store, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return StoreHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: StoreHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        val store = storeList?.get(position)
//        holder.view.listItemStore_nameTextView.text = store?.name
//        holder.view.listItemStore_addressTextView.text = store?.address
//        holder.view.listItemStore_stateTextView.text = "${store?.city}, ${store?.state} ${store?.zipCode}"
//        holder.view.listItemStore_phoneTextView.text = store?.phone
//        Glide.with(holder.view.listItemStore_imageView.context).load(store?.logoURL).into(holder.view.listItemStore_imageView)

        (holder as StoreHolder).bind(storeList?.get(position), clickListener)

//        currentStore = store
//        holder.view.setOnClickListener(clickListener(store))
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = storeList!!.size
}
