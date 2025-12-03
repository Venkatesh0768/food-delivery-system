package org.fooddeliverysystem.authservice.dto.userdtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

    @Schema(description = "Email of the user", example = "venkatesh@gmail.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Full name of the user", example = "Venkatesh Rapolu")
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Schema(description = "Password for user account", example = "Venkatesh@123")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain at least 1 letter, 1 digit and 1 special character"
    )
    private String password;

    @Schema($schema = "Phone number of the user", example = "+1234567890")
    @Pattern(
            regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "Invalid phone number format"
    )
    private String phoneNumber;

    @Schema(description = "Profile image URL", example = "https://example.com/image.png")
    @Size(max = 500, message = "Image URL too long")
    private String image;

}
