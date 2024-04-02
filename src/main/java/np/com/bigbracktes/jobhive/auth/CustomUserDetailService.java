package np.com.bigbracktes.jobhive.auth;

import lombok.RequiredArgsConstructor;
import np.com.bigbracktes.jobhive.entities.users.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUserName(username)
                .map(CustomUserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .map(CustomUserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found."));
    }
}
