package com.hoau.miser.module.biz.base.server.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.server.service.ICityTypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.CityTypeEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.MessageType;
import com.hoau.miser.module.biz.base.api.shared.vo.CityTypeVo;
import com.hoau.miser.module.sys.frameworkimpl.server.context.MiserUserContext;
import com.hoau.miser.module.util.define.MiserConstants;

@Controller
@Scope("prototype")
public class CityTypeAction extends AbstractAction{

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = -6134421696641252015L;
	
	private CityTypeVo cityTypeVo;
	@Resource
	private ICityTypeService cityTypeService;
	
	private File imgFile;
	//文件名
	private String imgFileFileName;
	
	
	
	@JSON
	public String index(){
		return "index";
	}
	/**
	 * 
	 * @Title: queryCityTypeInUpdate 
	 * @Description: TODO在更新窗口时查询方法
	 * @return
	 * @return: String
	 */
	@JSON
	public String queryCityTypeInUpdate(){
		try {
			cityTypeVo.setCityTypeEntity(cityTypeService.queryCityTypeInUpdate(cityTypeVo.getCityTypeEntity()));
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	
	
	//	查询城市类型列表
	@JSON
	public String queryCityTypeList(){
		try {
			if(cityTypeVo==null){
				cityTypeVo = new CityTypeVo();
				cityTypeVo.setCityTypeEntity(new CityTypeEntity());
				cityTypeVo.setCityTypeList(new ArrayList<CityTypeEntity>());
			}
			CityTypeEntity paramObEntity= cityTypeVo.getCityTypeEntity();
			paramObEntity.setActive(MiserConstants.ACTIVE);
			List<CityTypeEntity> outerBranchList = cityTypeService.queryCityTypeByEntity(paramObEntity, limit, start);
			totalCount=cityTypeService.countCityTypeByEntity(paramObEntity);
			cityTypeVo.setCityTypeList(outerBranchList);
			
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	
	
//	修改城市类别
	@JSON
	public String updateCityTypeResource(){
		cityTypeService.updateCityType(cityTypeVo.getCityTypeEntity());
		return returnSuccess(MessageType.UPDATE_SUCCESS);
	}

//导出
	@JSON
	public String excelExport() throws Exception{
		try {

			if(cityTypeVo ==null) cityTypeVo =new CityTypeVo();
			List<CityTypeEntity> cityTypeList=cityTypeService.queryCityTypeByEntity(cityTypeVo.getCityTypeEntity(), RowBounds.NO_ROW_LIMIT, RowBounds.NO_ROW_OFFSET);

			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String  filePath= cityTypeService.createCityTypeExcelFile(cityTypeList);
			JSONObject json=new JSONObject();
			json.put("filePath", filePath);
			json.put("count",cityTypeList!=null? cityTypeList.size():0);
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	
	
	
	
	
	//	导入
	@JSON
	public String cityTypeImport() throws Exception{
		try {
			HttpServletResponse response=ServletActionContext.getResponse();
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext().getRealPath(cityTypeVo.getFilePath());
			Map<String,Object> returnMap= cityTypeService.cityTypeImport(uploadPath);
			JSONObject json=new JSONObject();
			//addSize:增加条数,coverSize:覆盖条数
			json.put("sumCityTypeSize", returnMap.get("sumCityTypeSize"));
			json.put("addCityTypeSize", returnMap.get("addCityTypeSize"));
			json.put("coverCityTypeSize", returnMap.get("coverCityTypeSize"));
			json.put("errorCityTypeSize", returnMap.get("errorCityTypeSize"));
			json.put("errorFilePath", returnMap.get("errorFilePath"));
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public CityTypeVo getCityTypeVo() {
		return cityTypeVo;
	}
	public void setCityTypeVo(CityTypeVo cityTypeVo) {
		this.cityTypeVo = cityTypeVo;
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
