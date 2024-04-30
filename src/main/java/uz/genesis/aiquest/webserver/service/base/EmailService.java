package uz.genesis.aiquest.webserver.service.base;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendProfileAccessUrl(String recipientEmail, String url) throws MessagingException, IOException;

    void sendResetPasswordUrl(String recipientEmail, String url) throws MessagingException, IOException;

    void sendZoomRequestAcceptedEmail(String recipientEmail, String zoomLink) throws MessagingException, UnsupportedEncodingException;
    void sendZoomRequestDeclinedEmail(String recipientEmail, String reason) throws MessagingException, UnsupportedEncodingException;

    void sendTicketReceivedEmail(String recipient) throws MessagingException, UnsupportedEncodingException;
}
