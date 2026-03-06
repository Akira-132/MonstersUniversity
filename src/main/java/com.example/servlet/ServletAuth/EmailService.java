package com.example.servlet.ServletAuth;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailService {

    private static final String EMAIL_REMETENTE = "monstrossa132@gmail.com";
    private static final String SENHA_APP = "npix vsmf fpww zpfo";
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);
    private static final Session session;

    static {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_REMETENTE, SENHA_APP);
            }
        });
    }

    public static void enviarCodigoRecuperacaoAsync(String destinatario, String codigo) {

        executor.submit(() -> {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(EMAIL_REMETENTE));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
                message.setSubject("Monsters University - Recuperação de Senha");

                message.setText(
                        "RAAAAGH!\n\n" +
                                "Eae Monstrão! Seu código de verificação de 5 dígitos é: " + codigo +
                                "\n\nSe você não solicitou isso, ignore este e-mail."
                );

                Transport.send(message);

            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("ERRO DE EMAIL - CÓDIGO GERADO (fallback): " + codigo);
            }
        });
    }
}