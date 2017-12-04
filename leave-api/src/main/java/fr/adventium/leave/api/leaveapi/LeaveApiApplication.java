package fr.adventium.leave.api.leaveapi;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class LeaveApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveApiApplication.class, args);
	}
	
	@Bean
	public LocaleResolver getLocalResolver(){
		SessionLocaleResolver localResolver = new SessionLocaleResolver();
		localResolver.setDefaultLocale(Locale.FRENCH);
		
		return localResolver;
		
	}
	
	@Bean
	public ResourceBundleMessageSource bundleMessageSource(){
			ResourceBundleMessageSource mesageSource = new ResourceBundleMessageSource();
			mesageSource.setBasename("messages");
		return mesageSource;
	}
	
	
	
}
