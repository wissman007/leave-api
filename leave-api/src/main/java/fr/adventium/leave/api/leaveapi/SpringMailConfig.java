package fr.adventium.leave.api.leaveapi;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
//@PropertySource("classpath:mail/emailconfig.properties")
public class SpringMailConfig implements ApplicationContextAware, EnvironmentAware {

    private static final String JAVA_MAIL_FILE = "classpath:mail/javamail.properties";

    private ApplicationContext applicationContext;
    private Environment environment;

   

    @Bean
    public JavaMailSender mailSender() throws IOException {

        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Basic mail sender configuration, based on emailconfig.properties
        mailSender.setHost("");
        mailSender.setPort(2);
        mailSender.setProtocol("");
        mailSender.setUsername("");
        mailSender.setPassword("");

        // JavaMail-specific mail sender configuration, based on javamail.properties
        //final Properties javaMailProperties = new Properties();
        //javaMailProperties.load(this.applicationContext.getResource(JAVA_MAIL_FILE).getInputStream());
        //mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;

    }



	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		
	}



	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		
	}
}
