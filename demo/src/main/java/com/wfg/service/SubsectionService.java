package com.wfg.service;

import com.wfg.Entity.Subsection;

import java.util.List;

/**
 * @author 00938658-王富国
 * @date 2017/5/16
 */
public interface SubsectionService {

    List<Subsection> findAll();

    List<Subsection> findByName(String name);

    Integer getSubsectionTotal();
}
