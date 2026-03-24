package org.example.be_dimuadi.Mapper;

import org.example.be_dimuadi.Dto.Response.AddressResponse;
import org.example.be_dimuadi.Persitence.Entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(source = "addressId", target = "addressId")
    AddressResponse toRespon(Address address);
    List<AddressResponse> toResponList(List<Address> addresses);
}
