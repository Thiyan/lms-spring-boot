package com.yanmakes.lms.v1.utils;

import com.yanmakes.lms.v1.dto.StudentDTO;
import com.yanmakes.lms.v1.model.Student;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {

    StudentDTO toStudentDTO(Student student);

    Student toStudent(StudentDTO studentDTO);
}