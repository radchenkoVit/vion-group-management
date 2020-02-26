package com.vion.backend.service.mail;

import com.vion.backend.config.MailProperties;
import com.vion.backend.web.contoller.user.model.RegistrationDto;
import com.vion.backend.web.contoller.user.model.UserDto;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class DefaultMailService implements MailService {
    private AsyncMailer mailer;
    private Configuration configuration;
    private MailProperties mailProperties;

    @Autowired
    public DefaultMailService(AsyncMailer mailer, Configuration configuration, MailProperties mailProperties) {
        this.mailer = mailer;
        this.configuration = configuration;
        this.mailProperties = mailProperties;
    }

    public void send(String emailTo, String subject, String template, Map<String, Object> model) {
        try {
            MimeMessage message = mailer.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            String html = getTemplateHtml(template, model);
            helper.setTo(emailTo);
            helper.setText(html, true);
            helper.setSubject(subject);
            helper.setFrom(mailProperties.getUsername());

            mailer.send(message);
        } catch (MessagingException e) {
            log.error("Failed to send an email", e);
        }
    }

    @Override
    public void sendActivationEmail(RegistrationDto registrationDto) {
        Map<String, Object> model = new HashMap<>();
        model.put("activation_link", registrationDto.getActivationCodeLink());
        send(registrationDto.getEmail(), "Activate your account", "activation-mail.ftl", model);
    }

    @Override
    public void sendWelcomeEmail(UserDto user) {
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        send(user.getEmail(), String.format("Welcome %s to Vion Project", user.getName()), "welcome-mail.ftl", model);
    }

    private String getTemplateHtml(String template, Map<String, Object> model) {
        try {
            Template tmp = configuration.getTemplate(template);
            return FreeMarkerTemplateUtils.processTemplateIntoString(tmp, model);
        } catch (IOException | TemplateException e) {
            log.error("Failed to create message body from template `" + template + "`", e);
            return "Error! Please contact administration";
        }
    }
}
