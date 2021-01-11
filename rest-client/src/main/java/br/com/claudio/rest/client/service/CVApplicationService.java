package br.com.claudio.rest.client.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CVApplicationService {
  @Value("${app.endpoint}")
  private String serverUrl;

  public void sendCV(RestTemplate restTemplate){

    Path source = Paths.get("/Users/cresende/Desktop/Claudio_Augusto_de_Paulo_Resende_CV.docx");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body
        = new LinkedMultiValueMap<>();


    body.add("file", new FileSystemResource(source));
    body.add("firstname", "Claudio Augusto");
    body.add("lastname", "De Paulo Resende");
    body.add("email", "claudioaprdev@gmail.com");
    body.add("jobtitle", "Software Engineer");
    body.add("source", "Linkedin");


    HttpEntity<MultiValueMap<String, Object>> requestEntity
        = new HttpEntity<>(body, headers);


    ResponseEntity<String> response = restTemplate
        .postForEntity(serverUrl, requestEntity, String.class);

    if(response.getStatusCode() == HttpStatus.OK){
      System.out.println(response.getBody());
    } else {
      System.out.println("Something went wrong");
    }


  }

  public String getServerUrl() {
    return serverUrl;
  }

  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }

}
