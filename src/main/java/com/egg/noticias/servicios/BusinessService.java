package com.egg.noticias.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author Raúl Gómez
 */

@Service
public class BusinessService {
@Autowired
private JavaMailSender mailSender;
public void sendEmail(){
//usar mailSender acá…
}

String from = "sender@gmail.com";//dirección de correo que hace el envío.
String to = "recipient@gmail.com";//dirección de correo que recibe el mail.

public void sendEmail(String from, String to) {
SimpleMailMessage message = new SimpleMailMessage();
message.setFrom(from);
message.setTo(to);
message.setSubject("Asunto del correo");
//message.setText(“Este es un correo automático!");
mailSender.send(message); //método Send(envio), propio de Java Mail Sender.
}
}
