//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.directionfinding.beacon.parser;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

@SuppressLint({"UseSparseArrays", "SimpleDateFormat", "DefaultLocale"})
public class beaconParser {
    public static final UUID a = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID b = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
    public static final UUID c = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
    public static final UUID d = UUID.fromString("0000fffa-0000-1000-8000-00805f9b34fb");
    private static HashMap<Integer, String> map = new HashMap();
    private static HashMap<String, String> map2;

    public static void setData(JSONObject var0, String var1, Object var2) {
        try {
            var0.put(var1, var2);
        } catch (JSONException var4) {
        }
    }

    public static JSONObject parse(byte[] var0) {
        JSONObject var1 = new JSONObject();
        if (var0 != null && var0.length != 0) {
            JSONArray var2 = new JSONArray();
            JSONArray var3 = new JSONArray();
            JSONArray var4 = new JSONArray();

            byte[] var15;
            for(boolean var6 = true; var6 && var0 != null && var0.length != 0; var0 = var15) {
                byte var7 = var0[0];
                if (var7 == 0) {
                    var6 = false;
                    break;
                }

                byte[] var8 = new byte[var7];

                for(int var9 = 0; var9 < var8.length; ++var9) {
                    var8[var9] = var0[var9 + 1];
                }

                byte[] var14 = new byte[]{var8[0]};
                byte[] var10 = new byte[var8.length - 1];

                int var11;
                for(var11 = 0; var11 < var10.length; ++var11) {
                    var10[var11] = var8[var11 + 1];
                }

                int var12;
                if ((255 & var14[0]) == 2) {
                    var15 = new byte[var10.length];

                    for(var12 = 0; var12 < var15.length; ++var12) {
                        var15[var12] = var10[var10.length - var12 - 1];
                    }

                    var2.put(parseByteToStr(var15));
                } else {
                    byte[] var13;
                    if ((255 & var14[0]) == 3) {
                        var11 = var10.length / 2;

                        for(var12 = 0; var12 < var11; ++var12) {
                            var13 = new byte[]{var10[var12 * 2], var10[var12 * 2 + 1]};
                            var2.put(parseByteToStr(var13));
                        }
                    } else if ((255 & var14[0]) == 4) {
                        var15 = new byte[var10.length];

                        for(var12 = 0; var12 < var15.length; ++var12) {
                            var15[var12] = var10[var10.length - var12 - 1];
                        }

                        var2.put(parseByteToStr(var15));
                    } else if ((255 & var14[0]) == 5) {
                        var11 = var10.length / 4;

                        for(var12 = 0; var12 < var11; ++var12) {
                            var13 = new byte[]{var10[var12 * 4], var10[var12 * 4 + 1], var10[var12 * 4 + 2], var10[var12 * 4 + 3]};
                            var2.put(parseByteToStr(var13));
                        }
                    } else if ((255 & var14[0]) == 6) {
                        var15 = new byte[var10.length];

                        for(var12 = 0; var12 < var15.length; ++var12) {
                            var15[var12] = var10[var10.length - var12 - 1];
                        }

                        var2.put(parseByteToStr(var15));
                    } else if ((255 & var14[0]) == 7) {
                        var11 = var10.length / 16;

                        for(var12 = 0; var12 < var11; ++var12) {
                            var13 = new byte[]{var10[var12 * 16], var10[var12 * 16 + 1], var10[var12 * 16 + 2], var10[var12 * 16 + 3], var10[var12 * 16 + 4], var10[var12 * 16 + 5], var10[var12 * 16 + 6], var10[var12 * 16 + 7], var10[var12 * 16 + 8], var10[var12 * 16 + 9], var10[var12 * 16 + 10], var10[var12 * 16 + 11], var10[var12 * 16 + 12], var10[var12 * 16 + 13], var10[var12 * 16 + 14], var10[var12 * 16 + 15]};
                            var2.put(parseByteToStr(var13));
                        }
                    } else if ((255 & var14[0]) == 8) {
                        setData(var1, "localName", parseStrToStr(parseByteToStr(var10)));
                    } else if ((255 & var14[0]) == 9) {
                        setData(var1, "localName", parseStrToStr(parseByteToStr(var10)));
                    } else if ((255 & var14[0]) == 10) {
                        setData(var1, "txPowerLevel", parseByteToStr(var10));
                    } else if ((255 & var14[0]) == 18) {
                        setData(var1, "isConnected", parseByteToStr(var10));
                    } else if ((255 & var14[0]) == 20) {
                        var11 = var10.length / 2;

                        for(var12 = 0; var12 < var11; ++var12) {
                            var13 = new byte[]{var10[var12 * 2], var10[var12 * 2 + 1]};
                            var3.put(parseByteToStr(var13));
                        }
                    } else if ((255 & var14[0]) == 21) {
                        var11 = var10.length / 16;

                        for(var12 = 0; var12 < var11; ++var12) {
                            var13 = new byte[]{var10[var12 * 16], var10[var12 * 16 + 1], var10[var12 * 16 + 2], var10[var12 * 16 + 3], var10[var12 * 16 + 4], var10[var12 * 16 + 5], var10[var12 * 16 + 6], var10[var12 * 16 + 7], var10[var12 * 16 + 8], var10[var12 * 16 + 9], var10[var12 * 16 + 10], var10[var12 * 16 + 11], var10[var12 * 16 + 12], var10[var12 * 16 + 13], var10[var12 * 16 + 14], var10[var12 * 16 + 15]};
                            var3.put(parseByteToStr(var13));
                        }
                    } else if ((255 & var14[0]) == 22) {
                        setData(var1, "serviceData", parseByteToStr(var10));
                    } else if ((255 & var14[0]) == 255) {
                        setData(var1, "manufacturerData", parseByteToStr(var10));
                    }
                }

                var15 = new byte[var0.length - var7 - 1];

                for(var12 = 0; var12 < var15.length; ++var12) {
                    var15[var12] = var0[var12 + 1 + var7];
                }
            }

            setData(var1, "serviceUUIDs", var2);
            setData(var1, "solicitedServiceUUIDs", var3);
            setData(var1, "overflowServiceUUIDs", var4);
            setData(var1, "advData", var0);
            return var1;
        } else {
            return var1;
        }
    }

