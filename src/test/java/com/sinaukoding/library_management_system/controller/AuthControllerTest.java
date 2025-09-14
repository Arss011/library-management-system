package com.sinaukoding.library_management_system.controller;

import com.sinaukoding.library_management_system.controller.app.AuthController;
import com.sinaukoding.library_management_system.model.app.SimpleMap;
import com.sinaukoding.library_management_system.model.request.LoginRequestRecord;
import com.sinaukoding.library_management_system.service.app.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void testLogin_Success() {
        var request = new LoginRequestRecord("test@example.com", "password123");
        var response = SimpleMap.createMap()
                .add("token", "jwt-token-here")
                .add("user", "user-data");

        when(authService.login(any(LoginRequestRecord.class))).thenReturn(response);

        authController.login(request);

        verify(authService, times(1)).login(request);
    }
}
