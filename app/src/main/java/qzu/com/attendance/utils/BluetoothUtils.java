package qzu.com.attendance.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.widget.ImageView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import qzu.com.attendance.R;

/**
 * 蓝牙工具类
 * Created by Mr Hua on 2015/11/11 0011.
 */
public class BluetoothUtils {

    /**蓝牙被发现的时间，该时间最大为300秒 */
    public static final int BLUETOOTH_DISCOVERED_DURATION = 300;
    public static final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    /**蓝牙成功开启状态码 */
    public static final int CODE_ENABLE_BLUETOOTH = 100;

    public static final int COMPUTER = BluetoothClass.Device.Major.COMPUTER;
    public static final int PHONE = BluetoothClass.Device.Major.PHONE;
    public static final int HEADSET = BluetoothClass.Device.Major.AUDIO_VIDEO;

    public static final int[] device_icon = {
            R.mipmap.ic_computer_black_24dp,
            R.mipmap.ic_stay_current_portrait_black_24dp,
            R.mipmap.ic_headset_mic_black_24dp,
            R.mipmap.ic_bluetooth_black_24dp};

    /**
     * 检测是否支持蓝牙
     * @return
     */
    public static boolean isSupportBluetooth(){
        return mBluetoothAdapter == null ? false : true;
    }

    /**
     * 获取蓝牙名称
     * @return
     */
    public static String getBluetoothName() {
        return mBluetoothAdapter.getName();
    }

    /**
     * 设置蓝牙名称
     * @param name
     */
    public static void setBluetoothName(String name) {
        mBluetoothAdapter.setName(name);
    }

    /**
     * 蓝牙是否打开
     * @return
     */
    public static boolean isBluetoothEnabled(){
        return mBluetoothAdapter.isEnabled();
    }

    /**
     * 打开蓝牙
     * @return
     */
    public static boolean openBluetooth() {
        if(!isBluetoothEnabled()) {
            return mBluetoothAdapter.enable();
        }
        return false;
    }

    /**
     * 开启蓝牙，有回调
     * @param activity
     */
    public static void openBluetooth(Activity activity) {
        if(!isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(intent, CODE_ENABLE_BLUETOOTH);
        }
    }

    /**
     * 关闭蓝牙
     * @return
     */
    public static boolean closeBluetooth() {
        if(isBluetoothEnabled()){
            return mBluetoothAdapter.disable();
        }
        return false;
    }

    /**
     * 搜索蓝牙
     */
    public static void scanDevices() {
        if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            if(mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            mBluetoothAdapter.startDiscovery();
        }
    }

    /**
     * 设置蓝牙扫描模式，默认时间BLUETOOTH_DISCOVERED_DURATION
     * @param scanMode 扫描模式
     * @return
     */
    public static boolean setBluetoothScanMode(int scanMode) {
        Class<?> cls = BluetoothAdapter.class;
        boolean flag = false;
        Method scanModeMethod = null;
        try {
            scanModeMethod = cls.getMethod("setScanMode", int.class, int.class);
            flag = (boolean) scanModeMethod.invoke(mBluetoothAdapter, scanMode, BLUETOOTH_DISCOVERED_DURATION);
        } catch (Exception e) {
            L.i("setScanMode failed --- " + e.toString());
        }
        return flag;
    }

    /**
     * 获取已配对的蓝牙设备
     * @return
     */
    public static List<BluetoothDevice> getBondedDevices() {
        List<BluetoothDevice> bondedDevices = new ArrayList<>();
        Set<BluetoothDevice> set = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : set) {
            bondedDevices.add(device);
        }
        return bondedDevices;
    }

    /**
     * 设备蓝牙设备类型，设置相应图标
     * @param imageView
     * @param major
     */
    public static void setBluetoothIcon(ImageView imageView, int major){
        int icon = device_icon[3];
        switch (major) {
            case COMPUTER:
                icon = device_icon[0];
                break;

            case PHONE:
                icon = device_icon[1];
                break;

            case HEADSET:
                icon = device_icon[2];
                break;

            default:
                icon = device_icon[3];
                break;
        }
        imageView.setImageResource(icon);
    }

    public static int getBluetoothScanMode() {
        return mBluetoothAdapter.getScanMode();
    }

    public static void bondDevice(BluetoothDevice device) {
        if(mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();

        }
        if(device.getBondState() == BluetoothDevice.BOND_NONE) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                device.createBond();
            }
        }
    }
}
