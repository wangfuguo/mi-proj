package com.hoau.miser.module.sys.base.server.action;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.exception.BusinessException;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.sys.base.api.server.service.IOuterBranchService;
import com.hoau.miser.module.sys.base.api.shared.domain.OuterBranchEntity;
import com.hoau.miser.module.sys.base.api.shared.vo.OuterBranchVo;
import com.hoau.miser.module.util.define.MiserConstants;
/**
 * 
 * @Description: TODO(偏线公司action)
 * @Author 292078
 * @Time 2015年12月11日上午9:37:58
 */
@Controller
@Scope("prototype")
public class OuterBranchAction extends AbstractAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private IOuterBranchService outerBranchService;
	
	private OuterBranchVo outerBranchVo;
	
	
	/**
	 * 
	 * @Description: 跳转至偏线公司index页面
	 * @return String  
	 * @Author 292078
	 * @Time 2015年12月11日上午9:50:52
	 */
	public String indexOuterBranch() {
		return "index";
	}
	
	
	/**
	 * 
	 * @Description: 查询偏线公司信息
	 * @return String  
	 * @Author 292078
	 * @Time 2015年12月11日上午10:24:04
	 */
	@JSON
	public String queryOuterBranchList(){
		try {
			if(outerBranchVo==null){
				outerBranchVo=new OuterBranchVo();
				outerBranchVo.setOuterBranchEntity(new OuterBranchEntity());
				outerBranchVo.setOuterBranchList(new ArrayList<OuterBranchEntity>());
			}
			OuterBranchEntity paramObEntity=outerBranchVo.getOuterBranchEntity();
			paramObEntity.setActive(MiserConstants.ACTIVE);
			List<OuterBranchEntity> outerBranchList=outerBranchService.queryOuterBranchList(paramObEntity, limit, start);
			totalCount=outerBranchService.queryOuterBranchCount(paramObEntity);
			outerBranchVo.setOuterBranchList(outerBranchList);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
		
	}




	public OuterBranchVo getOuterBranchVo() {
		return outerBranchVo;
	}


	public void setOuterBranchVo(OuterBranchVo outerBranchVo) {
		this.outerBranchVo = outerBranchVo;
	}
	
	

	
	
}
