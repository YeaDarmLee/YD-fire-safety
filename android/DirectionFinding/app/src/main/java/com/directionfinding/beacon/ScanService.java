//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.directionfinding.beacon;

import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;

import androidx.annotation.Nullable;

import com.directionfinding.beacon.parser.beaconParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@TargetApi(21)
public class ScanService extends Service {
    private ArrayList<MinewBeacon> i = new ArrayList();
    private ArrayList<MinewBeacon> j = new ArrayList();
    private MinewBeaconManager k;
    private MinewBeaconManagerListener l;
    private static final UUID m = UUID.fromString("00001802-0000-1000-8000-00805f9b34fb");
    private static final UUID n = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    Handler a = new Handler();
    Runnable b = new Runnable() {
        public void run() {
            if (ScanService.this.l != null && ScanService.this.j.size() > 0) {
                ScanService.this.l.onAppearBeacons(ScanService.this.j);
            }

            ScanService.this.j.clear();
            ScanService.this.a.postDelayed(ScanService.this.b, 3000L);
        }
    };
    Handler c = new Handler();
    Runnable d = new Runnable() {
        public void run() {
            ScanService.this.c();
            if (ScanService.this.l != null) {
                long var1 = System.currentTimeMillis();
                ArrayList var3 = new ArrayList();
                Iterator var4 = ScanService.this.i.iterator();

                label32:
                while(true) {
                    Beacon var5;
                    long var6;
                    do {
                        if (!var4.hasNext()) {
                            if (var3.size() > 0) {
                                ScanService.this.l.onDisappearBeacons(var3);
                            }
                            break label32;
                        }

                        var5 = (Beacon)var4.next();
                        var6 = var5.getTemp();
                    } while(var1 - var6 <= 20000L);

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

            ScanService.this.c.postDelayed(ScanService.this.d, 1000L);
        }
    };
    Handler e = new Handler();
    Runnable f = new Runnable() {
        public void run() {
            if (ScanService.this.l != null) {
                ScanService.this.l.onRangeBeacons(ScanService.this.i);
            }

            MinewBeaconManager.inRangeBeacons = ScanService.this.i;
            ScanService.this.e.postDelayed(ScanService.this.f, 1000L);
        }
    };
    public BluetoothAdapter.LeScanCallback g = new BluetoothAdapter.LeScanCallback() {
        public void onLeScan(BluetoothDevice var1, int var2, byte[] var3) {
            try {
                JSONObject var4 = beaconParser.parse(var3);
                String var5 = var4.getString("serviceData");
                ScanService.this.a(var1, var2, var3);
            } catch (Exception var6) {
            }

        }
    };
    public ScanCallback h = new ScanCallback() {
        public void onScanResult(int var1, ScanResult var2) {
            try {
                JSONObject var3 = beaconParser.parse(var2.getScanRecord().getBytes());
                String var4 = var3.getString("serviceData");
                ScanService.this.a(var2.getDevice(), var2.getRssi(), var2.getScanRecord().getBytes());
            } catch (Exception var5) {
            }

        }

        public void onBatchScanResults(List<ScanResult> var1) {
            super.onBatchScanResults(var1);
        }

        public void onScanFailed(int var1) {
            super.onScanFailed(var1);
        }
    };

    public ScanService() {
    }

    public void onCreate() {
        super.onCreate();
        this.c();
        this.a();
        this.a.post(this.b);
        this.c.post(this.d);
        this.e.post(this.f);
    }

    private void c() {
        this.k = MinewBeaconManager.getInstance(this);
        this.l = this.k.getMinewBeaconManagerDelegateListener();
    }

    @TargetApi(21)
    public void a() {
        BluetoothManager var1;
        BluetoothAdapter var2;
        if (VERSION.SDK_INT < 21) {
            var1 = (BluetoothManager)this.getSystemService(Context.BLUETOOTH_SERVICE);
            var2 = BluetoothAdapter.getDefaultAdapter();
            UUID[] var3 = new UUID[0];
            var2.startLeScan(this.g);
        } else {
            var1 = (BluetoothManager)this.getSystemService(Context.BLUETOOTH_SERVICE);
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
            var13.startScan(var4, var12, this.h);
        }

    }

    @TargetApi(21)
    public void b() {
        BluetoothManager var1;
        BluetoothAdapter var2;
        if (VERSION.SDK_INT < 21) {
            var1 = (BluetoothManager)this.getSystemService(Context.BLUETOOTH_SERVICE);
            var2 = var1.getAdapter();
            if (this.g != null) {
                var2.stopLeScan(this.g);
            }
        } else {
            var1 = (BluetoothManager)this.getSystemService(Context.BLUETOOTH_SERVICE);
            var2 = var1.getAdapter();
            BluetoothLeScanner var3 = var2.getBluetoothLeScanner();
            if (this.h != null) {
                var3.stopScan(this.h);
            }
        }

    }

    public void onDestroy() {
        super.onDestroy();
        this.b();
        this.a.removeCallbacks(this.b);
        this.c.removeCallbacks(this.d);
        this.e.removeCallbacks(this.f);
    }

    @Nullable
    public IBinder onBind(Intent var1) {
        return new ScanBinder();
    }

    public boolean onUnbind(Intent var1) {
        return super.onUnbind(var1);
    }

    private void a(BluetoothDevice var1, int var2, byte[] var3) {
        boolean var4 = false;
        Iterator var5 = this.i.iterator();

        Beacon var7;
        String var13;
        while(var5.hasNext()) {
            MinewBeacon var6 = (MinewBeacon)var5.next();
            var7 = (Beacon)var6;
            String var8 = var7.getMajor();
            String var9 = var7.getMinor();
            JSONObject var10 = beaconParser.parse(var3);
            String var11 = null;
            String var12 = "0";
            var13 = "0";

            try {
                var11 = var10.getString("serviceData");
                var12 = this.a(var11);
                var13 = this.b(var11);
            } catch (JSONException var21) {
                var21.printStackTrace();
            }

            if (var8.equals(var12) && var9.equals(var13) && var7.getMac().equals(var1.getAddress())) {
                try {
                    var7.setRSSI((float)var2);
                    int[] var14 = new int[16];
                    StringBuffer var15 = new StringBuffer();

                    for(int var16 = 9; var16 < 25; ++var16) {
                        if (var3[var16] < 0) {
                            var14[var16 - 9] = Integer.valueOf(Integer.toBinaryString(var3[var16]).substring(24), 2);
                        } else {
                            var14[var16 - 9] = var3[var16];
                        }

                        String var17;
                        if (var14[var16 - 9] < 16) {
                            var17 = "0" + Integer.toHexString(var14[var16 - 9]);
                            var15.append(var17);
                        } else {
                            var17 = Integer.toHexString(var14[var16 - 9]);
                            var15.append(var17);
                        }
                    }

                    var7.setUUID(this.c(var15.toString().trim().toUpperCase()));
                    var7.setInRange(true);
                    long var34 = System.currentTimeMillis();
                    var7.setTemp(var34);
                    if (var11 != null && !"".equals(var11)) {
                        var7.setMajor(this.a(var11));
                        var7.setMinor(this.b(var11));
                        var7.setBatteryLevel(this.d(var11));
                    }

                    String var18 = var1.getName();
                    if (var18 == null || "".equals(var18)) {
                        var18 = "N/A";
                    }

                    if (var11 != null && !"".equals(var11) && (var11.startsWith("A5FD") || var11.startsWith("81AB")) && var11.length() >= 18) {
                        String var19 = var11.substring(14, 18);
                        var18 = var18 + var19;
                    }

                    var7.setName(var18);
                } catch (Exception var23) {
                    var23.printStackTrace();
                }

                var4 = true;
                break;
            }
        }

        if (!var4) {
            JSONObject var24 = new JSONObject();
            JSONObject var25 = beaconParser.parse(var3);
            beaconParser.setData(var24, "deviceAddress", var1.getAddress());
            beaconParser.setData(var24, "deviceName", var1.getName());
            beaconParser.setData(var24, "isConnected", "false");
            beaconParser.setData(var24, "RSSI", var2);
            beaconParser.setData(var24, "advertisementData", var25);
            beaconParser.setData(var24, "type", "BLE");
            var7 = new Beacon();
            var7.setRSSI((float)var2);
            long var26 = System.currentTimeMillis();
            var7.setTemp(var26);
            var7.setMac(var1.getAddress());
            var7.setInRange(true);

            try {
                String var27 = var25.getString("advData").substring(4, 6);
                int[] var28 = new int[16];
                StringBuffer var29 = new StringBuffer();

                String var31;
                for(int var30 = 9; var30 < 25; ++var30) {
                    if (var3[var30] < 0) {
                        var28[var30 - 9] = Integer.valueOf(Integer.toBinaryString(var3[var30]).substring(24), 2);
                    } else {
                        var28[var30 - 9] = var3[var30];
                    }

                    if (var28[var30 - 9] < 16) {
                        var31 = "0" + Integer.toHexString(var28[var30 - 9]);
                        var29.append(var31);
                    } else {
                        var31 = Integer.toHexString(var28[var30 - 9]);
                        var29.append(var31);
                    }
                }

                var7.setUUID(this.c(var29.toString().trim().toUpperCase()));
                var13 = var25.getString("serviceData");
                if (var13 != null && !"".equals(var13)) {
                    var7.setMajor(this.a(var13));
                    var7.setMinor(this.b(var13));
                    var7.setBatteryLevel(this.d(var13));
                }

                var31 = var1.getName();
                if (var31 == null || "".equals(var31)) {
                    var31 = "N/A";
                }

                if (var13 != null && !"".equals(var13) && (var13.startsWith("A5FD") || var13.startsWith("81AB")) && var13.length() >= 18) {
                    String var32 = var13.substring(14, 18);
                    var31 = var31 + var32;
                }

                var7.setName(var31);
                int var33 = 0;

                try {
                    String var35 = var25.getString("txPowerLevel");
                    var33 = Integer.parseInt(var35);
                } catch (NumberFormatException var20) {
                    var20.printStackTrace();
                }

                var7.setTXPower(var33);
            } catch (JSONException var22) {
                var22.printStackTrace();
            }

            this.i.add(var7);
            this.j.add(var7);
            MinewBeaconManager.scannedBeacons.add(var7);
        }

    }

    private String a(String var1) {
        if (var1.length() >= 16) {
            return var1.substring(8, 12);
        } else {
            return var1.length() >= 14 ? var1.substring(6, 10) : "0";
        }
    }

    private String b(String var1) {
        if (var1.length() >= 16) {
            return var1.substring(12, 16);
        } else {
            return var1.length() >= 14 ? var1.substring(10, 14) : "0";
        }
    }

    private String c(String var1) {
        return var1.length() < 32 ? var1 : var1.substring(0, 8) + '-' + var1.substring(8, 12) + '-' + var1.substring(12, 16) + '-' + var1.substring(16, 20) + '-' + var1.substring(20, 32);
    }

    private int d(String var1) {
        return var1.length() >= 6 ? Integer.parseInt(var1.substring(4, 6), 16) : 0;
    }

    public class ScanBinder extends Binder {
        public ScanBinder() {
        }

        public ScanService a() {
            return ScanService.this;
        }
    }
}
