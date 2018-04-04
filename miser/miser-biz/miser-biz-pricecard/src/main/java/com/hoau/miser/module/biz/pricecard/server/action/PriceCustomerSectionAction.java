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
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerSectionService;
import com.hoau.miser.module.biz.pricecard.api.server.service.IPriceCustomerSectionSubService;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.ExcelPriceCustomerSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.domain.PriceCustomerSectionSubEntity;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSectionSubVo;
import com.hoau.miser.module.biz.pricecard.api.shared.vo.PriceCustomerSectionVo;

/**
 * 易入户客户价格管理 
 * ClassName: PriceCustomerSectionAction
 * 
 * @author 何羽
 * @date 2016年5月3日
 * @version V2.0
 */
@Controller
@Scope("prototype")
public class PriceCustomerSectionAction extends AbstractAction{

	private static final long serialVersionUID = 3843240196205450507L;
	
	private PriceCustomerSectionVo priceCustomerSectionVo;
	
	private PriceCustomerSectionSubVo priceCustomerSectionSubVo;
	
	@Resource
	private IPriceCustomerSectionService priceCustomerSectionService;
	
	@Resource
	private IPriceCustomerSectionSubService priceCustomerSectionSubService;

	public PriceCustomerSectionVo getPriceCustomerSectionVo() {
		return priceCustomerSectionVo;
	}

	public void setPriceCustomerSectionVo(PriceCustomerSectionVo priceCustomerSectionVo) {
		this.priceCustomerSectionVo = priceCustomerSectionVo;
	}

	public PriceCustomerSectionSubVo getPriceCustomerSectionSubVo() {
		return priceCustomerSectionSubVo;
	}

	public void setPriceCustomerSectionSubVo(PriceCustomerSectionSubVo priceCustomerSectionSubVo) {
		this.priceCustomerSectionSubVo = priceCustomerSectionSubVo;
	}
	
	@Override
	public String execute() throws Exception {
		return "index";
	}
	

	@JSON
	public String listCustomerInfo() {
		try {
			
			List<PriceCustomerSectionEntity> priceCustomerEntityList = priceCustomerSectionService
					.queryPriceCustomerMixed(getPriceCustomerSectionVo()
							.getPriceCustomerSectionEntity(), limit, start);

			getPriceCustomerSectionVo()
					.setPriceCunsomerSectionList(priceCustomerEntityList);

			if (priceCustomerEntityList != null) {
				setTotalCount( priceCustomerSectionService.countOfPriceCustomerMixed(getPriceCustomerSectionVo().getPriceCustomerSectionEntity()));
			}
			return returnSuccess();
			
			
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * @Description: 添加 易入户客户专属价格 及其对应的 明细记录
	 * @param
	 * @return void
	 * @throws
	 * @author 何羽
	 * @date 2016年1月11日
	 */
	@JSON
	public String addPriceCustomer() {

		try {
			
			String result = priceCustomerSectionService
					.addCustomerAndSubItemList(priceCustomerSectionVo);

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
	 * @Description: 对选中的易入户客户专属记录及其明细记录进行统一的更新操作
	 * @param @return
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年5月5日
	 */
	@JSON
	public String updatePriceCustomer() {
		try {
			
			String result = priceCustomerSectionService
					.updateCustomerAndSubItemList(priceCustomerSectionVo);

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
	 * @Description: 根据 id(t_price_customer_section.id) 易入户客户专属价格主表 的主键 查询出其对应信息 以及
	 *               其副表的对应的所有明细记录 供修改界面展示
	 * @param @return
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年5月6日
	 */
	@JSON
	public String queryPriceCustomerAndSubList() {
		// 实际为根据 id 查询
		List<PriceCustomerSectionEntity> priceCustomerEntityList = priceCustomerSectionService
				.queryPriceCustomerMixed(
						priceCustomerSectionVo.getPriceCustomerSectionEntity(), 1, start);
		PriceCustomerSectionEntity priceCustomerEntity = priceCustomerEntityList
				.get(0);

		// 设值 表单上部分的 客户专属价格主表信息
		priceCustomerSectionVo.setPriceCustomerSectionEntity(priceCustomerEntity);

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
	 * @Description: 根据 parentId 查询对应客户专属价格明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年5月6日
	 */
	@JSON
	public String queryCustomerSubListByParam() {
		if (priceCustomerSectionSubVo == null) {
			priceCustomerSectionSubVo = new PriceCustomerSectionSubVo();
		}
		getPriceCustomerSectionSubVo();
		// 如 parentId 为空 直接返回 因为 新增时无须加载
		if (priceCustomerSectionSubVo.getPriceCustomerSubEntity().getParentId() == null
				|| "".equals(priceCustomerSectionSubVo.getPriceCustomerSubEntity()
						.getParentId())) {
			return returnSuccess();
		}

		List<PriceCustomerSectionSubEntity> priceCustomerSubEntityList = priceCustomerSectionSubService
				.selectCustomerSubListByParam(
						priceCustomerSectionSubVo.getPriceCustomerSubEntity(), limit,
						start);
		priceCustomerSectionSubVo
				.setPriceCustomerSubEntityList(priceCustomerSubEntityList);
		if (priceCustomerSubEntityList != null) {
			setTotalCount((long) priceCustomerSubEntityList.size());
		} else {
			setTotalCount((long) 0);
		}

		return returnSuccess();
	}
	
	/**
	 * @Description: 对选中的 易入户客户专属记录 及其副表的明细记录 统一作废实现
	 * @param @return
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年1月14日
	 */
	@JSON
	public String destoryPriceCustomerAndSubList() {

        try {
    		String result = priceCustomerSectionService
    				.destoryPriceCustomerAndSubList(priceCustomerSectionVo
    						.getDestoryIdStr());

    		return returnSuccess(result);
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 
	 * @Description: 导入excel到客户价格编辑窗体
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年5月9日
	 */
	public String excelImpl() throws Exception {
		PrintWriter out  = null;
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext()
					.getRealPath(priceCustomerSectionVo.getFilePath());
			Map<String, Object> returnMap = priceCustomerSectionSubService
					.bathImplPriceCustomer(uploadPath);
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
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		} finally {
			if(null != out ){
				out.close();
			}
		}
	}

	/**
	 * 
	 * @Description: 导出易入户客户价格包含明细
	 * @param @return
	 * @param @throws IOException
	 * @return String
	 * @throws
	 * @author 何羽
	 * @date 2016年5月9日
	 */
	public String excelExport() throws IOException {
		PrintWriter out = null;
		try {
			List<ExcelPriceCustomerSectionEntity> excelPriceCustomerList = priceCustomerSectionService.excelQueryListByParamForStandard(priceCustomerSectionVo);
			if(excelPriceCustomerList != null && excelPriceCustomerList.size() > 0 ){
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				String filePath = priceCustomerSectionService.createExcelFile(excelPriceCustomerList,priceCustomerSectionVo.getExportType());
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
			List<PriceCustomerSectionEntity> priceCustomerEntityList = priceCustomerSectionService
					.excelPriceCustomer(priceCustomerSectionVo.getPriceCustomerSectionEntity());
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			String filePath = priceCustomerSectionService.createExcelFileForCustomer(priceCustomerEntityList);
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
}
