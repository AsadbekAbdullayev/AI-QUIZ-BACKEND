package uz.genesis.aiquest.webserver.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uz.genesis.aiquest.webserver.utils.RestConstants;
import uz.genesis.aiquest.webserver.service.base.EmailService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SmtpEmailService implements EmailService {

    private final JavaMailSender javaMailSender;

    String HTML_CONTENT_TYPE = "text/html; charset=utf-8";


    @Value(value = "classpath:email/index.html")
    private Resource resource;

    @Value(value = "classpath:email/password-recover.html")
    private Resource resource2;


    @Override
    public void sendProfileAccessUrl(String recipientEmail, String url) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(RestConstants.EMAIL_FROM);
        mimeMessage.setSubject("Set your password");
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(RestConstants.EMAIL_FROM_METADATA, "IT Skills");
        mimeMessageHelper.setSubject("IT Skills - Account Confirmation");
        mimeMessageHelper.setTo(recipientEmail);
        mimeMessageHelper.setSentDate(new Date());
        var htmlContent = resource.getContentAsString(Charset.defaultCharset());
        htmlContent = htmlContent.replace("CONFIRMATION_LINK", url);
        mimeMessage.setContent(htmlContent,"text/html; charset=utf-8");
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendResetPasswordUrl(String recipientEmail, String url) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(RestConstants.EMAIL_FROM);
        mimeMessage.setSubject("Set your password");
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(RestConstants.EMAIL_FROM_METADATA, "IT Skills");
        mimeMessageHelper.setSubject("We are very proud of to give an opportunity in Itic.uz");
        mimeMessageHelper.setTo(recipientEmail);
        mimeMessageHelper.setSentDate(new Date());
        var htmlContent = resource2.getContentAsString(Charset.defaultCharset());
        htmlContent = htmlContent.replace("CONFIRMATION_LINK", url);

        mimeMessage.setContent(htmlContent,HTML_CONTENT_TYPE);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendZoomRequestAcceptedEmail(String recipientEmail, String zoomLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(RestConstants.EMAIL_FROM);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(RestConstants.EMAIL_FROM_METADATA, "IT Skills");
        mimeMessageHelper.setSubject("Your zoom request is accepted");
        mimeMessageHelper.setTo(recipientEmail);
        mimeMessageHelper.setSentDate(new Date());
        mimeMessageHelper.setText("Your zoom request is accepted , please check your profile");
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendZoomRequestDeclinedEmail(String recipientEmail, String reason) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(RestConstants.EMAIL_FROM);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(RestConstants.EMAIL_FROM_METADATA, "IT Skills");
        mimeMessageHelper.setSubject("Your zoom request is declined");
        mimeMessageHelper.setTo(recipientEmail);
        mimeMessageHelper.setSentDate(new Date());
        mimeMessage.setContent("Unfortunately we couldn't accept your request the reason is : " + "<b>"+reason+"</b>",HTML_CONTENT_TYPE);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendTicketReceivedEmail(String recipient) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(RestConstants.EMAIL_FROM);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(RestConstants.EMAIL_FROM_METADATA, "IT Skills (no-reply)");
        mimeMessageHelper.setSubject("Ticket Received");
        mimeMessageHelper.setTo(recipient);
        mimeMessageHelper.setSentDate(new Date());
        mimeMessage.setContent("<b>Congratulations!</b> You have just received confirmation ticket from our system : " + "<b>"+recipient+"</b>",HTML_CONTENT_TYPE);
        javaMailSender.send(mimeMessage);
    }


}
