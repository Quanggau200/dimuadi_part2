package org.example.be_dimuadi.Controller;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.apache.kafka.shaded.com.google.protobuf.Internal;
import org.example.be_dimuadi.Dto.Request.AddressRequest;
import org.example.be_dimuadi.Dto.Request.AuthenticationRequest;
import org.example.be_dimuadi.Dto.Response.AddressResponse;
import org.example.be_dimuadi.Dto.Response.ApiResponse;
import org.example.be_dimuadi.Dto.Response.AuthenticationResponse;
import jakarta.validation.Valid;
import org.example.be_dimuadi.Dto.UsersDto;
import org.example.be_dimuadi.Exception.ErrorCode;
import org.example.be_dimuadi.Service.AddressService;
import org.example.be_dimuadi.Service.AuthenticationService;
import org.example.be_dimuadi.Service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class UserController {
        private final AddressService addressService;
        @PostMapping("/create-address")
        public ResponseEntity<ApiResponse<AddressResponse>> createAddressForUser(@Valid @RequestBody AddressRequest request)
        {
            AddressResponse address=addressService.creataAddress(request);
            ApiResponse<AddressResponse> res=new ApiResponse<>(
                    200,
                    "CREATE ADDRESS SUCCESSFULLY",
                    ErrorCode.SUCCESS,
                    address
            );
            return ResponseEntity.ok(res);
        }
        @GetMapping("/get-all-address")
        public ResponseEntity<ApiResponse<List<AddressResponse>>> getAddress()
        {
            List<AddressResponse> getAll=addressService.getAllAddress();
            ApiResponse<List<AddressResponse>> res=new ApiResponse<>(
                    200,
                    "GET ADDRESS SUCCESSFULLY",
                    ErrorCode.SUCCESS,
                    getAll
            );
            return ResponseEntity.ok(res);
        }
        @DeleteMapping("/delete-address/{addressId}")
        public ResponseEntity<ApiResponse<Void>> deleteAddress(@PathVariable Integer addressId)
        {
            addressService.deleteAddress(addressId);
            ApiResponse<Void> res=new ApiResponse<>(
                    200,
                    "DELETE ADDRESS SUCCESSFULLY",
                    ErrorCode.SUCCESS,
                    null
            );
            return ResponseEntity.ok(res);
        }
        @PutMapping("/update-address/{addressId}")
        public ResponseEntity<ApiResponse<AddressResponse>> updateAddressUser(@Valid @RequestBody AddressRequest request, @PathVariable Integer addressId) {
            AddressResponse addressResponse = addressService.updateAddress(request, addressId);
            ApiResponse<AddressResponse> res = new ApiResponse<>(
                    200,
                    "UPDATE ADDRESS SUCCESSFULLY",
                    ErrorCode.SUCCESS,
                    addressResponse
            );
            return ResponseEntity.ok(res);
        }

}
