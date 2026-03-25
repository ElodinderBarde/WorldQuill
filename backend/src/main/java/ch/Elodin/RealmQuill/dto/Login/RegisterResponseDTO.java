package ch.Elodin.RealmQuill.dto.Login;


import ch.Elodin.RealmQuill.model.enums.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RegisterResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private RoleEnum role;

}