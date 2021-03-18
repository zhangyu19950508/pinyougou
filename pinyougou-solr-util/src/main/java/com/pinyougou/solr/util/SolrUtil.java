package com.pinyougou.solr.util;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private SolrTemplate solrTemplate;

    /*
    导入数据
    */
    public void importData(){
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        System.out.println("===开始===");
        for (TbItem tbitem : tbItems) {
            System.out.println(tbitem.getTitle() + "" + tbitem.getPrice());
            Map specMap = JSON.parseObject(tbitem.getSpec());
            tbitem.setSpecMap(specMap);
            System.out.println(tbitem.getTitle());
        }
        solrTemplate.saveBeans(tbItems);
        solrTemplate.commit();
        System.out.println("===结束===");
    }

    public static void main(String[] args) {
        //通配符*表示不止可以在当前工程的classpath下查找配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil solrUtil = (SolrUtil)context.getBean("solrUtil");
        solrUtil.importData();
    }
}
