package com.api1.dao;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import com.api1.entity.CustomerOrder;
import com.api1.entity.CustomerUser;
import com.api1.entity.Merchant;
import com.api1.entity.User;
import com.api1.repository.CustomerOrderRepo;
import com.api1.repository.CustomerUserRepo;
import com.api1.repository.Merchantrepo;
import com.api1.repository.Userrepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDao {
  
	private final Userrepo userrepo;
	private final CustomerUserRepo customerUserRepo;
	private final  Merchantrepo merchantrepo;
	private final CustomerOrderRepo customerOrderRepository;
	public User findByEmail(String email) {
		return userrepo.findByEmail(email).orElseThrow(() -> new NoSuchElementException("No User with Email: " + email));
	}

	public void save(User user) {
	userrepo.save(user);	
	}

	public boolean checkEmailAndMobileDuplicate(String email, Long mobile) {
		return userrepo.existsByEmailOrMobile(email,mobile);
	}
	public void save(Merchant merchant) {
		merchantrepo.save(merchant);
	}

	public void save(CustomerUser cUser) {
	   customerUserRepo.save(cUser);
	}

	public List<Merchant> getAllMerchants() {
		List<Merchant> merchants = merchantrepo.findAll();
		if (merchants.isEmpty())
			throw new NoSuchElementException("No Merchant Records Found");
		return merchants;
	}

	public List<CustomerUser> getAllCustomers() {
		List<CustomerUser> customers = customerUserRepo.findAll();
		if (customers.isEmpty())
			throw new NoSuchElementException("No Customer Records Found");
		return customers;
	}
	public User findById(Integer id) {
		return userrepo.findById(id).orElseThrow(()->new NoSuchElementException("No User with Id: "+id));
	}
	public Merchant getMerchantByEmail(String email) {
		User user = findByEmail(email);
		return merchantrepo.findByUser(user).orElseThrow(() -> new NoSuchElementException("No User with Email: " + email));
	
	
	}
	public CustomerUser findCustomerByEmail(String email) {
		User user = findByEmail(email);
		return customerUserRepo.findByUser(user)
				.orElseThrow(() -> new NoSuchElementException("No User with Email: " + email));
	}
	public void saveOrder(CustomerOrder customerOrder) {
		customerOrderRepository.save(customerOrder);
	}

	public CustomerOrder getOrder(Long id) {
		return customerOrderRepository.findById(id).orElseThrow(()->new NoSuchElementException("No Order Found"));
	}
}
