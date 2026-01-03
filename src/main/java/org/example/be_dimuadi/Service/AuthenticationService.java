package org.example.be_dimuadi.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be_dimuadi.Configuration.JwtServices;
import org.example.be_dimuadi.Dto.Request.AuthenticationRequest;
import org.example.be_dimuadi.Dto.Response.AuthenticationResponse;
import org.example.be_dimuadi.Dto.RolesDto;
import org.example.be_dimuadi.Persitence.Entity.Roles;
import org.example.be_dimuadi.Persitence.Entity.Users;
import org.example.be_dimuadi.Persitence.Responsitory.RoleReponsitory;
import org.example.be_dimuadi.Persitence.Responsitory.UserReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.example.be_dimuadi.Configuration.SecurityConfig.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthenticationService {
    private final UserReponsitory userReponsitory;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final RoleReponsitory roleReponsitory;

    public AuthenticationResponse createUser(AuthenticationRequest request) {
        log.info("In method register user");
        if (userReponsitory.existsByEmail(request.getEmail())) {
            throw new UsernameNotFoundException("User with email " + request.getEmail() + " already exists");
        }
        if (userReponsitory.existsByPhone((request.getPhone()))) {
            throw new UsernameNotFoundException("User with Phone " + request.getPhone() + " already exists");
        }
        Users newUser=buildUserRequest(request);
        String accessToken=jwtServices.GenerateAccessToken(newUser);
        String refreshToken=jwtServices.GenerateRefreshToken(newUser);
        Users saveUser=userReponsitory.save(newUser);

        log.debug("Tokens generated successfully for user: {}", saveUser.getUsername());
        return AuthenticationResponse.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken).build();
    }
    public AuthenticationResponse login(AuthenticationRequest request) {
        var userLogin=userReponsitory.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User with email " + request.getEmail() + " not found"));
        boolean passwordHash=passwordEncoder.matches(request.getPassword(),userLogin.getPassword());
        if(!passwordHash) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        String accessToken=jwtServices.GenerateAccessToken(userLogin);
        String refreshToken=jwtServices.GenerateRefreshToken(userLogin);
        return AuthenticationResponse.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .authenticated(true)
                .build();
    }
    public Users buildUserRequest(AuthenticationRequest request) {
        Roles userRole=roleReponsitory.findByRoleName("USER")
                .orElseThrow(()->new UsernameNotFoundException("Role code not found"));
        Users users=new Users();
        users.setUsername(request.getUsername());
        users.setPassword(passwordEncoder.encode(request.getPassword()));
        users.setEmail(request.getEmail());
        users.setRoles(userRole);
        users.setPhone(request.getPhone());
        users.setCreate_at(LocalDateTime.now());
        users.setIs_active(true);
        return users;
    }
    public boolean isVaildEmail(String email) {
        return email!=null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
