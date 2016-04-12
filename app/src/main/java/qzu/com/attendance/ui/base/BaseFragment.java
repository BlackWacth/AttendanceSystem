package qzu.com.attendance.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import in.srain.cube.views.ptr.PtrFrameLayout;

public abstract class BaseFragment extends Fragment {
    
    private View contentView;
    /** */
    protected boolean isVisible;
    /**标志位，标志已经初始化完成 */
    protected boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    protected boolean isHasLoadedOnce;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(contentView == null) {
            contentView = inflater.inflate(getLayoutId(), container, false);
            isPrepared = true;
            init(contentView);
            load();
        }
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if(parent != null) {
            parent.removeView(contentView);
        }
        return contentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected void init(View view){
        initView(view);
        initData();
    }

    protected abstract void initView(View view);

    protected abstract void initData();

    public void showToast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public String getResouseString(int id) {
        return getActivity().getResources().getString(id);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible(){
        
    }

    protected void onVisible(){
        load();
    }

    protected  void load(){
        if(!isPrepared || !isVisible || isHasLoadedOnce){
            return;
        }
        lazyLoad();
    }

    /**
     * 延迟加载
     */
    protected abstract void lazyLoad();

    public abstract boolean checkCanDoRefresh();

    public abstract void update(PtrFrameLayout frame);
}
