package com.hoau.miser.module.biz.pricecard.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.hoau.miser.module.biz.base.api.server.service.IPriceCityMappingService;
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityVo;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.miser.module.biz.base.api.server.service.ITranstypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCorpService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceSectionService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCorpEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpGirdVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpSearchFormVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCorpVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceSectionVo;
import com.hoau.miser.module.biz.pricecard.server.dao.PriceCorpDao;
import com.hoau.miser.module.common.shared.define.DataOrignEnum;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 
 * ClassName: PriceCorpServiceImpl
 * 
 * @author 李玮琰
 * @date 2016年1月13日
 * @version V1.0
 */
@Service
public class PriceCorpService implements IPriceCorpService {

	/**
	 * log exception
	 */
	private static final Logger log = LoggerFactory
			.getLogger(PriceCorpService.class);

	@Resource
	private PriceCorpDao priceCorpDao;

	@Resource
	private IPriceCityService priceCityService;

	@Resource
	private ITranstypeService transtypeService;

	@Resource
	private IPriceSectionService priceSectionService;
	private static final String PRICECITYSCOPE = "STANDARD";

	@Override
	public List<PriceCorpGirdVo> queryListByParam(PriceCorpVo priceCorpVo,
			int limit, int start) {

		RowBounds rowBounds = new RowBounds(start, limit);
		return priceCorpDao.queryBySearch(priceCorpVo.getSearchVo(), rowBounds);
	}

	@Override
	public long queryListByParamCount(PriceCorpVo priceCorpVo) {
		return priceCorpDao.queryBySearchCount(priceCorpVo.getSearchVo());
	}

	@Override
	@Transactional
	public Integer cancelByPrimaryKey(List<PriceCorpGirdVo> pcgv) {
		Integer resultCount = 0;
		for (PriceCorpGirdVo priceCorpGirdVo : pcgv) {
			priceCorpGirdVo.setModifyUserCode(MiserUserContext.getCurrentUser()
					.getUserName());
			// priceCorpGirdVo.setRemark(MiserUserContext.getCurrentUser().getEmpName()
			// + "与"+new Date().toString()+"人工作废");
			priceCorpDao.cancelByLJPrimary(priceCorpGirdVo);
			resultCount++;
		}
		return resultCount;
	}

	@Override
	public List<PriceCorpGirdVo> findByPriceCorpGirdVo(
			PriceCorpGirdVo priceCorpVo) {
		return priceCorpDao.findByCorpDetail(priceCorpVo);
	}

	@Override
	public Date queryCurrentDateTime() {
		return priceCorpDao.queryCurrentDateTime();
	}

