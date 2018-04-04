package com.hoau.miser.module.biz.extrafee.server.service;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceUpstairsEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceUpstairsVo;

public interface IPriceUpstairsService {

	/**
	 * @Description: 查询上楼费定义信息，采用分页查询
	 * @param priceUpstairsVo	查询参数
	 * @param strat				分页参数
	 * @param limit				分页参数
	 * @return List<PriceUpstairsEntity> 	查询结果，上楼费信息列表
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	public List<PriceUpstairsEntity> queryUpstairsPrices(PriceUpstairsVo priceUpstairsVo, int strat, int limit);
	
	/**
	 * @Description: 查询上楼费定义信息总记录条数
	 * @param priceUpstairsVo	查询参数
	 * @return Long 	总条数
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月4日
	 */
	public Long queryUpstairsPricesCount(PriceUpstairsVo priceUpstairsVo);
	
	/**
	 * @Description: 根据ID查询上楼费信息
	 * @param priceUpstairsVo	查询的id
	 * @return PriceUpstairsEntity 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月6日
	 */
	public PriceUpstairsEntity queryUpstairsPriceById(PriceUpstairsVo priceUpstairsVo);
	
	/**
	 * @Description: 检查保存的数据是否存在冲突
	 * @param priceUpstairsVo   需要检查的数据
	 * @param saveModel   		保存的类型：ADD新增，MODIFY修改
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	public String checkSaveData(PriceUpstairsVo priceUpstairsVo, String saveModel);
	
	/**
	 * @Description: 保存上楼费定义信息,新增与修改处理逻辑相同
	 * @param priceUpstairsVo  	新增数据 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	public void saveUpstairsPrice(PriceUpstairsVo priceUpstairsVo);
	
	/**
	 * @Description: 检查删除的数据
	 * @param @param priceUpstairsVo   
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	public String checkDeleteData(PriceUpstairsVo priceUpstairsVo);
	
	/**
	 * @Description: 删除上楼费定义信息
	 * @param @param priceUpstairsVo   
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年1月5日
	 */
	public void deleteUpstairsPrice(PriceUpstairsVo priceUpstairsVo);
	
	/**
	 * @Description: 查询上楼费明细
	 * @param priceUpstairsVo   
	 * @return void 
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年2月25日
	 */
	public PriceUpstairsEntity queryUpstairsDetails(PriceUpstairsVo priceUpstairsVo);
	
}
