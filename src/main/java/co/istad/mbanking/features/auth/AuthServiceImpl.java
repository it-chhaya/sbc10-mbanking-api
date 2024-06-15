package co.istad.mbanking.features.auth;

import co.istad.mbanking.domain.Role;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.auth.dto.RegisterRequest;
import co.istad.mbanking.features.auth.dto.RegisterResponse;
import co.istad.mbanking.features.user.RoleRepository;
import co.istad.mbanking.features.user.UserRepository;
import co.istad.mbanking.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        // Validate user's phone number
        if (userRepository.existsByPhoneNumber(registerRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Phone number has already been used");
        }

        // Validate user's email
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Email has already been used");
        }

        // Validate user's password
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password does not match");
        }

        // Validate national ID card
        if (userRepository.existsByNationalCardId(registerRequest.nationalCardId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "National card ID has already been used");
        }

        // Validation term and policy
        if (!registerRequest.acceptTerm()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You must accept the term");
        }

        User user = userMapper.fromRegisterRequest(registerRequest);

        // Set system data
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfileImage("profile/default-user.png");
        user.setIsBlocked(false);
        user.setIsDeleted(false);

        Role roleUser = roleRepository.findRoleUser(); // default role
        Role roleCustomer = roleRepository.findRoleCustomer();
        List<Role> roles = List.of(roleUser, roleCustomer);
        user.setRoles(roles);

        userRepository.save(user);

        return RegisterResponse.builder()
                .message("You have registered successfully, please verify an email!")
                .email(user.getEmail())
                .build();
    }

}
