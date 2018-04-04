package com.wfg.service.impl;

import com.wfg.Entity.Subsection;
import com.wfg.repository.SubsectionRepository;
import com.wfg.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 00938658-王富国
 * @date 2017/5/16
 */
@Service
public class SubsectionServiceImpl implements SubsectionService {

    @Autowired
    private SubsectionRepository subsectionRepository;

    @Override
    public List<Subsection> findAll() {
        return subsectionRepository.findAll();
    }

    @Override
    public List<Subsection> findByName(String name) {
        return subsectionRepository.findAllByNameLike(name);
    }

    @Override
    public Integer getSubsectionTotal() {
        return this.findAll().size();
    }
}
