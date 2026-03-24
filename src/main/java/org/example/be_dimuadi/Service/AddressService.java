package org.example.be_dimuadi.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.be_dimuadi.Dto.Request.AddressRequest;
import org.example.be_dimuadi.Dto.Response.AddressResponse;
import org.example.be_dimuadi.Exception.AppException;
import org.example.be_dimuadi.Exception.ErrorCode;
import org.example.be_dimuadi.Mapper.AddressMapper;
import org.example.be_dimuadi.Persitence.Entity.Address;
import org.example.be_dimuadi.Persitence.Entity.Users;
import org.example.be_dimuadi.Persitence.Responsitory.AddressResponsitory;
import org.example.be_dimuadi.Persitence.Responsitory.UserReponsitory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AddressService {
    private final AddressResponsitory addressReponsitory;
    private final UserReponsitory userReponsitory;
    private final AddressMapper mapper;
    public AddressResponse creataAddress(AddressRequest request) {
        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Users user= userReponsitory.findById(UUID.fromString(userId)).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND,"Không tìm thấy người dùng"));
        if (request.getIsDefault()) {
            addressReponsitory.resetDefaultByUserId(user.getUserId());
        }
        Address newAddress = Address.builder()
                .user(user)
                .receiverName(request.getReceiverName())
                .phone(request.getPhone())
                .city(request.getCity())
                .ward(request.getWard())
                .addressName(request.getAddressName())
                .isDefault(request.getIsDefault())
                .build();
        Address savedAddress = addressReponsitory.save(newAddress);

        return mapper.toRespon(savedAddress);
    }
    public List<AddressResponse> getAllAddress() {
        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        Users user = userReponsitory.findById(UUID.fromString(userId)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy người dùng"));
        return mapper.toResponList(
                addressReponsitory.findAllByUser_UserId(user.getUserId())
        );
    }
    public AddressResponse updateAddress(AddressRequest request,Integer addressId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = userReponsitory.findById(UUID.fromString(userId)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy người dùng"));
        Address address = addressReponsitory.findById(addressId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy địa chỉ"));

        if (!address.getUser().getUserId().equals(users.getUserId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED, "Bạn không có quyền sửa địa chỉ này");
        }

        if (Boolean.TRUE.equals(address.getIsDefault())
                && Boolean.FALSE.equals(request.getIsDefault())) {
            throw new AppException(ErrorCode.BAD_REQUEST,
                    "Vui lòng chọn một địa chỉ khác làm mặc định trước khi bỏ địa chỉ này");
        }
        if(addressReponsitory.existsByAddressName(request.getAddressName()))
        {
            throw new AppException(ErrorCode.ADDRESS_EXIST,"Địa chỉ nhận đã bị trùng ");
        }

        if (Boolean.TRUE.equals(request.getIsDefault())) {
            addressReponsitory.resetDefaultByUserId(users.getUserId());
        }
            address.setReceiverName(request.getReceiverName());
            address.setAddressName(request.getAddressName());
            address.setIsDefault(request.getIsDefault());
            address.setCity(request.getCity());
            address.setWard(request.getWard());
            address.setAddressName(request.getAddressName());
            return mapper.toRespon(addressReponsitory.save(address));
    }
    public void deleteAddress(Integer addressId)
    {
        String userId = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
        Users user = userReponsitory.findById(UUID.fromString(userId)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy người dùng"));
        Address address=addressReponsitory.findById(addressId).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND,"Không tìm tháy địa chỉ"));
        if(!address.getUser().getUserId().equals(user.getUserId()))
        {
            throw new AppException(ErrorCode.UNAUTHORIZED,"Không có quyền truy cập");

        }
        addressReponsitory.delete(address);
    }

}
