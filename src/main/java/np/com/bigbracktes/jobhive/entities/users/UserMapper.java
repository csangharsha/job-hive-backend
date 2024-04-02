package np.com.bigbracktes.jobhive.entities.users;

import np.com.bigbracktes.jobhive.entities.users.dtos.UserDto;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .userName(user.getUserName())
                .role(user.getRole())
                .build();
    }
}
