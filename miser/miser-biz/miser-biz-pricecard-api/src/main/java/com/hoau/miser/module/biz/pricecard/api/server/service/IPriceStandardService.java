package com.hoau.miser.module.biz.pricecard.api.server.service;

import java.util.List;
import java.util.Map;

import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceStandardEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceStandardVo;
import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;

/**
 * 
 * @Description: 标准价格action
 * @Author 廖文强
 * @Date 2015年12月15日
 */
public interface IPriceStandardService {

	/**
	 * 
	 * @Description: 查询标准价格集合信息
	 * @param param
	 * @param limit
	 * @param start
	 * @return List<PriceStandardEntity>  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午1:28:34
	 */
	public List<PriceStandardEntity> queryListByParam(PriceStandardVo psv, int limit, int start);
	
	/**
	 * 
	 * @Description: 查询标准价格列表(Excel)
	 * @param @param psv
	 * @param @return   
	 * @return List<ExcelPriceStandardEntity> 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	public List<ExcelPriceStandardEntity> excelQueryListByParam(PriceStandardVo psv);
	/**
	 * 
	 * @Description: 统计标准价格数量
	 * @param param
	 * @return Long  
	 * @Author 廖文强
	 * @Time 2015年12月15日下午4:37:17
	 */
	public Long queryCountByParam(PriceStandardVo psv);
	
	/**
	 * A、新增单条价格时同此规则，如果存在待生效数据提示“该线路存在相同的待生效数据，是否覆盖？”确认，则直接覆盖。
	 * 如果无则直接新增。
	 * @Description: 增加价格信息
	 * @param @param pse
	 * @param @param isConfirmAdd
	 * @param @return   
	 * @return Integer 
	 * @throws
	 * @author 廖文强
	 * @date 2015年12月30日
	 */
	public Integer addPriceStandard(PriceStandardEntity pse,Boolean isConfirmAdd);
	
	/**
	 * B、修改，如果修改待生效数据，则更新原待生效数据。
	 * 如果修改生效中数据，则产生一条待生效数据，此时如果存在另一条待生效数据提示“该线路存在相同的待生效数据，是否覆盖？” 确认，则直接覆盖。
	 * 如果无则直接新增待生效数据，已失效数据不能修改。
	 * @Description: 修改价格信息
	 * @param @param pse
	 * @param @param isConfirm 是否确认
	 * @param @return   
	 * @return Integer 
	 * @throws
	 * @author 廖文强
	 * @date 2015年12月30日
	 */
	public Integer updatePriceStandard(PriceStandardEntity pse,Boolean isConfirm);
	
	/**
	 * 作废，如果作废的是待生效数据，则数据删除。如果作废的是“生效中”数据，则该数据更新为“已失效”失效时间更新为作废操作时间。
	 * @Description: delPriceStandard
	 * @param pse void
	 * @Author 廖文强
	 * @Time 2015年12月15日下午5:26:32
	 */
	public int delPriceStandard(PriceStandardEntity pse);
	
	/**
	 * 
	 * @Description: 批量删除
	 * @param @param entitys   
	 * @return void 
	 * @throws
	 * @author 廖文强
	 * @date 2015年12月30日
	 */
	public int bathDelPriceStandard(List<PriceStandardEntity> entitys);
	/**
	 * 根据id得到价格表
	 * @param id
	 * @return PriceStandardEntity  
	 * @Author 廖文强
	 * @Time 2015年12月17日下午7:12:24
	 */
	public PriceStandardEntity queryPriceStandardById(String id);
	
	/**
	 * 该方法会将错误的信息收集起来，返回给调用者
	 * @Description: 处理从Excel里批量导入数据的操作
	 * @param @param path
	 * @param @return
	 * @param @throws Exception   
	 * @return Map<String,Object> addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
	 * @throws
	 * @author 廖文强
	 * @date 2015年12月31日
	 */
	public  Map<String,Object>  bathImplPriceStandards(String path) throws Exception;
	
	/**
	 * 
	 * @Description: 生成Excel导出文件
	 * @param @param list
	 * @param @return   
	 * @return String 
	 * @throws
	 * @author 廖文强
	 * @date 2016年1月4日
	 */
	public ExcelExportResultEntity createExcelFile(PriceStandardVo entity);
	
}
