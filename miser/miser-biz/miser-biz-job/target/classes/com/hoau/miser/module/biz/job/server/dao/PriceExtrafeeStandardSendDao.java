package com.hoau.miser.module.biz.job.server.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.PriceExtrafeeStandardSendEntity;
/**
 * 
 * @Description: 标准附加费Dao
 * @Author 292078
 * @Date 2015年12月15日
 */
@Repository
public interface PriceExtrafeeStandardSendDao {

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
	public ArrayList<PriceExtrafeeStandardSendEntity> queryListByParam(DataSendJobQueryFilterEntity entity);
	
	
	
	public ArrayList<PriceExtrafeeStandardSendEntity> queryFailureData(DataSendJobFailureEntity entity);
	
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
	public Long queryCountByParam(PriceExtrafeeStandardSendEntity psv);
	
}
