package toby.springboot.learn.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import toby.springboot.learn.entities.Address;
import toby.springboot.learn.entities.Gender;
import toby.springboot.learn.entities.Subject;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document
@NoArgsConstructor
@Data
public class Student
{
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private Address address;
    private Instant createdAt;
    private List<Subject> favouriteSubjects = new ArrayList<>();
    private BigDecimal totalSpentInBooks;

    public Student(String firstName, String lastName, String email,
                   Gender gender, Address address, Instant createdAt,
                   List<Subject> favouriteSubjects, BigDecimal totalSpentInBooks)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.createdAt = createdAt;
        this.favouriteSubjects = favouriteSubjects;
        this.totalSpentInBooks = totalSpentInBooks;
    }
}
