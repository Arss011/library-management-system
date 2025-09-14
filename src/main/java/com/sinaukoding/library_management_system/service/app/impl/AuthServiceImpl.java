package com.sinaukoding.library_management_system.service.app.impl;

import com.sinaukoding.library_management_system.entity.managementuser.User;
import com.sinaukoding.library_management_system.model.app.SimpleMap;
import com.sinaukoding.library_management_system.model.enums.Role;
import com.sinaukoding.library_management_system.model.request.LoginRequestRecord;
import com.sinaukoding.library_management_system.model.request.RegisterRequestRecord;
import com.sinaukoding.library_management_system.repository.managementuser.UserRepository;
import com.sinaukoding.library_management_system.service.app.AuthService;
import com.sinaukoding.library_management_system.service.app.ValidatorService;
import com.sinaukoding.library_management_system.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ValidatorService validatorService;

    @Override
    public SimpleMap login(LoginRequestRecord request) {
        validatorService.validator(request);
        var user = userRepository.findByUsername(request.username().toLowerCase()).orElseThrow(() -> new RuntimeException("Username atau password salah"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Username atau password salah");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        user.setToken(token);
        user.setExpiredTokenAt(LocalDateTime.now().plusHours(1));
        userRepository.save(user);
        SimpleMap result = new SimpleMap();
        result.put("token", token);
        return result;
    }

    @Override
    public void logout(User userLoggedIn) {
        userLoggedIn.setToken(null);
        userLoggedIn.setExpiredTokenAt(null);
        userRepository.save(userLoggedIn);
    }

    @Override
    public SimpleMap register(RegisterRequestRecord request) {
        validatorService.validator(request);
        
        if (userRepository.existsByUsername(request.username().toLowerCase())) {
            throw new RuntimeException("Username sudah digunakan");
        }
        
        if (userRepository.existsByEmail(request.email().toLowerCase())) {
            throw new RuntimeException("Email sudah digunakan");
        }
        
        User newUser = User.builder()
                .username(request.username().toLowerCase())
                .password(passwordEncoder.encode(request.password()))
                .namaLengkap(request.namaLengkap())
                .email(request.email().toLowerCase())
                .role(Role.ROLE_MEMBER)
                .createdBy("guest")
                .updateBy("guest")
                .build();
        
        userRepository.save(newUser);
        
        SimpleMap result = new SimpleMap();
        result.put("message", "Registrasi berhasil");
        result.put("username", newUser.getUsername());
        result.put("email", newUser.getEmail());
        result.put("namaLengkap", newUser.getNamaLengkap());
        result.put("role", newUser.getRole().getLabel());
        
        return result;
    }

    @Override
    public SimpleMap createAdmin(RegisterRequestRecord request) {
        validatorService.validator(request);
        
        if (userRepository.existsByUsername(request.username().toLowerCase())) {
            throw new RuntimeException("Username sudah digunakan");
        }
        
        if (userRepository.existsByEmail(request.email().toLowerCase())) {
            throw new RuntimeException("Email sudah digunakan");
        }
        
        User newAdmin = User.builder()
                .username(request.username().toLowerCase())
                .password(passwordEncoder.encode(request.password()))
                .namaLengkap(request.namaLengkap())
                .email(request.email().toLowerCase())
                .role(Role.ROLE_ADMIN)
                .createdBy("system")
                .updateBy("system")
                .build();
        
        userRepository.save(newAdmin);
        
        SimpleMap result = new SimpleMap();
        result.put("message", "Admin berhasil dibuat");
        result.put("username", newAdmin.getUsername());
        result.put("email", newAdmin.getEmail());
        result.put("namaLengkap", newAdmin.getNamaLengkap());
        result.put("role", newAdmin.getRole().getLabel());
        
        return result;
    }

}