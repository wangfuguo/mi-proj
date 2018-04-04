package com.wfg;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述: TODO
 *
 * @author 00938658-王富国
 * @date 2017-05-25 16:19
 * @since V1.0.0
 */
public class test {
    public static void main(String[] args){
        String startCode = "CK10086785";
        String codeFirst = startCode.substring(0, startCode.length() - 5);
        String codeLastNum = startCode.substring(startCode.length() - 5, startCode.length());
        System.out.println(codeFirst);
        System.out.println(codeLastNum);
        Integer a = 58621008;
        Integer b = 1000;
        System.out.println(a / b * b + 1);
        System.out.println((a / b + 1) * b);

        Integer c = 58621008;
        Integer d = 2;
        System.out.println(c / d * d + 1);

        String code = "DD58621008";
        System.out.println(getControlMaterialStartCode(code, 1000));
        System.out.println(getControlMaterialEndCode(code, 1000));

        BigDecimal bigDecimal = new BigDecimal(10);
        System.out.println(bigDecimal.toString());
        System.out.println(LocalDateTime.now());

        System.out.println(roundDown(new BigDecimal(12.322)));
        System.out.println(roundUp(new BigDecimal(12.322)));
        
        System.out.println(camel2Underline("cashOnDeliveryFee"));
        System.out.println(underline2Camel("ROUND_DOWN_", true));
        System.out.println(underline2Camel("ROUND_DOWN_", false));
        Apple apple = new Apple();
        apple.setName("sss");
        apple.setPrice(new BigDecimal(10.021));
        System.out.println(getPropertyValue(apple, "name"));
        System.out.println(receivableRound(new BigDecimal(getPropertyValue(apple, "price").toString())));
        System.out.println(roundUp(new BigDecimal(12.5).add(new BigDecimal(-12.6))));
        System.out.println(tableSizeFor(6));
        Integer unitNum = null;
        System.out.println(unitNum != null && unitNum > 0);
        Integer i1 = 155;
        Integer i2 = 155;
        System.out.println(i1 == i2);


    }

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static BigDecimal receivableRound(BigDecimal bigDecimal) {
        return scale(bigDecimal, DECIMAL_DIGIT, BigDecimal.ROUND_UP);
    }

    public static BigDecimal scale(BigDecimal value, int scale, int round) {
        BigDecimal b = nullToZero(value);
        b = b.setScale(scale, round);
        return b;
    }

    public static BigDecimal nullToZero(BigDecimal d) {
        return d == null?BigDecimal.ZERO:d;
    }

    public static Object getPropertyValue(Object obj, String property) {
        try {
            Field e = obj.getClass().getDeclaredField(property);
            makeAccessible(e);
            return getField(e, obj);
        } catch (SecurityException var3) {
            var3.printStackTrace();
        } catch (NoSuchFieldException var4) {
            var4.printStackTrace();
        }

        return null;
    }

    public static Object getField(Field field, Object target) {
        try {
            return field.get(target);
        }
        catch (IllegalAccessException ex) {
            throw new IllegalStateException(
                    "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    private static String getControlMaterialStartCode(String code, Integer unitNum) {
        Integer codeLastNum = getCodeLastNum(code);
        Integer firstNum = codeLastNum / unitNum * unitNum + 1;
        return code.substring(0, code.length() - 8) + firstNum.toString();
    }
    private static String getControlMaterialEndCode(String code, Integer unitNum) {
        Integer codeLastNum = getCodeLastNum(code);
        Integer lastNum = (codeLastNum / unitNum + 1) * unitNum;
        return code.substring(0, code.length() - 8) + lastNum.toString();
    }

    private static Integer getCodeLastNum(String code) {
        int codeLength = code.length();
        String codeLast = code.substring(codeLength - 8, codeLength);
        try {
            return Integer.parseInt(codeLast);
        } catch (NumberFormatException e) {
        }
        return null;
    }

    /**
     * 保留的小数位数
     */
    static int DECIMAL_DIGIT = 2;

    /**
     * 功能描述: 向下取整
     * @author 00938658-王富国
     * @date 2017-06-27 10:43
     * @since V1.0.0
     */
    static BigDecimal roundDown(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        bigDecimal.setScale(DECIMAL_DIGIT, RoundingMode.HALF_DOWN);
        return new BigDecimal(decimalFormat.format(bigDecimal));
    }

    /**
     * 功能描述: 向上取整
     * @author 00938658-王富国
     * @date 2017-06-27 10:44
     * @since V1.0.0
     */
    static BigDecimal roundUp(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        bigDecimal.setScale(DECIMAL_DIGIT, RoundingMode.HALF_UP);
        return new BigDecimal(decimalFormat.format(bigDecimal));
    }

    /**
    * 功能描述: 驼峰法转下划线
    * @param
    * @return 
    * @author 00938658-王富国
    * @date 2017-06-27 13:51
    * @since V1.0.0
    */
    public static String camel2Underline(String line){
        if(line==null||"".equals(line)){
            return "";
        }
        line=String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb=new StringBuffer();
        Pattern pattern= Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end()==line.length()?"":"_");
        }
        return sb.toString();
    }

    public static String underline2Camel(String line,boolean smallCamel){
        if(line==null||"".equals(line)){
            return "";
        }
        StringBuffer sb=new StringBuffer();
        Pattern pattern=Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(smallCamel&&matcher.start()==0?Character.toLowerCase(word.charAt(0)):Character.toUpperCase(word.charAt(0)));
            int index=word.lastIndexOf('_');
            if(index>0){
                sb.append(word.substring(1, index).toLowerCase());
            }else{
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
}

class Apple {

    private String name;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
