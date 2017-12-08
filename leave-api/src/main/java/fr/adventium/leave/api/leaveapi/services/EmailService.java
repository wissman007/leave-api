package fr.adventium.leave.api.leaveapi.services;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;
	
	
	public void sendMailWithInline(
			  final String recipientName, final String recipientEmail, final String imageResourceName,
			  final InputStreamSource imageSource, final String imageContentType, final Locale locale)
			  throws MessagingException {

			    // Prepare the evaluation context
			    final Context ctx = new Context(locale);
			    ctx.setVariable("name", recipientName);
			    ctx.setVariable("subscriptionDate", new Date());
			    ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
			    ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

			    // Prepare message using a Spring helper
			    final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			    final MimeMessageHelper message =
			        new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
			    message.setSubject("Example HTML email with inline image");
			    message.setFrom("thymeleaf@example.com");
			    message.setTo(recipientEmail);

			    // Create the HTML body using Thymeleaf
			    final String htmlContent = this.templateEngine.process("email", ctx);
			    message.setText(htmlContent, true); // true = isHtml

			    // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
			   
			    message.addInline(imageResourceName, imageSource, imageContentType);

			    // Send mail
			    this.mailSender.send(mimeMessage);

			}

}
