package com.hoau.miser.module.biz.extrafee.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.extrafee.shared.domain.ExtrafeeAddValueFeeEntity;
import com.hoau.miser.module.biz.extrafee.shared.vo.ExtrafeeAddValueFeeVo;
/**
 * 
 * @Description: 增值费Dao
 * @Author 292078
 * @Date 2015年12月15日
 */
@Repository
public interface ExtrafeeAddValueFeeDao {

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
	public List<ExtrafeeAddValueFeeEntity> queryListByParam(ExtrafeeAddValueFeeVo psv, RowBounds rowBounds);
	
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
	public List<ExtrafeeAddValueFeeEntity> queryListByParam(ExtrafeeAddValueFeeVo psv);
	
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
	public void addExtrafeeAddValueFee(ExtrafeeAddValueFeeEntity pse);
	/**
	 * 
	 * @Description: 修改增值费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public void updateExtrafeeAddValueFee(ExtrafeeAddValueFeeEntity pse);
	/**
	 * 
	 * @Description: 删除增值费
	 * @param @param pse   
	 * @return void 
	 * @throws
	 * @author 王茂
	 * @date 2015年12月30日
	 */
	public void delExtrafeeAddValueFee(ExtrafeeAddValueFeeEntity pse);

	
}
