package com.cinematch.serviceimpl;

import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cinematch.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	private JavaMailSender mailSender;

	public EmailServiceImpl(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}

	@Async
	@Override
	public void sendWelcomeEmail(String email, String name) {

			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject("Welcome to CineMatch");
			message.setText("Hi " + name + ",\n\n" + "Welcome to CineMatch! 🎬\n"
					+ "Start exploring personalized movie recommendations made just for you.\n\n"
					+ "Enjoy your movie journey!\n\n" + "Team CineMatch");

			mailSender.send(message);

	}

}
