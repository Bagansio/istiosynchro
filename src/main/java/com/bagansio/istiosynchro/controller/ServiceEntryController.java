package com.bagansio.istiosynchro.controller;

import com.bagansio.istiosynchro.api.dto.ServiceEntryRequest;
import com.bagansio.istiosynchro.service.ArgoCDService;
import com.bagansio.istiosynchro.service.GitService;
import com.bagansio.istiosynchro.service.ServiceEntryService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.bagansio.istiosynchro.api.ServiceEntryApi;

import java.io.IOException;

@RestController
public class ServiceEntryController implements ServiceEntryApi {

    @Autowired
    private ServiceEntryService serviceEntryService;

    @Autowired
    private GitService gitService;

    @Autowired
    private ArgoCDService argoCDService;

    @Override
    public ResponseEntity<Void> serviceEntryPost(ServiceEntryRequest serviceEntryRequest) {
        // Implement your logic here
        String yamlContent = serviceEntryService.generateServiceEntryYaml(serviceEntryRequest.getName(), serviceEntryRequest.getHost(), serviceEntryRequest.getPort(), serviceEntryRequest.getProtocol());
        try {
            gitService.commitAndPushYamlToRepo("main", yamlContent);
            argoCDService.triggerSync("swagger-app");
        } catch (GitAPIException ex) {
            System.out.println("Error gitapiexception: " + ex);
            return ResponseEntity.internalServerError().build();
        } catch (IOException ex) {
            System.out.println("Error ioexception: " + ex);
            return ResponseEntity.internalServerError().build();
        }
        System.out.println("Received request: " + serviceEntryRequest);
        return ResponseEntity.ok().build();
    }
}