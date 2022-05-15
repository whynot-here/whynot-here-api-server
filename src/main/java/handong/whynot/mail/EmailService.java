package handong.whynot.mail;

@FunctionalInterface
public interface EmailService {

    void sendEmail(EmailMessage emailMessage);
}
