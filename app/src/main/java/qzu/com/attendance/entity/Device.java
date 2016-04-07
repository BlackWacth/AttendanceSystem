package qzu.com.attendance.entity;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Mr Hua on 2015/11/13 0013.
 */
public class Device {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_SECTION = 1;
    public static final int STATE_UNBOND = 2;
    public static final int STATE_BOND = 3;

    private int state;
    private int type;
    private String name;
    private String address;
    private BluetoothDevice device;

    private int sectionPositon;
    private int listPositon;

    public Device(int state, int type, String name, String address, BluetoothDevice device) {
        this.state = state;
        this.type = type;
        this.name = name;
        this.address = address;
        this.device = device;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSectionPositon() {
        return sectionPositon;
    }

    public void setSectionPositon(int sectionPositon) {
        this.sectionPositon = sectionPositon;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "Device{" +
                "state=" + state +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", sectionPositon=" + sectionPositon +
                ", listPositon=" + listPositon +
                '}';
    }

    public int getListPositon() {
        return listPositon;
    }

    public void setListPositon(int listPositon) {
        this.listPositon = listPositon;
    }
}
