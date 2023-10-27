package models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class AuthDTO {
    private String id;
    private String uuid;
    private String username;
    private String password;
}
