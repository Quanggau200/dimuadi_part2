package org.example.be_dimuadi.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressRequest {

    @NotBlank(message = "Tên người nhận không được để trống")
    private String receiverName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String addressName;

    @NotBlank(message = "Quận/huyện không được để trống")
    private String city;

    @NotBlank(message = "Xã không được để trống")
    private String ward;
    @NotNull(message = "Vui lòng chọn địa chỉ mặc định")
    private Boolean isDefault = false;
}