package org.nirz.reservationApp.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Valid
@Data
public class UserRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Phone is mandatory")
    private Long phone;

    @Email(message = "Invalid Email Id format")
    private String email;

    @NotBlank(message = "Gender is mandatory")
    private String gender;

    @NotNull(message = "Age is mandatory")
    @Min(value = 0, message = "Age must be greater than or equal to 0")
    @Max(value = 110, message = "Age must be less than or equal to 110")
    private Integer age;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 15, message = "Password length must be between 6 and 15 characters")
    private String password;
}
