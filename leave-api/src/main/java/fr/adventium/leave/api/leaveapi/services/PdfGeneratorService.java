package fr.adventium.leave.api.leaveapi.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.openhtmltopdf.bidi.support.ICUBidiReorderer;
import com.openhtmltopdf.bidi.support.ICUBidiSplitter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder.TextDirection;

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
			
			
			
			renderer.getFontResolver().addFont("static/fonts/ARIALUNI.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
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
	
	public String generateHtmlToPdf(String fileName) throws Exception{
			//  String htmlContent = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><style>@font-face{font-family: noto; src: url('static/fonts/NotoNaskhArabicUI-Regular.ttf');}body, body *{font-family: 'noto', sans-serif;}</style></head><body><div class=\"border\"><h1>عربي</h1></div></body></html>";
			
			
			Context context = new Context();
			context.setVariable("name", "Thomas");
			context.setVariable("imageResourceName", "static/img/"); 
			// Get the plain HTML with the resolved ${name} variable!
			String html = templateEngine.process("template", context);
												 	
		   	
		   ByteArrayOutputStream os = new ByteArrayOutputStream();
		   PdfRendererBuilder builder = new PdfRendererBuilder();
		   builder.useUnicodeBidiSplitter(new ICUBidiSplitter.ICUBidiSplitterFactory());
		   builder.useUnicodeBidiReorderer(new ICUBidiReorderer());
		   builder.defaultTextDirection(TextDirection.RTL); // OR RTL
		   builder.useFont(new File("fonts/ARIALUNI.TTF"), "Arial Unicode MS");
		   builder.withHtmlContent(html, new ClassPathResource("/static/").getURL().toExternalForm());
		        
		   builder.toStream(os);
		   builder.run();
		   byte[] pdfAsBytes = os.toByteArray();
		   os.close();
		   Path file = Paths.get("D:\\EXECUTIONS\\test.pdf");
		   Files.write(file, pdfAsBytes);
		
		   return "D:\\EXECUTIONS\\test.pdf";
	}
	
	
}
