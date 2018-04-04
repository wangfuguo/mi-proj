package com.hoau.miser.module.biz.job.server.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.springframework.stereotype.Service;

import com.hoau.eai.pc.vo.PrivilegeContractDetailInfo;
import com.hoau.eai.pc.vo.PrivilegeContractInfo;
import com.hoau.eai.pc.vo.PrivilegeCouponAcceptorInfo;
import com.hoau.eai.pc.vo.PrivilegeCouponInfo;
import com.hoau.eai.pc.vo.PrivilegeDiscountInfo;
import com.hoau.eai.pc.vo.PrivilegeInfo;
import com.hoau.hbdp.framework.shared.util.ConfigFileLoadUtil;
import com.hoau.hbdp.framework.shared.util.classes.BeanUtils;
import com.hoau.miser.module.biz.job.server.dao.DiscountPrivilegeSendDao;
import com.hoau.miser.module.biz.job.server.service.IBaseSendService;
import com.hoau.miser.module.biz.job.server.service.IDiscountPrivilegeSendService;
import com.hoau.miser.module.biz.job.shared.define.DataSendJobConstant;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobFailureEntity;
import com.hoau.miser.module.biz.job.shared.domain.DataSendJobQueryFilterEntity;
import com.hoau.miser.module.biz.job.shared.domain.PrivilegeContractDetailEntity;
import com.hoau.miser.module.biz.job.shared.domain.PrivilegeContractEntity;
import com.hoau.miser.module.biz.job.shared.domain.PrivilegeCouponAcceptorEntity;
import com.hoau.miser.module.biz.job.shared.domain.PrivilegeCouponEntity;
import com.hoau.miser.module.biz.job.shared.domain.PrivilegeDiscountEntity;
import com.hoau.miser.module.biz.job.shared.domain.PrivilegeEntity;
import com.hoau.miser.module.util.StringUtils;
import com.hoau.miser.module.util.define.MiserConstants;

/**
 * 越发越惠数据同步Service实现
 * ClassName: DiscountPrivilegeSendService 
 * @author 286330付于令
 * @date 2016年2月23日
 * @version V1.0
 */
@Service
public class DiscountPrivilegeSendService implements IDiscountPrivilegeSendService {

	@Resource
	private IBaseSendService baseSendService;
	@Resource
	private DiscountPrivilegeSendDao discountPrivilegeSendDao;
	@Resource
	private Destination privilegeTopicDestination; // 越发越惠定义 队列
	@Resource
	private Destination privilegeContractTopicDestination; // 越发越惠客户合同 队列
	@Resource
	private Destination privilegeDiscountTopicDestination; // 越发越惠折扣 队列
	@Resource
	private Destination privilegeCouponTopicDestination; // 越发越惠返券 队列
	
