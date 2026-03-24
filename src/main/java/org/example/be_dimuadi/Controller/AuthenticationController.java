package org.example.be_dimuadi.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.be_dimuadi.Dto.Request.AuthenticationRequest;
import org.example.be_dimuadi.Dto.Response.ApiResponse;
import org.example.be_dimuadi.Dto.Response.AuthenticationResponse;
import org.example.be_dimuadi.Dto.UsersDto;
import org.example.be_dimuadi.Exception.ErrorCode;
import org.example.be_dimuadi.Service.AuthenticationService;
import org.example.be_dimuadi.Service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    @PostMapping("/create-new-user")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> createNewUser(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse userResponse=authenticationService.createUser(authenticationRequest);
        return buildAuthResponse(userResponse,"REGISTER SUCCESSFULLY");
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse userResponseLogin=authenticationService.login(authenticationRequest);
        return buildAuthResponse(userResponseLogin,"LOGIN SUCCESSFULLY");
    }
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UsersDto>> getMe() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        UsersDto resProfile=userService.getUserInfo(username);
        ApiResponse<UsersDto> apiResponse=new ApiResponse<>(
                200,
                "GET PROFILE SUCCESSFULLY",
                ErrorCode.SUCCESS,
                resProfile
        );
        return ResponseEntity.ok()
                .body(apiResponse);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> createRefreshToken(HttpServletRequest request) throws Exception {
        String token= userService.refreshTokenService(request);
        ApiResponse<AuthenticationResponse> apiResponse=new ApiResponse<>(
                200,
                "GET REFRESH TOKEN SUCCESSFULLY",
                ErrorCode.SUCCESS,
                AuthenticationResponse.builder()
                        .access_token(token)
                        .build()
        );
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/update-user")
    public ResponseEntity<ApiResponse<UsersDto>> updateInfor(@Valid @RequestBody UsersDto request)
    {
        UsersDto updateUser=userService.updateInforUser(request);
        ApiResponse<UsersDto> apiResponse =new ApiResponse<>(
                200,
                "UPDATE INFOR USER SUCCESSFULLY",
                ErrorCode.SUCCESS,
                updateUser
        );
        return ResponseEntity.ok(apiResponse);
    }
    private ResponseEntity<ApiResponse<AuthenticationResponse>> buildAuthResponse(@Valid @RequestBody AuthenticationResponse authenticationResponse,String messages){
        ResponseCookie cookie=ResponseCookie.from("refresh_token",authenticationResponse.getAccess_token())
                .path("/")
                .secure(true)
                .maxAge(30 * 24 * 60 * 60)
                .httpOnly(true)
                .sameSite("None")
                .build();
        ApiResponse<AuthenticationResponse> apiResponse=new ApiResponse<>(
                200,
                messages,
                ErrorCode.SUCCESS,
                authenticationResponse
        );
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()).header(HttpHeaders.AUTHORIZATION,"Bearer" +authenticationResponse.getAccess_token())
                .body(apiResponse);
    }
}
