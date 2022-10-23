package com.directionfinding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.directionfinding.base.BaseActivity
import com.directionfinding.databinding.ContentMainBinding
import com.directionfinding.ui.main.MainFragment
import com.directionfinding.ui.theme.DirectionFindingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DirectionFindingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AndroidViewBinding(ContentMainBinding::inflate) {
                        initFragment()
                    }
                }
            }
        }
    }

    private fun initFragment() {
        val fragment = MainFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("bleble", "acti onActivityResult $requestCode, $resultCode")
    }
}