    public static final String parseByteToStr(byte[] var0) {
        StringBuffer var1 = new StringBuffer(var0.length);

        for(int var3 = 0; var3 < var0.length; ++var3) {
            String var2 = Integer.toHexString(255 & var0[var3]);
            if (var2.length() < 2) {
                var1.append(0);
            }

            var1.append(var2.toUpperCase());
        }

        return var1.toString();
    }

    public static int parse(byte[] var0, int var1) {
        int var2 = 0;

        for(int var3 = 0; var3 < 4; ++var3) {
            int var4 = (3 - var3) * 8;
            var2 += (var0[var3 + var1] & 255) << var4;
        }

        return var2 >> 16;
    }

    public static byte[] parseStrToByte(String var0) {
        var0 = var0.toLowerCase();
        if (var0.length() % 2 != 0) {
            var0 = "0" + var0;
        }

        int var1 = var0.length() / 2;
        byte[] var2 = new byte[var1];
        char[] var3 = var0.toCharArray();

        for(int var4 = 0; var4 < var1; ++var4) {
            int var5 = var4 * 2;
            var2[var4] = (byte)("0123456789abcdef".indexOf(var3[var5]) << 4 | "0123456789abcdef".indexOf(var3[var5 + 1]));
        }

        return var2;
    }

    public static String parseStrToStr(String var0) {
        String var1 = "0123456789ABCDEF";
        char[] var2 = var0.toCharArray();
        byte[] var3 = new byte[var0.length() / 2];

        for(int var5 = 0; var5 < var3.length; ++var5) {
            int var4 = var1.indexOf(var2[2 * var5]) * 16;
            var4 += var1.indexOf(var2[2 * var5 + 1]);
            var3[var5] = (byte)(var4 & 255);
        }

        return new String(var3);
    }

