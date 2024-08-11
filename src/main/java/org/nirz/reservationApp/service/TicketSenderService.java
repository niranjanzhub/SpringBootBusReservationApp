package org.nirz.reservationApp.service;

import java.nio.charset.StandardCharsets;

import org.nirz.reservationApp.Dto.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class TicketSenderService {







    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendTicketEmail(TicketResponse ticketResponse) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariable("ticketResponse", ticketResponse);

        String htmlContent = templateEngine.process("cticket", context);

        helper.setTo("");
        helper.setSubject("Your Bus Ticket");
        helper.setText(htmlContent, true);

  
//        ClassPathResource logoImage = new ClassPathResource("static/barcode.jpeg");
//        helper.addInline("logo-image", logoImage, "image/png");


        mailSender.send(message);
    }
}
