package com.api1.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api1.dto.Merchantdto;
import com.api1.dto.UserDto;
import com.api1.dto.Userdt;
import com.api1.entity.CustomerUser;
import com.api1.entity.Merchant;
import com.api1.entity.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Mapping(target = "name", source = "username")
	public abstract Userdt toUserDto(User user);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", source = "name")
	@Mapping(target = "role", expression = "java(com.api1.enums.UserRole.MERCHANT)")
	@Mapping(target = "isactive", expression = "java(true)")
	@Mapping(target = "password", expression = "java(passwordEncoder.encode(merchantDto.getPassword()))")
	public abstract User toUserEntity(Merchantdto merchantDto);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", source = "user")
	@Mapping(target = "name", source = "merchantDto.name")
	@Mapping(target = "address", source = "merchantDto.address")
	@Mapping(target = "gstNo", source = "merchantDto.gstno")
	public abstract Merchant toMerchantEntity(Merchantdto merchantDto, User user);
	
	@Mapping(target = "password", constant = "**********")
	@Mapping(target = "email", expression = "java(merchant.getUser().getEmail())")
	@Mapping(target = "mobile", expression = "java(merchant.getUser().getMobile())")
	@Mapping(target = "id", expression = "java((long)(int)merchant.getUser().getId())")
	@Mapping(target = "status", expression = "java(merchant.getUser().isIsactive()?\"Active\":\"BLOCKED\")")
	public abstract Merchantdto toMerchantDto(Merchant merchant);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", source = "name")
	@Mapping(target = "role", expression = "java(com.api1.enums.UserRole.USER)")
	@Mapping(target = "isactive", expression = "java(true)")
	@Mapping(target = "password", expression = "java(passwordEncoder.encode(customerDto.getPassword()))")
	public abstract User toCustomeruserEntity(UserDto customerDto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", source = "user")
	@Mapping(target = "name",source = "customerDto.name")
	@Mapping(target = "gender", source = "customerDto.gender")
	public abstract CustomerUser toCustomerEntity(UserDto customerDto, User user);

	@Mapping(target = "password", constant = "**********")
	@Mapping(target = "email", expression = "java(customer.getUser().getEmail())")
	@Mapping(target = "mobile", expression = "java(customer.getUser().getMobile())")
	@Mapping(target = "id", expression = "java((long)(int)customer.getUser().getId())")
	@Mapping(target = "status", expression = "java(customer.getUser().isIsactive()?\"Active\":\"BLOCKED\")")
	public abstract UserDto toCustomerDto(CustomerUser customer);

	public abstract List<Merchantdto> toMerchantDtoList(List<Merchant> merchants);

	public abstract List<UserDto> toCustomerDtoList(List<CustomerUser> customers);


}
