package com.hibicode.bearstore.repository;

import com.hibicode.bearstore.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeersRepository extends JpaRepository<Beer, Long> {
}
