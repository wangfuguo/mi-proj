package com.wfg.Null;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-11-01 13:28
 * @since V1.0.0
 */
public class NullDemo {
    public static void main(String[] args){

        AbstractCustomer customer1 = CustomerFactory.getCustomer("Rob");
        AbstractCustomer customer2 = CustomerFactory.getCustomer("Laura");
        AbstractCustomer customer3 = CustomerFactory.getCustomer(null);
        System.out.println(customer1.getName());
        System.out.println(customer2.getName());
        System.out.println(customer3.getName());

    }
}
