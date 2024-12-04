package toby.springboot.learn.services;

import org.springframework.stereotype.Service;
import toby.springboot.learn.documents.Student;

import java.util.Collections;
import java.util.List;

@Service
public class StudentService
{
    public List<Student> getAllStudents()
    {
        return Collections.emptyList();
    }

    public Student createStudent(final Student student)
    {
        return student;
    }

    public void deleteStudent(final String id)
    {

    }
}
