package com.wfg.repository;

import com.wfg.Entity.Subsection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 00938658-王富国
 * @date 2017/5/16
 */
@Repository
public interface SubsectionRepository extends JpaRepository<Subsection, String> {

    List<Subsection> findAllByNameLike(String name);
}
