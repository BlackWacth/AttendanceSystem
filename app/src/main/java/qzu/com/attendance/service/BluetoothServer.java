package qzu.com.attendance.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;

/**
 * 蓝牙连接服务
 */
public abstract class BluetoothServer {

    //蓝牙的四个状态
    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;

    protected static final String NAME_SECURE = "BluetoothSecure";
    protected static final String NAME_INSECURE = "BluetoothInsecure";

    protected static final UUID UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    protected static final UUID UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    protected AcceptThread mSecureAcceptThread;
    protected AcceptThread mInsecureAcceptThread;
    protected ConnectThread mConnectThread;
    protected ConnectedThread mConnectedThread;

    /**蓝牙当前状态 */
    protected volatile int mState;

    /**蓝牙适配器 */
    protected BluetoothAdapter mBluetoothAdapter;

    protected Handler mHandler;

    public synchronized int getState() {
        return mState;
    }

    public synchronized void setState(int state) {
        mState = state;
    }

    public void cancelSecureAcceptThread() {
        if(mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }
    }

    public void cancelInsecureAcceptThread() {
        if(mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }
    }

    public void cancelConnectThread() {
        if(mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
    }

    public void cancelConnectedThread() {
        if(mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
    }

    public void stop() {
        cancelConnectThread();
        cancelConnectedThread();
        cancelSecureAcceptThread();
        cancelInsecureAcceptThread();
        setState(STATE_NONE);
    }

    public abstract void connected(BluetoothSocket socket, BluetoothDevice device, String socketType);

    public synchronized void connect(BluetoothDevice device, boolean secure) {
        L.i("connect to " + device);
        if(mState == STATE_CONNECTING) {
            cancelConnectThread();
        }
        cancelConnectedThread();

        mConnectThread = new ConnectThread(device, secure);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    private void connectionFailed(){
        sendMsg(Constants.MESSAGE_TOAST, Constants.EXTRA_TOAST, "连接失败");
    }

    public void sendMsg(int action) {
        mHandler.obtainMessage(action);
    }

    public void sendMsg(int action, String extra, String value) {
        Message msg = mHandler.obtainMessage();
        msg.what = action;
        Bundle data = new Bundle();
        data.putString(extra, value);
        msg.setData(data);
        mHandler.sendMessage(msg);

    }

    public void sendMsg(int action, String extra, int value) {
        Message msg = mHandler.obtainMessage();
        msg.what = action;
        Bundle data = new Bundle();
        data.putInt(extra, value);
        msg.setData(data);
        mHandler.sendMessage(msg);
    }

    public void write(String msg) {
        ConnectedThread ct = null;
        synchronized (this) {
            ct = mConnectedThread;
        }
        ct.write(msg.getBytes());
    }

    /**
     * 服务端等待连接线程
     */
    public class AcceptThread extends Thread {
        private BluetoothServerSocket mServerSocket;
        private String mSocketType;

        public AcceptThread(boolean secure) {
            mSocketType = secure ? "Secure" : "Insecure";
            BluetoothServerSocket temp = null;
            try{
                if(secure) {
                    temp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, UUID_SECURE);
                }else {
                    temp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, UUID_INSECURE);
                }

            }catch (IOException e) {
                L.e("Socket type : " + mSocketType + " listen failed !! " + e.getMessage());
            }
            mServerSocket = temp;
        }

        @Override
        public void run() {
            L.i("Socket type : " + mSocketType + " Begin AcceptThread");
            setName("AcceptThread : " + mSocketType);

            while (true) {
                try {
                    if(mServerSocket != null) {
                        BluetoothSocket socket = mServerSocket.accept();
                        if(socket != null) {
                            connected(socket, socket.getRemoteDevice(), mSocketType);
                        }
                    }
                } catch (IOException e) {
                    L.e("Socket type : " + mSocketType + " accept failed !! " + e.getMessage());
                    break;
                }
            }
            L.i("Socket type : " + mSocketType + " End AcceptThread");
        }

        public void cancel() {
            try {
                mServerSocket.close();
            } catch (IOException e) {
                L.e("Socket type : " + mSocketType + " BluetoothServerSocket close failed !! " + e.getMessage());
            }
        }
    }

    public class ConnectThread extends Thread {

        private final BluetoothSocket mSocket;
        private final BluetoothDevice mDevice;
        private String mSocketType;

        public ConnectThread(BluetoothDevice device, boolean secure) {
            mDevice = device;
            mSocketType = secure ? "Secure" : "Insecure";
            BluetoothSocket temp = null;
            try {
                if (secure) {
                    temp = device.createRfcommSocketToServiceRecord(UUID_SECURE);
                }else {
                    temp = device.createInsecureRfcommSocketToServiceRecord(UUID_INSECURE);
                }
            } catch (IOException e) {
                L.e("Socket type : " + mSocketType + " socket create failed !! " + e.getMessage());
            }
            mSocket = temp;
        }

        @Override
        public void run() {
            L.i("Begin ConnectThread" + "SockeyType : " + mSocketType);
            setName("ConnectThread " + mSocketType);
            try{
                mSocket.connect();
            }catch (IOException e) {
                cancel();
                connectionFailed();
                return;
            }
            synchronized (BluetoothServer.this) {
                mConnectThread = null;
            }
            connected(mSocket, mDevice, mSocketType);
            L.i("End ConnectThread" + "SockeyType : " + mSocketType);
        }

        public void cancel() {
            try {
                mSocket.close();
            } catch (IOException e) {
                L.e("Socket close failed + " + e.getMessage());
            }
        }
    }

    public class ConnectedThread extends Thread {
        private final BluetoothSocket mSocket;
        private final InputStream mInputStream;
        private final OutputStream mOutputStream;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            mSocket = socket;
            InputStream in = null;
            OutputStream out = null;
            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mInputStream = in;
            mOutputStream = out;
        }

        @Override
        public void run() {
            L.i("Begin ConnectedThread");
            byte[] buff = new byte[1024];
            int bytes;
            while (true) {
                try {
                    bytes = mInputStream.read(buff);
                    String readMsg = new String(buff, 0, bytes);
                    sendMsg(Constants.MESSAGE_READ, Constants.EXTRA_READ_MASSAGE, readMsg);
                } catch (IOException e) {
                    L.e("disconnected " + e.getMessage());
                    break;
                }
            }
        }

        public void write(byte[] buff) {
            try {
                mOutputStream.write(buff);
                sendMsg(Constants.MESSAGE_WRITE, Constants.EXTRA_WRITE_MASSAGE, new String(buff));
            } catch (IOException e) {
                L.e("Exception during write " + e.getMessage());
            }
        }

        public void cancel() {
            try {
                mSocket.close();
            } catch (IOException e) {
                L.e("Socket close failed + " + e.getMessage());
            }
        }
    }
}
