package com.directionfinding.beacon;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

public class BluetoothChangedReceiver {
    public BluetoothChangedReceiver() {
    }

    public void onReceive(Context var1, Intent var2) {
        if (var2.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
            BluetoothAdapter var3 = BluetoothAdapter.getDefaultAdapter();
            MinewBeaconManagerListener var4;
            if (var3.isEnabled()) {
                var4 = MinewBeaconManager.getInstance(var1.getApplicationContext()).getMinewBeaconManagerDelegateListener();
                if (var4 != null) {
                    var4.onUpdateState(BluetoothState.BluetoothStatePowerOn);
                }
            } else {
                var4 = MinewBeaconManager.getInstance(var1.getApplicationContext()).getMinewBeaconManagerDelegateListener();
                if (var4 != null) {
                    var4.onUpdateState(BluetoothState.BluetoothStatePowerOff);
                }
            }
        }

    }
}
