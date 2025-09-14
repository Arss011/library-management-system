package com.sinaukoding.library_management_system.service.app;

import com.sinaukoding.library_management_system.entity.managementuser.User;
import com.sinaukoding.library_management_system.model.app.SimpleMap;
import com.sinaukoding.library_management_system.model.request.LoginRequestRecord;
import com.sinaukoding.library_management_system.model.request.RegisterRequestRecord;

public interface AuthService {

    SimpleMap login(LoginRequestRecord request);

    void logout(User userLoggedIn);

    SimpleMap register(RegisterRequestRecord request);

    SimpleMap createAdmin(RegisterRequestRecord request);

}