	@Override
	@Transactional
	public void insertBatch(PriceCorpVo pcgv) {

		Date currentTime = queryCurrentDateTime();
		Calendar ca = Calendar.getInstance();
		ca.setTime(currentTime);
		// ca.add(Calendar.HOUR_OF_DAY, 1);
		Date currnetNextOneHous = ca.getTime();

		pcgv.getGridVo().setCreateUserCode(
				MiserUserContext.getCurrentUser().getUserName());
		pcgv.getGridVo().setModifyUserCode(
				MiserUserContext.getCurrentUser().getUserName());
		pcgv.getGridVo().setCreateTime(currentTime);
		pcgv.getGridVo().setModifyTime(currentTime);

		// 生效时间可以更改
		if (pcgv.getOperationType().equals("true")) {

			// 如果当前时间大于生效时间，把生效时间改成当前时间,否则保存生效时间
			if (pcgv.getGridVo().getEffectiveTime().getTime() < currnetNextOneHous
					.getTime()) {
				pcgv.getGridVo().setEffectiveTime(currnetNextOneHous);
			}

		}
		List<PriceCorpEntity> list = new ArrayList<PriceCorpEntity>();

		for (PriceCorpGirdVo priceCorpGirdVo : pcgv.getGridVoList()) {
			// 校验
			if (priceCorpGirdVo.getCorpCode() == null
					|| priceCorpGirdVo.getCorpCode() == "") {
				throw new BusinessException("选择正确的网点");
			}
			if (priceCorpGirdVo.getCorpName() == null
					|| priceCorpGirdVo.getCorpName() == "") {
				throw new BusinessException("网点名称不正确");
			}
			if (pcgv.getGridVo().getEffectiveTime() == null) {
				throw new BusinessException("选择正确的生效时间");
			}
			if (pcgv.getGridVo().getInvalidTime() == null) {
				throw new BusinessException("选择正确的失效时间");
			}
			if (priceCorpGirdVo.getArrivePriceCity() == null
					|| priceCorpGirdVo.getArrivePriceCity() == "") {
				throw new BusinessException("选择正确的到达价格城市");
			}
			if (priceCorpGirdVo.getTransTypeCode() == null
					|| priceCorpGirdVo.getTransTypeCode() == "") {
				throw new BusinessException("选择正确的运输类型");
			}
			if (priceCorpGirdVo.getWeightPrice() == null) {
				throw new BusinessException("输入正确的重量单价");
			}
			if (priceCorpGirdVo.getVolumePrice() == null) {
				throw new BusinessException("输入正确的体积单价");
			}

			// 保存
			PriceCorpEntity pce = new PriceCorpEntity();
			pce.setId(UUID.randomUUID().toString());
			pce.setTransTypeCode(priceCorpGirdVo.getTransTypeCode());
			pce.setTransTypeName(priceCorpGirdVo.getTransTypeName());
			pce.setCorpCode(priceCorpGirdVo.getCorpCode());
			pce.setCorpName(priceCorpGirdVo.getCorpName());
			pce.setArrivePriceCity(priceCorpGirdVo.getArrivePriceCity());
			pce.setWeightPrice(priceCorpGirdVo.getWeightPrice());
			pce.setVolumePrice(priceCorpGirdVo.getVolumePrice());
			pce.setAddFee(priceCorpGirdVo.getAddFee());
			pce.setLowestFee(priceCorpGirdVo.getLowestFee());
			pce.setFuelFeeSection(priceCorpGirdVo.getFuelAddFeeSection());

			// 对生效中的修改才进行
			if (pcgv.getOldPriceCorp().getState() != null
					&& pcgv.getOldPriceCorp().getState()
							.equals(PriceCorpSearchFormVo.STATE_2)
					&& pcgv.getOperationType().equals("false")) {
				pcgv.getGridVo().setEffectiveTime(currentTime);
			}

			pce.setRemark(priceCorpGirdVo.getRemark());
			pce.setActive("Y");
			pce.setDataOrign(DataOrignEnum.PMS.getCode());
			list.add(pce);
		}
		pcgv.setPrcList(list);
		/**
		 * 删除数据在保存之前,校验之前，,新增不需要作废,新增时不删除历史记录
		 */
		if (pcgv.getOldPriceCorp().getCorpCode() != null) {
			/**
			 * 生效中的数据，修改时把失效时间改为当前时间即可
			 */
			if (pcgv.getOldPriceCorp().getState()
					.equals(PriceCorpSearchFormVo.STATE_2)) {
				pcgv.getOldPriceCorp().setModifyUserCode(
						MiserUserContext.getCurrentUser().getUserName());
				priceCorpDao.updateByLJPrimaryForInsert(pcgv.getOldPriceCorp());
				pcgv.getGridVo().setEffectiveTime(currentTime);
			} else {
				// 非生效中的数据
				pcgv.getOldPriceCorp().setModifyUserCode(
						MiserUserContext.getCurrentUser().getUserName());
				priceCorpDao.cancelByLJPrimary(pcgv.getOldPriceCorp());
			}

		}

		// 第一步查询出该网点所有有效的
		List<PriceCorpGirdVo> li = priceCorpDao.queryListCorp(pcgv.getGridVo());
		for (PriceCorpGirdVo pg : li) {

			if (!(compareRange(pg.getEffectiveTime(), pg.getInvalidTime(), pcgv
					.getGridVo().getEffectiveTime(), pcgv.getGridVo()
					.getInvalidTime()))) {
				throw new BusinessException("该网点已有正在生效的专属价格，且有效时间重合，不能提交！");
			}
		}

		priceCorpDao.insertBatch(pcgv);
	}

