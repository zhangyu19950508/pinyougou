package com.pinyougou.pojogroup;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;

import java.io.Serializable;

/**
 * 商品组合实体类
 */
public class Goods implements Serializable {
    private TbGoods goods;//商品主表
    private TbGoodsDesc goodsDesc;//商品扩展表

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }
}
