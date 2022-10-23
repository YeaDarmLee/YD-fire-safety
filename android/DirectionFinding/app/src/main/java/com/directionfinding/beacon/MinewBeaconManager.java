//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.directionfinding.beacon;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.util.Log;

import com.directionfinding.beacon.parser.beaconParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@TargetApi(21)
public class MinewBeaconManager {
    private static MinewBeaconManager single;
    private static Context mContext;
    private MinewBeaconManagerListener mMinewBeaconManagerListener;
    private ArrayList<MinewBeacon> mInRangeMinewBeacons = new ArrayList();
    private ArrayList<MinewBeacon> mNewMinewBeaconList = new ArrayList();
    public static List<MinewBeacon> scannedBeacons;
    public static List<MinewBeacon> inRangeBeacons;
    private ScanService mService;
    private HashMap<Object, BluetoothAdapter.LeScanCallback> jellymap = new HashMap();
    private HashMap<Object, ScanCallback> lollimap = new HashMap();
    private ScanCallback mScanCallback;
    public BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        public void onLeScan(BluetoothDevice var1, int var2, byte[] var3) {
            try {
                JSONObject var4 = beaconParser.parse(var3);
                String var5 = var4.getString("serviceData");
                MinewBeaconManager.this.addDevice(var1, var2, var3);
            } catch (Exception var6) {
            }

        }
    };
    Handler appearHandler = new Handler();
    Runnable appearRunnable = new Runnable() {
        public void run() {
            if (MinewBeaconManager.this.mMinewBeaconManagerListener != null && MinewBeaconManager.this.mNewMinewBeaconList.size() > 0) {
                MinewBeaconManager.this.mMinewBeaconManagerListener.onAppearBeacons(MinewBeaconManager.this.mNewMinewBeaconList);
            }

            MinewBeaconManager.this.mNewMinewBeaconList.clear();
            MinewBeaconManager.this.appearHandler.postDelayed(MinewBeaconManager.this.appearRunnable, 3000L);
        }
    };
    Handler disappearHandler = new Handler();
    Runnable disappearRunnable = new Runnable() {
        public void run() {
            if (MinewBeaconManager.this.mMinewBeaconManagerListener != null) {
                long var1 = System.currentTimeMillis();
                ArrayList var3 = new ArrayList();
                Iterator var4 = MinewBeaconManager.this.mInRangeMinewBeacons.iterator();

                label32:
                while(true) {
                    Beacon var5;
                    long var6;
                    do {
                        if (!var4.hasNext()) {
                            if (var3.size() > 0) {
                                MinewBeaconManager.this.mMinewBeaconManagerListener.onDisappearBeacons(var3);
                            }
                            break label32;
                        }

                        var5 = (Beacon)var4.next();
                        var6 = var5.getTemp();
                    } while(var1 - var6 <= 10000L);

                    var3.add(var5);
                    var4.remove();

                    for(int var8 = 0; var8 < MinewBeaconManager.scannedBeacons.size(); ++var8) {
                        Beacon var9 = (Beacon)MinewBeaconManager.scannedBeacons.get(var8);
                        if (var9.getMac().equals(var5.getMac())) {
                            var9.setInRange(false);
                        }
                    }
                }
            }

            MinewBeaconManager.this.disappearHandler.postDelayed(MinewBeaconManager.this.disappearRunnable, 1000L);
        }
    };
    Handler rangeHandler = new Handler();
    Runnable rangeRunnable = new Runnable() {
        public void run() {
            if (MinewBeaconManager.this.mMinewBeaconManagerListener != null) {
                MinewBeaconManager.this.mMinewBeaconManagerListener.onRangeBeacons(MinewBeaconManager.this.mInRangeMinewBeacons);
            }

            MinewBeaconManager.inRangeBeacons = MinewBeaconManager.this.mInRangeMinewBeacons;
            MinewBeaconManager.this.rangeHandler.postDelayed(MinewBeaconManager.this.rangeRunnable, 1000L);
        }
    };
    ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName var1, IBinder var2) {
            ScanService.ScanBinder var3 = (ScanService.ScanBinder)var2;
            MinewBeaconManager.this.mService = var3.a();
        }

        public void onServiceDisconnected(ComponentName var1) {
        }
    };

    public MinewBeaconManager() {
    }

    public void setDeviceManagerDelegateListener(MinewBeaconManagerListener var1) {
        this.mMinewBeaconManagerListener = var1;
    }

    public MinewBeaconManagerListener getMinewBeaconManagerDelegateListener() {
        return this.mMinewBeaconManagerListener;
    }

    public static MinewBeaconManager getInstance(Context context) {
        if (single == null) {
            Class var1 = MinewBeaconManager.class;
            synchronized(MinewBeaconManager.class) {
                if (single == null) {
                    single = new MinewBeaconManager();
                    mContext = context;
                    scannedBeacons = new ArrayList();
                }
            }
        }

        return single;
    }

    public BluetoothState checkBluetoothState() {
        if (!mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            return BluetoothState.BluetoothStateNotSupported;
        } else if (mContext != null) {
            BluetoothManager var1 = (BluetoothManager)mContext.getSystemService(Context.BLUETOOTH_SERVICE);
            BluetoothAdapter var2 = var1.getAdapter();
            return var2.isEnabled() ? BluetoothState.BluetoothStatePowerOn : BluetoothState.BluetoothStatePowerOff;
        } else {
            return BluetoothState.BluetoothStatePowerOff;
        }
    }

    public void startScan() throws Exception {
        if (!this.isBLESuppotred()) {
            throw new Exception("BluetoothIsNotSupport");
        } else if (!this.isBluetoothEnabled()) {
            throw new Exception("BluetoothIsNotEnabled");
        } else {
            this.stopScan();
            BluetoothManager var1;
            BluetoothAdapter var2;
            if (VERSION.SDK_INT < 21) {
                var1 = (BluetoothManager)mContext.getSystemService(Context.BLUETOOTH_SERVICE);
                var2 = BluetoothAdapter.getDefaultAdapter();
                UUID[] var3 = new UUID[0];
                var2.startLeScan(this.mLeScanCallback);
            } else {
                var1 = (BluetoothManager)mContext.getSystemService(Context.BLUETOOTH_SERVICE);
                var2 = var1.getAdapter();
                BluetoothLeScanner var13 = var2.getBluetoothLeScanner();
                ArrayList var4 = new ArrayList();
                ScanFilter var5 = (new ScanFilter.Builder()).setServiceData(ParcelUuid.fromString("00001802-0000-1000-8000-00805f9b34fb"), (byte[])null).build();
                ScanFilter var6 = (new ScanFilter.Builder()).setServiceData(ParcelUuid.fromString("0000fff0-0000-1000-8000-00805f9b34fb"), (byte[])null).build();
                ScanFilter var7 = (new ScanFilter.Builder()).setServiceData(ParcelUuid.fromString("0000fff1-0000-1000-8000-00805f9b34fb"), (byte[])null).build();
                ScanFilter var8 = (new ScanFilter.Builder()).setServiceData(ParcelUuid.fromString("0000fda5-0000-1000-8000-00805f9b34fb"), (byte[])null).build();
                ScanFilter var9 = (new ScanFilter.Builder()).setServiceData(ParcelUuid.fromString("0000ab81-0000-1000-8000-00805f9b34fb"), (byte[])null).build();
                ScanFilter var10 = (new ScanFilter.Builder()).setServiceData(ParcelUuid.fromString("0000ffe1-0000-1000-8000-00805f9b34fb"), (byte[])null).build();
                ScanFilter var11 = (new ScanFilter.Builder()).setServiceData(ParcelUuid.fromString("0000ffd1-0000-1000-8000-00805f9b34fb"), (byte[])null).build();
                var4.add(var5);
                var4.add(var6);
                var4.add(var7);
                var4.add(var8);
                var4.add(var9);
                var4.add(var10);
                var4.add(var11);
                ScanSettings var12 = (new ScanSettings.Builder()).setScanMode(2).setReportDelay(0L).build();
                this.mScanCallback = new ScanCallback() {
                    public void onScanResult(int var1, ScanResult var2) {
                        MinewBeaconManager.this.addDevice(var2.getDevice(), var2.getRssi(), var2.getScanRecord().getBytes());
                    }

                    public void onBatchScanResults(List<ScanResult> var1) {
                        super.onBatchScanResults(var1);
                    }

                    public void onScanFailed(int var1) {
                        super.onScanFailed(var1);
                    }
                };
                var13.startScan(var4, var12, this.mScanCallback);
            }

            this.appearHandler.post(this.appearRunnable);
            this.disappearHandler.post(this.disappearRunnable);
            this.rangeHandler.post(this.rangeRunnable);
        }
    }

    public void stopScan() {
        BluetoothManager var1;
        BluetoothAdapter var2;
        if (VERSION.SDK_INT < 21) {
            var1 = (BluetoothManager)mContext.getSystemService(Context.BLUETOOTH_SERVICE);
            var2 = var1.getAdapter();
            if (this.mLeScanCallback != null) {
                var2.stopLeScan(this.mLeScanCallback);
            }
        } else {
            var1 = (BluetoothManager)mContext.getSystemService(Context.BLUETOOTH_SERVICE);
            var2 = var1.getAdapter();
            BluetoothLeScanner var3 = var2.getBluetoothLeScanner();
            if (this.mScanCallback != null) {
                var3.stopScan(this.mScanCallback);
            }
        }

        this.appearHandler.removeCallbacks(this.appearRunnable);
        this.disappearHandler.removeCallbacks(this.disappearRunnable);
        this.rangeHandler.removeCallbacks(this.rangeRunnable);
    }

    private boolean isBLESuppotred() {
        return mContext != null ? mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le") : false;
    }

    private boolean isBluetoothEnabled() {
        if (mContext != null) {
            BluetoothManager var1 = (BluetoothManager)mContext.getSystemService(Context.BLUETOOTH_SERVICE);
            BluetoothAdapter var2 = var1.getAdapter();
            return var2.isEnabled();
        } else {
            return false;
        }
    }

    private void addDevice(BluetoothDevice var1, int var2, byte[] var3) {
        boolean var4 = false;
        Iterator var5 = this.mInRangeMinewBeacons.iterator();

        String var9;
        String var11;
        while(var5.hasNext()) {
            MinewBeacon var6 = (MinewBeacon)var5.next();
            Beacon var7 = (Beacon)var6;
            JSONObject var8 = beaconParser.parse(var3);
            if (var7.getMac().equals(var1.getAddress())) {
                var7.setRSSI((float)var2);
                var9 = var1.getName();
                if (var9 == null || "".equals(var9)) {
                    var9 = "N/A";
                }

                var7.setName(var9);
                String var10 = null;

                try {
                    var10 = var8.getString("manufacturerData");
                } catch (JSONException var26) {
                }

                var11 = null;

                try {
                    var11 = var8.getString("serviceData");
                } catch (JSONException var25) {
                }

                if (var10 != null && var10.length() == 50) {
                    var7.setUUID(this.formatUUID(var10.substring(8, 40)));
                    var7.setMajor(Integer.parseInt(var10.substring(40, 44), 16) + "");
                    var7.setMinor(Integer.parseInt(var10.substring(44, 48), 16) + "");
                    if (var11 != null) {
                        var7.setBatteryLevel(this.getBattery(var11));
                    }
                }

                var7.setInRange(true);
                long var12 = System.currentTimeMillis();
                var7.setTemp(var12);
                String var14;
                if (var11 != null && (var11.startsWith("E1FF") || var11.startsWith("E2FF"))) {
                    var7.setBatteryLevel(this.getBattery(var11));
                    if (var11.length() == 34) {
                        Log.e("tag", var11);
                        var14 = var11.substring(26, 30);
                        byte[] var15 = beaconParser.parseStrToByte(var14);
                        byte[] var16 = new byte[]{var15[0], var15[1], 0, 0};
                        int var17 = beaconParser.parse(var16, 0);
                        String var18 = var11.substring(30, 34);
                        byte[] var19 = beaconParser.parseStrToByte(var18);
                        byte[] var20 = new byte[]{var19[0], var19[1], 0, 0};
                        int var21 = beaconParser.parse(var20, 0);
                        var7.setTemperature((float)var17 / 10.0F);
                        var7.setHumidity((float)var21 / 10.0F);
                        var7.setBatteryLevel(Integer.parseInt(var11.substring(14, 16), 16));
                    }
                }

                if (var11 != null && (var11.startsWith("A5FD") || var11.startsWith("81AB")) && var11.length() >= 18) {
                    var14 = var11.substring(14);
                }

                var4 = true;
                break;
            }
        }

        if (!var4) {
            JSONObject var27 = beaconParser.parse(var3);
            Beacon var28 = null;
            String var29 = null;

            try {
                var29 = var27.getString("manufacturerData");
            } catch (JSONException var24) {
            }

            String var30 = null;

            try {
                var30 = var27.getString("serviceData");
            } catch (JSONException var23) {
            }

            long var31;
            if (var29 != null && var29.length() == 50) {
                var28 = new Beacon();
                var28.setRSSI((float)var2);
                var31 = System.currentTimeMillis();
                var28.setTemp(var31);
                var28.setMac(var1.getAddress());
                var11 = var1.getName();
                if (var11 == null || "".equals(var11)) {
                    var11 = "N/A";
                }

                var28.setName(var11);
                var28.setInRange(true);
                var28.setUUID(this.formatUUID(var29.substring(8, 40)));
                var28.setMajor(Integer.parseInt(var29.substring(40, 44), 16) + "");
                var28.setMinor(Integer.parseInt(var29.substring(44, 48), 16) + "");
                if (var30 != null) {
                    var28.setBatteryLevel(this.getBattery(var30));
                }
            }

            if (var30 != null && (var30.startsWith("E1FF") || var30.startsWith("E2FF"))) {
                var28 = new Beacon();
                var28.setRSSI((float)var2);
                var31 = System.currentTimeMillis();
                var28.setTemp(var31);
                var28.setMac(var1.getAddress());
                var11 = var1.getName();
                if (var11 == null || "".equals(var11)) {
                    var11 = "N/A";
                }

                var28.setName(var11);
                var28.setInRange(true);
                var28.setBatteryLevel(this.getBattery(var30));
                if (var30.substring(4, 6).equals("A1") && Integer.parseInt(var30.substring(6, 8), 16) == 1) {
                    Log.e("tagservicedata", var30);
                    byte[] var34 = beaconParser.parseStrToByte(var30);
                    byte var35 = var34[5];
                    float var37 = (float)(var34[6] & 255) / 256.0F;
                    var28.setTemperature((float)var35 + var37);
                    byte var39 = var34[7];
                    float var41 = (float)(var34[8] & 255) / 256.0F;
                    var28.setHumidity((float)var39 + var41);
                } else if (var30.length() == 34) {
                    String var33 = var30.substring(26, 30);
                    byte[] var13 = beaconParser.parseStrToByte(var33);
                    byte[] var36 = new byte[]{var13[0], var13[1], 0, 0};
                    int var38 = beaconParser.parse(var36, 0);
                    String var40 = var30.substring(30, 34);
                    byte[] var42 = beaconParser.parseStrToByte(var40);
                    byte[] var43 = new byte[]{var42[0], var42[1], 0, 0};
                    int var44 = beaconParser.parse(var43, 0);
                    var28.setTemperature((float)var38 / 10.0F);
                    var28.setHumidity((float)var44 / 10.0F);
                    var28.setBatteryLevel(Integer.parseInt(var30.substring(14, 16), 16));
                }
            }

            if (var30 != null && (var30.startsWith("A5FD") || var30.startsWith("81AB")) && var30.length() >= 18) {
                var9 = var30.substring(14);
            }

            var9 = null;

            try {
                var9 = var27.getString("txPowerLevel");
            } catch (JSONException var22) {
            }

            int var32 = 0;
            if (var9 != null) {
                var32 = Integer.parseInt(var9, 16);
            }

            if (var28 != null) {
                var28.setTXPower(var32);
                this.mInRangeMinewBeacons.add(var28);
                this.mNewMinewBeaconList.add(var28);
                scannedBeacons.add(var28);
            }
        }

    }

    private String formatUUID(String var1) {
        return var1.length() < 32 ? var1 : var1.substring(0, 8) + '-' + var1.substring(8, 12) + '-' + var1.substring(12, 16) + '-' + var1.substring(16, 20) + '-' + var1.substring(20, 32);
    }

    private int getBattery(String var1) {
        return var1.length() >= 6 ? Integer.parseInt(var1.substring(4, 6), 16) : 0;
    }
}
