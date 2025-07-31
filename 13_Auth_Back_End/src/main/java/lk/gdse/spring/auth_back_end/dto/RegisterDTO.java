package lk.gdse.spring.auth_back_end.dto;

import lk.gdse.spring.auth_back_end.entity.Role;
import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String role;
}
