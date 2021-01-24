package com.demo.recyclerview.loadmore.bean;

import com.demo.recyclerview.loadmore.TestItemType;

import java.io.Serializable;

/**
 * @date 2021/1/23
 */
public class TestBannerBean extends BaseTestTypeBean {

    @Override
    public int getItemType() {
        return TestItemType.TYPE_BANNER;
    }
}
