package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceStandardSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardSectionVo;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;

/**
 * @ClassName: IPriceStandardSectionService
 * @Description: 易入户标准价格管理Service
 * @author zhangqingfu
 * @date 2016年5月4日 下午4:03:06
 *
 */
public interface IPriceStandardSectionService {
	/**
	 * @Title: queryAllByCondition
	 * @Description: 根据条件查询分页结果集
	 * @return List<PriceStandardSectionEntity> 易入户标准价格结果集
	 * @param vo
	 *            易入户标准价格
	 * @param limit
	 * @param start
	 */
	public List<PriceStandardSectionEntity> queryPageByCondition(PriceStandardSectionVo vo, int limit, int start);

	/**
	 * @Title: export
	 * @Description: 导出
	 * @return ExcelExportResultEntity excel导出文件返回数据类
	 * @param vo
	 *            易入户标准价格查询条件
	 */
	public ExcelExportResultEntity export(PriceStandardSectionVo vo) throws IOException;

	/**
	 * @Title: queryById
	 * @Description: 根据ID查询易入户标准价格结果
	 * @return PriceStandardSectionEntity 易入户标准价格实体
	 * @param vo
	 *            易入户标准价格
	 */
	public PriceStandardSectionEntity queryById(PriceStandardSectionVo vo);

	/**
	 * @Title: checkRecord
	 * @Description: 根据条件查询易入户标准价格结果
	 * @return List<PriceStandardSectionEntity> 易入户标准价格集合
	 * @param vo
	 *            易入户标准价格
	 */
	public List<PriceStandardSectionEntity> checkRecord(PriceStandardSectionVo vo);

	/**
	 * @Title: delete
	 * @Description: 删除
	 * @return Integer 操作状态
	 * @param vo
	 *            易入户标准价格
	 */
	public Integer delete(PriceStandardSectionVo vo, String user, Date dt);

	/**
	 * @Title: update
	 * @Description: 更新
	 * @return Integer 操作状态
	 * @param vo
	 *            易入户标准价格
	 */
	public Integer update(PriceStandardSectionVo vo, String user, Date dt);

	/**
	 * @Title: queryCountByCondition
	 * @Description: 获取记录数
	 * @return long 记录数
	 * @param vo
	 *            易入户标准价格
	 */
	public long queryCountByCondition(PriceStandardSectionVo vo);

	/**
	 * @Title: importExcel
	 * @Description: excel导入
	 * @return Map<String,Object> 
	 * @param path 文件路径
	 */
	public Map<String, Object> importExcel(String path) throws Exception;

}
