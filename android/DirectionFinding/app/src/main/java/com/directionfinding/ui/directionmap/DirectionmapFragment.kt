package com.directionfinding.ui.directionmap

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.directionfinding.R
import com.directionfinding.base.BaseFragment
import com.directionfinding.beacon.*
import com.directionfinding.ui.theme.DirectionFindingTheme
import com.directionfinding.util.AppPreferences
import com.directionfinding.util.MathUtils
import com.directionfinding.viewmodel.DirectionMapViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.pow

@AndroidEntryPoint
class DirectionmapFragment  : BaseFragment(){
    companion object {
        private const val REQUEST_ACCESS_FINE_LOCATION = 1000
    }

    @Inject
    lateinit var appPreferences: AppPreferences

    private val viewModel: DirectionMapViewModel by viewModels()
    private lateinit var mMinewBeaconManager: MinewBeaconManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(inflater.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                CompositionLocalProvider(

                ) {
                    DirectionFindingTheme() {

                    }
                    DirectionFindingTheme {
                        DirectionmapUI(distance = viewModel.distance.value)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  Log.d("sqrt", "${10.0.pow(0.65)}")
        initBeaconManager()
        checkBluetooth()
        checkLocationPermission()
        initListener()
    }

    private fun startScan() {
        mMinewBeaconManager.startScan()
    }

    private fun finishFragment() {
        requireActivity().supportFragmentManager.popBackStack()
    }
    private fun initBeaconManager() {
        mMinewBeaconManager = MinewBeaconManager.getInstance(requireActivity())
    }

    private fun initListener() {
        mMinewBeaconManager.setDeviceManagerDelegateListener(object : MinewBeaconManagerListener {
            override fun onAppearBeacons(minewBeacons: MutableList<MinewBeacon>?) {

            }

            override fun onDisappearBeacons(minewBeacons: MutableList<MinewBeacon>?) {

            }

            override fun onRangeBeacons(minewBeacons: MutableList<MinewBeacon>?) {
                Log.d("beacons", "onRangeBeacons")
                minewBeacons?.forEach {
                    val name = it.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Name).stringValue

                    if (name == "MBeacon") {
                        val rssi = it.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_RSSI).intValue
                       // Log.d("rssi","$rssi")
                        viewModel.distance.value =  MathUtils.calRSSIDistance(rssi = rssi)
                    }
                    Log.d("beacons","${it.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Name).stringValue}")
                }
            }

            override fun onUpdateState(minewBeacons: BluetoothState?) {

            }

        })
    }
    /**
     * check Bluetooth state
     */
    private fun checkBluetooth() {
        val bluetoothState: BluetoothState = mMinewBeaconManager.checkBluetoothState()
        when (bluetoothState) {
            BluetoothState.BluetoothStateNotSupported -> {
                Toast.makeText(requireContext(), "Not Support BLE", Toast.LENGTH_SHORT).show()

            }
            BluetoothState.BluetoothStatePowerOff -> {
                Log.d("bleble" ,"BluetoothStatePowerOff")
            }//showBLEDialog()
            BluetoothState.BluetoothStatePowerOn -> {
                Log.d("bleble" ,"BluetoothStatePowerOn")
            }
        }
    }

    private fun checkLocationPermission(resultSetting: Boolean = false) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (resultSetting) {
                    closeFragment()
                    return
                }
                val reqCount = appPreferences.blPermissionReqCount
                if (reqCount >= 2) {
                    showBluetoothPopup()
                    return
                }
                appPreferences.blPermissionReqCount = reqCount + 1
                val array = arrayOf(Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                requestMultiplePermissions.launch(array)
            } else {
                startScan()
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val permissionCheck: Int =
                ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                Log.d("bleble" ,"checkLocationPermission - 0")
                // 권한 없음
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION
                )
            } else {
                Log.d("bleble" ,"checkLocationPermission - 1")
                startScan()
                // ACCESS_FINE_LOCATION 에 대한 권한이 이미 있음.
            }
        } else {
            startScan()
        }
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                if (!it.value) {
                    closeFragment()
                    return@registerForActivityResult
                }
            }
            startScan()
        }

    private val requestBluetoothSetting = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        checkLocationPermission(true)
    }

    private fun showBluetoothPopup() {
        val builder = AlertDialog.Builder(requireContext()).run {
            setMessage(R.string.bluetooth_permission_guide)
            setPositiveButton(R.string.confirmation) { p0, p1 ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireContext().packageName, null)
                intent.data = uri
                requestBluetoothSetting.launch(intent)
                //startActivity(intent)
            }
            setNegativeButton(R.string.cancel) { p0, p1 ->
                closeFragment()
            }
            show()
        }
    }

    private fun closeFragment() {
        requireActivity().supportFragmentManager.popBackStack()
    }
}