package qzu.com.attendance.ui.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import qzu.com.attendance.R;
import qzu.com.attendance.entity.Device;
import qzu.com.attendance.ui.view.PinnedSectionListView;
import qzu.com.attendance.utils.BluetoothUtils;
import qzu.com.attendance.utils.L;


public class BluetoothDevicesAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter{

    private static final int[] COLORS = new int[] {R.color.amber500, R.color.blue500};
    private LayoutInflater mInflater;
    private List<Device> mList;

    public BluetoothDevicesAdapter(Context context, List<Device> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == Device.TYPE_SECTION;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceHolder holder;
        if(convertView == null) {
            holder = new DeviceHolder();
            convertView = mInflater.inflate(R.layout.item_devices, parent, false);
            holder.icon = (ImageView) convertView.findViewById(R.id.item_device_icon);
            holder.name = (TextView) convertView.findViewById(R.id.item_device_name);
            holder.setting = (ImageView) convertView.findViewById(R.id.item_device_setting);
            convertView.setTag(holder);
        }else {
            holder = (DeviceHolder) convertView.getTag();
        }
        Device device = (Device) getItem(position);
        if(device.getType() == Device.TYPE_SECTION) {
            holder.icon.setVisibility(View.GONE);
            holder.setting.setVisibility(View.GONE);
            holder.name.setText(device.getName());
            L.i("device.getSectionPositon()  : " + device.getSectionPositon());
            convertView.setBackgroundColor(parent.getResources().getColor(COLORS[device.getSectionPositon()]));
        }else if(device.getType() == Device.TYPE_ITEM) {
            BluetoothDevice bd = device.getDevice();
            BluetoothUtils.setBluetoothIcon(holder.icon, bd.getBluetoothClass().getMajorDeviceClass());
            holder.name.setText(device.getName());
            if(device.getState() == Device.STATE_BOND){
                holder.setting.setVisibility(View.VISIBLE);
                holder.setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        L.i("setting ====");
                    }
                });
            }else {
                holder.setting.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return ((Device)getItem(position)).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public void clear(){
        if(mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }

    class DeviceHolder {
        ImageView icon;
        TextView name;
        ImageView setting;
    }
}
