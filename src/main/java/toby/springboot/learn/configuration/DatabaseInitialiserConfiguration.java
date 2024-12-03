package toby.springboot.learn.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import toby.springboot.learn.documents.Student;
import toby.springboot.learn.entities.Address;
import toby.springboot.learn.entities.Gender;
import toby.springboot.learn.entities.Subject;
import toby.springboot.learn.repositories.StudentRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Configuration
public class DatabaseInitialiserConfiguration
{
    private static final Logger log = LoggerFactory.getLogger(DatabaseInitialiserConfiguration.class);

    @ConditionalOnMissingBean(CommandLineRunner.class)
    @Bean
    CommandLineRunner provideCommandLineRunner(final StudentRepository repository, final MongoTemplate mongoTemplate)
    {
        final var email = "tobyg7794@hotmail.com";
        return args ->
        {
            final var student = new Student(
                    "Toby",
                    "Goddard",
                    email,
                    Gender.MALE,
                    new Address("United Kingdom", "London", "W14 9NA"),
                    Instant.now(),
                    List.of(Subject.MATH),
                    new BigDecimal("132.65")
            );
            final var query = new Query()
                    .addCriteria(Criteria.where("email")
                            .is("tobyg7794@hotmail.com"));

            final var students = mongoTemplate.find(query, Student.class);
            if(students.size() > 1)
            {
                throw new IllegalStateException();
            }
            else if (students.size() == 1)
            {
                log.warn("Student with email [{}] already exists", email);
            }
            else
            {
                repository.insert(student);
            }
        };
    }

}
