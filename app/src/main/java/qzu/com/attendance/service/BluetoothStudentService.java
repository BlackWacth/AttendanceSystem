package qzu.com.attendance.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import qzu.com.attendance.utils.L;


/**
 * Created by ZHONG WEI  HUA on 2016/4/6.
 */
public class BluetoothStudentService extends BluetoothServer{

    public BluetoothStudentService(Handler handler) {
        super.mHandler = handler;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void connected(BluetoothSocket socket, BluetoothDevice device, String socketType) {
        cancelConnectThread();
        cancelConnectedThread();
        mConnectedThread = new ConnectedThread(socket, socketType);
        mConnectedThread.start();
        L.i("蓝牙连接成功");
        String str = "可以开始通信了";
        mConnectedThread.write(str.getBytes());
    }
}
