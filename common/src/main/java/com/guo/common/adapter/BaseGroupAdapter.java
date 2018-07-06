package com.guo.common.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;



public abstract class BaseGroupAdapter<T extends SectionEntity> extends BaseSectionQuickAdapter<T, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    protected BaseGroupAdapter(int layoutResId, int sectionHeadResId, List<T> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, T item) {
        this.convertHeader(helper, item);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        this.convertItem(helper, item);
    }

    public abstract void convertHeader(BaseViewHolder helper, T item);

    public abstract void convertItem(BaseViewHolder helper, T item);
}
