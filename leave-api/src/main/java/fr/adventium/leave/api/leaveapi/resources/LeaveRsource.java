package fr.adventium.leave.api.leaveapi.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.MessagingException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.adventium.leave.api.leaveapi.dao.repositories.LeaveRepository;
import fr.adventium.leave.api.leaveapi.entities.Leave;
import fr.adventium.leave.api.leaveapi.services.EmailService;
import fr.adventium.leave.api.leaveapi.services.PdfGeneratorService;
import fr.adventium.leave.api.leaveapi.user.exceptions.LeaveNotSavedException;

@RestController
public class LeaveRsource {
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	@Autowired
	private PdfGeneratorService pdfGeneratorService;
	
	
	@CrossOrigin(origins="*")
	@GetMapping(path="/leaves")
	public List<Leave> retrieveLeaves(){
		return leaveRepository.findAll();
	}
	
	
	@PostMapping(path="/leaves")
	public ResponseEntity<Object> createLeave(@RequestBody Leave leave){
		Leave leaveSaved = leaveRepository.save(leave);
		
		URI location =		
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(leaveSaved.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}
	
	@GetMapping(path="/leaves/{id}")
	public Resource<Leave> retrieveLeave(@PathVariable int id) {
		Optional<Leave> leaveOption = this.leaveRepository.findById(id); 
		
		if(!leaveOption.isPresent()){
			throw new LeaveNotSavedException("id-"+id);
		}

		//Hateos
		// "all-users", SERVER_PATH + "users"
		 
		Resource<Leave> resource = new Resource<Leave>(leaveOption.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveLeaves());
		resource.add(linkTo.withRel("all-leaves"));
		
		return resource ;
	}
	@CrossOrigin(origins="*")
	@GetMapping(path="/leaves/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> generateLeavePdf(@PathVariable int id) throws IOException{
		Optional<Leave> leaveOption = this.leaveRepository.findById(id); 
		
//		if(!leaveOption.isPresent()){
//			throw new LeaveNotSavedException("id-"+id);
//		}
		
		
		 HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "inline; filename=test.pdf");
		 
		//ClassPathResource pdfFile = new ClassPathResource(pdfGeneratorService.generatePdf(""));
	        File file = new File(pdfGeneratorService.generatePdf(""));
	        FileInputStream inputStream = new FileInputStream(file);
	        
		return ResponseEntity
	            .ok()
	            .headers(headers)
	           
	            .contentType(
	                    MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(inputStream));
	
	}
	@Autowired
	private EmailService emailService;
	
	@Value(value = "classpath:static/img/pizza.png")
	private org.springframework.core.io.Resource logoImageResource;
	
	
	@CrossOrigin(origins="*")
	@GetMapping(value = "/sendMailWithInlineImage")
	public String sendMailWithInline(
	  
	  )
	  throws MessagingException, IOException {
				
		 String imageContentType = new MimetypesFileTypeMap().getContentType(logoImageResource.getFile());
		 final InputStreamSource imageSource = new ByteArrayResource(IOUtils.toByteArray(logoImageResource.getInputStream()));
		    
	    this.emailService.sendMailWithInline(
	        "testRece", "recipientEmail", logoImageResource.getFilename(),
	        imageSource, imageContentType, new Locale("",""));
	    return "redirect:sent.html";

	}
	
}
