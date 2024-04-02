package np.com.bigbracktes.jobhive.auth.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String userName;
    private String email;
    private String mobileNumber;
    private String password;
}
