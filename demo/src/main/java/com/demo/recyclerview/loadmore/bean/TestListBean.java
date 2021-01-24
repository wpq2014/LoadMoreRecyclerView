package com.demo.recyclerview.loadmore.bean;

import com.demo.recyclerview.loadmore.TestItemType;
import com.demo.recyclerview.utils.ScreenUtil;

import java.io.Serializable;

/**
 * @date 2021/1/23
 */
public class TestListBean extends BaseTestTypeBean {

    public int height = ScreenUtil.dp2px(144);

    @Override
    public int getItemType() {
        return TestItemType.TYPE_LIST;
    }
}
