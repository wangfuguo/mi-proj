package com.hoau.miser.module.biz.extrafee.server.service;

import java.util.List;

import com.hoau.miser.module.biz.extrafee.shared.domain.ExtrafeeAddValueFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.ExtrafeeAddValueFeeVo;

/**
 * 
 * @Description: 增值费action
 * @Author 292078
 * @Date 2015年12月15日
 */
public interface IExtrafeeAddValueFeeService {

	/**
	 * 
	 * @Description: 查询增值费列表
	 * @param @param psv
	 * @param @param rowBounds
	 * @param @return   
	 * @return List<ExtrafeeAddValueFeeEntity> 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public List<ExtrafeeAddValueFeeEntity> queryListByParam(ExtrafeeAddValueFeeVo psv, int limit, int start);
	/**
	 * 
	 * @Description: 统计增值费数量
	 * @param @param psv
	 * @param @return   
	 * @return Long 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public Long queryCountByParam(ExtrafeeAddValueFeeVo psv);
	/**
	 * 
	 * @Description: 新增增值费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String addExtrafeeAddValueFee(ExtrafeeAddValueFeeEntity pse);
	/**
	 * 
	 * @Description: 修改增值费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String updateExtrafeeAddValueFee(ExtrafeeAddValueFeeEntity pse);
	/**
	 * 
	 * @Description: 删除增值费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public String delExtrafeeAddValueFee(ExtrafeeAddValueFeeEntity pse);
	/**
	 * 
	 * @Description: 查询增值费ById
	 * @param @param id
	 * @param @return   
	 * @return ExtrafeeAddValueFeeEntity 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public ExtrafeeAddValueFeeEntity queryExtrafeeAddValueFeeById(String id);
	
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
	public String createExcelFile(List<ExtrafeeAddValueFeeEntity> list);
	
	/**
	 * excel 数据导出
	 * @Description: TODO描述该方法是做什么的
	 * @param @param psv
	 * @param @return   
	 * @return List<ExtrafeeAddValueFeeEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年4月5日
	 */
	public List<ExtrafeeAddValueFeeEntity> excelListByParam(ExtrafeeAddValueFeeVo psv);
}
