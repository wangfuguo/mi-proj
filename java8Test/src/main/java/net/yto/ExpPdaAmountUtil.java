package net.yto;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: ExpPdaAmountUtil
 * @Description: pda结算代收货款和到付工具类
 * @author: wangfuguo
 * @date: 2017年3月8日 上午9:00:03
 */
public class ExpPdaAmountUtil {

    /**
     * @fieldName: DIGEST_KEY
     * @fieldType: String
     * @Description: 秘钥
     */
    private static final String DIGEST_KEY = "StlSearchCodAmount";

    /**
     * @param requestParameter
     * @Title: getDateDigest
     * @Description: 获取digest
     * @return: String
     */
    public static String getDateDigest(String requestParameter) {
        try {
            byte[] bytes = Base64.encodeBase64URLSafe(DigestUtils
                    .md5((requestParameter + DIGEST_KEY)
                            .getBytes("UTF-8")));
            for (int i = 0; i < bytes.length; i++) {
                System.out.print(bytes[i]);
            }
            System.out.println();
            return new String(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param xmlDataStr xml数据字符串
     * @param nodeName   节点名称
     * @Title: getNodeData
     * @Description: 解析xml数据
     * @author: wangfuguo
     * @return: String xml节点的数据
     */
    public static String getNodeData(String xmlDataStr, String nodeName) {
        if (xmlDataStr == null || nodeName == null) {
            return null;
        }
        String regex = "<" + nodeName + ">(.*)</" + nodeName + ">";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(xmlDataStr);
        while (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void main(String[] args){

        System.out.println(getDateDigest("D00078468992"));
//            System.out.println(DigestUtils.md5(("D00078468992" + DIGEST_KEY).getBytes("UTF-8")));
            System.out.println(Base64.decodeBase64(getDateDigest("D00078468992")));


    }
}