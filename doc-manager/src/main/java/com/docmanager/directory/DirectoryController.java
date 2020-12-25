package com.docmanager.directory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ExposesResourceFor(Directory.class)
@RequestMapping(path = "/directory",produces = "application/json")
public class DirectoryController {
	private DirectoryService service;
	private DirectoryResourceMapper mapper;
	
	@Autowired
	public DirectoryController(DirectoryService service, DirectoryResourceMapper mapper) {
		this.service = service;
		this.mapper = mapper;
		service.setRootPath("C:\\\\tmp\\\\documentation");
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "get",produces = "application/json")
	public ResponseEntity<Collection<Directory>> getDirectories() {
        return ResponseEntity.ok(mapper.toResourceCollection(service.getFullDirectoryFromRootPath()));
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(path = "/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam(name = "fileName") String fileName, HttpServletRequest request) {
		String filePath = "";
		try {
			filePath = URLDecoder.decode(fileName,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		Path path = Paths.get(filePath);
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        if(!resource.exists()) {
             return ResponseEntity.notFound().build();
        } 
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
	
}
