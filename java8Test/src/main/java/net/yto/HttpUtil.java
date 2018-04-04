package net.yto;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * @author 00938658-王富国
 * @date 2017/5/11
 */
public class HttpUtil {
    /**
     * @Title: doPost
     * @Description: 发送post请求
     * @return: String
     * @throws IOException
     */
    public static String doPost(String url, JSONObject jsonParam, boolean noNeedResponse) throws IOException {
        // post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(),
                        "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /** 请求发送成功，并得到响应 **/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /** 读取服务器返回过来的json字符串数据 **/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /** 把json字符串转换成json对象 **/
                    return str;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


	public static void main(String[] args) throws IOException {
		String waybillNo = "D900312100";
		waybillNo = "D00078468992";
		System.out
				.println(HttpUtil
						.doPost("http://jingangtest.yto56.com.cn/stlwsquery/stlinterfacequery/searchCodAmount.action?requestParameter="
								+ waybillNo
								+ "&data_digest="
								+ ExpPdaAmountUtil.getDateDigest(waybillNo), null, false));
//		System.out
//		.println(HttpUtil
//				.doPost("http://jingangtest.yto56.com.cn/stlwsquery/stlinterfacequery/searchFcAmount.action?requestParameter="
//						+ waybillNo
//						+ "&data_digest="
//						+ ExpPdaAmountUtil.getDateDigest(waybillNo), null, false));
//		System.out
//		.println(HttpUtil
//				.doPost("http://jingangtest.yto56.com.cn/stlwsquery/stlinterfacequery/searchAllAmount.action?requestParameter="
//						+ waybillNo
//						+ "&data_digest="
//						+ ExpPdaAmountUtil.getDateDigest(waybillNo), null, false));
//		System.out
//				.println(HttpUtil.doPost(
//						"http://blog.jobbole.com/wp-admin/admin-ajax.php",
//						null, false));

//		String response = "<codSearchAmountResponse>" +
//							  "<isSuccess>true</isSuccess> " +
//							  "<StlAmountForCodOrFc> " +
//							    "<waybillNo>D900312100</waybillNo> " +
//							    "<codAmount>90</codAmount> " +
//							  "</StlAmountForCodOrFc> " +
//							"</codSearchAmountResponse>";
//		System.out.println(getNodeData(response, "codAmount"));
	}
}
