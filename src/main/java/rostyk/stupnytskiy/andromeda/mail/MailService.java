package rostyk.stupnytskiy.andromeda.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.mail.MailRequest;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    private MailDataRepository mailDataRepository;

    private Session session;

    public MailService(){
        MailData mailData = getMainMail();
        session = mailConfig(mailData.getEmail(),mailData.getPassword());
    }

//    sendEmail(session, toEmail, "TLSEmail Testing Subject", "TLSEmail Testing Body");

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

    private Session mailConfig(String email, String password) {
        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            @Override //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        return session;
    }

    private void sendEmail(String target, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(target, false));
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
