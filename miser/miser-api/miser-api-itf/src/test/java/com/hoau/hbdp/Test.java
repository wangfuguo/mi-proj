package com.hoau.hbdp;

import com.hoau.hbdp.framework.shared.util.JsonUtils;
import com.hoau.miser.module.api.itf.api.shared.vo.AvailableTransportTypeQueryParam;
import com.hoau.miser.module.api.itf.api.shared.vo.AvailableTransportTypeQueryResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 廖文强
 * @version V1.0
 * @Title: Test
 * @Package com.hoau.hbdp
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date 2016/6/2 11:50
 */
public class Test {
    public static void main(String[] args) {
        AvailableTransportTypeQueryParam tyParam=new AvailableTransportTypeQueryParam();
        tyParam.setOriginCode("SHA2T045");
        tyParam.setDestCode("PEK2T033");
        System.out.println(JsonUtils.toJson(tyParam));
//       new Test().test2();
    }
    public void test2(){
        List<AvailableTransportTypeQueryResult> list1=new ArrayList<AvailableTransportTypeQueryResult>();
        AvailableTransportTypeQueryResult td1=new AvailableTransportTypeQueryResult();
        td1.setCode("1");
        td1.setName("11");
        AvailableTransportTypeQueryResult td2=new AvailableTransportTypeQueryResult();
        td2.setCode("2");
        td2.setName("22");
        AvailableTransportTypeQueryResult td3=new AvailableTransportTypeQueryResult();
        td3.setCode("3");
        td3.setName("33");
        list1.add(td1);
        list1.add(td2);
        list1.add(td3);
        List<AvailableTransportTypeQueryResult> list2=new ArrayList<AvailableTransportTypeQueryResult>();
        list2.add(td1);
        list2.add(td1);
        list2.add(td3);
        List<AvailableTransportTypeQueryResult> list3=new ArrayList<AvailableTransportTypeQueryResult>();
        list3.addAll(list1);
        list3.retainAll(list2);
        for(AvailableTransportTypeQueryResult dt:list3){
            System.out.println(dt.getCode()+"===="+dt.getName());
        }
    }

}
