package com.hoau.miser.module.biz.base.server.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hoau.hbdp.framework.server.components.security.SecurityNonCheckRequired;
import com.hoau.hbdp.framework.server.web.action.AbstractAction;
import com.hoau.hbdp.framework.server.web.result.json.annotation.JSON;
import com.hoau.miser.module.biz.base.api.server.service.ITranstypeService;
import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.exception.TranstypeException;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;
import com.hoau.miser.module.common.server.interceptor.CookieNonCheckRequired;
import com.hoau.miser.module.sys.base.api.shared.exception.MessageType;

/**
 * ClassName: TranstypeAction 
 * @Description: 基础数据-运输类型界面Action 
 * @author yulin.chen@newhoau.com.cn
 * @date 2015年12月26日
 * @version V1.0   
 */
@Controller
@Scope("prototype")
public class TranstypeAction extends AbstractAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
    ITranstypeService transtypeService;

    TranstypeVo transtypeVo;

    public String index() {
        return "index";
    }

    /**
     * @Description: 根据条件查询运输类型
     * @return String 
     * @author yulin.chen@newhoau.com.cn
     * @date 2015年12月26日
     */
    @JSON
    @SecurityNonCheckRequired
    @CookieNonCheckRequired
    public String queryTranstypes() {
        List<TranstypeEntity> entities = transtypeService.queryTranstypes(transtypeVo, start, limit);
        totalCount = entities == null ? 0l : entities.size();
        transtypeVo.setTranstypeEntities(entities);
        transtypeVo.setTranstypeEntity(null);
        return this.returnSuccess();
    }
    
    /**
     * @Description:	新增/修改运输类型 
     * @return String 
     * @author yulin.chen@newhoau.com.cn
     * @date 2015年12月26日
     */
    @JSON
    public String postTranstype(){
        String result = "";
        try {
            result = transtypeService.postTranstype(transtypeVo);
        } catch (TranstypeException e) {
            return this.returnError(e.getMessage());
        }
        transtypeVo = null;
        return this.returnSuccess(result);
    }

    /**
     * @Description: 作废运输类型
     * @return String 
     * @author yulin.chen@newhoau.com.cn
     * @date 2015年12月26日
     */
    @JSON
    public String deleteTranstype() {
        try {
            transtypeService.deleteTranstype(transtypeVo);
        }catch (TranstypeException e)  {
            return this.returnError(e.getMessage());
        }
        return this.returnSuccess(MessageType.DELETE_SUCCESS);
    }

    /**
     * @Description: 根据id查询运输类型，修改前获取数据使用
     * @return String 
     * @author yulin.chen@newhoau.com.cn
     * @date 2015年12月26日
     */
    @JSON
    public String queryTranstypeById() {
        TranstypeEntity entity = transtypeService.queryTranstypeById(transtypeVo);
        ArrayList<TranstypeEntity> entities = new ArrayList<TranstypeEntity>();
        entities.add(entity);
        transtypeVo.setTranstypeEntities(entities);
        transtypeVo.setTranstypeEntity(null);
        return this.returnSuccess();
    }

    public TranstypeVo getTranstypeVo() {
        return transtypeVo;
    }

    public void setTranstypeVo(TranstypeVo transtypeVo) {
        this.transtypeVo = transtypeVo;
    }
}
