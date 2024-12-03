package toby.springboot.learn.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import toby.springboot.learn.documents.Student;

public interface StudentRepository extends MongoRepository<Student, String>
{
}