	private static int MAX_SEND_COUNT; // 每次发送MQ的最大条数
	static {
		try {
			Properties prop = ConfigFileLoadUtil.getPropertiesForClasspath("config.properties");
			String maxCount = prop.getProperty("SENDMQ.PRIVILEGE.MAXCOUNT", "100");
			MAX_SEND_COUNT = StringUtils.toInt(maxCount);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @Description: 发送越发越惠定义数据
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月23日
	 */
	@Override
	public void sendPrivilege() {
		Date currentDate = new Date();
		String jobCode = DataSendJobConstant.JOB_CODE_PRIVILEGE;
		// 查询出上次发送失败的记录，并再次发送MQ
		DataSendJobFailureEntity failureQueryFilter = baseSendService
				.getFailureQueryFilter(jobCode);
		List<PrivilegeEntity> failurePrivilegeList = discountPrivilegeSendDao
				.queryFailurePrivilege(failureQueryFilter);
		List<PrivilegeInfo> infoList = new ArrayList<PrivilegeInfo>();
		for(PrivilegeEntity entity : failurePrivilegeList) {
			infoList.add(copy(entity));
		}
		// 查询上次发送之后到现在为止的数据，并发送MQ
		DataSendJobQueryFilterEntity queryFilter = baseSendService
				.getQueryFilter(jobCode, currentDate);
		List<PrivilegeEntity> privilegeList = discountPrivilegeSendDao
				.queryPrivilege(queryFilter);
		for(PrivilegeEntity entity : privilegeList) {
			infoList.add(copy(entity));
		}
		baseSendService.sendData(infoList, privilegeTopicDestination, MAX_SEND_COUNT);
		// 更新本次发送时间
		baseSendService.updateCurrentSendTime(jobCode, currentDate);
	}
	private PrivilegeInfo copy(PrivilegeEntity entity) {
		PrivilegeInfo info = new PrivilegeInfo();
		BeanUtils.copyProperties(entity, info);
		return info;
	}
	/**
	 * @Description: 发送越发越惠客户合同数据
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月23日
	 */
	@Override
	public void sendPrivilegeContract() {
		Date currentDate = new Date();
		String jobCode = DataSendJobConstant.JOB_CODE_PRIVILEGE_CONTRACT;
		// 查询出上次发送失败的记录，并再次发送MQ
		DataSendJobFailureEntity failureQueryFilter = baseSendService
				.getFailureQueryFilter(jobCode);
		List<PrivilegeContractEntity> failurePrivilegeContractList = discountPrivilegeSendDao
				.queryFailurePrivilegeContract(failureQueryFilter);
		List<PrivilegeContractInfo> infoList = new ArrayList<PrivilegeContractInfo>();
		for(PrivilegeContractEntity entity : failurePrivilegeContractList) {
			infoList.add(copy(entity));
		}
		// 查询上次发送之后到现在为止的数据，并发送MQ
		DataSendJobQueryFilterEntity queryFilter = baseSendService
				.getQueryFilter(jobCode, currentDate);
		List<PrivilegeContractEntity> privilegeContractList = discountPrivilegeSendDao
				.queryPrivilegeContract(queryFilter);
		for(PrivilegeContractEntity entity : privilegeContractList) {
			infoList.add(copy(entity));
		}
		baseSendService.sendData(infoList, privilegeContractTopicDestination, MAX_SEND_COUNT);
		// 更新本次发送时间
		baseSendService.updateCurrentSendTime(jobCode, currentDate);
	}
	private PrivilegeContractInfo copy(PrivilegeContractEntity entity) {
		PrivilegeContractInfo info = new PrivilegeContractInfo();
		BeanUtils.copyProperties(entity, info);
		List<PrivilegeContractDetailEntity> detail = entity.getDetail();
		if(detail != null && detail.size() > 0) {
			List<PrivilegeContractDetailInfo> detailInfo = new ArrayList<PrivilegeContractDetailInfo>();
			for(PrivilegeContractDetailEntity detailEntity : detail) {
				PrivilegeContractDetailInfo e = new PrivilegeContractDetailInfo();
				BeanUtils.copyProperties(detailEntity, e);
				detailInfo.add(e);
			}
			info.setDetail(detailInfo);
		}
		return info;
	}
	/**
	 * @Description: 发送越发越惠折扣数据
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	@Override
	public void sendPrivilegeDiscount() {
		Date currentDate = new Date();
		String jobCode = DataSendJobConstant.JOB_CODE_PRIVILEGE_DISCOUNT;
		// 查询出上次发送失败的记录，并再次发送MQ
		DataSendJobFailureEntity failureQueryFilter = baseSendService
				.getFailureQueryFilter(jobCode);
		List<PrivilegeDiscountEntity> failurePrivilegeDiscountList = discountPrivilegeSendDao
				.queryFailurePrivilegeDiscount(failureQueryFilter);
		List<PrivilegeDiscountInfo> infoList = new ArrayList<PrivilegeDiscountInfo>();
		for(PrivilegeDiscountEntity entity : failurePrivilegeDiscountList) {
			infoList.add(copy(entity));
		}
		// 查询上次发送之后到现在为止的数据，并发送MQ
		DataSendJobQueryFilterEntity queryFilter = baseSendService
				.getQueryFilter(jobCode, currentDate);
		List<PrivilegeDiscountEntity> privilegeDiscountList = discountPrivilegeSendDao
				.queryPrivilegeDiscount(queryFilter);
		for(PrivilegeDiscountEntity entity : privilegeDiscountList) {
			infoList.add(copy(entity));
		}
		baseSendService.sendData(infoList, privilegeDiscountTopicDestination, MAX_SEND_COUNT);
		// 更新本次发送时间
		baseSendService.updateCurrentSendTime(jobCode, currentDate);
	}
	private PrivilegeDiscountInfo copy(PrivilegeDiscountEntity entity) {
		PrivilegeDiscountInfo info = new PrivilegeDiscountInfo();
		BeanUtils.copyProperties(entity, info);
		return info;
	}
	/**
	 * @Description: 发送越发越惠返券数据
	 * @return void 
	 * @author 286330付于令
	 * @date 2016年2月24日
	 */
	@Override
	public void sendPrivilegeCoupon() {
		Date currentDate = new Date();
		String jobCode = DataSendJobConstant.JOB_CODE_PRIVILEGE_COUPON;
		// 查询出上次发送失败的记录，并再次发送MQ
		DataSendJobFailureEntity failureQueryFilter = baseSendService
				.getFailureQueryFilter(jobCode);
		List<PrivilegeCouponEntity> failurePrivilegeCouponList = discountPrivilegeSendDao
				.queryFailurePrivilegeCoupon(failureQueryFilter);
		List<PrivilegeCouponInfo> infoList = new ArrayList<PrivilegeCouponInfo>();
		for(PrivilegeCouponEntity entity : failurePrivilegeCouponList) {
			infoList.add(copy(entity));
		}
		// 查询上次发送之后到现在为止的数据，并发送MQ
		DataSendJobQueryFilterEntity queryFilter = baseSendService
				.getQueryFilter(jobCode, currentDate);
		List<PrivilegeCouponEntity> privilegeCouponList = discountPrivilegeSendDao
				.queryPrivilegeCoupon(queryFilter);
		for(PrivilegeCouponEntity entity : privilegeCouponList) {
			// 可申请状态为PENDING_APPLICATION，不可申请为空，其他状态保持原状态
			if(StringUtils.isEmptyWithBlock(entity.getCouponState())) {
				String hasCoupon = entity.getHasCoupon();
				Double shouldPayAmountSum = entity.getShouldPayAmountSum();
				if(MiserConstants.YES.equals(hasCoupon) && 
						(shouldPayAmountSum ==null || shouldPayAmountSum == 0.0)) {
					entity.setCouponState("PENDING_APPLICATION");
				}
			}
			infoList.add(copy(entity));
		}
		baseSendService.sendData(infoList, privilegeCouponTopicDestination, MAX_SEND_COUNT);
		// 更新本次发送时间
		baseSendService.updateCurrentSendTime(jobCode, currentDate);
	}
	private PrivilegeCouponInfo copy(PrivilegeCouponEntity entity) {
		PrivilegeCouponInfo info = new PrivilegeCouponInfo();
		BeanUtils.copyProperties(entity, info);
		PrivilegeCouponAcceptorEntity acceptor = entity.getAcceptor();
		if(acceptor != null) {
			PrivilegeCouponAcceptorInfo acceptorInfo = new PrivilegeCouponAcceptorInfo();
			BeanUtils.copyProperties(acceptor, acceptorInfo);
			info.setAcceptor(acceptorInfo);
		}
		return info;
	}

}
