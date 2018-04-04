/**   
 * @Title: PriceCustomerAction.java 
 * @Package com.hoau.miser.module.biz.pricecard.server.action 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author dengyin
 * @date 2016年1月5日 下午1:33:55 
 * @version V1.0   
 */
package com.hoau.miser.module.biz.pricecard.server.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.pricecard.api.server.service.IBseCustomerService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerSubService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.BseCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.BseCustomerVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSubVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerVo;
import com.hoau.miser.module.sys.base.api.server.service.ICommonOrgService;
import com.hoau.miser.module.sys.base.api.shared.domain.CommonOrgEntity;
import com.hoau.miser.module.sys.base.api.shared.vo.CommonOrgVo;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;

/**
 * 客户专属价格管理 ClassName: PriceCustomerAction
 * 
 * @author dengyin
 * @date 2016年1月5日
 * @version V1.0
 */
@Controller
@Scope("prototype")
public class PriceCustomerAction extends AbstractAction {

	private static final long serialVersionUID = -2854034324832976354L;

	private CommonOrgVo commonOrgVo;

	private BseCustomerVo bseCustomerVo;

	private PriceCustomerVo priceCustomerVo;

	private PriceCustomerSubVo priceCustomerSubVo;

	@Resource
	private ICommonOrgService commonOrgService;

	@Resource
	private IPriceCustomerService priceCustomerService;

	@Resource
	private IPriceCustomerSubService priceCustomerSubService;

	@Resource
	private IBseCustomerService bseCustomerService;
	
	//add by dengyin 2016-3-17 18:33:46 是否为新客户
	private String customerType;
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	//end by dengyin 2016-3-17 18:33:46 是否为新客户



	@Override
	public String execute() throws Exception {
		return "index";
	}

	@JSON
	public String searchDivisionList() {
		commonOrgVo.getOrgSearchConditionEntity().setLimit(1000);
		List<CommonOrgEntity> commonOrgEntityList = commonOrgService
				.queryOrgByParam(commonOrgVo.getOrgSearchConditionEntity());
		setTotalCount(commonOrgService.countOrgByParam(commonOrgVo
				.getOrgSearchConditionEntity()));
		commonOrgVo.setCommonOrgEntityList(commonOrgEntityList);
		return returnSuccess();
	}

	/**
	 * @Description: 查询客户列表
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月6日
	 */
	@JSON
	public String queryCustomerListByParam() {
		if (bseCustomerVo == null) {
			bseCustomerVo = new BseCustomerVo();
		}
		List<BseCustomerEntity> bseCustomerEntityList = bseCustomerService
				.selectCustomerAllList(bseCustomerVo, limit, start);
		bseCustomerVo.setBseCustomerEntityList(bseCustomerEntityList);
		setTotalCount((long) bseCustomerEntityList.size());
		return returnSuccess();
	}

	/**
	 * @Description: 新增客户专属价格明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月8日
	 */
	@JSON
	public String addPriceCustomerSub() {
		String recordId = priceCustomerSubService
				.insertSelective(priceCustomerSubVo.getPriceCustomerSubEntity());
		priceCustomerSubVo.setAddRecordId(recordId);
		return returnSuccess(MessageType.ADD_SUCCESS);
	}

	/**
	 * @Description: 修改客户专属价格明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月8日
	 */
	@JSON
	public String updatePriceCustomerSub() {
		priceCustomerSubService
				.updateByPrimaryKeySelective(getPriceCustomerSubVo()
						.getPriceCustomerSubEntity());
		return returnSuccess(MessageType.UPDATE_SUCCESS);
	}

	@JSON
	public String deletePriceCustomerSub() {
		priceCustomerSubService
				.updateByPrimaryKeySelective(getPriceCustomerSubVo()
						.getPriceCustomerSubEntity());
		return returnSuccess(MessageType.DELETE_SUCCESS);
	}

	@JSON
	public String batchUpdateActiveByIdStr() {
		 
		priceCustomerSubService
				.batchUpdateActiveByIdStr(getPriceCustomerSubVo());
		return returnSuccess(MessageType.DELETE_SUCCESS);
	}

