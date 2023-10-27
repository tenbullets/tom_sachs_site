package models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class User {
    private String id;
    private String role;
    private String uuid;
    private String username;
    private String email;
    private String password;
}
