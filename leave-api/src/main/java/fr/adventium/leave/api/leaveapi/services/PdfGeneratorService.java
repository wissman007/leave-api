package fr.adventium.leave.api.leaveapi.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

@Service
public class PdfGeneratorService {
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public String generatePdf(String fileName){
		
		OutputStream outputStream = null;
		try {
			Context context = new Context();
			context.setVariable("name", "Thomas");
			 
			// Get the plain HTML with the resolved ${name} variable!
			String html = templateEngine.process("template", context);
												 	
			outputStream = new FileOutputStream("C:\\Developpement\\test.pdf");
			ITextRenderer renderer = new ITextRenderer();
			
			renderer.setDocumentFromString(html, "file:///C:/Users/ceugenawa-e/git/leave-api/leave-api/src/main/resources/");
			
			renderer.layout();
			renderer.createPDF(outputStream);
			
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "C:\\Developpement\\test.pdf";
		
	}
}
