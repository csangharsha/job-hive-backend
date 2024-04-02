package np.com.bigbracktes.jobhive.entities.users.dtos;

import lombok.Builder;
import lombok.Data;
import np.com.bigbracktes.jobhive.entities.users.enums.Role;

@Data
@Builder
public class UserDto {
    private Long id;

    private String userName;

    private String email;

    private String mobileNumber;

    private Role role;
}
