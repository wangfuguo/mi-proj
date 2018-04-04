package com.hoau.miser.module.biz.base.server.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.hoau.miser.module.biz.base.api.shared.domain.PriceCityMappingEntity;
import org.apache.ibatis.session.RowBounds;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.server.service.IPriceCityMappingService;
import com.hoau.miser.module.biz.base.api.shared.vo.PriceCityMappingVo;
import com.hoau.miser.module.sys.base.api.shared.exception.MessageType;
import com.hoau.miser.module.util.define.MiserConstants;


/**
 * @description：价格城市管理
 * @author 刘海飞
 *@date 2015年12月15日
 */
@Controller
@Scope("prototype")
public class PriceCityMappingAction extends AbstractAction{

	@Resource
	private IPriceCityMappingService priceCityMappingService;
	private PriceCityMappingVo priceCityMappingVo;
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
	 * @author 刘海飞
	 * @date 2016年1月6日
	 */
	@JSON
	public String queryPriceCityInUpdate()
	{
		try {
			priceCityMappingVo.setPriceCityMappingEntity(priceCityMappingService.queryPriceCityMappingInUpdate(priceCityMappingVo.getPriceCityMappingEntity()));
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
			if(priceCityMappingVo ==null){
				priceCityMappingVo =new PriceCityMappingVo();
				priceCityMappingVo.setPriceCityMappingEntity(new PriceCityMappingEntity());
				priceCityMappingVo.setPriceCityList(new ArrayList<PriceCityMappingEntity>());
			}
			PriceCityMappingEntity paramObEntity= priceCityMappingVo.getPriceCityMappingEntity();
			
			//add by dengyin 2016-6-3 14:58:02 价格城市管理导出时 是否有网点 没有进行转义
			paramObEntity.setFuncFlag("query");
			//end by dengyin 2016-6-3 14:58:02 价格城市管理导出时 是否有网点 没有进行转义
			
			paramObEntity.setActive(MiserConstants.ACTIVE);
			List<PriceCityMappingEntity> outerBranchList=priceCityMappingService.queryPriceCityMappingByEntity(paramObEntity, limit, start);
			totalCount=priceCityMappingService.countPriceCityMappingByEntity(paramObEntity);
			priceCityMappingVo.setPriceCityList(outerBranchList);
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
	 * @author 刘海飞
	 * @date 2015年12月22日
	 */
	@JSON
	public String updatePriceCityResource() {
		priceCityMappingService.updatePriceCityMapping(priceCityMappingVo.getPriceCityMappingEntity());
		return returnSuccess(MessageType.UPDATE_SUCCESS);
	}



	/**
	 * @Description: 价格城市映射关系导入
	 * @param @return   
	 * @return String
	 * @throws
	 * @author 刘海飞
	 * @date 2015年12月30日
	 */
	public String cityImport() throws Exception{
		try {
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext().getRealPath(priceCityMappingVo.getFilePath());
			Map<String,Object> returnMap= priceCityMappingService.priceCityMappingImport(uploadPath);
			JSONObject json=new JSONObject();
			//addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址
			json.put("addStartPriceCitySize", returnMap.get("addStartPriceCitySize"));
			json.put("coverStartPriceCitySize", returnMap.get("coverStartPriceCitySize"));
			json.put("addArrivePriceCitySize", returnMap.get("addArrivePriceCitySize"));
			json.put("coverArrivePriceCitySize", returnMap.get("coverArrivePriceCitySize"));
			json.put("errorFilePath", returnMap.get("errorFilePath"));
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
	 * @author 刘海飞
	 * @date 2016年1月5日
	 */
	public String excelExport() throws Exception{
		try {

			if(priceCityMappingVo ==null) priceCityMappingVo =new PriceCityMappingVo();
			
			//add by dengyin 2016-6-3 14:58:02 价格城市管理导出时 是否有网点 没有进行转义
			priceCityMappingVo.getPriceCityMappingEntity().setFuncFlag("export");
			//end by dengyin 2016-6-3 14:58:02 价格城市管理导出时 是否有网点 没有进行转义
			
			List<PriceCityMappingEntity> priceCityList=priceCityMappingService.queryPriceCityMappingByEntity(priceCityMappingVo.getPriceCityMappingEntity(), RowBounds.NO_ROW_LIMIT, RowBounds.NO_ROW_OFFSET);

			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String  filePath= priceCityMappingService.createPriceCityMappingExcelFile(priceCityList);
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

	public PriceCityMappingVo getPriceCityMappingVo() {
		return priceCityMappingVo;
	}

	public void setPriceCityMappingVo(PriceCityMappingVo priceCityMappingVo) {
		this.priceCityMappingVo = priceCityMappingVo;
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
