package rostyk.stupnytskiy.andromeda.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.mail.MailRequest;

@Service
public class MailService {

    @Autowired
    private MailDataRepository mailDataRepository;

    @Autowired
    public JavaMailSender emailSender;

    public void testSend() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("rostyk.stup@gmail.com");
        message.setSubject("Test Simple Email");
        message.setText("Hello, Im testing Simple Email");

        emailSender.send(message);
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
}
