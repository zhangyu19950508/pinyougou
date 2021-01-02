package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 品牌服务层实现
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper tbBrandMapper;


    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByExample(null);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 新增品牌信息
     * @param tbBrand
     */
    @Override
    public void addTbBrand(TbBrand tbBrand) {
        tbBrandMapper.insert(tbBrand);
    }

    /**
     * 修改
     */
    @Override
    public void UpdateTbBrand(TbBrand tbBrand) {
        tbBrandMapper.updateByPrimaryKey(tbBrand);
    }

    /**
     * 删除
     * @param ids
     */
    @Override
    public void deleteTbBrand(long[] ids) {
        for (long id : ids) {
            tbBrandMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    @Override
    public TbBrand findOne(Long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TbBrand> findAll() {
        return tbBrandMapper.selectByExample(null);
    }

    /**
     * 查询符合条件的商品
     * @param tbBrand
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult getAll(TbBrand tbBrand, int page, int rows) {
        //执行分页
        PageHelper.startPage(page,rows);

        //条件查询
        TbBrandExample tbBrandExample = new TbBrandExample();
        //构建查询条件的类
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();

        if(tbBrand!=null){
            if(tbBrand.getName()!=null&&tbBrand.getName().length()>0){
                //模糊查询条件
                criteria.andNameLike(tbBrand.getName());
            }
            if(tbBrand.getFirstChar()!=null&&tbBrand.getFirstChar().length()>0){
                criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
            }
        }
        Page<TbBrand> pages = (Page<TbBrand>) tbBrandMapper.selectByExample(tbBrandExample);
        return new PageResult(pages.getTotal(),pages.getResult());
    }


}
