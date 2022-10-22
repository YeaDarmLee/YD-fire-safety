package com.floortracking

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.content.ContextCompat
import com.floortracking.databinding.ContentMainBinding
import com.floortracking.fragment.AppbarFragment
import com.floortracking.ui.base.BaseActivity
import com.floortracking.ui.main.MainFragment
import com.floortracking.ui.registration.RegistrationFragment
import com.floortracking.ui.theme.FloorTrackingTheme
import com.floortracking.viewmodel.MainViewModel
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient       // 위치 요청 메소드 담고 있는 객체
    private lateinit var locationRequest: LocationRequest                               // 위치 요청할 때 넘겨주는 데이터에 관한 객체
    private lateinit var locationCallback: HomeLocationCallBack                         // 위치 확인되고 호출되는 객체

    private val GPS_REQUEST_CODE =
        1000                                                 // gps에 관한 권한 요청 코드(번호)
    private val GPS_REQUEST_INTERVAL =
        5000                                            // 위치를 갱신하는데 필요한 시간 <밀리초 기준>
    private val GPS_REQUEST_FASTEST_INTERVAL =
        5000                                     // 다른 앱에서 위치를 갱신했을 때 그 정보를 가져오는 시간 <밀리초 기준>

    private val mainViewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test", "onCreate()")
        initLocation()
        setContentView(
            ComposeView(this).apply {

                setContent {
                    CompositionLocalProvider(
                        //  LocalBackPressedDispatcher provides this@NavActivity.onBackPressedDispatcher
                    ) {
                        FloorTrackingTheme {
                            // A surface container using the 'background' color from the theme
                       //     Surface(
                       //         modifier = Modifier.fillMaxSize(),
                       //         color = MaterialTheme.colors.background
                       //     )
                     //       {
                                Log.d("test", "asfdsfafsasfd")
                                AndroidViewBinding(ContentMainBinding::inflate) {
                                    //     appBarFragment()
                                    Log.d("test", "initFragment")
                                    initFragment()
                                }
                       //     }

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

    private fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = HomeLocationCallBack()
        locationRequest = LocationRequest()
        locationRequest.priority =
            LocationRequest.PRIORITY_HIGH_ACCURACY               // 가장 정확한 위치를 요청한다,
        locationRequest.interval =
            GPS_REQUEST_INTERVAL.toLong()                        // 위치를 갱신하는데 필요한 시간 <밀리초 기준>
        locationRequest.fastestInterval = GPS_REQUEST_FASTEST_INTERVAL.toLong()

        // It's a run-time permission check
        // 거부됐으면 showPermissionInfoDialog(알림)메소드를 호출, 승인됐으면 addLocationListener(위치 요청)메소드를 호출
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission(cancel = {
                showPermissionInfoDialog()
            }, ok = {
                addLocationListener()
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission(cancel: () -> Unit, ok: () -> Unit) {
        // 앱에 GPS이용하는 권한이 없을 때
        // <앱을 처음 실행하거나, 사용자가 이전에 권한 허용을 안 했을 때 성립>
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {  // <PERMISSION_DENIED가 반환됨>
            // 이전에 사용자가 앱 권한을 허용하지 않았을 때 -> 왜 허용해야되는지 알람을 띄움
            // shouldShowRequestPermissionRationale메소드는 이전에 사용자가 앱 권한을 허용하지 않았을 때 ture를 반환함
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                cancel()
            } else {
                // 권한 요청 알림
                requestPermission()
            }
        } else {
            ok()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), GPS_REQUEST_CODE)
        }
    }

    // 사용자가 권한 요청<허용,비허용>한 후에 이 메소드가 호출됨
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            // (gps 사용에 대한 사용자의 요청)일 때
            GPS_REQUEST_CODE -> {
                // 요청이 허용일 때
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addLocationListener()
                    // 요청이 비허용일 때
                } else {
                    showPermissionInfoDialog()
                }
            }
        }
    }

    // 위치 요청 메소드
    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        // 위치 정보 요청
        // (정보 요청할 때 넘겨줄 데이터)에 관한 객체, 위치 갱신되면 호출되는 콜백, 특정 스레드 지정(별 일 없으니 null)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    // 사용자가 이전에 권한을 거부했을 때 호출된다.
    private fun showPermissionInfoDialog() {
        android.app.AlertDialog.Builder(this).run {
            setTitle(getString(R.string.confirmation)).setMessage(
                getString(
                    R.string.gps_request_permissions_alert
                )
            )
                .setPositiveButton(R.string.confirmation) { _, _ ->
                    // 권한 요청
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + this@MainActivity.packageName))
                    startActivity(intent)
                }.setNegativeButton(R.string.cancel) { _, _ ->
                    Toast.makeText(this@MainActivity, "권힌이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
                    this@MainActivity.finish()
                }.show()

        }
    }

    // 위치 정보를 찾고 나서 인스턴스화되는 클래스
    inner class HomeLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation
            location?.run {
                Log.d("myLocation", "$latitude, $longitude")
                mainViewModel.location.postValue(this)
            }
        }
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

