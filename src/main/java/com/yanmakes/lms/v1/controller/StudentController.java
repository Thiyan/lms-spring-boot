package com.yanmakes.lms.v1.controller;

import com.yanmakes.lms.v1.model.Student;
import com.yanmakes.lms.v1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.alps.Alps;
import org.springframework.hateoas.mediatype.alps.Descriptor;
import org.springframework.hateoas.mediatype.alps.Ext;
import org.springframework.hateoas.mediatype.alps.Type;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.springframework.hateoas.MediaTypes.ALPS_JSON_VALUE;
import static org.springframework.hateoas.mediatype.PropertyUtils.getExposedProperties;

@RestController
@RequestMapping("/v1/students")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("")
    public CollectionModel<Page<Student>> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public RepresentationModel<EntityModel<Student>> getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping("")
    public ResponseEntity<Student> createOne(@Valid @RequestBody Student student) {
        return service.createOne(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateOne(@PathVariable Long id, @Valid @RequestBody Student student) {
        return service.updateOne(id, student);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteOne(@PathVariable Long id) {
        return service.deleteOne(id);
    }

    @GetMapping(value = "/profile", produces = ALPS_JSON_VALUE)
    public Alps profile() {

        return Alps.alps() //
                .descriptor(getExposedProperties(Student.class).stream() //
                        .map(property -> Descriptor.builder() //
                                .id("class field [" + property.getName() + "]") //
                                .name(property.getName()) //
                                .type(Type.SEMANTIC) //
                                .ext(Ext.builder() //
                                        .id("ext [" + property.getName() + "]") //
                                        .href("https://example.org/samples/ext/" + property.getName()) //
                                        .value("value goes here") //
                                        .build()) //
                                .rt("rt for [" + property.getName() + "]") //
                                .descriptor(Collections.singletonList(Descriptor.builder().id("embedded").build())) //
                                .build()) //
                        .collect(Collectors.toList()))
                .build();
    }

}
