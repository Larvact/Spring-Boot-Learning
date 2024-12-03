package toby.springboot.learn.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import toby.springboot.learn.documents.Student;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String>
{
    Optional<Student> findStudentByEmail(final String email);
}
