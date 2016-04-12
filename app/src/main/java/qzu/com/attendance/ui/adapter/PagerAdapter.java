package qzu.com.attendance.ui.adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import qzu.com.attendance.ui.base.BaseFragment;

/**
 * 主界面适配器
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<FragmentModel> mList;

    public PagerAdapter(FragmentManager fm, List<FragmentModel> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getTitle();
    }

    public boolean checkCanDoRefresh(int position) {
        return getCurrentFragment(position).checkCanDoRefresh();
    }

    private BaseFragment getCurrentFragment(int position) {
        return (BaseFragment) getItem(position);
    }

    public void update(int position, PtrFrameLayout frame){
        getCurrentFragment(position).update(frame);
    }

    public static class FragmentModel {
        private String title;
        private BaseFragment fragment;

        public FragmentModel(String title, BaseFragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public BaseFragment getFragment() {
            return fragment;
        }

        public void setFragment(BaseFragment fragment) {
            this.fragment = fragment;
        }
    }
}
