package com.wfg;

import com.wfg.Entity.Subsection;
import com.wfg.service.impl.SubsectionServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author 00938658-王富国
 * @date 2017/5/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubsectionServiceTests {

    private static SubsectionServiceImpl subsectionService;

    @BeforeClass
    public static void mockInit() {
        subsectionService = mock(SubsectionServiceImpl.class);
        List<Subsection> list = new ArrayList<>();
        Subsection subsection = new Subsection();
        subsection.setId(UUID.randomUUID().toString());
        subsection.setCode("google_001");
        subsection.setName("google");
        subsection.setActive("Y");
        subsection.setFeeType("S");
        subsection.setCreateUser("googleMan");
        subsection.setRemark("google R");
        list.add(subsection);
        when(subsectionService.findAll()).thenReturn(list);
    }

    @Test
    public void findAll() {
        List<Subsection> all = subsectionService.findAll();
        Assert.assertTrue(all.size() > 0);
    }

    @Test
    public void testMap() {
        Map mock = Mockito.mock(Map.class);
        Mockito.when(mock.get("city")).thenReturn("上海");
        Assert.assertEquals("测试城市", "上海", mock.get("city"));
        Mockito.verify(mock).get( Matchers.eq( "city" ) );
        Mockito.verify( mock, Mockito.times( 2 ) );
    }
}
