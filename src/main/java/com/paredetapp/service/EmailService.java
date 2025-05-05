package com.paredetapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCorreoHTML(String para, String asunto, String enlace) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setTo(para);
        helper.setSubject(asunto);

        String html = """
            <div style="font-family: 'Lato', sans-serif; background-color: #f7f7f7; padding: 30px; color: #1c1c1c;">
                <div style="max-width: 600px; margin: auto; background: white; border-radius: 8px; padding: 20px;">
                    <div style="text-align: center;">
                        <img src='cid:logo' alt='Logo' style='width: 80px; margin-bottom: 20px;'/>
                        <h2 style="color: #c9a04d; font-family: 'Playfair Display', serif;">Recuperación de Contraseña</h2>
                        <p style="font-size: 16px;">Haz clic en el siguiente botón para restablecer tu contraseña:</p>
                        <a href='%s' style="display: inline-block; background-color: #c9a04d; color: white; text-decoration: none; padding: 10px 20px; margin-top: 15px; border-radius: 5px;">Restablecer contraseña</a>
                        <p style="font-size: 14px; color: #777; margin-top: 20px;">Si no solicitaste este cambio, puedes ignorar este mensaje.</p>
                    </div>
                </div>
            </div>
        """.formatted(enlace);

        helper.setText(html, true);
        helper.addInline("logo", new ClassPathResource("static/icono.png"));
        mailSender.send(mensaje);
    }
}



