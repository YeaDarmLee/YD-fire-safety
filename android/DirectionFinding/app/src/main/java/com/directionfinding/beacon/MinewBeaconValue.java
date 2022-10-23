package com.directionfinding.beacon;

public class MinewBeaconValue {
    private int intValue;
    private float floatValue;
    private String stringValue;
    private boolean bool;
    private byte[] dataValue;

    public MinewBeaconValue() {
    }

    public int getIntValue() {
        return this.intValue;
    }

    public void setIntValue(int var1) {
        this.intValue = var1;
    }

    public float getFloatValue() {
        return this.floatValue;
    }

    public void setFloatValue(float var1) {
        this.floatValue = var1;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public void setStringValue(String var1) {
        this.stringValue = var1;
    }

    public byte[] getDataValue() {
        return this.dataValue;
    }

    public void setDataValue(byte[] var1) {
        this.dataValue = var1;
    }

    public boolean isBool() {
        return this.bool;
    }

    public void setBool(boolean var1) {
        this.bool = var1;
    }
}
