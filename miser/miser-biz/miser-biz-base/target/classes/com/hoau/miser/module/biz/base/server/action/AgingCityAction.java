package com.hoau.miser.module.biz.base.server.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.server.service.IAgingCityService;
import com.hoau.miser.module.biz.base.api.shared.domain.AgingCityEntity;
import com.hoau.miser.module.biz.base.api.shared.domain.DistrictEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.AgingCityVo;
import com.hoau.miser.module.sys.base.api.shared.exception.MessageType;
import com.hoau.miser.module.sys.base.api.shared.vo.DistrictTreeNode;
import com.hoau.miser.module.util.define.MiserConstants;


/**
 * @description：时效城市管理
 * @author 刘海飞
 *@date 2015年12月15日
 */
/**
 * @author 289612
 * @date 2015年12月18日
 */
@Controller
@Scope("prototype")
public class AgingCityAction extends AbstractAction{

	/**
	 * 
	 */
	@Resource
	private IAgingCityService agingCityService;
	private AgingCityVo agingCityVo;
	private String node; //省
	private String node1; //市
	private String node2; //区 
	private List<DistrictTreeNode<DistrictEntity>> nodes;

	// 菜单树展开路径集合
	private Set<String> pathList;
	/**
	 * 用于菜单树展开路径设置
	 */
	private String path = "";
	//文件
	private File imgFile;
	//文件名
	private String imgFileFileName;
	
	private static final long serialVersionUID = 4322178912982130748L;
	
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
    public String queryAgingCityInUpdate()
    {
    	try {
    		agingCityVo.setAgingCityEntity(agingCityService.queryAgingCityInUpdate(agingCityVo.getAgingCityEntity()));
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
	public String queryAgingCityList(){
		try {
			if(agingCityVo==null){
				agingCityVo=new AgingCityVo();
				agingCityVo.setAgingCityEntity(new AgingCityEntity());
				agingCityVo.setAgingCityList(new ArrayList<AgingCityEntity>());
			}
			AgingCityEntity paramObEntity=agingCityVo.getAgingCityEntity();
			paramObEntity.setActive(MiserConstants.ACTIVE);
			List<AgingCityEntity> outerBranchList=agingCityService.queryAgingCityByEntity(paramObEntity, limit, start);
			totalCount=agingCityService.countAgingCityByEntity(paramObEntity);
			agingCityVo.setAgingCityList(outerBranchList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	
	/**
	 * 
	 * @Description: 增加时效城市信息
	 * @return String  
	 * @Author lhf
	 * @Time 2015年12月15日上午10:24:04
	 */
	@JSON
	public String updateAgingCityResource() {
		agingCityService.updateAgingCityEntity(agingCityVo.getAgingCityEntity());
		return returnSuccess(MessageType.UPDATE_SUCCESS);
	}
	
	/**
	 * @Description: TODO时效城市导入
	 * @param @return   
	 * @return String 
	 * @throws Exception 
	 * @throws
	 * @author 刘海飞
	 * @date 2015年12月30日
	 */
	public void cityExport() throws Exception{
		try {
			HttpServletResponse response=ServletActionContext.getResponse();
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out = response.getWriter();
			String uploadPath = ServletActionContext.getServletContext().getRealPath(agingCityVo.getFilePath());
			Map<String,Object> returnMap= agingCityService.cityInprot(uploadPath);
			JSONObject json=new JSONObject();
			//addSize:增加条数,coverSize:覆盖条数,sumSize:总共条数,filePath:错误的信息的文件地址

			json.put("addStartAgingCitySize", returnMap.get("addStartAgingCitySize"));
			json.put("coverStartAgingCitySize", returnMap.get("coverStartAgingCitySize"));
			json.put("fileStartPath", returnMap.get("fileStartPath"));
			json.put("addArriveAgingCitySize", returnMap.get("addArriveAgingCitySize"));
			json.put("coverArriveAgingCitySize", returnMap.get("coverArriveAgingCitySize"));
			json.put("fileArrivePath", returnMap.get("fileArrivePath"));
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (BusinessException e) {
			throw e;
		}
	}
	/**
	 * @Description: TODO导出功能
	 * @param @return
	 * @param @throws Exception   
	 * @return String 
	 * @throws
	 * @author 刘海飞
	 * @date 2016年1月5日
	 */
	public String excelExport() throws Exception{
		try {
			
			if(agingCityVo==null)agingCityVo=new AgingCityVo();
			List<AgingCityEntity> agingCityList=agingCityService.excelQueryListByParam(agingCityVo);
			
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String  filePath= agingCityService.createExcelFile(agingCityList);
			JSONObject json=new JSONObject();
			//filePath:错误的信息的文件地址
			json.put("filePath", filePath); 
			json.put("count",agingCityList!=null? agingCityList.size():0); 
			out.println(json.toString());
			out.flush();
			out.close();
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}
	@JSON
	public String queryStartAgingCity(){
		List<AgingCityEntity> items = agingCityService.queryStartAgingCityList(agingCityVo.getAgingCityEntity(), limit, start);
		agingCityVo.setAgingCityList(items);
    	totalCount=agingCityService.queryCountByParam(agingCityVo.getAgingCityEntity());
		return returnSuccess();
	}
	@JSON
	public String queryArriveAgingCity(){
		List<AgingCityEntity> items = agingCityService.queryArriveAgingCityList(agingCityVo.getAgingCityEntity(), limit, start);
		agingCityVo.setAgingCityList(items);
    	totalCount=agingCityService.queryCountByParamArrive(agingCityVo.getAgingCityEntity());
		return returnSuccess();
	}
	public AgingCityVo getAgingCityVo() {
		return agingCityVo;
	}

	public void setAgingCityVo(AgingCityVo agingCityVo) {
		this.agingCityVo = agingCityVo;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public List<DistrictTreeNode<DistrictEntity>> getNodes() {
		return nodes;
	}

	public void setNodes(List<DistrictTreeNode<DistrictEntity>> nodes) {
		this.nodes = nodes;
	}

	public Set<String> getPathList() {
		return pathList;
	}

	public void setPathList(Set<String> pathList) {
		this.pathList = pathList;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNode1() {
		return node1;
	}

	public void setNode1(String node1) {
		this.node1 = node1;
	}

	public String getNode2() {
		return node2;
	}

	public void setNode2(String node2) {
		this.node2 = node2;
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
