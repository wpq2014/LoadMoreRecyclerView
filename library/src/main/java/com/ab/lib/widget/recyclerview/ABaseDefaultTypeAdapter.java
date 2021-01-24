package com.ab.lib.widget.recyclerview;

import java.util.List;

/**
 * @author wupuquan
 * @version 1.0
 * @since 2020/4/10 14:18
 */
public abstract class ABaseDefaultTypeAdapter extends ABaseTypeAdapter<AdapterItemType> {

    public ABaseDefaultTypeAdapter(List<AdapterItemType> list) {
        super(list);
    }

    @Override
    protected int getViewType(int position) {
        try {
            return getData().get(position).getItemType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
