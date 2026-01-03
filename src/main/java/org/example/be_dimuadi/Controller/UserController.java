package org.example.be_dimuadi.Controller;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.example.be_dimuadi.Dto.Request.AuthenticationRequest;
import org.example.be_dimuadi.Dto.Response.ApiResponse;
import org.example.be_dimuadi.Dto.Response.AuthenticationResponse;
import jakarta.validation.Valid;
import org.example.be_dimuadi.Service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    @PostMapping("/create-new-user")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> createNewUser(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse userResponse=authenticationService.createUser(authenticationRequest);
        return buildAuthResponse(userResponse,"REGISTER SUCCESSFULLY");
    }
    @PostMapping("/user-login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse userResponseLogin=authenticationService.login(authenticationRequest);
        return buildAuthResponse(userResponseLogin,"LOGIN SUCCESSFULLY");
    }
    private ResponseEntity<ApiResponse<AuthenticationResponse>> buildAuthResponse(@Valid @RequestBody AuthenticationResponse authenticationResponse,String messages){
        ResponseCookie cookie=ResponseCookie.from("refresh_token",authenticationResponse.getRefresh_token())
                .path("/")
                .secure(false)
                .maxAge(30 * 24 * 60 * 60)
                .httpOnly(true)
                .sameSite("None")
                .build();
        ApiResponse<AuthenticationResponse> apiResponse=new ApiResponse<>(
                200,
                messages,
                "SUCCESS",
                authenticationResponse
        );
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .header(HttpHeaders.AUTHORIZATION,"Bearer" +authenticationResponse.getAccess_token())
                .body(apiResponse);
    }
   }