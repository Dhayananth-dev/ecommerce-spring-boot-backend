package com.api1.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.api1.entity.User;
import com.api1.enums.UserRole;
import com.api1.repository.Userrepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
@RequiredArgsConstructor
public class AdminAccountCreator  implements CommandLineRunner{
  private final Userrepo repo;
  private final PasswordEncoder passencode;
  
  @Value("${admin.email}")
  private String adminEmail;
  @Value("${admin.password}")
  private String adminPassword;
  @Value("${admin.mobile}")
  private Long adminMobile;
  @Value("${admin.username}")
  private String adminUserName;
	@Override
	public void run(String... args) throws Exception {
		log.info("Admin Account Creation Started");
		if (repo.existsByEmail(adminEmail)) {
			log.info("Admin Account Is Already Exists");
		} else {
              User user=new User(null,adminUserName,adminEmail,adminMobile,passencode.encode(adminPassword),UserRole.ADMIN,true);
              repo.save(user);
              log.info("Admin Account Creation Success!-" + adminUserName);
		}
	}

}
