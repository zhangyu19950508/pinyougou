package com.pinyougou.sellergoods.service;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojo.TbBrand;

import java.util.List;

public interface BrandService {
    /*
    * 查询品牌列表
    */
    List<TbBrand> findAll();

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findPage(int pageNum,int pageSize);

    /**
     * 新增品牌信息
     */
    public void addTbBrand(TbBrand tbBrand);

    /**
     * 修改品牌信息
     */
    public void UpdateTbBrand(TbBrand tbBrand);

    /**
     * 删除品牌信息
     */
    public void deleteTbBrand(long [] ids);

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public TbBrand findOne(Long id);




}
