package com.yanmakes.lms.v1.service_impl;

import com.yanmakes.lms.v1.controller.StudentController;
import com.yanmakes.lms.v1.dao.StudentRepository;
import com.yanmakes.lms.v1.dto.StudentDTO;
import com.yanmakes.lms.v1.exception.NoContentException;
import com.yanmakes.lms.v1.exception.NotFoundException;
import com.yanmakes.lms.v1.model.Student;
import com.yanmakes.lms.v1.service.StudentService;
import com.yanmakes.lms.v1.utils.MapStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    private final PagedResourcesAssembler<StudentDTO> studentsAssembler;

    private final MapStructMapper mapper;

    @Autowired
    public StudentServiceImpl(StudentRepository repository, PagedResourcesAssembler<StudentDTO> studentsAssembler, MapStructMapper mapper) {
        this.repository = repository;
        this.studentsAssembler = studentsAssembler;
        this.mapper = mapper;
    }

    @Override
    public CollectionModel getAll(Pageable pageable) {

        Page<Student> students = repository.findAll(pageable);

        Page<StudentDTO> studentDTOS = students.map(student -> mapper.toStudentDTO(student));

        PagedModel<EntityModel<StudentDTO>> model = studentsAssembler.toModel(studentDTOS);

        model.add(getStudentsLink());
        return model;
    }

    @Override
    public StudentDTO createOne(StudentDTO student) {

        Student storedStudent = repository.save(mapper.toStudent(student));
        StudentDTO studentDTO = mapper.toStudentDTO(storedStudent);

        studentDTO.add(getSelfLink(storedStudent.getId()));
        studentDTO.add(getStudentsLink());

        return studentDTO;
    }

    @Override
    public StudentDTO getOne(Long id) {
        Optional<Student> response = repository.findById(id);

        if (!response.isPresent()) {
            throw new NotFoundException(id.toString());
        }

        StudentDTO studentDTO = mapper.toStudentDTO(response.get());
        studentDTO.add(getStudentsLink());
        studentDTO.add(getSelfLink(response.get().getId()));

        return studentDTO;
    }

    @Override
    public StudentDTO updateOne(Long id, StudentDTO studentDTO) {

        if (!repository.existsById(id)) {
            throw new NotFoundException(id.toString());
        }
        Student student = mapper.toStudent(studentDTO);
        student.setId(id);
        student = repository.save(student);

        studentDTO = mapper.toStudentDTO(student);
        studentDTO.add(getSelfLink(student.getId()));
        studentDTO.add(getStudentsLink());

        return studentDTO;
    }

    @Override
    public void deleteOne(Long id) {

        if (!repository.existsById(id)) {
            throw new NoContentException(id.toString());
        }

        repository.deleteById(id);

    }

    private static Link getStudentsLink() {
        Pageable pageable = PageRequest.of(0, 20);
        return linkTo(methodOn(StudentController.class).getAll(pageable)).withRel("students");
    }

    private static Link getSelfLink(Long id) {
        return linkTo(methodOn(StudentController.class).getOne(id)).withRel("self");
    }


}
