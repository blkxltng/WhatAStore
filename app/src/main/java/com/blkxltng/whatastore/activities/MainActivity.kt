package com.blkxltng.whatastore.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blkxltng.whatastore.R
import com.blkxltng.whatastore.fragments.StoreListFragment

class MainActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return StoreListFragment()
    }
}