	/**
	 * 
	 * @Description: 根据 parentId 查询对应客户专属价格明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月8日
	 */
	@JSON
	public String queryCustomerSubListByParam() {
		if (priceCustomerSubVo == null) {
			priceCustomerSubVo = new PriceCustomerSubVo();
		}

		// 如 parentId 为空 直接返回 因为 新增时无须加载
		if (priceCustomerSubVo.getPriceCustomerSubEntity().getParentId() == null
				|| "".equals(priceCustomerSubVo.getPriceCustomerSubEntity()
						.getParentId())) {
			return returnSuccess();
		}

		List<PriceCustomerSubEntity> priceCustomerSubEntityList = priceCustomerSubService
				.selectCustomerSubListByParam(
						priceCustomerSubVo.getPriceCustomerSubEntity(), limit,
						start);
		priceCustomerSubVo
				.setPriceCustomerSubEntityList(priceCustomerSubEntityList);
		if (priceCustomerSubEntityList != null) {
			setTotalCount((long) priceCustomerSubEntityList.size());
		} else {
			setTotalCount((long) 0);
		}

		return returnSuccess();
	}

	/**
	 * @Description: 添加 客户专属价格 及其对应的 明细记录
	 * @param
	 * @return void
	 * @throws
	 * @author dengyin
	 * @date 2016年1月11日
	 */
	@JSON
	public String addPriceCustomer() {

		try {
			
			String result = priceCustomerService
					.addCustomerAndSubItemList(priceCustomerVo);

			if ("success".equals(result)) {
				return returnSuccess(MessageType.ADD_SUCCESS);
			} else {
				return returnError(result);
			}
			
		} catch (BusinessException e) {
			return returnError(e);
		}


	}

	/**
	 * @Description: 对选中的客户专属记录及其明细记录进行统一的更新操作
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月14日
	 */
	@JSON
	public String updatePriceCustomer() {
		try {
			
			String result = priceCustomerService
					.updateCustomerAndSubItemList(priceCustomerVo);

			if ("success".equals(result)) {
				return returnSuccess(MessageType.UPDATE_SUCCESS);
			} else {
				return returnError(result);
			}
			
		} catch (BusinessException e) {
			return returnError(e);
		}

	}

