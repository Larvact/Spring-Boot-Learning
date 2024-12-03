package toby.springboot.learn.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    @ConditionalOnMissingBean(CommandLineRunner.class)
    @Bean
    CommandLineRunner provideCommandLineRunner(final StudentRepository repository)
    {
        return args ->
        {
            final var student = new Student(
                    "Toby",
                    "Goddard",
                    "tobyg7794@hotmail.com",
                    Gender.MALE,
                    new Address("United Kingdom", "London", "W14 9NA"),
                    Instant.now(),
                    List.of(Subject.MATH),
                    new BigDecimal("132.65")
            );
            repository.insert(student);
        };
    }

}