	@Override
	public Map<String, Object> bathImplPriceCorps(PriceCorpGirdVo priceCorpVo)
			throws Exception {
		// 返回信息串
		Map<String, Object> retMap = new HashMap<String, Object>();
		// excel中读出的网店详细信息
		List<Map<String, String>> excelList = ExcelUtil.readToListByFile(
				priceCorpVo.getFilePath(), 7, 2);
		// excel信息用于填充前台表单
		List<PriceCorpGirdVo> priceCorpList = new ArrayList<PriceCorpGirdVo>();

		// 优惠分段--燃油附加费
		Map<String, String> priceSectionCodeMap = new HashMap<String, String>();
		List<PriceSectionEntity> priceSectionList = priceSectionService
				.queryPriceSection(new PriceSectionVo());
		for (PriceSectionEntity value : priceSectionList) {
			priceSectionCodeMap.put(value.getName(), value.getCode());
		}

		// 运输类型
		Map<String, String> transTypeCodeMap = new HashMap<String, String>();

		TranstypeVo transtypeVo = new TranstypeVo();
		TranstypeEntity transtypeEntity = new TranstypeEntity();
		transtypeEntity.setActive(MiserConstants.YES);
		transtypeVo.setTranstypeEntity(transtypeEntity);
		List<TranstypeEntity> transtypeEntityList = transtypeService
				.queryTranstypes(transtypeVo);
		for (TranstypeEntity value : transtypeEntityList) {
			transTypeCodeMap.put(value.getName(), value.getCode());
		}

		// 缓存出发到达价格城市名称对应编码
		Map<String, String> arrivalPriceCityNameAndCodes = new HashMap<String, String>();

		// 批量导入后，提示：“本次导入***条数据，新增待生效数据***条，覆盖原待生效数据***条。”
		int succCount = 0;
		int failCount = 0;
		// 如果excel文件有重复数据提示并放弃导入
		String repeat = "";
		String verify = "";
		Map<String, Long> repeatMap = new HashMap<String, Long>();
		// 6、明细列表——批量导入入，包含以下字段：出发价格城市 ^,到达价格城市 ^,运输类型 ^,重量单价 ^v,体积单价 ^v,附件费
		// ^v,最低收费 ^v,燃油服务费 ^v
		for (Map<String, String> map : excelList) {
			try {
				// 出发价格城市
				// 到达价格城市,根据名称从接口查询
				String arrivalPriceCityName = StringUtils.trim(map.get("0"));
				if (arrivalPriceCityNameAndCodes
						.containsKey(arrivalPriceCityName)) {
					priceCorpVo.setArrivePriceCity(arrivalPriceCityNameAndCodes
							.get(arrivalPriceCityName));
				} else {
					PriceCityEntity arrivalPriceCityEntity = new PriceCityEntity();
					arrivalPriceCityEntity.setName(arrivalPriceCityName);
					arrivalPriceCityEntity.setType("ARRIVAL");
					arrivalPriceCityEntity.setPriceCityScope(PRICECITYSCOPE);
					PriceCityVo arrivalPriceCityVo = new PriceCityVo();
					arrivalPriceCityVo.setQueryParam(arrivalPriceCityEntity);
					PriceCityEntity arrivalPriceCity = priceCityService
							.queryPriceCityByName(arrivalPriceCityVo);
					if (arrivalPriceCity != null) {
						arrivalPriceCityNameAndCodes.put(arrivalPriceCityName,
								arrivalPriceCity.getCode());
						priceCorpVo.setArrivePriceCity(arrivalPriceCity
								.getCode());
					} else {
						throw new RuntimeException("到达价格城市["
								+ arrivalPriceCityEntity.getName() + "]不存在");
					}
				}
				priceCorpVo.setArrivePriceCityName(arrivalPriceCityName);
				// 运输类型
				priceCorpVo
						.setTransTypeCode(transTypeCodeMap.get(map.get("1")));
				priceCorpVo.setTransTypeName(StringUtils.trim(map.get("1")));
				// 重量单价
				String wigp = map.get("2");
				BigDecimal db = new BigDecimal(0);
				try {
					Double temp = Double.valueOf(wigp);
					if (temp < 0) {
						verify = "重量单价不能小于0";
						break;
					}
					;
					db = BigDecimal.valueOf(temp);
				} catch (Exception ex) {
					verify = "重量单价只能是数字";
					break;
				}
				priceCorpVo.setWeightPrice(db);
				// 体积单价
				String vop = map.get("3");
				db = new BigDecimal(0);
				try {
					Double temp = Double.valueOf(vop);
					if (temp < 0) {
						verify = "体积单价不能小于0";
						break;
					}
					;
					db = BigDecimal.valueOf(temp);
				} catch (Exception ex) {
					verify = "体积单价只能是数字";
					break;
				}
				priceCorpVo.setVolumePrice(db);
				// 附件费
				String adf = map.get("4");
				db = new BigDecimal(0);
				try {
					Double temp = Double.valueOf(adf);
					if (temp < 0) {
						verify = "附件费不能小于0";
						break;
					}
					;
					db = BigDecimal.valueOf(temp);
				} catch (Exception ex) {
					verify = "附件费只能是数字";
					break;
				}
				priceCorpVo.setAddFee(db);
				// 最低收费
				String lf = map.get("5");
				db = new BigDecimal(0);
				try {
					Double temp = Double.valueOf(lf);
					if (temp < 0) {
						verify = "最低收费不能小于0";
						break;
					}
					;
					db = BigDecimal.valueOf(temp);
				} catch (Exception ex) {
					verify = "最低收费只能是数字";
					break;
				}
				priceCorpVo.setLowestFee(db);
				// 燃油服务费 --优惠分段
				priceCorpVo.setFuelAddFeeSection(priceSectionCodeMap.get(map
						.get("6")));
				priceCorpVo.setFuelAddFeeSectionName(map.get("6"));

				// 累计重复:到达城市+运输类型相同则为重复
				String repeatKey = priceCorpVo.getArrivePriceCityName() + ":"
						+ priceCorpVo.getTransTypeName();
				if (repeatMap.containsKey(repeatKey)) {
					repeatMap.put(repeatKey, repeatMap.get(repeatKey) + 1);
					// 有重复随意取一提示即可
					repeat = repeatKey;
					// 如果重复放弃导入
					break;
				} else {
					repeatMap.put(repeatKey, 1L);
				}
				if (StringUtils.isEmpty(priceCorpVo.getArrivePriceCity())) {
					verify = "系统没有到达价格城市："
							+ priceCorpVo.getArrivePriceCityName();
				} else if (StringUtils.isEmpty(priceCorpVo.getTransTypeCode())) {
					verify = "系统没有运输类型：" + priceCorpVo.getTransTypeName();
				} else if (StringUtils.isEmpty(priceCorpVo
						.getFuelAddFeeSection()) && !StringUtils.isEmpty(priceCorpVo.getFuelAddFeeSectionName())) {
					verify = "系统没有燃油附加费："
							+ priceCorpVo.getFuelAddFeeSectionName();
				}
				if (StringUtils.isNotEmpty(verify)) {
					// 如果校验失败放弃导入
					break;
				}
				priceCorpList.add(priceCorpVo.copy());
				succCount++;
			} catch (Exception e) {
				failCount++;
				log.error("批量导入模版数据异常，业务需要仅作提示", e);
				verify = e.getMessage();
				// 如果失败放弃导入
				break;
			} finally {

				// priceCorpList.add(priceCorpVo.copy());
			}

		}
		retMap.put("succCount", succCount);
		retMap.put("failCount", failCount);
		if (StringUtils.isNotEmpty(verify)) {
			retMap.put(
					"repeatTip",
					"第"
							+ (priceCorpList.size() - succCount + 1)
							+ "行->"
							+ (StringUtils.isEmpty(repeat) ? (StringUtils
									.isEmpty(verify) ? "" : verify) : repeat + "重复"
									+ repeatMap.get(repeat) + "条"));
		}else{
			retMap.put("repeatTip","");
		}
		
		retMap.put("list", priceCorpList);
		return retMap;
	}

