package com.example.servlet.ServletAuth;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    private static final String EMAIL_REMETENTE = "monstrossa132@gmail.com";
    private static final String SENHA_APP = "npix vsmf fpww zpfo";

    public static boolean enviarCodigoRecuperacao(String destinatario, String codigo) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMETENTE, SENHA_APP);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_REMETENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Monsters University - Recuperação de Senha");
            message.setText("Olá!\n\nSeu código de verificação de 5 dígitos é: " + codigo + "\n\nSe você não solicitou isso, ignore este e-mail.");

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("CÓDIGO DE RECUPERAÇÃO GERADO: " + codigo);
            return false;
        }
    }
}