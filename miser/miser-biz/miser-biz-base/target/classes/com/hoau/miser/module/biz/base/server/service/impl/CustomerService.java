package com.hoau.miser.module.biz.base.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;
import com.hoau.miser.module.common.shared.util.ExcelUtil;
import com.hoau.miser.module.util.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.hoau.miser.module.biz.base.api.server.service.ICustomerService;
import com.hoau.miser.module.biz.base.api.shared.domain.CustomerEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.CustomerVo;
import com.hoau.miser.module.biz.base.server.dao.CustomerDao;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;

/**
 * ClassName: CustomerService 
 * @Description: 客户信息维护实现 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年3月8日
 * @version V1.0   
 */
@Service
public class CustomerService implements ICustomerService {

	@Resource
	private CustomerDao customerDao;
	
	@Override
	public ArrayList<CustomerEntity> queryCustomers(CustomerVo customerVo,
			int limit, int start) {
		CustomerEntity queryParam = customerVo.getQueryParam();
		RowBounds bounds = new RowBounds(start, limit);
		return customerDao.queryCustomers(queryParam, bounds);
	}

	@Override
	public ArrayList<CustomerEntity> queryExcelCustomers(CustomerVo customerVo) {
		CustomerEntity queryParam = customerVo.getQueryParam();
		return customerDao.queryExcelCustomers(queryParam);
	}

	@Override
	public Long queryCustomerCount(CustomerVo customerVo) {
		return customerDao.queryCustomerCount(customerVo.getQueryParam());
	}

	@Override
	public CustomerEntity queryCustomerById(CustomerVo customerVo) {
		return customerDao.queryCustomerById(customerVo.getQueryParam());
	}

	@Override
	public void updateCustomer(CustomerVo customerVo) {
		CustomerEntity queryParam = customerVo.getQueryParam();
		queryParam.setModifyDate(new Date());
		queryParam.setModifyUser(MiserUserContext.getCurrentUser().getEmpNameAndUserName());
		customerDao.updateCustomer(queryParam);
	}

	/**
	 * @param entity
	 * @return void
	 * @Description: 将需要导出的客户信息生成excel文件
	 * @author yulin.chen@newhoau.com.cn
	 * @date 2016年3月8日
	 */
	@Override
	public ExcelExportResultEntity createCustomerExcelFile(CustomerEntity entity) {
		if (entity == null) {
			return null;
		}
		String filePath=null;
		List<String> titleNames=new ArrayList<String>();
		titleNames.add("客户编码");
		titleNames.add("客户名称");
		titleNames.add("客户所属组织");
		titleNames.add("客户所属组织名称");
		titleNames.add("物流代码");
		titleNames.add("合同开始时间");
		titleNames.add("合同结束时间");
		titleNames.add("价格开始时间");
		titleNames.add("价格结束时间");
		titleNames.add("是否启用客户价格");
		titleNames.add("是否启用客户折扣");
		titleNames.add("签收单是否显示运费金额");
		titleNames.add("易到家产品使用定日达价格");
		titleNames.add("备注");
		titleNames.add("是否可用");
		titleNames.add("修改人");
		titleNames.add("修改日期");
		titleNames.add("创建人");
		titleNames.add("创建日期");
		List<Object> paramNames=new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  ");
		sql.append("T.CODE AS CODE, ");
		sql.append("T.NAME AS NAME, ");
		sql.append("T.ORG_CODE AS ORG_CODE, ");
		sql.append("ORG.NAME AS ORG_NAME, ");
		sql.append("ORG.LOGIST_CODE AS LOGISTIC_NAME, ");
		sql.append("T.CONTRACT_START_TIME AS CONTRACT_START_TIME, ");
		sql.append("T.CONTRACT_END_TIME AS CONTRACT_END_TIME, ");
		sql.append("T.PC_START_TIME AS PC_START_TIME, ");
		sql.append("T.PC_END_TIME AS PC_END_TIME, ");
		sql.append("DECODE(T.USE_CUSTOMER_PC, 'Y', '是', '否') AS USE_CUSTOMER_PC, ");
		sql.append("DECODE(T.USE_CUSTOMER_DISCOUNT, 'Y', '是', '否') AS USE_CUSTOMER_DISCOUNT, ");
		sql.append("DECODE(T.SIGNBILL_SHOW_MONEY, 'Y', '是', '否') AS SIGNBILL_SHOW_MONEY, ");
		sql.append("DECODE(T.YDJ_USE_DRD, 'Y', '是', '否') AS YDJ_USE_DRD, ");
		sql.append("T.REMARK AS REMARK, ");
		sql.append("DECODE(T.ACTIVE, 'Y', '是', '否') AS ACTIVE, ");
		sql.append("T.MODIFY_USER_CODE AS MODIFY_USER_CODE, ");
		sql.append("T.MODIFY_TIME AS MODIFY_TIME, ");
		sql.append("T.CREATE_USER_CODE AS CREATE_USER_CODE, ");
		sql.append("T.CREATE_TIME AS CREATE_TIME ");
		
		
		sql.append("FROM T_BSE_CUSTOMER T LEFT JOIN T_BSE_ORG ORG ON T.ORG_CODE = ORG.CODE ");
		sql.append("WHERE 1 = 1");  // ORG.ACTIVE = 'Y'
		if (!StringUtils.isEmptyWithBlock(entity.getCode())) {
			sql.append("AND T.CODE = ? ");
			paramNames.add(entity.getCode());
		}
		if (!StringUtils.isEmptyWithBlock(entity.getName())) {
			sql.append("AND T.NAME LIKE ? ");
			paramNames.add("%" + entity.getName() + "%");
		}
		if (!StringUtils.isEmptyWithBlock(entity.getOrgCode())) {
			sql.append("AND T.ORG_CODE = ? ");
			paramNames.add(entity.getOrgCode());
		}
		if (!StringUtils.isEmptyWithBlock(entity.getActive())) {
			sql.append("AND T.ACTIVE = ? ");
			paramNames.add(entity.getActive());
		}
		ExcelExportResultEntity result = new ExcelUtil<CustomerEntity>().sqlTofile(titleNames, sql.toString(), paramNames);
		return result;
	}
}
