package toby.springboot.learn.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toby.springboot.learn.documents.Student;
import toby.springboot.learn.repositories.StudentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService
{
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents()
    {
        return studentRepository.findAll();
    }

    public Student createStudent(final Student student)
    {
        return studentRepository.insert(student);
    }

    public void deleteStudent(final String id)
    {
        studentRepository.deleteById(id);
    }

    public Object updateStudent(final Student student)
    {
        return student;
    }
}
