package com.contactvault.contact_vault;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.contactvault.cvault.ContactVaultApplication;
import com.contactvault.cvault.services.EmailSender;

 
@SpringBootTest(classes = ContactVaultApplication.class)
class ContactVaultApplicationTests {
	@Autowired
	private EmailSender emailSender;

 

	@Test
	void sendEmail(){
		emailSender.sendEmail("learnwithphoenix21@gmail.com", "Test Email", "This is a test email");
	}

}
