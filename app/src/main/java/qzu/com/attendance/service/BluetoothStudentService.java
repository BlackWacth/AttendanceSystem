package qzu.com.attendance.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;


/**
 * 学生端
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
        sendMsg(Constants.MESSAGE_STATE_CHANGE, Constants.EXTRA_BLUETOOTH_STATE, STATE_CONNECTED);
        L.i("蓝牙连接成功");
    }

    @Override
    public void stop() {
        cancelConnectThread();
        cancelConnectedThread();
        setState(STATE_NONE);
    }
}
