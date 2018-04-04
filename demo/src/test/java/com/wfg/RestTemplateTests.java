package com.wfg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author 00938658-王富国
 * @date 2017/5/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTests {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://10.1.240.166:8082/pms-itf/api/resource/fee/receivable/infoFee?fixedCostFeeType=INFO_FEE&piece=120&calculateDate=2017-05-08",
                String.class, (Object) null);
        System.out.println(forEntity);
    }
}
