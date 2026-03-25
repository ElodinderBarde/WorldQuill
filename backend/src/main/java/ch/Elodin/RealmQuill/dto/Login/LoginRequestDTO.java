package ch.Elodin.RealmQuill.dto.Login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString(exclude = "password")
@NoArgsConstructor
public class LoginRequestDTO {


    @NotBlank(message= "Username or Email needed and cannot be blank")
    private String usernameOrEmail;

    @NotBlank(message = "Password cannot be blank")
    private String password;




}
