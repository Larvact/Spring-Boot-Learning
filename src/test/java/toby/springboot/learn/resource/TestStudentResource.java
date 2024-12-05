package toby.springboot.learn.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import toby.springboot.learn.documents.Student;
import toby.springboot.learn.entities.Address;
import toby.springboot.learn.entities.Gender;
import toby.springboot.learn.entities.Subject;
import toby.springboot.learn.services.StudentService;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentResource.class)
class TestStudentResource
{
    private static final String STUDENT_ID = "1d5bcbe1-9716-4262-96a4-3aab8e49f338";
    private static final Student STUDENT = new Student(
            "Toby",
            "Goddard",
            "tobyg7794@hotmail.com",
            Gender.MALE,
            new Address("United Kingdom", "London", "W14 9NA"),
            Instant.now(),
            List.of(Subject.MATH),
            new BigDecimal("132.65"));

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    void givenStudent_whenAllRequested_studentReturned() throws Exception
    {
        Mockito.when(studentService.getAllStudents()).thenReturn(List.of(STUDENT));

        mvc.perform(MockMvcRequestBuilders.get("/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName").value("Toby"));
    }

    @Test
    void givenStudent_requestToCreate_studentCreated() throws Exception
    {
        Mockito.when(studentService.createStudent(STUDENT)).thenReturn(STUDENT);
        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        final var requestBody = objectMapper.writeValueAsString(STUDENT);

        mvc.perform(MockMvcRequestBuilders.post(URI.create("/v1/students"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Toby"));
    }

    @Test
    void givenStudentId_requestToDelete_studentDeleted() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.delete(URI.create(format("/v1/students/%s", STUDENT_ID))))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void givenStudent_requestToUpdate_studentUpdated() throws Exception
    {
        Mockito.when(studentService.updateStudent(STUDENT_ID, STUDENT)).thenReturn(STUDENT);
        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        final var requestBody = objectMapper.writeValueAsString(STUDENT);

        mvc.perform(MockMvcRequestBuilders.put(URI.create(format("/v1/students/%s", STUDENT_ID)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Toby"));
    }
}