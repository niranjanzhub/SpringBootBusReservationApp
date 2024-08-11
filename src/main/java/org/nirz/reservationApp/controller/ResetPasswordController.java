package org.nirz.reservationApp.controller;

import java.security.SecureRandom;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.nirz.reservationApp.model.Admin;
import org.nirz.reservationApp.respository.AdminRepository;
import org.eclipse.angus.mail.util.MailConnectException;
import org.nirz.reservationApp.Dto.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import jakarta.mail.internet.MimeMessage;

import net.bytebuddy.utility.RandomString;

@CrossOrigin
@RestController
@RequestMapping("/resetpassword")
public class ResetPasswordController {

	@Autowired
	private AdminRepository adminRepository;  // For time being, using it directly

	@Autowired
	private JavaMailSender javaMailSender;

	private final Map<String, String> otpStorage = new ConcurrentHashMap<>();


	@PostMapping("/generateotp")
	public ResponseEntity<ResponseStructure<String>> generateOtp(@RequestParam String email) throws MessagingException {
		Optional<Admin> optAdmin = adminRepository.findByEmail(email);
		ResponseStructure<String> response = new ResponseStructure<>();

		if (optAdmin.isPresent()) {
			String otp = String.format("%06d", new SecureRandom().nextInt(1000000));
			//  String otp = RandomString.make(4);
			otpStorage.put(email, otp); //new entry is created if user is exist in DB (without timer)
			sendMail(email, "OTP for resetting password", otp); //email,subject,otp
			response.setMessage("OTP sent to your mail.");
			response.setData(email);
			response.setStatusCode(HttpStatus.OK.value());
			return ResponseEntity.ok(response);
		} else {
			response.setMessage("Email ID not registered.");
			response.setData(null);
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}


	@PostMapping("/verifyotp")
	public ResponseEntity<ResponseStructure<String>> verifyOtp(@RequestParam String email, @RequestParam String otp) throws MessagingException {
		Optional<Admin> optAdmin = adminRepository.findByEmail(email);
		ResponseStructure<String> response = new ResponseStructure<>();

		if (optAdmin.isPresent()) {
			if (otp.equals(otpStorage.get(email))) {
				Admin admin = optAdmin.get();
				String randomPassword = RandomString.make(8);
				admin.setPassword(randomPassword);
				adminRepository.save(admin);
				sendMail(email, "Your temporary password for login", randomPassword);//email,subject,randam password
				otpStorage.remove(email);   //entry removed in hashmap

				response.setMessage("OTP verified successfully. \n"
						+ "A new random password has been sent to your email. \n"
						+ "Use that password to login and change it in your user console.");
				response.setData(null);
				response.setStatusCode(HttpStatus.OK.value());
				return ResponseEntity.ok(response);
			} else {
				response.setMessage("Invalid OTP. Please try again.");
				response.setData(null);
				response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		} else {
			response.setMessage("Email ID not registered or OTP expired. You can regenerate it again.");
			response.setData(null);
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}





	private void sendMail(String email, String subject, String text) throws MessagingException {


		 
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			try {
				helper.setTo(email);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			helper.setText("<h1>" + text + "</h1>", true);
			helper.setSubject(subject);
			javaMailSender.send(message);
		
		

	}




}
