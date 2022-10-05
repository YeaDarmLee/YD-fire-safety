package com.floortracking

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.floortracking.databinding.ContentMainBinding
import com.floortracking.fragment.AppbarFragment
import com.floortracking.ui.base.BaseActivity
import com.floortracking.ui.main.MainFragment
import com.floortracking.ui.theme.FloorTrackingTheme

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            ComposeView(this).apply {
                setContent {
                    FloorTrackingTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        )
                        {
                            AndroidViewBinding(ContentMainBinding::inflate) {
                           //     appBarFragment()
                                initFragment()
                            }
                            /*ToolbarWithTitle(name = "Toolbar With Title")
                            Column {
                                CompositionLocalProvider(LocalContentColor provides Color.Red) {
                                    Greeting("Android")
                                }
                                Greeting("Android BaboBabo")
                            }*/


                        }

                    }
                }
            }
        )
    }

    private fun initFragment() {
        val fragment = MainFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }

    private fun appBarFragment() {
        val fragment = AppbarFragment()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FloorTrackingTheme {
        Greeting("Android")
    }
}

@Composable
fun ToolbarWithTitle(name: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = name) },
            )
        }
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun ToolbarWithTitlePreview() {
    FloorTrackingTheme {
        ToolbarWithTitle(name = "Toolbar With Title")
    }
}

