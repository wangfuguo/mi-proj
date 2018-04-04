package com.hoau.miser.module.biz.base.server.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingCustomerEntity;
import org.apache.ibatis.session.RowBounds;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityMappingCustomerService;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityMappingCustomerVo;
import com.hoau.miser.module.sys.base.api.shared.exception.MessageType;
import com.hoau.miser.module.util.define.MiserConstants;


/**
 * @description：老价格城市管理
 * @author 李玮琰
 *@date 2015年12月15日
 */
@Controller
@Scope("prototype")
public class PriceCityMappingCustomerAction extends AbstractAction{

	@Resource
	private IPriceCityMappingCustomerService priceCityMappingCustomerService;
	private PriceCityMappingCustomerVo priceCityCustomerVo;
	private File imgFile;
	//文件名
	private String imgFileFileName;


	private static final long serialVersionUID = 4322178912982130748L;

	@JSON
	public String index(){
		return "index";
	}

	/**
	 * @Description: TODO 在打开更新窗口时查询方法
	 * @param @return   
	 * @return String
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月6日
	 */
	@JSON
	public String queryPriceCityInUpdate()
	{
		try {
			priceCityCustomerVo.setPriceCityCustomerEntity(priceCityMappingCustomerService.queryPriceCityCustomerInUpdate(priceCityCustomerVo.getPriceCityCustomerEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 *
	 * @Description: 查询价格时效城市信息
	 * @return String
	 * @Author lhf
	 * @Time 2015年12月15日上午10:24:04
	 */
	@JSON
	public String queryPriceCityList(){
		try {
			if(priceCityCustomerVo ==null){
				priceCityCustomerVo =new PriceCityMappingCustomerVo();
				priceCityCustomerVo.setPriceCityCustomerEntity(new PriceCityMappingCustomerEntity());
				priceCityCustomerVo.setPriceCityList(new ArrayList<PriceCityMappingCustomerEntity>());
			}
			PriceCityMappingCustomerEntity paramObEntity= priceCityCustomerVo.getPriceCityCustomerEntity();
			paramObEntity.setActive(MiserConstants.ACTIVE);
			
			//add by dengyin 2016-6-3 15:23:27 导出时 是否有网点字段值  没有转义
			paramObEntity.setFuncFlag("query");
			//end by dengyin 2016-6-3 15:23:27 导出时 是否有网点字段值  没有转义
			
			
			List<PriceCityMappingCustomerEntity> outerBranchList=priceCityMappingCustomerService.queryPriceCityCustomerByEntity(paramObEntity, limit, start);
			totalCount=priceCityMappingCustomerService.countPriceCityCustomerByEntity(paramObEntity);
			priceCityCustomerVo.setPriceCityList(outerBranchList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}

	}
	/**
	 *
	 * @Description: TODO价格城市修改方法
	 * @param @return   
	 * @return String
	 * @throws
	 * @author 李玮琰
	 * @date 2015年12月22日
	 */
	@JSON
	public String updatePriceCityResource() {
		priceCityMappingCustomerService.updatePriceCityCustomer(priceCityCustomerVo.getPriceCityCustomerEntity());
		return returnSuccess(MessageType.UPDATE_SUCCESS);
	}



	/**
	 * @Description: 价格城市映射关系导入
	 * @param @return   
	 * @return String
	 * @throws
	 * @author 李玮琰
	 * @date 2015年12月30日
	 */
	public String cityImport() throws Exception{
		try {
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			String uploadPath = ServletActionContext.getServletContext().getRealPath(priceCityCustomerVo.getFilePath());
			Map<String,Object> returnMap= priceCityMappingCustomerService.priceCityCustomerImport(uploadPath);
			JSONObject json=new JSONObject();
			//addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
			json.put("addStartPriceCitySize", returnMap.get("addStartPriceCitySize"));
			json.put("coverStartPriceCitySize", returnMap.get("coverStartPriceCitySize"));
			json.put("addArrivePriceCitySize", returnMap.get("addArrivePriceCitySize"));
			json.put("coverArrivePriceCitySize", returnMap.get("coverArrivePriceCitySize"));
			json.put("errorFilePath", returnMap.get("errorFilePath"));
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	/**
	 * @Description: 价格城市导出(按查询条件)
	 * @param @return
	 * @param @throws Exception   
	 * @return String
	 * @throws
	 * @author 李玮琰
	 * @date 2016年1月5日
	 */
	public String excelExport() throws Exception{
		try {

			if(priceCityCustomerVo ==null) priceCityCustomerVo =new PriceCityMappingCustomerVo();
			
			//add by dengyin 2016-6-3 15:23:27 导出时 是否有网点字段值  没有转义
			priceCityCustomerVo.getPriceCityCustomerEntity().setFuncFlag("export");
			//end by dengyin 2016-6-3 15:23:27 导出时 是否有网点字段值  没有转义
			
			List<PriceCityMappingCustomerEntity> priceCityList=priceCityMappingCustomerService.queryPriceCityCustomerByEntity(priceCityCustomerVo.getPriceCityCustomerEntity(), RowBounds.NO_ROW_LIMIT, RowBounds.NO_ROW_OFFSET);

			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String  filePath= priceCityMappingCustomerService.createPriceCityCustomerExcelFile(priceCityList);
			JSONObject json=new JSONObject();
			json.put("filePath", filePath);
			json.put("count",priceCityList!=null? priceCityList.size():0);
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	public PriceCityMappingCustomerVo getPriceCityCustomerVo() {
		return priceCityCustomerVo;
	}

	public void setPriceCityCustomerVo(PriceCityMappingCustomerVo priceCityCustomerVo) {
		this.priceCityCustomerVo = priceCityCustomerVo;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}


}
