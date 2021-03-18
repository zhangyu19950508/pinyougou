package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;

import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;



import java.util.HashMap;
import java.util.Map;

@Service(timeout = 3000)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String,Object> map = new HashMap<String, Object>();
        Query query = new SimpleQuery("*:*");
        String keywords = (String) searchMap.get("keywords");
        Criteria criteria = new Criteria("item_keywords").is(keywords);
        query.addCriteria(criteria);
        ScoredPage<TbItem> itemScoredPage = solrTemplate.queryForPage(query, TbItem.class);
        map.put("rows",itemScoredPage);
        map.put("rows", itemScoredPage.getContent());
        System.out.println(map);
        System.out.println(itemScoredPage.getContent());
        return map;

    }
}
