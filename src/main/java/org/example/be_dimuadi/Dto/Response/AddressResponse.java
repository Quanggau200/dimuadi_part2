package org.example.be_dimuadi.Dto.Response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@Builder
@Getter
@Setter
public class AddressResponse {
    @NotBlank
    private Integer addressId;
    private String receiverName;
    private String city;
    @NotBlank
    private String addressName;
    private String phone;
    private Boolean isDefault ;
    @NotBlank
    private String ward;
}
