package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 品牌的控制器曾
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page,int rows){
        return brandService.findPage(page,rows);
    }

    /**
     * 增加
     * @param tbBrand
     * @return
     */
    @RequestMapping("/addTbBrand")
    public Result addTbBrand(@RequestBody TbBrand tbBrand){
        try{
            brandService.addTbBrand(tbBrand);
            return new Result(true,"增加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"增加失败");
        }
    }

    /* 修改
	 * @param brand
	 * @return
     */
    @RequestMapping("/updateTbBrand")
    public Result Update(@RequestBody TbBrand tbBrand){
        try{
            brandService.UpdateTbBrand(tbBrand);
            return new Result(true,"修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    /* 删除
     * @param brand
     * @return
     */
    @RequestMapping("/deleteTbBrand")
    public Result delete(long[] ids){
        try{
            brandService.deleteTbBrand(ids);
            return new Result(true,"删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }



    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbBrand findOne(Long id){
        return brandService.findOne(id);
    }

    /**
     * 查询
     * @param tbBrand
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand tbBrand, int page, int rows){
        return brandService.getAll(tbBrand,page,rows);
    }


}
