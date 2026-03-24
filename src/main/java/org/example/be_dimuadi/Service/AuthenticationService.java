package org.example.be_dimuadi.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be_dimuadi.Configuration.JwtAuthenticationFilter;
import org.example.be_dimuadi.Configuration.JwtServices;
import org.example.be_dimuadi.Dto.Request.AuthenticationRequest;
import org.example.be_dimuadi.Dto.Response.AuthenticationResponse;
import org.example.be_dimuadi.Exception.AppException;
import org.example.be_dimuadi.Exception.ErrorCode;
import org.example.be_dimuadi.Persitence.Entity.*;
import org.example.be_dimuadi.Persitence.Responsitory.RoleReponsitory;
import org.example.be_dimuadi.Persitence.Responsitory.UserReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS ,"Email " + request.getEmail() + " đã tồn tại ");
        }
        if (userReponsitory.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS,"Tên đăng nhập đã tồn tại!");
        }
        Users newUser=buildUserRequest(request);
        String accessToken=jwtServices.GenerateAccessToken(newUser);
        Users saveUser=userReponsitory.save(newUser);
        return AuthenticationResponse.builder()
                .access_token(accessToken)
                .authenticated(true)
                .build();
    }
    public AuthenticationResponse login(AuthenticationRequest request) {
        if (!isVaildEmail(request.getEmail())) {
            throw new AppException(ErrorCode.VALIDATION_ERROR, "Email không đúng định dạng ");
        }
        var userLogin = userReponsitory.findByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND, "Email " + request.getEmail() + " không tồn tại"));
        boolean passwordHash=passwordEncoder.matches(request.getPassword(),userLogin.getPassword());
        if(!passwordHash) {
            throw new AppException(ErrorCode.INVALID_PASSWORD,"Mật khẩu không trùng khớp ");
        }
        String accessToken=jwtServices.GenerateAccessToken(userLogin);
        return AuthenticationResponse.builder()
                .access_token(accessToken)
                .authenticated(true)
                .build();
    }
    public Users buildUserRequest(AuthenticationRequest request) {
        Roles userRole=roleReponsitory.findByRoleName("USER")
                .orElseThrow(()->new AppException(ErrorCode.ROLE_CODE_NOT_FOUND,"Không tìm thấy role"));
        Users users=new Users();
        users.setUsername(request.getUsername());
        users.setPassword(passwordEncoder.encode(request.getPassword()));
        users.setEmail(request.getEmail());
        users.setRoles(userRole);
        users.setCreate_at(LocalDateTime.now());
        users.setIs_active(true);
        return users;
    }

    public boolean isVaildEmail(String email) {
        return email!=null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
