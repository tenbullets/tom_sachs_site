package models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
@Builder
public class UsersListDTO {
    private String role;
    private String email;
    private String username;
}
