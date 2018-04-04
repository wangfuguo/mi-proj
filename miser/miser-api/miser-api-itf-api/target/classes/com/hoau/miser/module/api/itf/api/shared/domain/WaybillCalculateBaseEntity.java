package com.hoau.miser.module.api.itf.api.shared.domain;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 输出:入参与返回值
 *
 * @author 蒋落琛
 * @date 2016-6-15下午2:29:33
 */
public class WaybillCalculateBaseEntity implements Serializable {

	private static final long serialVersionUID = -1026323448130796359L;

	public String toString() {
        StringBuffer sb =new StringBuffer();

        Field[] fields = this.getClass().getDeclaredFields();//获得属性
        for (Field field : fields) {
            try{
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
                        this.getClass());
                Method getMethod = pd.getReadMethod();//获得get方法
                if(getMethod!=null){
                    Object o = getMethod.invoke(this);//执行get方法返回一个Object
                    sb.append(field.getName()+"["+(o==null?null:o.toString())+"] ");
                }
            }  catch (Exception e) {

            }
        }
        return sb.toString();
    }

}
