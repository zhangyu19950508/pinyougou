package com.pinyougou.shop.controller;
import java.io.IOException;
import java.util.List;

import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojogroup.Goods;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.sellergoods.service.GoodsService;

import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;

	@Reference
	private ItemPageService itemPageService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Goods goods){
		//设置sellerId
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(sellerId.toString());
		goods.getGoods().setSellerId(sellerId);
        System.out.println(goods.getGoods().toString());
		try {
			goodsService.add(goods);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		//校验是否是当前商家的id
		Goods goods2 = goodsService.findOne(goods.getGoods().getId());
		//获取当前登录的商家ID
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		//如果传递过来的商家ID并不是当前登录的用户的ID,则属于非法操作
		if(!goods2.getGoods().getSellerId().equals(sellerId) ||  !goods.getGoods().getSellerId().equals(sellerId) ){
			return new Result(false, "操作非法");
		}

		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		// 获取商家 ID
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			goodsService.delete(ids,sellerId);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}


	/**
	 * 查询+分页
	 * @param goods
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		//获取商家id
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		//添加查询条件
		goods.setSellerId(sellerId);
		return goodsService.findPage(goods, page, rows);		
	}


	@RequestMapping("/updateMarketable")
	public Result updateMarketable(Long[] ids, String marketable){

		try {
			//获取当前的商家ID
			String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			goodsService.updateMarketable(ids,marketable,sellerId);
			//上架后 生成商品详细页
			for(Long goodsId:ids){
				itemPageService.getItemHtml(goodsId);
			}
			return new Result(true,"上架成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"上架失败");
		}
	}
	/**
	 * 生成静态页（测试）
	 * @param goodsId
	 */
	@RequestMapping("/getHtml")
	public void getHtml(Long goodsId){
		itemPageService.getItemHtml(goodsId);
	}




}
