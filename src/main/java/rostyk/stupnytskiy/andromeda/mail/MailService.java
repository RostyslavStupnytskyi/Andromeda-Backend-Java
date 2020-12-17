package rostyk.stupnytskiy.andromeda.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.mail.MailRequest;
import rostyk.stupnytskiy.andromeda.tools.ConfirmationCodeGenerator;
import rostyk.stupnytskiy.andromeda.tools.MailMessageTool;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    private MailDataRepository mailDataRepository;

    @Autowired
    private MailMessageTool mailMessageService;

    private Session session;

    public void sendConfirmationCodeMail(String target, String code){
        sendEmail(target,"Andromeda registration", mailMessageService.getConfirmMessage(code));
    }

    public void saveMain(MailRequest request) {
        MailData mailData = mailRequestToMailData(request);
        if (!mailDataRepository.existsMailDataByMailRole(MailRole.MAIL_MAIN)) {
            mailData.setMailRole(MailRole.MAIL_MAIN);
        }
        mailDataRepository.save(mailData);
    }

    public void save(MailRequest request) {
        mailDataRepository.save(mailRequestToMailData(request));
    }

    public MailData getMainMail() {
        return mailDataRepository.findOneByMailRole(MailRole.MAIL_MAIN).orElseThrow(() -> new IllegalArgumentException("Error"));
    }

    private MailData mailRequestToMailData(MailRequest request) {
        MailData mailData = new MailData();
        mailData.setEmail(request.getLogin());
        mailData.setPassword(request.getPassword());
        return mailData;
    }

    private Session mailConfig(MailData mailData) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            @Override //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailData.getEmail(), mailData.getPassword());
            }
        };

        return Session.getInstance(props, auth);
    }

    private void checkoutSession(){
        if (session == null) session = mailConfig(getMainMail());
    }

    private void sendEmail( String target, String subject, String body) {
        checkoutSession();
        MailData mailData = getMainMail();
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(mailData.getEmail(), "Andromeda"));
            msg.setReplyTo(InternetAddress.parse(mailData.getEmail(), false));
            msg.setSubject(subject, "UTF-8");
//            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(target, false));

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(" ");

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");
            multipart.addBodyPart(messageBodyPart);

            //Set the multipart message to the email message
            msg.setContent(multipart);

            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
