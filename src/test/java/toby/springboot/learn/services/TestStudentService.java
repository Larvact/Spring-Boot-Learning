package toby.springboot.learn.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import toby.springboot.learn.documents.Student;
import toby.springboot.learn.entities.Address;
import toby.springboot.learn.entities.Gender;
import toby.springboot.learn.entities.Subject;
import toby.springboot.learn.exception.MalformedApiRequestException;
import toby.springboot.learn.repositories.StudentRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class TestStudentService
{
    private static final String STUDENT_ID = "fa9d4636-5ea3-4468-9002-5d5e2d4fd360";

    private Student student;

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

    @BeforeEach
    void setup()
    {
        student = new Student(
                "Toby",
                "Goddard",
                "tobyg7794@hotmail.com",
                Gender.MALE,
                new Address("United Kingdom", "London", "W14 9NA"),
                Instant.now(),
                List.of(Subject.MATH),
                new BigDecimal("132.65"));
    }

    @Test
    void givenStudents_getAllStudents_returnsTheList()
    {
        Mockito.when(studentRepository.findAll()).thenReturn(List.of(student));

        assertThat(studentService.getAllStudents())
                .hasSize(1)
                .containsExactly(student);
    }

    @Test
    void givenStudent_createStudent_returnsTheStudent()
    {
        final var studentResponse = new Student();
        studentResponse.setId(STUDENT_ID);
        Mockito.when(studentRepository.insert(student)).thenReturn(studentResponse);

        assertThat(studentService.createStudent(student))
                .isEqualTo(studentResponse);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenBlankId_deleteStudent_malformedApiExceptionThrown(final String id)
    {
        final var malformedApiException = assertThrows(MalformedApiRequestException.class, () -> studentService.deleteStudent(id));

        assertEquals("The provided Id was missing. Unable to update the student.", malformedApiException.getMessage());
    }

    @Test
    void givenStudentId_deleteStudent_studentDeleted()
    {
        Mockito.doNothing().when(studentRepository).deleteById(STUDENT_ID);

        studentService.deleteStudent(STUDENT_ID);

        Mockito.verify(studentRepository).deleteById(STUDENT_ID);
    }

    @Test
    void givenStudentId_updatedStudentObject_updateStudent_studentUpdated()
    {
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        assertThat(studentService.updateStudent(STUDENT_ID, student))
                .extracting(Student::getId, Student::getFirstName)
                .containsExactly(STUDENT_ID, "Toby");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void givenEmptyId_updatedStudentObject_updateStudent_malformedRequestExceptionThrown(final String id)
    {
        final var malformedApiException = assertThrows(MalformedApiRequestException.class, () -> studentService.updateStudent(id, student));

        assertEquals("The provided Id was missing. Unable to update the student.", malformedApiException.getMessage());
    }

    @Test
    void givenMismatchedIds_updatedStudentObject_updateStudent_malformedRequestExceptionThrown()
    {
        student.setId("1");

        final var malformedApiException = assertThrows(MalformedApiRequestException.class, () -> studentService.updateStudent(STUDENT_ID, student));

        assertEquals("The provided Id does not match with the Id given in the payload.", malformedApiException.getMessage());
    }
}