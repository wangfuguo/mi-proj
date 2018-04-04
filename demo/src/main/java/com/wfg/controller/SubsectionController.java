package com.wfg.controller;

import com.google.gson.Gson;
import com.wfg.Entity.Subsection;
import com.wfg.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 00938658-王富国
 * @date 2017/5/16
 */
@Controller
public class SubsectionController {

    @Autowired
    private SubsectionService subsectionService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        List<Subsection> all = subsectionService.findAll();
        int size = all.size();
        Gson gson = new Gson();
        return size + " " + "google" + gson.toJson(all);
    }
    @RequestMapping("/test1")
    @ResponseBody
    public String test1(String name) {
       return name.toUpperCase();
    }

    @RequestMapping("/test2")
    @ResponseBody
    public String test2(@RequestBody String name) {
        return name.toUpperCase();
    }
}
