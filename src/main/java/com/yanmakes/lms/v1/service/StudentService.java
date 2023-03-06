package com.yanmakes.lms.v1.service;

import com.yanmakes.lms.v1.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface StudentService {

    CollectionModel<Page<Student>> getAll(Pageable pageable);

    ResponseEntity<Student> createOne(Student student);

    EntityModel<Student> getOne(Long id);

    ResponseEntity<Student> updateOne(Long id, Student student);

    ResponseEntity<Student> deleteOne(Long id);
}
