package com.directionfinding.base

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // requireActivity().onBackPressedDispatcher.addCallback(this) {
        //        activity?.onBackPressed()
        //  }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //            activity?.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}