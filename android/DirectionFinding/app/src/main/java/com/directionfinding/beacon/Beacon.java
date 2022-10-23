//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.directionfinding.beacon;

public class Beacon extends MinewBeacon {
    private String uuid;
    private String major;
    private String minor;
    private String name;
    private float rssi;
    private int batteryLevel;
    private float temperature;
    private float humidity;
    private int txPower;
    private String mac;
    private boolean inRange;
    private long temp;

    public Beacon() {
    }

    public MinewBeaconValue getBeaconValue(BeaconValueIndex var1) {
        MinewBeaconValue beaconValue = new MinewBeaconValue();
        switch (var1) {
            case MinewBeaconValueIndex_MAC:
                beaconValue.setStringValue(this.mac);
                break;
            case MinewBeaconValueIndex_UUID:
                beaconValue.setStringValue(this.uuid);
                if (this.uuid != null) {
                    beaconValue.setDataValue(this.uuid.getBytes());
                }
                break;
            case MinewBeaconValueIndex_Major:
                if (this.major == null) {
                    this.major = "0";
                }

                beaconValue.setStringValue(this.major + "");
                beaconValue.setDataValue(this.major.getBytes());
                break;
            case MinewBeaconValueIndex_Minor:
                if (this.minor == null) {
                    this.minor = "0";
                }

                beaconValue.setStringValue(this.minor + "");
                beaconValue.setDataValue(this.minor.getBytes());
                break;
            case MinewBeaconValueIndex_Name:
                beaconValue.setStringValue(this.name);
                if (this.name == null) {
                    this.name = "N/A";
                }

                beaconValue.setDataValue(this.name.getBytes());
                break;
            case MinewBeaconValueIndex_RSSI:
                String var3 = this.rssi + "";
                beaconValue.setStringValue(var3);
                beaconValue.setDataValue(var3.getBytes());
                beaconValue.setFloatValue(this.rssi);
                beaconValue.setIntValue((int)this.rssi);
                break;
            case MinewBeaconValueIndex_BatteryLevel:
                String var4 = this.batteryLevel + "";
                beaconValue.setStringValue(var4);
                beaconValue.setDataValue(var4.getBytes());
                beaconValue.setFloatValue((float)this.batteryLevel);
                beaconValue.setIntValue(this.batteryLevel);
                break;
            case MinewBeaconValueIndex_TxPower:
                String var5 = this.txPower + "";
                beaconValue.setStringValue(var5);
                beaconValue.setDataValue(var5.getBytes());
                beaconValue.setFloatValue((float)this.txPower);
                beaconValue.setIntValue(this.txPower);
                break;
            case MinewBeaconValueIndex_InRage:
                beaconValue.setBool(this.inRange);
                break;
            case MinewBeaconValueIndex_Temperature:
                beaconValue.setStringValue(this.temperature + "");
                beaconValue.setDataValue((this.temperature + "").getBytes());
                beaconValue.setFloatValue(this.temperature);
                beaconValue.setIntValue((int)this.temperature);
                break;
            case MinewBeaconValueIndex_Humidity:
                beaconValue.setStringValue(this.humidity + "");
                beaconValue.setDataValue((this.humidity + "").getBytes());
                beaconValue.setFloatValue(this.humidity);
                beaconValue.setIntValue((int)this.humidity);
        }

        return beaconValue;
    }

    public long getTemp() {
        return this.temp;
    }

    public void setTemp(long temp) {
        this.temp = temp;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRSSI(float rssi) {
        this.rssi = rssi;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public void setTXPower(int txPower) {
        this.txPower = txPower;
    }

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
    }

    public String getMinor() {
        return this.minor;
    }

    public String getMajor() {
        return this.major;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
}
