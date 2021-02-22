package com.galucus.assignment.repo;

import org.springframework.data.repository.CrudRepository;

import com.galucus.assignment.entity.NumberTbl;

public interface NumberRepo extends CrudRepository<NumberTbl, Integer> {
}
