/**   
 * @Title: DiscountCustomerAction.java 
 * @Package com.hoau.miser.module.biz.discount.server.action 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 陈启勇 
 * @date 2016年1月5日 下午4:34:24 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.discount.server.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.hoau.miser.module.common.shared.domain.ExcelExportResultEntity;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.discount.api.server.service.IDiscountCustomerService;
import com.hoau.miser.module.biz.discount.api.shared.domain.DiscountCustomerEntity;
import com.hoau.miser.module.biz.discount.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.discount.api.shared.vo.DiscountCustomerVo;

/**
 * ClassName: DiscountCustomerAction
 * 
 * @author 陈启勇
 * @date 2016年1月5日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class DiscountCustomerAction extends AbstractAction {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 8341554539267121620L;

	@Resource
	private IDiscountCustomerService discountCustomerService;

	private DiscountCustomerVo discountCustomerVo;

	public String execute() {
		return "index";
	}

	/**
	 * 
	 * @Description: 查询客户折扣
	 * @param @return
	 * @return String
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月5日
	 */
	@JSON
	public String queryListByParam() {
		try {
			if (discountCustomerVo == null) {
				discountCustomerVo = new DiscountCustomerVo();
			}
			discountCustomerVo.getDiscountCustomerEntity().setNowDate(
					new Date());
			String state = discountCustomerVo.getDiscountCustomerEntity()
					.getState();
			if (StringUtil.isEmpty(state)) {
				discountCustomerVo.getDiscountCustomerEntity().setState("5");
			}
			List<DiscountCustomerEntity> discountCorpList = discountCustomerService
					.queryListByParam(discountCustomerVo, limit, start);
			totalCount = discountCustomerService
					.queryCountByParam(discountCustomerVo);
			discountCustomerVo.setDiscountCustomerList(discountCorpList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 增加客户折扣
	 * 
	 * @Description: addDiscountCustomer
	 * @return String
	 * @Author 陈启勇
	 * @Time 2016年1月6日
	 */
	@JSON
	public String addDiscountCustomer() {
		try {
			DiscountCustomerEntity discountCustomerEntity = discountCustomerVo
					.getDiscountCustomerEntity();
			Integer result = discountCustomerService.addDiscountCustomer(
					discountCustomerEntity,
					"1".equals(discountCustomerVo.getIsConfirm()));
			if (result.intValue() == 0) {
				return returnSuccess(MessageType.CUSTOMERSTANDARD_ISCONFIRM);
			} else if (result.intValue() == 2) {
				return returnSuccess(MessageType.CUSTOMERS_VERIFICATION);
			} else if (result.intValue() == 3) {
				return returnSuccess(MessageType.TIME_ISCONFIRM);
			} else {
				return returnSuccess(MessageType.ADD_SUCCESS);
			}

		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 根据id得到客户折扣信息
	 * 
	 * @return String
	 * @Author 陈启勇
	 * @Time 2016年1月7日
	 */
	@JSON
	public String queryById() {
		try {
			DiscountCustomerEntity discountCustomerEntity = new DiscountCustomerEntity();
			discountCustomerEntity = discountCustomerService
					.queryById(discountCustomerVo.getDiscountCustomerEntity()
							.getId());
			discountCustomerVo
					.setDiscountCustomerEntity(discountCustomerEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	@JSON
	public String queryAddById() {
		try {
			DiscountCustomerEntity discountCustomerEntity = new DiscountCustomerEntity();
			String id = discountCustomerVo.getDiscountCustomerEntity().getId();
			if (StringUtil.isNotEmpty(id)) {
				DiscountCustomerEntity bean = new DiscountCustomerEntity();
				bean = discountCustomerService.queryById(discountCustomerVo
						.getDiscountCustomerEntity().getId());
				discountCustomerEntity
						.setTransTypeCode(bean.getTransTypeCode());
				discountCustomerEntity
						.setTransTypeName(bean.getTransTypeName());
				discountCustomerEntity.setCustomerCode(bean.getCustomerCode());
				discountCustomerEntity.setCustomerName(bean.getCustomerName());
			}
			discountCustomerVo
					.setDiscountCustomerEntity(discountCustomerEntity);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 修改客户折扣
	 * 
	 * @Description: updateDiscountCustomer
	 * @return String
	 * @Author 陈启勇
	 * @Time 2016年1月8日
	 */
	@JSON
	public String updateDiscountCustomer() {
		try {
			DiscountCustomerEntity discountCustomerEntity = discountCustomerVo
					.getDiscountCustomerEntity();
			Integer result = discountCustomerService.updateDiscountCustomer(
					discountCustomerEntity,
					"1".equals(discountCustomerVo.getIsConfirm()));
			if (result.intValue() == 0) {
				return returnSuccess(MessageType.CORPSTANDARD_ISCONFIRM);
			} else if (result.intValue() == 3) {
				return returnSuccess(MessageType.TIME_ISCONFIRM);  //失效时间不能小于等于生效时间
			} else if (result.intValue() == 2) {  //存在重复的时间段
				return returnSuccess(MessageType.REPEAT_TIME_ZONE);
			}else {
				return returnSuccess(MessageType.UPDATE_SUCCESS);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 作废客户折扣
	 * 
	 * @Description: deleteDiscountCustomer
	 * @return String
	 * @Author 陈启勇
	 * @Time 2016年1月8日
	 */
	@JSON
	public String deleteDiscountCustomer() {
		try {

			List<DiscountCustomerEntity> list = JsonUtils.toList(
					discountCustomerVo.selects, DiscountCustomerEntity.class);
			discountCustomerService.bathDelDiscountCustomer(list);
			return returnSuccess(MessageType.DELETE_SUCCESS);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 
	 * @Description: excel导出
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 * @author mao.wang@newHoau.com.cn
	 * @date 2016年4月7日
	 */
	public void exportExcelDiscountCustomer() throws Exception {
		// downloadFilePath
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		try {
			ExcelExportResultEntity resultEntity = discountCustomerService
					.createExcelFileToSql(discountCustomerVo
							.getDiscountCustomerEntity());
			if (resultEntity == null) {
				json.put("error", "导出错误--查询条件无效");
			}else{
				json.put("filePath", resultEntity.getFilePath());
				json.put("count", resultEntity.getRecordCount());
			}
			if(resultEntity.getRecordCount() == 0){
				json.put("error", "没有需要导出的数据");
			}
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (BusinessException e) {
			out.println(e.getMessage());
			out.flush();
			out.close();
		}
	}

	/**
	 * 
	 * @Description: excel模版导出
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月18日
	 */
	public void exportExcel() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String filePath = discountCustomerService.createExcelFile();
		JSONObject json = new JSONObject();
		// filePath:错误的信息的文件地址
		json.put("filePath", filePath);
		out.println(json.toString());
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @Description: excel导入方法
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 * @author 陈启勇
	 * @date 2016年1月18日
	 */
	public void implExcel() throws Exception {
		PrintWriter out = null;
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext()
					.getRealPath(discountCustomerVo.getFilePath());
			Map<String, Object> returnMap = discountCustomerService
					.bathImplDiscountCustomer(uploadPath);
			JSONObject json = new JSONObject();
			// addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
			json.put("addSize", returnMap.get("addSize"));
			json.put("coverSize", returnMap.get("coverSize"));
			json.put("sumSize", returnMap.get("sumSize"));
			json.put("filePath", returnMap.get("filePath"));
			json.put("error", returnMap.get("error"));
			out.println(json.toString());
			out.flush();
			out.close();
			//return returnSuccess();
		} catch (BusinessException e) {
			//return returnError(e);
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	/**
	 * @return the discountCustomerVo
	 */
	public DiscountCustomerVo getDiscountCustomerVo() {
		return discountCustomerVo;
	}

	/**
	 * @param discountCustomerVo
	 *            the discountCustomerVo to set
	 */
	public void setDiscountCustomerVo(DiscountCustomerVo discountCustomerVo) {
		this.discountCustomerVo = discountCustomerVo;
	}
}
