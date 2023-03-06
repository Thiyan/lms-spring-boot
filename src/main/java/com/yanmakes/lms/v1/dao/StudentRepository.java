package com.yanmakes.lms.v1.dao;

import com.yanmakes.lms.v1.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
}
