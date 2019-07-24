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
import com.blkxltng.whatastore.objects.StoresList
import kotlinx.android.synthetic.main.fragment_store_list.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class StoreListFragment : Fragment() {

    //    var storeDownloader: StoreDownloader
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val persistence by lazy { Repository(context!!.cacheDir) }
    lateinit var data: StoresList

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(com.blkxltng.whatastore.R.layout.fragment_store_list, container, false)


        val isConnected = StoreDownloader.verifyAvailableNetwork(activity)
        Toast.makeText(context, "Connection status is $isConnected", Toast.LENGTH_SHORT).show()
//        val cacheAvailable = true

//        GlobalScope.launch (Dispatchers.Main) {
//            val data = persistence.getStore()
//            Log.d("StoresListFragment", "Persistence data is ${data.stores[0].logoURL}")
//        }

//        if (isConnected) {
//            loadStores(view)
//        } else if (cacheAvailable) {
//
//        } else {
//            //Tell the user to connect to the internet
//            Toast.makeText(context, "Please connect to the internet", Toast.LENGTH_SHORT).show()
//        }

        loadStores(view)

//        GlobalScope.launch(Dispatchers.Main) {
//            val call: Call<StoresList> = StoreDownloader.storeApi.getAStore()
//
//            Log.d("StoresListFragment", "data is ${call.execute().body()?.stores?.get(0)?.name}")
//        }

//        GlobalScope.launch(Dispatchers.Main) {
//
//            val call = StoreDownloader.storeApi.getStores()
//            val result = call.execute().body()
//
//            Log.d("StoreListFragment", "Data is ${result?.stores?.get(0)?.name}")
//        }

//        fun storeCicked(store : Store) {
//            Toast.makeText(context, "Clicked: ${store.name}", Toast.LENGTH_LONG).show()
//        }

//        var storesList: StoresList? = null
//        val context = this.context
//        val call = StoreDownloader.storeApi.getStores()
//        call.enqueue(object : Callback<StoresList> {
//            override fun onResponse(call: Call<StoresList>, response: Response<StoresList>) {
//
//                if (response.isSuccessful) {
//                    storesList = response.body()
//                    Log.d("test", "size is ${storesList?.stores?.size}")
//
//
//                    viewManager = LinearLayoutManager(context)
//                    viewAdapter = StoreAdapter(storesList?.stores, { store : Store? -> storeClicked(store) })
//
//                    recyclerView = view.fragmentStoreList_recyclerview.apply {
//                        // use this setting to improve performance if you know that changes
//                        // in content do not change the layout size of the RecyclerView
//                        setHasFixedSize(true)
//
//                        // use a linear layout manager
//                        layoutManager = viewManager
//
//                        // specify an viewAdapter (see also next example)
//                        adapter = viewAdapter
//
//                    }
//                } else {
//                    Log.d("StoresListFragment", "Data error")
//                }
//            }
//
//            override fun onFailure(call: Call<StoresList>, t: Throwable?) {
//                Log.d("StoresListFragment", "Data error 2")
//            }
//        })

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
//        var storesList: StoresList?
//        val context = this.context
//        val call = StoreDownloader.storeApi.getStores()
//        mainView.fragmentStoreList_noStoresTextView.visibility = View.INVISIBLE
//        call.enqueue(object : Callback<StoresList> {
//            override fun onResponse(call: Call<StoresList>, response: Response<StoresList>) {
//
//                if (response.isSuccessful) {
//                    storesList = response.body()
//                    Log.d("test", "size is ${storesList?.stores?.size}")
//
//                    viewManager = LinearLayoutManager(context)
//                    //Setup the RecyclerView Adapter and the item click listeners
//                    viewAdapter = StoreAdapter(storesList?.stores) { store : Store? -> storeClicked(store) }
//
//                    recyclerView = mainView.fragmentStoreList_recyclerview.apply {
//                        // use this setting to improve performance if you know that changes
//                        // in content do not change the layout size of the RecyclerView
//                        setHasFixedSize(true)
//
//                        // use a linear layout manager
//                        layoutManager = viewManager
//
//                        // specify an viewAdapter
//                        adapter = viewAdapter
//
//                    }
//                } else {
////                    Log.d("StoresListFragment", "Data error")
//                    Toast.makeText(context, "Error downloading stores :(", Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onFailure(call: Call<StoresList>, t: Throwable?) {
////                Log.d("StoresListFragment", "Data error 2")
//                Toast.makeText(context, "Failed to download stores :(", Toast.LENGTH_LONG).show()
//            }
//        })


        GlobalScope.launch(Dispatchers.Main) {
            data = persistence.getStore()
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