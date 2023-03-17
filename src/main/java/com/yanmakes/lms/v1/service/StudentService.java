package com.yanmakes.lms.v1.service;

import com.yanmakes.lms.v1.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;

public interface StudentService {

    CollectionModel<Page<StudentDTO>> getAll(Pageable pageable);

    StudentDTO createOne(StudentDTO student);

    StudentDTO getOne(Long id);

    StudentDTO updateOne(Long id, StudentDTO student);

    void deleteOne(Long id);
}
