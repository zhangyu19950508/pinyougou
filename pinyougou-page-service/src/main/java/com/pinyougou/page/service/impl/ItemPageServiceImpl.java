package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Value("${pagedir}")
    private String pagedir;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfig;

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public boolean getItemHtml(Long goodsId)  {
        try{
            //获取配置类
            Configuration configuration = freeMarkerConfig.getConfiguration();
            // 2.获取模板
            Template template = configuration.getTemplate("item.ftl");
            //3.加载商品表数据
            Map dataModel = new HashMap<>();
            TbGoods goods = tbGoodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods",goods);
            //4.加载商品扩展表数据
            TbGoodsDesc goodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc",goodsDesc);

            //一级分类名称
            String Category1Name = tbItemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            //二级分类名称
            String Category2Name = tbItemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            //三级分类名称
            String Category3Name = tbItemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();

            dataModel.put("Category1Name",Category1Name);
            dataModel.put("Category2Name",Category2Name);
            dataModel.put("Category3Name",Category3Name);

            //SKU列表
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(goodsId);
            criteria.andStatusEqualTo("1");
            //按照是否默认倒序排序 为了让默认sku排在第一位
            example.setOrderByClause("is_default desc");

            List<TbItem> itemList = tbItemMapper.selectByExample(example);

            dataModel.put("itemList",itemList);
            //6.创建writer对象
            OutputStreamWriter out=new OutputStreamWriter(new FileOutputStream(pagedir+goodsId+".html"),"UTF-8");

            //7.输出
            template.process(dataModel,out);
            //8.关闭writer
            out.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

}