    static {
        map.put(1, "broadcast");
        map.put(2, "read");
        map.put(4, "writeWithoutResponse");
        map.put(8, "write");
        map.put(16, "notify");
        map.put(32, "indicate");
        map.put(64, "authenticatedSignedWrites");
        map.put(128, "extendedProperties");
        map2 = new HashMap();
        map2.put("00001800-0000-1000-8000-00805f9b34fb", "Generic Access");
        map2.put("00001801-0000-1000-8000-00805f9b34fb", "Generic Attribute");
        map2.put("00001802-0000-1000-8000-00805f9b34fb", "Immediate Alert");
        map2.put("00001803-0000-1000-8000-00805f9b34fb", "Link Loss");
        map2.put("00001804-0000-1000-8000-00805f9b34fb", "Tx Power");
        map2.put("00001805-0000-1000-8000-00805f9b34fb", "Current Time Service");
        map2.put("00001806-0000-1000-8000-00805f9b34fb", "Reference Time Update Service");
        map2.put("00001807-0000-1000-8000-00805f9b34fb", "Next DST Change Service");
        map2.put("00001808-0000-1000-8000-00805f9b34fb", "Glucose");
        map2.put("00001809-0000-1000-8000-00805f9b34fb", "Health Thermometer");
        map2.put("0000180a-0000-1000-8000-00805f9b34fb", "MinewBeacon Information");
        map2.put("0000180b-0000-1000-8000-00805f9b34fb", "Network Availability Service");
        map2.put("0000180c-0000-1000-8000-00805f9b34fb", "Watchdog");
        map2.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate");
        map2.put("0000180e-0000-1000-8000-00805f9b34fb", "Phone Alert Status Service");
        map2.put("0000180f-0000-1000-8000-00805f9b34fb", "Battery Service");
        map2.put("00001810-0000-1000-8000-00805f9b34fb", "Blood Pressure");
        map2.put("00001811-0000-1000-8000-00805f9b34fb", "Alert Notification Service");
        map2.put("00001812-0000-1000-8000-00805f9b34fb", "Human Interface MinewBeacon");
        map2.put("00001813-0000-1000-8000-00805f9b34fb", "Scan Parameters");
        map2.put("00001814-0000-1000-8000-00805f9b34fb", "RUNNING SPEED AND CADENCE");
        map2.put("00001815-0000-1000-8000-00805f9b34fb", "Automation IO");
        map2.put("00001816-0000-1000-8000-00805f9b34fb", "Cycling Speed and Cadence");
        map2.put("00001817-0000-1000-8000-00805f9b34fb", "Pulse Oximeter");
        map2.put("00001818-0000-1000-8000-00805f9b34fb", "Cycling Power Service");
        map2.put("00001819-0000-1000-8000-00805f9b34fb", "Location and Navigation Service");
        map2.put("0000181a-0000-1000-8000-00805f9b34fb", "Continous Glucose Measurement Service");
        map2.put("00002a00-0000-1000-8000-00805f9b34fb", "MinewBeacon Name");
        map2.put("00002a01-0000-1000-8000-00805f9b34fb", "Appearance");
        map2.put("00002a02-0000-1000-8000-00805f9b34fb", "Peripheral Privacy Flag");
        map2.put("00002a03-0000-1000-8000-00805f9b34fb", "Reconnection Address");
        map2.put("00002a04-0000-1000-8000-00805f9b34fb", "Peripheral Preferred Connection Parameters");
        map2.put("00002a05-0000-1000-8000-00805f9b34fb", "Service Changed");
        map2.put("00002a06-0000-1000-8000-00805f9b34fb", "Alert Level");
        map2.put("00002a07-0000-1000-8000-00805f9b34fb", "Tx Power Level");
        map2.put("00002a08-0000-1000-8000-00805f9b34fb", "Date Time");
        map2.put("00002a09-0000-1000-8000-00805f9b34fb", "Day of Week");
        map2.put("00002a0a-0000-1000-8000-00805f9b34fb", "Day Date Time");
        map2.put("00002a0b-0000-1000-8000-00805f9b34fb", "Exact Time 100");
        map2.put("00002a0c-0000-1000-8000-00805f9b34fb", "Exact Time 256");
        map2.put("00002a0d-0000-1000-8000-00805f9b34fb", "DST Offset");
        map2.put("00002a0e-0000-1000-8000-00805f9b34fb", "Time Zone");
        map2.put("00002a1f-0000-1000-8000-00805f9b34fb", "Local Time Information");
        map2.put("00002a10-0000-1000-8000-00805f9b34fb", "Secondary Time Zone");
        map2.put("00002a11-0000-1000-8000-00805f9b34fb", "Time with DST");
        map2.put("00002a12-0000-1000-8000-00805f9b34fb", "Time Accuracy");
        map2.put("00002a13-0000-1000-8000-00805f9b34fb", "Time Source");
        map2.put("00002a14-0000-1000-8000-00805f9b34fb", "Reference Time Information");
        map2.put("00002a15-0000-1000-8000-00805f9b34fb", "Time Broadcast");
        map2.put("00002a16-0000-1000-8000-00805f9b34fb", "Time Update Control Point");
        map2.put("00002a17-0000-1000-8000-00805f9b34fb", "Time Update State");
        map2.put("00002a18-0000-1000-8000-00805f9b34fb", "Glucose Measurement");
        map2.put("00002a19-0000-1000-8000-00805f9b34fb", "Battery Level");
        map2.put("00002a1a-0000-1000-8000-00805f9b34fb", "Battery Power State");
        map2.put("00002a1b-0000-1000-8000-00805f9b34fb", "Battery Level State");
        map2.put("00002a1c-0000-1000-8000-00805f9b34fb", "Temperature Measurement");
        map2.put("00002a1d-0000-1000-8000-00805f9b34fb", "Temperature Type");
        map2.put("00002a1e-0000-1000-8000-00805f9b34fb", "Intermediate Temperature");
        map2.put("00002a1f-0000-1000-8000-00805f9b34fb", "Temperature in Celsius");
        map2.put("00002a20-0000-1000-8000-00805f9b34fb", "Temperature in Fahrenheit");
        map2.put("00002a21-0000-1000-8000-00805f9b34fb", "Measurement Interval");
        map2.put("00002a22-0000-1000-8000-00805f9b34fb", "Boot Keyboard Input Report");
        map2.put("00002a23-0000-1000-8000-00805f9b34fb", "System ID");
        map2.put("00002a24-0000-1000-8000-00805f9b34fb", "Model Number String");
        map2.put("00002a25-0000-1000-8000-00805f9b34fb", "Serial Number String");
        map2.put("00002a26-0000-1000-8000-00805f9b34fb", "Firmware Revision String");
        map2.put("00002a27-0000-1000-8000-00805f9b34fb", "Hardware Revision String");
        map2.put("00002a28-0000-1000-8000-00805f9b34fb", "Software Revision String");
        map2.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
        map2.put("00002a2a-0000-1000-8000-00805f9b34fb", "IEEE 11073-20601 Regulatory Certification Data List");
        map2.put("00002a2b-0000-1000-8000-00805f9b34fb", "Current Time");
        map2.put("00002a2c-0000-1000-8000-00805f9b34fb", "Elevation");
        map2.put("00002a2d-0000-1000-8000-00805f9b34fb", "Latitude");
        map2.put("00002a2e-0000-1000-8000-00805f9b34fb", "Longitude");
        map2.put("00002a2f-0000-1000-8000-00805f9b34fb", "Position 2D");
        map2.put("00002a30-0000-1000-8000-00805f9b34fb", "Position 3D");
        map2.put("00002a31-0000-1000-8000-00805f9b34fb", "Scan Refresh");
        map2.put("00002a32-0000-1000-8000-00805f9b34fb", "Boot Keyboard Output Report");
        map2.put("00002a33-0000-1000-8000-00805f9b34fb", "Boot Mouse Input Report");
        map2.put("00002a34-0000-1000-8000-00805f9b34fb", "Glucose Measurement Context");
        map2.put("00002a35-0000-1000-8000-00805f9b34fb", "Blood Pressure Measurement");
        map2.put("00002a36-0000-1000-8000-00805f9b34fb", "Intermediate Cuff Pressure");
        map2.put("00002a37-0000-1000-8000-00805f9b34fb", "Heart Rate Measurement");
        map2.put("00002a38-0000-1000-8000-00805f9b34fb", "Body Sensor Location");
        map2.put("00002a39-0000-1000-8000-00805f9b34fb", "Heart Rate Control Point");
        map2.put("00002a3a-0000-1000-8000-00805f9b34fb", "Removable");
        map2.put("00002a3b-0000-1000-8000-00805f9b34fb", "Service Required");
        map2.put("00002a3c-0000-1000-8000-00805f9b34fb", "Scientific Temperature in Celsius");
        map2.put("00002a3d-0000-1000-8000-00805f9b34fb", "String");
        map2.put("00002a3e-0000-1000-8000-00805f9b34fb", "Network Availability");
        map2.put("00002a3g-0000-1000-8000-00805f9b34fb", "Alert Status");
        map2.put("00002a40-0000-1000-8000-00805f9b34fb", "Ringer Control Point");
        map2.put("00002a41-0000-1000-8000-00805f9b34fb", "Ringer Setting");
        map2.put("00002a42-0000-1000-8000-00805f9b34fb", "Alert Category ID Bit Mask");
        map2.put("00002a43-0000-1000-8000-00805f9b34fb", "Alert Category ID");
        map2.put("00002a44-0000-1000-8000-00805f9b34fb", "Alert Notification Control Point");
        map2.put("00002a45-0000-1000-8000-00805f9b34fb", "Unread Alert Status");
        map2.put("00002a46-0000-1000-8000-00805f9b34fb", "New Alert");
        map2.put("00002a47-0000-1000-8000-00805f9b34fb", "Supported New Alert Category");
        map2.put("00002a48-0000-1000-8000-00805f9b34fb", "Supported Unread Alert Category");
        map2.put("00002a49-0000-1000-8000-00805f9b34fb", "Blood Pressure Feature");
        map2.put("00002a4a-0000-1000-8000-00805f9b34fb", "HID Information");
        map2.put("00002a4b-0000-1000-8000-00805f9b34fb", "Report Map");
        map2.put("00002a4c-0000-1000-8000-00805f9b34fb", "HID Control Point");
        map2.put("00002a4d-0000-1000-8000-00805f9b34fb", "Report");
        map2.put("00002a4e-0000-1000-8000-00805f9b34fb", "Protocol Mode");
        map2.put("00002a4g-0000-1000-8000-00805f9b34fb", "Scan Interval Window");
        map2.put("00002a50-0000-1000-8000-00805f9b34fb", "PnP ID");
        map2.put("00002a51-0000-1000-8000-00805f9b34fb", "Glucose Features");
        map2.put("00002a52-0000-1000-8000-00805f9b34fb", "Record Access Control Point");
        map2.put("00002a53-0000-1000-8000-00805f9b34fb", "RSC Measurement");
        map2.put("00002a54-0000-1000-8000-00805f9b34fb", "RSC Feature");
        map2.put("00002a55-0000-1000-8000-00805f9b34fb", "SC Control Point");
        map2.put("00002a56-0000-1000-8000-00805f9b34fb", "Digital Input");
        map2.put("00002a57-0000-1000-8000-00805f9b34fb", "Digital Output");
        map2.put("00002a58-0000-1000-8000-00805f9b34fb", "Analog Input");
        map2.put("00002a59-0000-1000-8000-00805f9b34fb", "Analog Output");
        map2.put("00002a5a-0000-1000-8000-00805f9b34fb", "Aggregate Input");
        map2.put("00002a5b-0000-1000-8000-00805f9b34fb", "CSC Measurement");
        map2.put("00002a5c-0000-1000-8000-00805f9b34fb", "CSC Feature");
        map2.put("00002a5d-0000-1000-8000-00805f9b34fb", "Sensor Location");
        map2.put("00002a5e-0000-1000-8000-00805f9b34fb", "Pulse Oximetry Spot-check Measurement");
        map2.put("00002a5f-0000-1000-8000-00805f9b34fb", "Pulse Oximetry Continuous Measurement");
        map2.put("00002a60-0000-1000-8000-00805f9b34fb", "Pulse Oximetry Pulsatile Event");
        map2.put("00002a61-0000-1000-8000-00805f9b34fb", "Pulse Oximetry Features");
        map2.put("00002a62-0000-1000-8000-00805f9b34fb", "Pulse Oximetry Control Point");
        map2.put("00002a63-0000-1000-8000-00805f9b34fb", "Cycling Power Measurement Characteristic");
        map2.put("00002a64-0000-1000-8000-00805f9b34fb", "Cycling Power Vector Characteristic");
        map2.put("00002a65-0000-1000-8000-00805f9b34fb", "Cycling Power Feature Characteristic");
        map2.put("00002a66-0000-1000-8000-00805f9b34fb", "Cycling Power Control Point Characteristic");
        map2.put("00002a67-0000-1000-8000-00805f9b34fb", "Location and Speed Characteristic");
        map2.put("00002a68-0000-1000-8000-00805f9b34fb", "Navigation Characteristic");
        map2.put("00002a69-0000-1000-8000-00805f9b34fb", "Position Quality Characteristic");
        map2.put("00002a6a-0000-1000-8000-00805f9b34fb", "LN Feature Characteristic");
        map2.put("00002a6b-0000-1000-8000-00805f9b34fb", "LN Control Point Characteristic");
        map2.put("00002a6c-0000-1000-8000-00805f9b34fb", "CGM Measurement Characteristic");
        map2.put("00002a6d-0000-1000-8000-00805f9b34fb", "CGM Features Characteristic");
        map2.put("00002a6e-0000-1000-8000-00805f9b34fb", "CGM Status Characteristic");
        map2.put("00002a6f-0000-1000-8000-00805f9b34fb", "CGM Session Start Time Characteristic");
        map2.put("00002a70-0000-1000-8000-00805f9b34fb", "Application Security Point Characteristic");
        map2.put("00002a71-0000-1000-8000-00805f9b34fb", "CGM Specific Ops Control Point Characteristic");
    }
}
