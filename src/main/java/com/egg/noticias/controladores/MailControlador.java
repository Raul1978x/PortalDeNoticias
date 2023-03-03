package com.egg.noticias.controladores;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Raúl Gómez
 */
@Controller
public class MailControlador {

    private JavaMailSender mailSender;

    public void sendEmail() {
        // use mailSender here...
    }
}

