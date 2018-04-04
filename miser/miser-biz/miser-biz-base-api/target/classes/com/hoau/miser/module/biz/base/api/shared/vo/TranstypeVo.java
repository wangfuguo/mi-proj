package com.hoau.miser.module.biz.base.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.hoau.miser.module.biz.base.api.shared.domain.TranstypeEntity;

/**
 * Created by Sai on 15/12/15.
 */
public class TranstypeVo implements Serializable{

    /** 多个运输类型 */
    private List<TranstypeEntity> transtypeEntities;

    /** 单个运输类型 */
    TranstypeEntity transtypeEntity;

    public List<TranstypeEntity> getTranstypeEntities() {
        return transtypeEntities;
    }

    public void setTranstypeEntities(List<TranstypeEntity> transtypeEntities) {
        this.transtypeEntities = transtypeEntities;
    }

    public TranstypeEntity getTranstypeEntity() {
        return transtypeEntity;
    }

    public void setTranstypeEntity(TranstypeEntity transtypeEntity) {
        this.transtypeEntity = transtypeEntity;
    }
}
