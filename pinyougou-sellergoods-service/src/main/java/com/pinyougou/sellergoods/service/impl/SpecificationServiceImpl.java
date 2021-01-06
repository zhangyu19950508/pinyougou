package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.sellergoods.service.SpecificationService;

import com.pinyougou.entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper tbSpecificationOptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		//存储规格实体
		specificationMapper.insert(specification.getTbSpecification());
		//存储规格选项列表
		for(TbSpecificationOption sp :specification.getSpecificationOptionList()){
			//设置规格ID
			sp.setSpecId(specification.getTbSpecification().getId());
			tbSpecificationOptionMapper.insert(sp);
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
		//修改规格
		specificationMapper.updateByPrimaryKey(specification.getTbSpecification());

		//删除此规格的所有规格选项
		TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
		criteria.andSpecIdEqualTo(specification.getTbSpecification().getId());
		tbSpecificationOptionMapper.deleteByExample(tbSpecificationOptionExample);
		//重新添加
		for(TbSpecificationOption sp :specification.getSpecificationOptionList()){
			sp.setSpecId(specification.getTbSpecification().getId());
			tbSpecificationOptionMapper.insert(sp);
		}
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		//查询规格
		TbSpecification tbSpecifications = specificationMapper.selectByPrimaryKey(id);
		//查询选项列表
		//1.创建TbSpecificationOptionExample对象
		TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
		//2.创建Criteria，并写入规则
		TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
		criteria.andSpecIdEqualTo(id);
		//3.根据规格id，选出符合的规格选项
		List<TbSpecificationOption> tbSpecificationOptions = tbSpecificationOptionMapper.selectByExample(tbSpecificationOptionExample);
		//4.创建规格组对象并将信息封装，格式为{id，{{}，{}，{}，...}}
		Specification specification = new Specification();
		specification.setTbSpecification(tbSpecifications);
		specification.setSpecificationOptionList(tbSpecificationOptions);
		return specification;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			specificationMapper.deleteByPrimaryKey(id);
			//删除此规格的选项
			TbSpecificationOptionExample tbSpecificationOptionExample = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = tbSpecificationOptionExample.createCriteria();
			criteria.andSpecIdEqualTo(id);
			tbSpecificationOptionMapper.deleteByExample(tbSpecificationOptionExample);
		}
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 获取品牌下拉列表数据
	 * @return
	 */
	public List<Map> selectOptionList() {
		return specificationMapper.selectOptionList();
	}

}
