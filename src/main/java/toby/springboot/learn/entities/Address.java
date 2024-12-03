package toby.springboot.learn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address
{
    private String country;
    private String city;
    private String postcode;
}
