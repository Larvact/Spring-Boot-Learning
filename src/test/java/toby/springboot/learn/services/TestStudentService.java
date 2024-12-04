package toby.springboot.learn.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import toby.springboot.learn.documents.Student;
import toby.springboot.learn.entities.Address;
import toby.springboot.learn.entities.Gender;
import toby.springboot.learn.entities.Subject;
import toby.springboot.learn.repositories.StudentRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class TestStudentService
{
    private static final String STUDENT_ID = UUID.randomUUID().toString();
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
    private StudentService studentService;
    @MockitoBean
    private StudentRepository studentRepository;

    @TestConfiguration
    static class StudentServiceConfiguration
    {
        @Bean
        StudentService studentService(final StudentRepository studentRepository)
        {
            return new StudentService(studentRepository);
        }
    }

    @Test
    void givenStudents_getAllStudents_returnsTheList()
    {
        Mockito.when(studentRepository.findAll()).thenReturn(List.of(STUDENT));

        assertThat(studentService.getAllStudents())
                .hasSize(1)
                .containsExactly(STUDENT);
    }

    @Test
    void givenStudent_createStudent_returnsTheStudent()
    {
        final var studentResponse = new Student();
        studentResponse.setId(STUDENT_ID);
        Mockito.when(studentRepository.insert(STUDENT)).thenReturn(studentResponse);

        assertThat(studentService.createStudent(STUDENT))
                .isEqualTo(studentResponse);
    }

    @Test
    void givenStudentId_deleteStudent_studentDeleted()
    {
        Mockito.doNothing().when(studentRepository).deleteById(STUDENT_ID);

        studentService.deleteStudent(STUDENT_ID);

        Mockito.verify(studentRepository).deleteById(STUDENT_ID);
    }


}