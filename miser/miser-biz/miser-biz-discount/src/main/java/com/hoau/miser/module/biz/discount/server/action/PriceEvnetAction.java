package com.hoau.miser.module.biz.discount.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.hbdp.framework.shared.util.string.StringUtil;
import com.hoau.miser.module.biz.discount.api.server.service.IPriceEventService;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventCorpSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventDiscountSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventOrgEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEventRouteSubEntity;
import com.hoau.miser.module.biz.discount.api.shared.domain.PriceEvnetEntity;
import com.hoau.miser.module.biz.discount.api.shared.vo.AttachResource;
import com.hoau.miser.module.sys.base.api.shared.exception.ResourceException;
import com.hoau.miser.module.sys.base.api.shared.vo.OrgTreeNode;
import com.hoau.miser.module.util.AttachmentRootPath;

@Controller
@Scope("prototype")
public class PriceEvnetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static String QYFW = "1";
	private final static String XLZX = "2";
	private final static String YHFW = "3";
	
	@Resource 
	private IPriceEventService priceEventService;
	
//	@Resource
//	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	private List<OrgTreeNode<PriceEventOrgEntity>> nodes;
	
	private PriceEventOrgEntity orgEntity;
	
	// 菜单树展开路径集合
	private Set<String> pathList;
	/**
	 * 用于菜单树展开路径设置
	 */
	private String path = "";
	
	private String node;
	
	private PriceEvnetEntity priceEvnetEntity;
	
	private List<PriceEvnetEntity> priceEvnetList;
	
//	private PriceEventDiscountSubEntity priceEventDiscountSubEntity;
	
	private String parentId;

	/**
	 * 线路集合
	 * */
	private List<PriceEventRouteSubEntity> routeSubEntities;
	
	/**
	 * 优惠集合
	 * */
	private List<PriceEventDiscountSubEntity> discountSubEntities;
	
	/**
	 * 范围集合
	 **/
	private List<PriceEventCorpSubEntity> corpSubEntities;
	
	/**
	 * 上传文件
	 */
