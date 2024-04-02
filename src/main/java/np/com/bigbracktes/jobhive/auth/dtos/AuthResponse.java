package np.com.bigbracktes.jobhive.auth.dtos;

import lombok.*;
import np.com.bigbracktes.jobhive.entities.users.dtos.UserDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private UserDto user;

    private String token;

    private String message;
}
