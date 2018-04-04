package com.hoau.miser.module.biz.extrafee.server.service;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.PriceExtrafeeStandardEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.PriceExtrafeeStandardVo;

/**
 * 
 * @Description: 标准附加费action
 * @Author 292078
 * @Date 2015年12月15日
 */
public interface IPriceExtrafeeStandardService {

	/**
	 * 
	 * @Description: 查询标准附加费列表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<PriceExtrafeeStandardEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public List<PriceExtrafeeStandardEntity> queryListByParam(PriceExtrafeeStandardVo psv, int limit, int start);
	/**
	 * 
	 * @Description: 统计标准附加费数量
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public Long queryCountByParam(PriceExtrafeeStandardVo psv);
	/**
	 * 
	 * @Description: 新增标准附加费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String addPriceExtrafeeStandard(PriceExtrafeeStandardEntity pse);
	/**
	 * 
	 * @Description: 修改标准附加费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String updatePriceExtrafeeStandard(PriceExtrafeeStandardEntity pse);
	/**
	 * 
	 * @Description: 删除标准附加费
	 * @param @param pse   
	 * @return String 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String delPriceExtrafeeStandard(PriceExtrafeeStandardEntity pse);
	/**
	 * 
	 * @Description: 查询标准附加费ById
	 * @param @param id
	 * @param @return   
	 * @return PriceExtrafeeStandardEntity 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public PriceExtrafeeStandardEntity queryPriceExtrafeeStandardById(String id);
	/**
	 * excel导出
	 * @Description: TODO描述该方法是做什么的
	 * @param @param list
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月5日
	 */
	public String createExcelFile(List<PriceExtrafeeStandardEntity> list);
	/**
	 * 无分页
	 * @Description: TODO描述该方法是做什么的
	 * @param @param psv
	 * @param @return   
	 * @return List<PriceExtrafeeStandardEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月5日
	 */
	public List<PriceExtrafeeStandardEntity> excelListByParam(PriceExtrafeeStandardVo psv);
}
