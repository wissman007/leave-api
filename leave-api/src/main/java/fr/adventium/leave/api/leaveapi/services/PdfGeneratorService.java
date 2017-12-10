package fr.adventium.leave.api.leaveapi.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

@Service
public class PdfGeneratorService {
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public String generatePdf(String fileName){
		
		OutputStream outputStream = null;
		try {
			Context context = new Context();
			context.setVariable("name", "Thomas");
			context.setVariable("imageResourceName", "static/img/"); 
			// Get the plain HTML with the resolved ${name} variable!
			String html = templateEngine.process("template", context);
												 	
			outputStream = new FileOutputStream("D:\\EXECUTIONS\\test.pdf");
			ITextRenderer renderer = new ITextRenderer();
			
		//	renderer.getFontResolver().addFont("/static/fonts/GHEAGpalatBld.ttf", BaseFont.EMBEDDED);
		//	renderer.getFontResolver().addFont("/static/fonts/GHEAGrapalatBlit.ttf", BaseFont.EMBEDDED);
		    //Set sb = ITextFontResolver.getDistinctFontFamilyNames("/static/fonts/glyphicons-halflings-regular.ttf", BaseFont.IDENTITY_H,true);
			
			renderer.setDocumentFromString(html,  new ClassPathResource("/static/").getURL().toExternalForm());
			
			
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
		
		return "D:\\EXECUTIONS\\test.pdf";
		
	}
}
