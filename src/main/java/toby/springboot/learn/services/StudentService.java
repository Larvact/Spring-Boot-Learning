package toby.springboot.learn.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toby.springboot.learn.documents.Student;
import toby.springboot.learn.exception.MalformedApiRequestException;
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

    public Student updateStudent(final String id, final Student student)
    {
        validateUpdateStudentRequest(id, student);
        student.setId(id);
        return studentRepository.save(student);
    }

    private static void validateUpdateStudentRequest(final String id, final Student student)
    {
        if(id == null || id.isBlank())
        {
            throw new MalformedApiRequestException("The provided Id was missing. Unable to update the student.");
        }
        else if (student.getId() != null && !id.equals(student.getId()))
        {
            throw new MalformedApiRequestException("The provided Id does not match with the Id given in the payload.");
        }
    }
}
