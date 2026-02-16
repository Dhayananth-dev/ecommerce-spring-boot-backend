package com.api1.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.api1.dao.ProductDao;
import com.api1.dao.UserDao;
import com.api1.entity.CustomerUser;
import com.api1.entity.Merchant;
import com.api1.entity.Product;
import com.api1.entity.User;
import com.api1.mapper.ProductMapper;
import com.api1.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminImlimentation implements AdminService {
	
	private final UserDao dao;
	private final UserMapper mapper;
	private final ProductMapper productMapper;
	private final ProductDao productDao;

	@Override
	public Map<String, Object> getAllMerchants() {
		List<Merchant>list=dao.getAllMerchants();
		return Map.of("message", "Merchant Records Found", "merchants", mapper.toMerchantDtoList(list));		
	}

	@Override
	public Map<String, Object> getAllCustomers() {
		List<CustomerUser>customerUsers=dao.getAllCustomers();
		return Map.of("message", "Customer Records Found", "customers", mapper.toCustomerDtoList(customerUsers));
	}

	@Override
	public Map<String, Object> blockUser(Integer id) {
		User user = dao.findById(id);
		user.setIsactive(false);
		dao.save(user);
		return Map.of("message","Blocked Success","user",mapper.toUserDto(user));
	}

	@Override
	public Map<String, Object> unblockUser(Integer id) {
		User user = dao.findById(id);
		user.setIsactive(true);
		dao.save(user);
		return Map.of("message","Un-Blocked Success","user",mapper.toUserDto(user));
	}
	@Override
	public Map<String, Object> getAllProducts() {
		List<Product> products = productDao.getProducts();
		return Map.of("message", "Product Records Found", "products", productMapper.toProductDtoList(products));
	}

	@Override
	public Map<String, Object> approveProduct(Long id) {
		Product product = productDao.getProductById(id);
		product.setApproved(true);
		productDao.save(product);
		return Map.of("message", "Product Approved Success", "product", productMapper.toProductDto(product));
}
}