package org.example.be_dimuadi.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.be_dimuadi.Configuration.JwtServices;
import org.example.be_dimuadi.Dto.UsersDto;
import org.example.be_dimuadi.Exception.AppException;
import org.example.be_dimuadi.Exception.ErrorCode;
import org.example.be_dimuadi.Persitence.Entity.Users;
import org.example.be_dimuadi.Persitence.Responsitory.UserReponsitory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
public class UserService implements UserDetailsService {
    private UserReponsitory userReponsitory;
    private JwtServices jwtServices;
    @Override
    public UserDetails loadUserByUsername(String username) throws AppException {
        Users users = userReponsitory.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.VALIDATION_ERROR,"Không thấy tên người dùng"));
        return org.springframework.security.core.userdetails.
                User.withUsername(users.getUsername())
                .password(users.getPassword())
                .authorities(users.getRoles().getRoleName())
                .build();
    }

    public UsersDto getUserInfo(String username)
    {
        Users users=userReponsitory.findById(UUID.fromString(username)).orElseThrow(()->new UsernameNotFoundException(username));
        return UsersDto.builder()
                .username(users.getUsername())
                .phone(users.getPhone())
                .email(users.getEmail())
                .roles(users.getRoles().getRoleName())
                .build();
    }
    public UsersDto updateInforUser(UsersDto request)
    {
        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Users user= userReponsitory.findById(UUID.fromString(userId)).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND,"Không tìm thấy người dùng"));
        if (userReponsitory.existsByUsername(request.getUsername())) {

            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS,"Tên người dùng đã tồn tại");
        }
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        if (request.getBirthday() != null && !request.getBirthday().isEmpty()) {
            user.setBirth(LocalDate.parse(request.getBirthday()));
        }
        userReponsitory.save(user);
        return mapToDto(user) ;
    }
    private UsersDto mapToDto(Users user)
    {
        UsersDto dto=new UsersDto();
        dto.setUsername(user.getUsername());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles().getRoleName());
        dto.setBirthday(user.getBirth() != null ? user.getBirth().toString() : null);
        dto.setGender(user.getGender());
        return dto;
    }
    public String refreshTokenService(HttpServletRequest request) throws Exception {
        String refreshToken=getCookieValue(request,"refresh_token");
        if(refreshToken.isEmpty())
        {
            throw new Exception("No found");
        }
        String username=jwtServices.extractUsername(refreshToken);
        Users user=userReponsitory.findById(UUID.fromString(username)).orElseThrow(null);
        UserDetails userDetails=loadUserByUsername(user.getUsername());
        if(!jwtServices.ValidateToken(refreshToken,userDetails))
        {
            throw new RuntimeException("Refresh token expired");
        }
        return jwtServices.GenerateAccessToken(user);

    }
    private  String getCookieValue(HttpServletRequest request, String cookieName) {
        if(request.getCookies()==null){
            return null;
        }
        else {
            request.getCookies();
        }
        for (Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;


    }

}
