package com.sinaukoding.library_management_system.controller.app;

import com.sinaukoding.library_management_system.model.dto.response.BaseResponse;
import com.sinaukoding.library_management_system.model.request.LoginRequestRecord;
import com.sinaukoding.library_management_system.model.request.RegisterRequestRecord;
import com.sinaukoding.library_management_system.security.UserLoggedInConfig;
import com.sinaukoding.library_management_system.service.app.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public BaseResponse<?> register(@RequestBody RegisterRequestRecord request) {
        return BaseResponse.ok("Registrasi berhasil", authService.register(request));
    }

    @PostMapping("login")
    public BaseResponse<?> login(@RequestBody LoginRequestRecord request) {
        return BaseResponse.ok(null, authService.login(request));
    }

    @GetMapping("logout")
    public BaseResponse<?> logout(@AuthenticationPrincipal UserLoggedInConfig userLoggedInConfig) {
        var userLoggedIn = userLoggedInConfig.getUser();
        authService.logout(userLoggedIn);
        return BaseResponse.ok("Berhasil logout", null);
    }

    @PostMapping("create-admin")
    public BaseResponse<?> createAdmin(@RequestBody RegisterRequestRecord request) {
        return BaseResponse.ok("Admin berhasil dibuat", authService.createAdmin(request));
    }

}