	/**
	 * @Description: 对选中的 客户专属记录 及其副表的明细记录 统一作废实现
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月14日
	 */
	@JSON
	public String destoryPriceCustomerAndSubList() {

        try {
    		String result = priceCustomerService
    				.destoryPriceCustomerAndSubList(priceCustomerVo
    						.getDestoryIdStr());

    		return returnSuccess(result);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	@JSON
	public String listCustomerInfo() {
		try {
			
			List<PriceCustomerEntity> priceCustomerEntityList = priceCustomerService
					.queryPriceCustomerMixed(getPriceCustomerVo()
							.getPriceCustomerEntity(), limit, start);

			getPriceCustomerVo()
					.setPriceCustomerEntityList(priceCustomerEntityList);

			if (priceCustomerEntityList != null) {
				setTotalCount( priceCustomerService.countOfPriceCustomerMixed(getPriceCustomerVo().getPriceCustomerEntity()) );
			}
			return returnSuccess();
			
			
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 
	 * @Description: 根据 客户编号 查询 客户信息 用于新增界面 无勾选时 填充默认的 事业部 大区 等基础信息
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月13日
	 */
	@JSON
	public String queryPriceCustomerOrgInfo() {
	      try {
			
	  		priceCustomerVo = new PriceCustomerVo();
	
	  		// 虽然后台对应于 code 是 like 查询 但这里传递的 code 是完整的 能定位到唯一的记录
	  		PriceCustomerEntity priceCustomerEntity = priceCustomerService
	  				.queryPriceCustomerOrgInfo(MiserUserContext.getCurrentUser()
	  						.getOrgCode());
	  		priceCustomerVo.setPriceCustomerEntity(priceCustomerEntity);
	
	  		return returnSuccess();
	  		
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * @Description: 根据 id(t_price_customer.id) 客户专属价格主表 的主键 查询出其对应信息 以及
	 *               其副表的对应的所有明细记录 供修改界面展示
	 * @param @return
	 * @return String
	 * @throws
	 * @author dengyin
	 * @date 2016年1月13日
	 */
	@JSON
	public String queryPriceCustomerAndSubList() {
		// 实际为根据 id 查询
		List<PriceCustomerEntity> priceCustomerEntityList = priceCustomerService
				.queryPriceCustomerMixed(
						priceCustomerVo.getPriceCustomerEntity(), 1, start);
		PriceCustomerEntity priceCustomerEntity = priceCustomerEntityList
				.get(0);

		// 设值 表单上部分的 客户专属价格主表信息
		priceCustomerVo.setPriceCustomerEntity(priceCustomerEntity);

	   /*
		PriceCustomerSubEntity priceCustomerSubEntity = new PriceCustomerSubEntity();
		priceCustomerSubEntity.setParentId(priceCustomerVo
				.getPriceCustomerEntity().getId());

		List<PriceCustomerSubEntity> priceCustomerSubEntityList = priceCustomerSubService.selectCustomerSubListByParam(priceCustomerSubEntity);

		// 设值 表单上部分的 客户专属价格副表的明细信息
		priceCustomerVo.setPriceCustomerSubEntityList(priceCustomerSubEntityList);
		*/

		return returnSuccess();
	}

	/**
	 * 
	 * @Description: 导入excel到客户价格编辑窗体
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 * @author 赵金义
	 * @date 2016年1月20日
	 */
	public void excelImpl() throws Exception {
		PrintWriter out  = null;
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext()
					.getRealPath(priceCustomerVo.getFilePath());
			Map<String, Object> returnMap = priceCustomerSubService
					.bathImplPriceCustomer(uploadPath,customerType);
			JSONObject json = new JSONObject();
			// succCount:成功数量,failCount:失败数量,list: 导入的客户价格明细
			json.put("succCount", returnMap.get("succCount"));
			json.put("failCount", returnMap.get("failCount"));
			json.put("list", returnMap.get("list"));
			json.put("error", returnMap.get("error"));
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			//return returnSuccess();
		} catch (BusinessException e) {
			//return returnError(e);
		} finally {
			if(null != out ){
				out.close();
			}
		}
	}

	/**
	 * 
	 * @Description: 导出客户价格包含明细
	 * @param @return
	 * @param @throws IOException
	 * @return String
	 * @throws
	 * @author 赵金义
	 * @date 2016年1月20日
	 */
	public String excelExport() throws IOException {
		PrintWriter out = null;
		try {
			List<ExcelPriceCustomerEntity> excelPriceCustomerList = null;
			
			if("Y".equals(priceCustomerVo.getPriceCustomerEntity().getCustomerType())){
				excelPriceCustomerList = priceCustomerService.excelQueryListByParamForStandard(priceCustomerVo);
			} else {
				excelPriceCustomerList = priceCustomerService.queryExcelListByParam(priceCustomerVo);				
			}
			
			if(excelPriceCustomerList != null && excelPriceCustomerList.size() > 0 ){
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				String filePath = priceCustomerService.createExcelFile(excelPriceCustomerList,priceCustomerVo.getExportType());
				JSONObject json = new JSONObject();
				// filePath:错误的信息的文件地址
				json.put("filePath", filePath);
				json.put("count",
						excelPriceCustomerList != null ? excelPriceCustomerList.size()
								: 0);
				out.println(json.toString());
				out.flush();
				out.close();
				return returnSuccess();
			}
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		} finally {
			if(null != out ){
				out.close();
			}
		}
	}
	
	public String excelExportCustomer() throws IOException {
		PrintWriter out = null;
		try {
			List<PriceCustomerEntity> priceCustomerEntityList = priceCustomerService
					.excelPriceCustomer(priceCustomerVo.getPriceCustomerEntity());
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String filePath = priceCustomerService.createExcelFileForCustomer(priceCustomerEntityList);
			JSONObject json = new JSONObject();
			// filePath:错误的信息的文件地址
			json.put("filePath", filePath);
			json.put("count",priceCustomerEntityList != null ? priceCustomerEntityList.size(): 0);
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
		finally{
			if(null != out){
				out.close();
			}
		}
	}
	
	 
	public CommonOrgVo getCommonOrgVo() {
		return commonOrgVo;
	}

	public void setCommonOrgVo(CommonOrgVo commonOrgVo) {
		this.commonOrgVo = commonOrgVo;
	}

	public BseCustomerVo getBseCustomerVo() {
		return bseCustomerVo;
	}

	public void setBseCustomerVo(BseCustomerVo bseCustomerVo) {
		this.bseCustomerVo = bseCustomerVo;
	}

	public PriceCustomerVo getPriceCustomerVo() {
		return priceCustomerVo;
	}

	public void setPriceCustomerVo(PriceCustomerVo priceCustomerVo) {
		this.priceCustomerVo = priceCustomerVo;
	}

	public PriceCustomerSubVo getPriceCustomerSubVo() {
		return priceCustomerSubVo;
	}

	public void setPriceCustomerSubVo(PriceCustomerSubVo priceCustomerSubVo) {
		this.priceCustomerSubVo = priceCustomerSubVo;
	}

}
