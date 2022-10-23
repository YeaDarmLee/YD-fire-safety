package com.directionfinding.beacon;

import java.util.List;

public interface MinewBeaconManagerListener {
    void onAppearBeacons(List<MinewBeacon> minewBeacons);

    void onDisappearBeacons(List<MinewBeacon> minewBeacons);

    void onRangeBeacons(List<MinewBeacon> minewBeacons);

    void onUpdateState(BluetoothState minewBeacons);
}
