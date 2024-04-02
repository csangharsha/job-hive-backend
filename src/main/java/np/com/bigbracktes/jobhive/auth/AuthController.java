package np.com.bigbracktes.jobhive.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import np.com.bigbracktes.jobhive.auth.dtos.AuthRequest;
import np.com.bigbracktes.jobhive.auth.dtos.AuthResponse;
import np.com.bigbracktes.jobhive.auth.dtos.SignUpDto;
import np.com.bigbracktes.jobhive.config.securities.JwtTokenProvider;
import np.com.bigbracktes.jobhive.entities.users.User;
import np.com.bigbracktes.jobhive.entities.users.UserMapper;
import np.com.bigbracktes.jobhive.entities.users.UserService;
import np.com.bigbracktes.jobhive.entities.users.dtos.UserDto;
import np.com.bigbracktes.jobhive.entities.users.enums.Role;
import np.com.bigbracktes.jobhive.exceptions.CustomBackendException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private Authentication authentication;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest){

        String token = generateToken(authRequest.getUsername(),authRequest.getPassword());
        String username = ((CustomUserDetail) authentication.getPrincipal()).getUsername();
        User user = userService.findUserByUsername(username)
                .orElseThrow(()-> new CustomBackendException("User not Found"));

        UserDto userDto = UserMapper.mapToUserDto(user);
        return new ResponseEntity<>(new AuthResponse(userDto, token, "Login successful"), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody SignUpDto signUpDto) {
        String hashed = passwordEncoder.encode(signUpDto.getPassword());
        User user = User.builder()
                .mobileNumber(signUpDto.getMobileNumber())
                .password(hashed)
                .email(signUpDto.getEmail())
                .userName(signUpDto.getUserName())
                .role(Role.JOB_SEEKER)
                .build();
        user = userService.save(user);
        return new ResponseEntity<>(UserMapper.mapToUserDto(user), HttpStatus.CREATED);
    }

    private String generateToken(String username, String password) {
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Bearer " + jwtTokenProvider.generateAccessToken(authentication);
    }


}
