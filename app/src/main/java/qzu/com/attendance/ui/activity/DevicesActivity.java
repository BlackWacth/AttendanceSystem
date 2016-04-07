package qzu.com.attendance.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import qzu.com.attendance.R;
import qzu.com.attendance.entity.Device;
import qzu.com.attendance.ui.adapter.BluetoothDevicesAdapter;
import qzu.com.attendance.ui.base.BaseActivity;
import qzu.com.attendance.ui.view.PinnedSectionListView;
import qzu.com.attendance.utils.BluetoothUtils;
import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;

public class DevicesActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PinnedSectionListView mListView;
    private MaterialProgressBar mProgressBar;

    private BluetoothDevicesAdapter mAdapter;
    private BluetoothReceiver mReceiver;
    private List<Device> mDevices;

    private int selcetPosition;
    private int listPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_devices;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("蓝牙搜索");

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.devices_swipe_refresh_layout);
        mListView = (PinnedSectionListView) findViewById(R.id.devices_pinned_section_listview);

        mProgressBar = (MaterialProgressBar) LayoutInflater.from(this).inflate(R.layout.item_device_progress, null);
        mProgressBar.setVisibility(View.INVISIBLE);
        mProgressBar.setEnabled(false);
        mListView.addFooterView(mProgressBar);
    }

    @Override
    protected void initData() {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.deep_purple_a200,
                R.color.green500,
                R.color.indigo_a200);

        if(mDevices == null) {
            mDevices = new ArrayList<>();
        }

        selcetPosition = 0;
        listPosition = 0;

        mAdapter = new BluetoothDevicesAdapter(this, mDevices);
        mListView.setAdapter(mAdapter);

        initViewListener();
    }

    protected void initViewListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getAdapter().getCount()) {
                    return;
                }
                Device device = (Device) parent.getAdapter().getItem(position);
                if (device.getType() == Device.TYPE_ITEM) {
                    if (device.getState() == Device.STATE_BOND) {
                        resultForActivity(device.getDevice());
                    } else if (device.getState() == Device.STATE_UNBOND) {
                        BluetoothUtils.bondDevice(device.getDevice());
                    }
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scanDevices();
            }
        });
    }

    private void resultForActivity(BluetoothDevice device){
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_CONNECT_DEVICE, device);
        this.setResult(RESULT_OK, intent);
        L.i("数据回执----devices : " + device);
        this.finish();
    }

    /**
     * 蓝牙设备搜索
     */
    private void scanDevices() {
        if(!BluetoothUtils.isBluetoothEnabled()) {
            L.i("蓝牙设备为打开，现在开启");
            BluetoothUtils.openBluetooth(this);
        }else{
            L.i("蓝牙设备已打开，开始搜索蓝牙设备");
            clear();
            addBondedDevices();
            //搜索未配对的蓝牙设备
            BluetoothUtils.scanDevices();
        }
    }

    private void addBondedDevices() {
        List<BluetoothDevice> bondedDevices = BluetoothUtils.getBondedDevices();
        Device section = new Device(Device.STATE_BOND, Device.TYPE_SECTION, "已配对设备", "", null);
        section.setSectionPositon(selcetPosition++);
        section.setListPositon(listPosition++);
        addDevices(section, true);
        for (BluetoothDevice dev : bondedDevices) {
            String name = dev.getName();
            if(TextUtils.isEmpty(name)) {
                name = dev.getAddress();
            }
            L.i("已配对 ： " + name + " : " + dev.getAddress());
            Device item = new Device(Device.STATE_BOND, Device.TYPE_ITEM, name, dev.getAddress(), dev);
            item.setSectionPositon(selcetPosition);
            item.setListPositon(listPosition++);
            addDevices(item, false);
        }
        notifyRefresh();
    }

    private void clear() {
        mDevices.clear();
        if(mAdapter != null) {
            mAdapter.clear();
        }
        selcetPosition = 0;
        listPosition = 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BluetoothUtils.CODE_ENABLE_BLUETOOTH :
                if(resultCode == RESULT_OK) {
                    L.i("蓝牙设备成功开启， 开始搜索蓝牙设备");
                    clear();
                    addBondedDevices();
                    BluetoothUtils.scanDevices();
                }
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void addDevices(Device device, boolean isRefresh) {
        mDevices.add(device);
        if(isRefresh) {
            notifyRefresh();
        }
    }

    private void notifyRefresh() {
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 注册广播接收器
     */
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        if(mReceiver == null) {
            mReceiver = new BluetoothReceiver();
        }
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        clear();
        registerReceiver();
        scanDevices();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    /**
     * 蓝牙设备各种操作接收器
     */
    class BluetoothReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
                L.i("搜索蓝牙设备，开始");
                Device section = new Device(Device.STATE_UNBOND, Device.TYPE_SECTION, "可用设备", "", null);
                section.setSectionPositon(selcetPosition);
                section.setListPositon(listPosition++);
                addDevices(section, true);
                mSwipeRefreshLayout.setRefreshing(false);
                mProgressBar.setVisibility(View.VISIBLE);
            }else if(action.equals(BluetoothDevice.ACTION_FOUND)) {
                L.i("搜索蓝牙设备，进行中……");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getBluetoothClass().getMajorDeviceClass() == BluetoothUtils.PHONE) {
                    String name = device.getName();
                    if(TextUtils.isEmpty(name)) {
                        name = device.getAddress();
                    }
                    L.i("未配对 ： " + name + " : " + device.getAddress());
                    Device item = new Device(Device.STATE_UNBOND, Device.TYPE_ITEM, name, device.getAddress(), device);
                    item.setSectionPositon(selcetPosition);
                    item.setListPositon(listPosition++);
                    addDevices(item, true);
                }
            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                L.i("搜索蓝牙设备，结束");
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_NONE:
                        L.i("未配对");
                        break;

                    case BluetoothDevice.BOND_BONDING:
                        L.i("正在配对……");
                        break;

                    case BluetoothDevice.BOND_BONDED:
                        L.i("配对成功");
                        resultForActivity(device);
                        break;
                }
            }
        }
    }
}
