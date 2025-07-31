package lk.gdse.spring.auth_back_end.service;

import lk.gdse.spring.auth_back_end.dto.AuthDTO;
import lk.gdse.spring.auth_back_end.dto.AuthResponseDTO;
import lk.gdse.spring.auth_back_end.dto.RegisterDTO;
import lk.gdse.spring.auth_back_end.entity.Role;
import lk.gdse.spring.auth_back_end.entity.User;
import lk.gdse.spring.auth_back_end.repository.UserRepository;
import lk.gdse.spring.auth_back_end.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthResponseDTO authenticate(AuthDTO authDTO) {

         User user=userRepository.findByUsername(authDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(
                authDTO.getPassword(),
                user.getPassword())){
            throw new BadCredentialsException("Invalid credentials");
        }
        String token=jwtUtil.generateToken(authDTO.getUsername());
        return new AuthResponseDTO(token);
    }

    public String register(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername())
                .isPresent()) {
            throw new RuntimeException("Username is already in use");
        }
        User user=User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.valueOf(registerDTO.getRole()))
                .build();
        userRepository.save(user);
        return "User Register Successfully";
    }
}

