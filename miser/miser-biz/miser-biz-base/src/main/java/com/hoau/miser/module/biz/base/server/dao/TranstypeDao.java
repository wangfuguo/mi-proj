package com.hoau.miser.module.biz.base.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;

/**
 * Created by Sai on 15/12/15.
 */
@Repository
public interface TranstypeDao {

    /**
     * 查询运输类型
     * */
    public List<TranstypeEntity> queryTranstypes(TranstypeEntity entity, RowBounds rowBounds);
    /**
     * 查询运输类型
     * */
    public List<TranstypeEntity> queryTranstypes(TranstypeEntity entity);

    /**
     * 新增运输类型
     * */
    public void addTranstype(TranstypeEntity entity);

    /**
     * 修改运输类型
     * */
    public void updateTranstype(TranstypeEntity entity);

    /**
     * 作废运输类型
     * */
    public void deleteTranstype(TranstypeEntity entity);

    /**
     * 根据id查询运输类型
     * */
    public TranstypeEntity queryTranstypeById(String id);

    /**
     * 根据名称查询运输类型,用户新增时判断是否有重复
     * */
    public TranstypeEntity queryTranstypeByName(TranstypeEntity entity);

    /**
     * 根据编码查询运输类型,用户新增时判断是否有重复
     * */
    public TranstypeEntity queryTranstypeByCode(TranstypeEntity entity);


}
