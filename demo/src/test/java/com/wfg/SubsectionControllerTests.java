package com.wfg;

import com.google.gson.Gson;
import com.wfg.Entity.Subsection;
import com.wfg.controller.SubsectionController;
import com.wfg.service.SubsectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 00938658-王富国
 * @date 2017/5/18
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SubsectionController.class)
public class SubsectionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SubsectionService subsectionService;

    @Test
    public void test() throws Exception {
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
        given(this.subsectionService.findAll()).willReturn(list);
        Gson gson = new Gson();
        this.mvc.perform(get("/test").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andExpect(content().string(list.size() + " " + "google" + gson.toJson(list)));;
//        this.mvc.perform(get("/demo/test").accept(MediaType.TEXT_PLAIN))
//                .andExpect(status().isOk()).andExpect(content().string("Honda Civic"));

    }
}
