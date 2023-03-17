package com.yanmakes.lms.v1.controller;

import com.yanmakes.lms.v1.dto.StudentDTO;
import com.yanmakes.lms.v1.model.Student;
import com.yanmakes.lms.v1.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.mediatype.alps.Alps;
import org.springframework.hateoas.mediatype.alps.Descriptor;
import org.springframework.hateoas.mediatype.alps.Ext;
import org.springframework.hateoas.mediatype.alps.Type;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.springframework.hateoas.MediaTypes.ALPS_JSON_VALUE;
import static org.springframework.hateoas.mediatype.PropertyUtils.getExposedProperties;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Students", description = "Students specific interactions")
@RestController
@RequestMapping("/v1/students")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @Operation(summary = "Get list of students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found a student list",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))})})
    @GetMapping("")
    public ResponseEntity<CollectionModel<Page<StudentDTO>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));

    }

    @Operation(summary = "Get a student by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @Operation(summary = "Create a new student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid student data supplied",
                    content = @Content)})
    @PostMapping("")
    public ResponseEntity<StudentDTO> createOne(@Valid @RequestBody StudentDTO student) {

        StudentDTO persistedStudent = service.createOne(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persistedStudent.getId())
                .toUri();

        return ResponseEntity.created(location).body(persistedStudent);
    }

    @Operation(summary = "Update a student by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the student",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id or student data supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateOne(@PathVariable Long id, @Valid @RequestBody StudentDTO student) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity
                .status(OK)
                .header(HttpHeaders.LOCATION, location.toString())
                .body(service.updateOne(id, student));

    }

    @Operation(summary = "Delete a student by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted the student",
                    content = @Content),
            @ApiResponse(responseCode = "204", description = "No content for the supplied Id",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteOne(@PathVariable Long id) {
        service.deleteOne(id);
        return ResponseEntity.ok().build();
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