	@Override
	public String createExcelFile(List<PriceCorpGirdVo> details) {
		String filePath = null;
		List<String> titleNames = new ArrayList<String>();
		// 6、明细列表——导出，包含以下字段：
		// 门店名称，门店CODE，出发价格城市名称，
		// 出发价格城市CODE,到达价格城市名称，到达价格城市CODE,运输类型名称，运输类型CODE,重量单价,体积单价,附件费 ,最低收费
		// ,燃油服务费，生效时间，失效时间，备注，创建时间，创建用户，更改时间，更改人，状态，事业部，大区，路区
		titleNames.add("序号");
		titleNames.add("所属事业部");
		titleNames.add("所属大区");
		titleNames.add("所属路区");
		titleNames.add("所属门店");
		titleNames.add("物流代码");
		titleNames.add("到达价格城市");
		// titleNames.add("到达价格城市");
		titleNames.add("运输类型");
		titleNames.add("重量单价");
		titleNames.add("体积单价");
		titleNames.add("附件费");
		titleNames.add("最低收费");
		titleNames.add("燃油服务费");
		titleNames.add("生效时间");
		titleNames.add("失效时间");
		titleNames.add("当前状态");
		titleNames.add("备注");
		titleNames.add("创建人");
		titleNames.add("创建时间");
		titleNames.add("最后修改人");
		titleNames.add("最后修改时间");
		// 属性名，反射取属性
		List<String> paramNames = new ArrayList<String>();
		paramNames.add("index");
		paramNames.add("division");
		paramNames.add("bigRegion");
		paramNames.add("roadArea");
		paramNames.add("corpName");
		paramNames.add("logisticName");
		paramNames.add("arrivePriceCityName");
		paramNames.add("transTypeName");
		paramNames.add("weightPrice");
		paramNames.add("volumePrice");
		paramNames.add("addFee");
		paramNames.add("lowestFee");
		paramNames.add("fuelAddFeeSectionName");
		paramNames.add("effectiveTime");
		paramNames.add("invalidTime");
		paramNames.add("stateName");
		paramNames.add("remark");
		paramNames.add("createUserCode");
		paramNames.add("createTime");
		paramNames.add("modifyUserCode");
		paramNames.add("modifyTime");
		try {
			filePath = new ExcelUtil<PriceCorpGirdVo>().exportExcel(titleNames,
					paramNames, details);
		} catch (Exception e) {
			log.error("生成导出数据异常!", e);
			filePath = null;
		}

		return filePath;
	}

