package com.blkxltng.whatastore.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blkxltng.whatastore.adapters.StoreAdapter
import com.blkxltng.whatastore.cache.Repository
import com.blkxltng.whatastore.network.StoreDownloader
import com.blkxltng.whatastore.objects.Store
import kotlinx.android.synthetic.main.fragment_store_list.view.*
import kotlinx.coroutines.*


class StoreListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val persistence by lazy { Repository(context!!.cacheDir) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(com.blkxltng.whatastore.R.layout.fragment_store_list, container, false)

        val isConnected = StoreDownloader.verifyAvailableNetwork(activity)
        val cacheAvailable = StoreDownloader.checkExists("${context?.cacheDir}/StoresKey")
        Toast.makeText(context, "Connection status is $isConnected", Toast.LENGTH_SHORT).show()
        Toast.makeText(context, "Cache exists is $cacheAvailable", Toast.LENGTH_SHORT).show()

        if (isConnected || cacheAvailable) {
            loadStores(view)
        } else {
            //Tell the user to connect to the internet
            Toast.makeText(context, "Please connect to the internet", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun storeClicked(store: Store?) {
        Toast.makeText(context, "You clicked ${store?.name}", Toast.LENGTH_SHORT).show()

        val storeDetailFragment = StoreDetailFragment.newInstance(store)
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
//        fragmentTransaction?.replace(com.blkxltng.whatastore.R.id.fragment_container, storeDetailFragment)
        fragmentTransaction?.hide(this)
        fragmentTransaction?.add(com.blkxltng.whatastore.R.id.fragment_container, storeDetailFragment)
        fragmentTransaction?.addToBackStack(null)
//        fragmentTransaction?.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
        fragmentTransaction?.commit()

    }

    private fun loadStores(mainView: View) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = persistence.getStore()
            Log.d("StoresListFragment", "Persistence data is ${data.stores[0].logoURL}")

            activity?.runOnUiThread {
                mainView.fragmentStoreList_noStoresTextView.visibility = View.INVISIBLE

                viewManager = LinearLayoutManager(context)
                //Setup the RecyclerView Adapter and the item click listeners
                viewAdapter = StoreAdapter(data.stores) { store: Store? -> storeClicked(store) }

                recyclerView = mainView.fragmentStoreList_recyclerview.apply {
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    setHasFixedSize(true)

                    // use a linear layout manager
                    layoutManager = viewManager

                    // specify an viewAdapter
                    adapter = viewAdapter
                }
            }
        }
    }
}