//	private File file;

	/**
	 * 附件信息实体
	 */
	private AttachResource resource;
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 流传输
	 */
	private InputStream inputStream;
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	

	/**
	 * 进入活动管理页面
	 * 
	 * @return
	 * @author 275636
	 * @date 2015-1-6
	 * @update
	 */
	public String index() {
		return returnSuccess();
	}
	
	/**
	 * 分页查询活动信息
	 * 
	 * @return
	 * @author 275636
	 * @date 2016-1-12
	 * @update
	 */
	public String queryPriceEvnetInfo() {
		try {
			priceEvnetList = priceEventService.queryListByParam(priceEvnetEntity,this.start,this.limit );
			this.setTotalCount(priceEventService.queryCountByParam(priceEvnetEntity));
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 加载菜单
	 * 
	 * @return
	 * @author 275636
	 * @date 2015-1-11
	 * @update
	 */
	public String loadDepartmentTree() {
		/**
		 *根据传入实体缓存活动信息
		 */
		if(node != null && node.equals("Root")){
//			orgEntity.setEventCode("");
			orgEntity.setParentCode("DP010000");
			nodes = priceEventService.queryOrgByParentRes(orgEntity);
		}else{
			if(orgEntity == null ){
				orgEntity = new PriceEventOrgEntity();
//				orgEntity.setEventCode("");
				orgEntity.setParentCode(node);
			}else
				orgEntity.setParentCode(node);
			nodes = priceEventService.queryOrgByParentRes(orgEntity);
		}
		return this.returnSuccess();
	}
	/**
	 * 根据部门信息查询部门
	 * 
	 * @return
	 * @author 张贞献
	 * @date 2015-7-20
	 * @update
	 */
	@JSON
	public String queryTreePathForName() {
		try {
			if(orgEntity != null){
				List<OrgTreeNode<PriceEventOrgEntity>> resList = priceEventService.queryOrgByParentRes(orgEntity);

				pathList = new HashSet<String>();
				for (OrgTreeNode<PriceEventOrgEntity> res : resList) {
					node = res.getId();
					this.expendTreePath(node,res.getParentId(),resList);
					if (!"/Root".equals(path.trim())) {
						pathList.add(new String(path));
					}
					path = "";
				}
			}
			return returnSuccess();
		} catch (ResourceException e) {
			return returnError(e);
		}
	}
	

	/**
	 * 菜单树的展开路径加载
	 * 
	 * @return
	 * @author 275636
	 * @param resList 
	 * @date 2016年1月11日
	 * @update
	 */
	@JSON
	private String expendTreePath(String nodec,String prentNode,List<OrgTreeNode<PriceEventOrgEntity>> resList) {
		try {
			path = "/" + nodec + path;
			boolean ischeckparent = false;
			for (OrgTreeNode<PriceEventOrgEntity> res : resList) {
				if(prentNode.equals(res.getId())){
					ischeckparent = true;
					nodec = res.getId();
					prentNode = res.getParentId();
					break;
				}
			}
			if(ischeckparent)
				return expendTreePath(nodec,prentNode,resList);
			if(!prentNode.equals("DP010000"))
					path = "/" + prentNode + path;
			path = "/Root" + path;
			return returnSuccess();
		} catch (ResourceException e) {
			return returnError(e);
		}
	}
	
	/**
	 * 新增活动信息
	 * 
	 * @return
	 * @author275636
	 * @date 2016-1-12
	 * @update
	 */
	public String addPriceEvnet() {
		try {
			if(priceEvnetEntity.getId() == null || priceEvnetEntity.getId().equals("")){
				//随机时间戳
				priceEventService.addPriceEvnet(priceEvnetEntity);
			}else{
				priceEventService.updatePriceEvnet(priceEvnetEntity);
			}
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 作废活动信息
	 * 
	 * @return
	 * @author275636
	 * @date 2016-1-12
	 * @update
	 */
	public String deletePriceEvnet() {
		try {
			priceEventService.delPriceEvnet(priceEvnetEntity);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 强制作废活动信息
	 * 
	 * @return
	 * @author275636
	 * @date 2016-1-12
	 * @update
	 */
	public String stopPriceEvent() {
		try {
			priceEventService.stopPriceEvent(priceEvnetEntity);
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 活动信息查看
	 * 
	 * @return
	 * @author275636
	 * @date 2016-1-12
	 * @update
	 */
	public String queryPriceEvnetrInfoById() {
		try {
			priceEvnetEntity = priceEventService.queryPriceEvnetById(priceEvnetEntity.getId());
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}
	
	/**
	 * 
	 * 上传
	 * 
	 * @return
	 * @author: 275636
	 * @date: 2016年1月15日
	 * @update
	 */
	@SuppressWarnings("unchecked")
	public String tableUpload() {
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("path", ServletActionContext.getServletContext().getRealPath(resource.getAttachpath()));
			param.put("eventCode", resource.getAttachname());
			if(QYFW.equals(resource.getAttachtype())){
				Map<String, Object> rs = priceEventService.impleAreaScope(param);
				corpSubEntities = (List<PriceEventCorpSubEntity>) rs.get("lists");
			}
			else if(XLZX.equals(resource.getAttachtype()))
				routeSubEntities = priceEventService.impleRouteSub(param);
			else
				discountSubEntities = priceEventService.impleDiscountSub(param);
		} catch(Exception e){
			return returnError(e.getMessage());
		}	
		return returnSuccess();
	}
	
	public String deleteCorpImportTemp(){
		priceEventService.deleteCorpImportTemp();
		return returnSuccess();
	}

	/**
	 * 附件下载
	 * 
	 * @return
	 * @throws IOException
	 * @author 275636
	 * @date 2016-1-15
	 * @update
	 */
	public String downloadFile() {
		// 首先判断，需要下载的附件名称是否为空，如果不为空则直接给filename赋值
		if (resource != null && !StringUtil.isEmpty(resource.getAttachname())) {
//			fileName = "区域范围.xlsx";
			fileName = resource.getAttachname();
			// 如果为空则自定义一个文件名称给这个附件
		} else {
			int index = resource.getAttachpath().lastIndexOf("/");
			fileName = resource.getAttachpath().substring(index + 1);
			resource.setAttachname(fileName);
		}
		try {
			resource.setAttachpath(new String(resource.getAttachpath()
					.getBytes("iso-8859-1"), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			fileName = encodeFilename(fileName);
		} catch (UnsupportedEncodingException e) {
			return "fileNotExist";
		}
		// 文件上传根目录
		String rootPath = AttachmentRootPath.getAttachRootPath();
		File file = new File(rootPath + resource.getAttachpath());
		if (file.isFile()) {
			try {
				// 把文件写入流，供客户端下载
				inputStream = new FileInputStream(file);
				// 如果下载失败，则抛出异常
			} catch (FileNotFoundException e) {
				return "fileNotExist";
			}
		}
		return "download";
	}
	
	/**
	 * 为附件名称转码
	 * 
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author 275636
	 * @date 2016-1-15
	 * @update
	 */
	private String encodeFilename(String fileName)
			throws UnsupportedEncodingException {
		try {
			// 支持谷歌
			fileName = URLEncoder.encode(fileName, "UTF8");
			// 如果在转换编码的时候出现异常了，则执行下面的转换操作。把UTF8的编码转换成iso-8859-1
		} catch (UnsupportedEncodingException e) {
			try {
				//
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
				// 抛出异常
			} catch (UnsupportedEncodingException e1) {
				throw new UnsupportedEncodingException();
			}
			throw new UnsupportedEncodingException();
		}
		return fileName;
	}
	
	
	/**
	 * 
	 * @Description: 根据 parentId 查询对应活动优惠明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author 275636
	 * @date 2016年2月36日
	 */
	public String queryPDESubListByParam() {

		// 如 parentId 为空 直接返回 因为 新增时无须加载
		if (parentId == null || "".equals(parentId)) {
			return returnSuccess();
		}

		discountSubEntities = priceEventService.queryPDESubListByParam(parentId, limit,start);
		if (discountSubEntities != null) {
			setTotalCount((long) discountSubEntities.size());
		} else {
			setTotalCount((long) 0);
		}

		return returnSuccess();
	}
	
	/**
	 * 
	 * @Description: 根据 parentId 查询对应活动线路明细
	 * @param @return
	 * @return String
	 * @throws
	 * @author 275636
	 * @date 2016年2月36日
	 */
	public String queryLineSubListByParam() {

		// 如 parentId 为空 直接返回 因为 新增时无须加载
		if (parentId == null || "".equals(parentId)) {
			return returnSuccess();
		}

		routeSubEntities = priceEventService.queryLineSubListByParam(parentId, limit,start);
		if (routeSubEntities != null) {
			setTotalCount((long) routeSubEntities.size());
		} else {
			setTotalCount((long) 0);
		}

		return returnSuccess();
	}
	
	public List<OrgTreeNode<PriceEventOrgEntity>> getNodes() {
		return nodes;
	}

	public void setNodes(List<OrgTreeNode<PriceEventOrgEntity>> nodes) {
		this.nodes = nodes;
	}
	
	public PriceEventOrgEntity getOrgEntity() {
		return orgEntity;
	}

	public void setOrgEntity(PriceEventOrgEntity orgEntity) {
		this.orgEntity = orgEntity;
	}
	
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
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
	
	public PriceEvnetEntity getPriceEvnetEntity() {
		return priceEvnetEntity;
	}

	public void setPriceEvnetEntity(PriceEvnetEntity priceEvnetEntity) {
		this.priceEvnetEntity = priceEvnetEntity;
	}
	
	public List<PriceEvnetEntity> getPriceEvnetList() {
		return priceEvnetList;
	}

	public void setPriceEvnetList(List<PriceEvnetEntity> priceEvnetList) {
		this.priceEvnetList = priceEvnetList;
	}
	
//	public File getFile() {
//		return file;
//	}
//
//	public void setFile(File file) {
//		this.file = file;
//	}
//	
	public AttachResource getResource() {
		return resource;
	}

	public void setResource(AttachResource resource) {
		this.resource = resource;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public List<PriceEventRouteSubEntity> getRouteSubEntities() {
		return routeSubEntities;
	}

	public void setRouteSubEntities(List<PriceEventRouteSubEntity> routeSubEntities) {
		this.routeSubEntities = routeSubEntities;
	}
	
	public List<PriceEventDiscountSubEntity> getDiscountSubEntities() {
		return discountSubEntities;
	}

	public void setDiscountSubEntities(
			List<PriceEventDiscountSubEntity> discountSubEntities) {
		this.discountSubEntities = discountSubEntities;
	}
	
	public List<PriceEventCorpSubEntity> getCorpSubEntities() {
		return corpSubEntities;
	}

	public void setCorpSubEntities(List<PriceEventCorpSubEntity> corpSubEntities) {
		this.corpSubEntities = corpSubEntities;
	}

	
//	public PriceEventDiscountSubEntity getPriceEventDiscountSubEntity() {
//		return priceEventDiscountSubEntity;
//	}
//
//	public void setPriceEventDiscountSubEntity(
//			PriceEventDiscountSubEntity priceEventDiscountSubEntity) {
//		this.priceEventDiscountSubEntity = priceEventDiscountSubEntity;
//	}

}
