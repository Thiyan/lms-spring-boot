package com.yanmakes.lms.tests.controller;

import com.yanmakes.lms.v1.constant.Gender;
import com.yanmakes.lms.v1.dto.StudentDTO;
import com.yanmakes.lms.v1.service_impl.StudentServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentServiceImpl service;

    @Test
    public void getOne() throws Exception {

        when(service.getOne(1L)).thenReturn(createTestDTO(true));
        RequestBuilder request = MockMvcRequestBuilders
                .get("/v1/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(getExpectedSuccessResponse()))
                .andReturn();
    }

    @Test
    public void createOne() throws Exception {

        when(service.createOne(createTestDTO(false))).thenReturn(createTestDTO(true));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(SUCCESSFUL_REQUEST_BODY)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(getExpectedSuccessResponse()))
                .andExpect(header().string("Location", "http://localhost/v1/students/1"))
                .andReturn();
    }

    @Test
    public void createOneWithValidationFail() throws Exception {

        StudentDTO requestDTO = createTestDTO(false);
        requestDTO.setEmail("lsdsjsldj");

        when(service.createOne(requestDTO)).thenReturn(requestDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALIDATION_FAIL_REQUEST_BODY())
                .accept(MediaType.APPLICATION_JSON);


        MvcResult mvcResult = mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andReturn();

        JSONObject response = new JSONObject(mvcResult.getResponse().getContentAsString());
        response.put("date-time", "2023-03-10T20:57:49.166");

        JSONAssert.assertEquals(new JSONObject(createExceptionDetailObject()), response, false);
    }

    @Test
    public void updateOne() throws Exception {

        when(service.updateOne(1L, createTestDTO(false))).thenReturn(createTestDTO(true));

        RequestBuilder request = MockMvcRequestBuilders
                .put("/v1/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(SUCCESSFUL_REQUEST_BODY)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(getExpectedSuccessResponse()))
                .andExpect(header().string("Location", "http://localhost/v1/students/1"))
                .andReturn();
    }

    @Test
    public void deleteOne() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/v1/students/1");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    private StudentDTO createTestDTO(boolean isMockResponse) {
        StudentDTO student = new StudentDTO();
        student.setId(1L);
        student.setFirstName("Ram");
        student.setLastName("Kumar");
        student.setGender(Gender.MALE);
        student.setMobile("07686348682");
        student.setEmail("thiyasdsdan@gmail.com");
        student.setPassword("test123");

        if (isMockResponse) {
            student.add(Link.of("http://localhost:8080/v1/students/").withRel("students"));
            student.add(Link.of("http://localhost:8080/v1/students/1").withRel("self"));
        }

        return student;
    }

    //    private String createExceptionDetailObject(String message, String details, int errorCount, String field){
//
//    return "    \"date-time\": \"2023-03-10T20:57:49.166\",\n" +
//            "    \"error-message\": \"Invalid email\",\n" +
//            "    \"details\": \"uri=/v1/students\",\n" +
//            "    \"invalid-field\": \"email\",\n" +
//            "    \"total-error-count\": 1";
//    }
    private String createExceptionDetailObject() {

        return "{\n" +
                "    \"date-time\": \"2023-03-10T20:57:49.166\",\n" +
                "    \"error-message\": \"Invalid email\",\n" +
                "    \"details\": \"uri=/v1/students\",\n" +
                "    \"invalid-field\": \"email\",\n" +
                "    \"total-error-count\": 1\n" +
                "}";
    }

    private String getExpectedSuccessResponse() {
        return "{\n" +
                "    \"firstName\": \"Ram\",\n" +
                "    \"lastName\": \"Kumar\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"mobile\": \"07686348682\",\n" +
                "    \"email\": \"thiyasdsdan@gmail.com\",\n" +
                "    \"password\": \"test123\",\n" +
                "    \"_links\": {\n" +
                "        \"students\": {\n" +
                "            \"href\": \"http://localhost:8080/v1/students/\"\n" +
                "        },\n" +
                "        \"self\": {\n" +
                "            \"href\": \"http://localhost:8080/v1/students/1\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    private final String SUCCESSFUL_REQUEST_BODY = " {\n" +
            "    \"firstName\": \"Ram\",\n" +
            "    \"lastName\": \"Kumar\",\n" +
            "    \"gender\": \"MALE\",\n" +
            "    \"mobile\": \"076863sd48d682\",\n" +
            "    \"email\": \"thiyasdsdan@gmail.com\",\n" +
            "    \"password\": \"test123\"\n" +
            " }";

    private String VALIDATION_FAIL_REQUEST_BODY() {
        return " {\n" +
                "    \"firstName\": \"Ram\",\n" +
                "    \"lastName\": \"Kumar\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"mobile\": \"076863sd48d682\",\n" +
                "    \"email\": \"lsdsjsldj\",\n" +
                "    \"password\": \"test123\"\n" +
                " }";
    }


}
