package com.pinyougou.page.service;

import java.io.IOException;

/**
 * 商品详细页接口
 * @author Administrator
 *
 */

public interface ItemPageService {
    /**
     * 生成商品详细页
     * @param goodsId
     */
    public boolean getItemHtml(Long goodsId);
}
