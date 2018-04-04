package com.wfg;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-10-25 8:51
 * @since V1.0.0
 */

public enum NotifyType {
    ACCEPT((short) 1, "ACCEPT", "接单", "notifyAccept"),
    UNACCEPT((short)2, "UNACCEPT", "撤单", "notifyUnaccept"),
    GOT((short)3, "GOT", "已收件", "notifyCollect"),
    GETTING((short)5, "GETTING", "已收件", ""),
    NOT_SEND((short)4, "NOT_SEND", "揽收失败 ", "notifyUncollect"),
    DEPARTURE((short)12, "DEPARTURE", "已发出", "notifyDeparture"),
    DEPARTURE_CMCC((short)32, "DEPARTURE_CMM", "Cmcc已发出", "notifyDeparture32"),
    ARRIVAL((short)13, "ARRIVAL", "已收入", "notifyArrival"),
    SIGNED((short)7, "SIGNED", "已签收", "notifySignoff"),
    FAILED((short)8, "FAILED", "签收失败", "notifyUnsign"),
    CONFIRM((short)9, "CONFIRM", "订单创建确认", "notifyConfirm"),
    PACKAGE((short)10, "TMS_PACKAGE", "已打包", "notifyPackage"),
    UNPACK((short)14, "TMS_UNPACKAGE", "已拆包", "notifyUnpack"),
    DELIVERY((short)11, "SENT_SCAN", "派件中", "notifyDelivery"),
    TRANSFER((short)20, "TRANSFER", "超区换单", "notifyTransfer"),
    WEIGHT((short)21, "WEIGHT", "重量更新", "notifyWeight"),
    MAILNO((short)22, "MAILNO", "面单更新", "notifyMailNo"),
    TMS_ERROR((short)8, "TMS_ERROR", "签收失败", ""),
    INBOUND((short)6, "INBOUND", "已入柜", "notifyInbound");

    private final short code;
    private final String content;
    private final String desc;
    private final String methodName;

    private NotifyType(short code, String content, String desc, String methodName) {
        this.code = code;
        this.content = content;
        this.desc = desc;
        this.methodName = methodName;
    }

    public static void main(String[] args){

        System.out.println(byContext("WEIGHT").getDesc());

    }

    public static NotifyType value(short code) {
        NotifyType[] values = values();
        NotifyType[] var2 = values;
        int var3 = values.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            NotifyType value = var2[var4];
            if(value.code == code) {
                return value;
            }
        }

        throw new IllegalArgumentException("未知的NotifyType编码");
    }

    public static NotifyType byContext(String context) {
        NotifyType[] values = values();
        NotifyType[] var2 = values;
        int var3 = values.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            NotifyType value = var2[var4];
            if(value.content.equalsIgnoreCase(context)) {
                return value;
            }
        }

        throw new IllegalArgumentException("未知的NotifyType编码");
    }

    public short getCode() {
        return this.code;
    }

    public String getContent() {
        return this.content;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getMethodName() {
        return this.methodName;
    }
}
