package com.hoau.miser.module.api.itf.api.shared.vo;

import com.hoau.hbdp.framework.shared.util.string.StringUtil;

import java.io.Serializable;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: TransTypeFyDto 产品类型
 * @Package com.hoau.miser.module.api.itf.shared.vo
 * @Description: 可用产品类型接口查询返回参数
 * @date 2016/6/1 10:42
 */
public class AvailableTransportTypeQueryResult implements Serializable {

    private static final long serialVersionUID = -25471493678477118L;
    /** 编码 */
    private String code;

    /** 名称 */
    private String name;

    /**
     * 重写equals 当code相同且不为空时，return true
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        if(StringUtil.isEmpty(this.code)){
            return false;
        }
        if (AvailableTransportTypeQueryResult.class.isAssignableFrom((obj.getClass()))) {
            AvailableTransportTypeQueryResult dto=(AvailableTransportTypeQueryResult)obj;
            if(this.code.equals(dto.getCode())){
                return true;
            }
        }
      return false;
    }

    @Override
    public int hashCode() {
        int result =(code != null ? code.hashCode() : 0);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
