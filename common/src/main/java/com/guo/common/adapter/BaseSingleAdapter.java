package com.guo.common.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public abstract class BaseSingleAdapter<T> extends BaseItemDraggableAdapter<T, BaseViewHolder> {
    /**
     * 实例化BaseSingleAdapter
     *
     * @param layoutResId 条目布局id
     * @param data        数据
     */
    protected BaseSingleAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        this.convertCallback(helper, item);
    }

    /**
     * 转换回调
     *
     * @param helper BaseViewHolder
     * @param item   item
     */
    public abstract void convertCallback(BaseViewHolder helper, T item);
}
