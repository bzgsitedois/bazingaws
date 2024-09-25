package com.bazinga.bases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<Entity> extends JpaRepository<Entity , Long> {
    Page<Entity> findAll(Specification<Entity> specification, Pageable pageable);

}
