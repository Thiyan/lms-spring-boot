package com.yanmakes.lms.v1.service_impl;

import com.yanmakes.lms.v1.controller.StudentController;
import com.yanmakes.lms.v1.dao.StudentRepository;
import com.yanmakes.lms.v1.exception.NoContentException;
import com.yanmakes.lms.v1.exception.NotFoundException;
import com.yanmakes.lms.v1.model.Student;
import com.yanmakes.lms.v1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    private final PagedResourcesAssembler<Student> studentsAssembler;

    @Autowired
    public StudentServiceImpl(StudentRepository repository, PagedResourcesAssembler<Student> studentsAssembler) {
        this.repository = repository;
        this.studentsAssembler = studentsAssembler;
    }

    @Override
    public CollectionModel getAll(Pageable pageable) {

        Page<Student> students = repository.findAll(pageable);
        PagedModel<EntityModel<Student>> model = studentsAssembler.toModel(students);

        model.add(getStudentsLink());
        return model;
    }

    @Override
    public ResponseEntity<Student> createOne(Student student) {
        Student storedStudent = repository.save(student);

        storedStudent.add(getSelfLink(storedStudent));
        storedStudent.add(getStudentsLink());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(storedStudent.getId())
                .toUri();

        return ResponseEntity.created(location).body(storedStudent);
    }

    @Override
    public EntityModel<Student> getOne(Long id) {
        Optional<Student> student = repository.findById(id);

        if (!student.isPresent()) {
            throw new NotFoundException(id.toString());
        }

        EntityModel<Student> entityModel = EntityModel.of(student.get());

        entityModel.add(getStudentsLink());
        entityModel.add(getSelfLink(student.get()));

        return entityModel;
    }

    @Override
    public ResponseEntity<Student> updateOne(Long id, Student student) {

        if (!repository.existsById(id)) {
            throw new NotFoundException(id.toString());
        }

        student.setId(id);
        student = repository.save(student);

        student.add(getSelfLink(student));
        student.add(getStudentsLink());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity
                .status(OK)
                .header(HttpHeaders.LOCATION, location.toString())
                .body(student);
    }

    @Override
    public ResponseEntity<Student> deleteOne(Long id) {

        if (!repository.existsById(id)) {
            throw new NoContentException(id.toString());
        }

        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private static Link getStudentsLink() {
        Pageable pageable = PageRequest.of(0,20);
        return linkTo(methodOn(StudentController.class).getAll(pageable)).withRel("students");
    }

    private static Link getSelfLink(Student student) {
        return linkTo(methodOn(StudentController.class).getOne(student.getId())).withRel("self");
    }
}
