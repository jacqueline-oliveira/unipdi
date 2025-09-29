package br.com.unipds;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class NotificacaoHandler implements RequestHandler<S3Event, String> {
    private final AWSSimpleSystemsManagement ssmClient =
            AWSSimpleSystemsManagementClientBuilder.defaultClient();

    private String getParam(String name, boolean decrypt) {
        GetParameterRequest req = new GetParameterRequest()
                .withName(name)
                .withWithDecryption(decrypt);
        GetParameterResult res = ssmClient.getParameter(req);
        return res.getParameter().getValue();
    }


    @Override
    public String handleRequest(S3Event input, Context context) {
        try {
            String bucket = input.getRecords().get(0).getS3().getBucket().getName();
            String key = input.getRecords().get(0).getS3().getObject().getKey();

            String remetente = getParam("/notificacao/email/user", true);
            String senha = getParam("/notificacao/email/pass", true);
            String destinatario = getParam("/app/email/rh", false);

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);
            mailSender.setUsername(remetente);
            mailSender.setPassword(senha);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "false");

            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(destinatario);
            mail.setSubject("Novo arquivo enviado para o bucket " + bucket);
            mail.setText("Alguém fez upload de um novo currículo: " + key + " no bucket. Verifique!");

            mailSender.send(mail);

            context.getLogger().log("Mensagem enviada com sucesso");
            return "Success";

        } catch (Exception e) {
            context.getLogger().log("Processo falhou: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
