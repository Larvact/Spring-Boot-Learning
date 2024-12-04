package toby.springboot.learn.resource;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toby.springboot.learn.documents.Student;
import toby.springboot.learn.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/v1/students")
@RequiredArgsConstructor
public class StudentResource
{
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> fetchStudents()
    {
        final var students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody final Student student)
    {
        final var createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStudent(@PathParam("id") final String id)
    {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@PathParam("id") final String id, @RequestBody final Student student)
    {
        final var updatedStudent = studentService.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }
}
