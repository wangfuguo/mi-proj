package com.hoau.miser.module.biz.base.api.server.service;

import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;
import com.hoau.miser.module.biz.base.api.shared.vo.TranstypeVo;

/**
 * ClassName: ITranstypeService 
 * @Description: 运输类型操作服务 
 * @author yulin.chen@newhoau.com.cn
 * @date 2016年3月1日
 * @version V1.0   
 */
public interface ITranstypeService {

    /**
     * @Description: 带分页信息的运输类型查询
     * @param transtypeVo
     * @param start
     * @param limit
     * @return List<TranstypeEntity> 
     * @author yulin.chen@newhoau.com.cn
     * @date 2016年3月1日
     */
    public List<TranstypeEntity> queryTranstypes(TranstypeVo transtypeVo, int start, int limit);
    
    /**
     * @Description: 根据条件查询所有运输类型
     * @param transtypeVo
     * @return List<TranstypeEntity> 
     * @author yulin.chen@newhoau.com.cn
     * @date 2016年3月1日
     */
    public List<TranstypeEntity> queryTranstypes(TranstypeVo transtypeVo);
    
    /**
     * @Description: 保存运输类型信息
     * @param transtypeVo
     * @return String 
     * @author yulin.chen@newhoau.com.cn
     * @date 2016年3月1日
     */
    public String postTranstype(TranstypeVo transtypeVo);

    /**
     * @Description: 删除运输类型信息
     * @param transtypeVo   
     * @return void 
     * @author yulin.chen@newhoau.com.cn
     * @date 2016年3月1日
     */
    public void deleteTranstype(TranstypeVo transtypeVo);

    /**
     * @Description: 根据ID查询运输类型信息
     * @param transtypeVo
     * @return TranstypeEntity 
     * @author yulin.chen@newhoau.com.cn
     * @date 2016年3月1日
     */
    public TranstypeEntity queryTranstypeById(TranstypeVo transtypeVo);

}
