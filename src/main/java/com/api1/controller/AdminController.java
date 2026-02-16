package com.api1.controller;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api1.services.AdminService;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminService adminService;
	@GetMapping("/merchants")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Object> viewMerchants() {
		return adminService.getAllMerchants();
	}

	@GetMapping("/customers")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Object> viewCustomers() {
		return adminService.getAllCustomers();
	}
	@PatchMapping("/block/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Object> blockUser(@PathVariable Integer id) {
		return adminService.blockUser(id);
	}
	
	@PatchMapping("/unblock/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Object> unblockUser(@PathVariable Integer id) {
		return adminService.unblockUser(id);
	}
	@GetMapping("/products")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Object> viewProducts() {
		return adminService.getAllProducts();
	}

	@PatchMapping("/products/approve/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, Object> approveProduct(@PathVariable Long id) {
		return adminService.approveProduct(id);
	}
}
