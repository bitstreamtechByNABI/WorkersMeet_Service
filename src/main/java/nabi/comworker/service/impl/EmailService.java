package nabi.comworker.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender emailSender;

    // Inject from application.properties
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.mail.fromName:WorkersMeet}")
    private String fromName;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendHtmlEmail(String to, String userName, String applicationNo) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String currentDate = LocalDate.now().toString();

            // ✅ Set proper FROM and TO addresses
            helper.setFrom(new InternetAddress(fromEmail, fromName));
            helper.setTo(to);
            helper.setSubject("Welcome to WorkersMeet!");

            // ✅ Build HTML body
            String htmlContent = "<!DOCTYPE html>"
                    + "<html lang=\"en\">"
                    + "<head>"
                    + "    <meta charset=\"UTF-8\">"
                    + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                    + "    <title>Welcome to WorkersMeet</title>"
                    + "    <style>"
                    + "        body {"
                    + "            font-family: -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Open Sans\", sans-serif;"
                    + "            background-color: #f4f6f8;"
                    + "            color: #333;"
                    + "            margin: 0;"
                    + "            padding: 0;"
                    + "            display: flex;"
                    + "            justify-content: center;"
                    + "            align-items: center;"
                    + "            min-height: 100vh;"
                    + "        }"
                    + "        .container {"
                    + "            background: #ffffff;"
                    + "            padding: 40px 30px;"
                    + "            border-radius: 15px;"
                    + "            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);"
                    + "            max-width: 500px;"
                    + "            text-align: center;"
                    + "            border: 1px solid #e0e0e0;"
                    + "        }"
                    + "        h1 {"
                    + "            color: #4CAF50;"
                    + "            font-size: 2em;"
                    + "            margin-bottom: 20px;"
                    + "        }"
                    + "        p {"
                    + "            font-size: 1em;"
                    + "            line-height: 1.6;"
                    + "            margin: 10px 0;"
                    + "        }"
                    + "        .highlight {"
                    + "            color: #2196F3;"
                    + "            font-weight: 600;"
                    + "        }"
                    + "        .info-box {"
                    + "            background-color: #f1f5f9;"
                    + "            border-radius: 10px;"
                    + "            padding: 20px;"
                    + "            margin-top: 20px;"
                    + "            text-align: left;"
                    + "        }"
                    + "        .info-box p {"
                    + "            margin: 8px 0;"
                    + "        }"
                    + "        .status {"
                    + "            color: #FF5722;"
                    + "            font-weight: bold;"
                    + "        }"
                    + "        .button {"
                    + "            display: inline-block;"
                    + "            padding: 12px 25px;"
                    + "            background-color: #2196F3;"
                    + "            color: white !important;"
                    + "            font-weight: 600;"
                    + "            text-decoration: none;"
                    + "            border: none;"
                    + "            border-radius: 6px;"
                    + "            cursor: pointer;"
                    + "            transition: background-color 0.3s ease, transform 0.2s ease;"
                    + "            margin-top: 10px;"
                    + "        }"
                    + "        .button:hover {"
                    + "            background-color: #1976D2;"
                    + "            transform: translateY(-2px);"
                    + "        }"
                    + "    </style>"
                    + "</head>"
                    + "<body>"
                    + "    <div class=\"container\">"
                    + "        <h1>Welcome to <span class=\"highlight\">WorkersMeet</span>! 🎉</h1>"
                    + "        <p>Dear <span class=\"highlight\">" + userName + "</span>,</p>"
                    + "        <p>We are thrilled to have you join our community of professionals, creators, and problem-solvers. 🎊</p>"
                    + "        <p>At <span class=\"highlight\">WorkersMeet</span>, we believe in connecting people, sharing knowledge, and fostering a collaborative environment. 🚀</p>"
                    + "        <p>Feel free to reach out if you have any questions or need assistance. 🙌</p>"
                    + "        <div class=\"info-box\">"
                    + "            <p><strong>Date:</strong> <span class=\"highlight\">" + currentDate + "</span></p>"
                    + "            <p><strong>Application Number:</strong> <span class=\"highlight\">" + applicationNo + "</span></p>"
                    + "            <p class=\"status\"><strong>Status:</strong> Your application is currently under review. 🕒</p>"
                    + "            <div class=\"link\">"
                    + "                <a href=\"http://localhost:4200/WorkersMeet\" target=\"_blank\" class=\"button\">Go to WorkersMeet</a>"
                    + "            </div>"
                    + "        </div>"
                    + "    </div>"
                    + "</body>"
                    + "</html>";


            helper.setText(htmlContent, true);


            // ✅ Send email
            emailSender.send(message);
            logger.info("HTML email sent successfully to {}", to);

        } catch (MailException e) {
            logger.error("Error sending HTML email", e);
        } catch (MessagingException e) {
            logger.error("Messaging exception", e);
        } catch (Exception e) {
            logger.error("Unexpected error in email sending", e);
        }
    }
}
