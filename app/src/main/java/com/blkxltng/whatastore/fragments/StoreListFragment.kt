package com.blkxltng.whatastore.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blkxltng.whatastore.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_store_list, container, false)

        val isConnected = StoreDownloader.verifyAvailableNetwork(activity)
        val cacheAvailable = StoreDownloader.checkExists("${context?.cacheDir}/StoresKey")
        Log.d("onCreateView", "Connection is $isConnected, Cache is $cacheAvailable")

        if (isConnected || cacheAvailable) {
            loadStores(view)
        } else {
            //Tell the user to connect to the internet
            Toast.makeText(context, "Stores could not be loaded. Please connect to the internet", Toast.LENGTH_LONG).show()
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_store_list_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_refresh ->  {
                refreshStores()
                return true}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun storeClicked(store: Store?) {

        val storeDetailFragment = StoreDetailFragment.newInstance(store)
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.setCustomAnimations(R.anim.slide_out_down, R.anim.slide_in_down, R.anim.slide_in_up, R.anim.slide_out_up)
        fragmentTransaction?.hide(this)
        fragmentTransaction?.add(R.id.fragment_container, storeDetailFragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()

    }

    private fun loadStores(mainView: View) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = persistence.getStore()

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

    private fun refreshStores() {
        val isConnected = StoreDownloader.verifyAvailableNetwork(activity)
        val cacheAvailable = StoreDownloader.checkExists("${context?.cacheDir}/StoresKey")
        Log.d("refreshStores", "Connection is $isConnected, Cache is $cacheAvailable")
        if(isConnected && cacheAvailable) {
            GlobalScope.launch {
                val data = persistence.getStore()

                activity?.runOnUiThread {
                    viewAdapter = StoreAdapter(data.stores) { store: Store? -> storeClicked(store) }
                    recyclerView.adapter = viewAdapter
                    Toast.makeText(context, "Stores refreshed", Toast.LENGTH_SHORT).show()
                }
            }
        } else if(isConnected && !cacheAvailable) {
            Toast.makeText(context, "Stores could not be refreshed. Please close the app and try again.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Stores could not be refreshed. Please connect to the Internet.", Toast.LENGTH_LONG).show()
        }
    }
}