	/**
	 * 
	 * @Description: TODO 比较两个时间断，把数据库时间做为基准看前端数据是否合法
	 * @param @param dbeffectiveTime 数据库中生效时间
	 * @param @param dbinvalidTime 数据库失效时间
	 * @param @param pageeffectiveTime 页面生效时间
	 * @param @param pageinvalidTime 页面失效时间
	 * @param @return
	 * @return boolean 返回true 表示数据库不存在时间已经包含页面时间
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月28日
	 */
	private boolean compareRange(Date dbeffectiveTime, Date dbinvalidTime,
			Date pageeffectiveTime, Date pageinvalidTime) {
		Long dbinvalidTimeT = Long.MAX_VALUE;
		Long pageinvalidTimeT = Long.MAX_VALUE;
		if (dbinvalidTime != null) {
			dbinvalidTimeT = dbinvalidTime.getTime();
		}
		if (pageinvalidTime != null) {
			pageinvalidTimeT = pageinvalidTime.getTime();
		}
		return compareRange(dbeffectiveTime.getTime(), dbinvalidTimeT,
				pageeffectiveTime.getTime(), pageinvalidTimeT);
	}

	/**
	 * 
	 * @Description: TODO比较两个区间把区间一作为基准
	 * @param @param start1 区间一开始
	 * @param @param end1 区间一结束
	 * @param @param start2区间二开始
	 * @param @param end2区间二结束
	 * @param @return
	 * @return boolean true 表示 区间一不包含区间二，false表示区间一包含区间二
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月28日
	 */
	private boolean compareRange(Long start1, Long end1, Long start2, Long end2) {
		// 1作为参考数基准
		if (end2 <= start1)
			return true;
		if (end1 <= start2)
			return true;
		return false;
	}

}
