package com.hoau.miser.module.api.itf.api.shared.vo;

import java.io.Serializable;
import java.util.Date;

import com.hoau.miser.module.api.itf.api.shared.domain.OrgPositionInfoTyEntity;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: TransTypeFyParam
 * @Package com.hoau.miser.module.api.itf.shared.vo
 * @Description: 可用运输类型查询接口入参
 * @date 2016/6/1 11:15
 */
public class AvailableTransportTypeQueryParam implements Serializable {

    private static final long serialVersionUID = -3282318781975771439L;
    /** 起运地 物流编码**/
    private String originCode;

    /** 目的地 物流编码**/
    private String destCode;

    /** 开单时间 **/
    private Date billTime;

    /** 是否历史**/
    private boolean isHistory;
    
    /**
     * 起运地公司省市区信息
     */
    private OrgPositionInfoTyEntity originPositionInfo;
    
    /**
     * 目的地公司省市区信息
     */
    private OrgPositionInfoTyEntity destPositionInfo;

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public Date getBillTime() {
        return billTime;
    }

    public void setBillTime(Date billTime) {
        this.billTime = billTime;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

	public OrgPositionInfoTyEntity getOriginPositionInfo() {
		return originPositionInfo;
	}

	public void setOriginPositionInfo(OrgPositionInfoTyEntity originPositionInfo) {
		this.originPositionInfo = originPositionInfo;
	}

	public OrgPositionInfoTyEntity getDestPositionInfo() {
		return destPositionInfo;
	}

	public void setDestPositionInfo(OrgPositionInfoTyEntity destPositionInfo) {
		this.destPositionInfo = destPositionInfo;
	}
}
