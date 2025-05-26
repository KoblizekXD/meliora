package io.github.aa55h.meliora.controller;

import io.github.aa55h.meliora.dto.FeatureResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Used for endpoints to retrieve public health information about the application.
 */
@RestController
@RequestMapping("/api/v1/health")
public class HealthController {
    /**
     * This endpoint will always return a 200 OK response, indicating that the application is running and healthy.
     */
    @GetMapping("/check")
    public ResponseEntity<Void> checkStatus() {
        return ResponseEntity.ok().build();
    }
    
    @Value("${meliora.auth.disable-signup}")
    private boolean disableSignup;
    
    @GetMapping("/features")
    public ResponseEntity<FeatureResponse> getFeatures() {
        return ResponseEntity.ok(new FeatureResponse(!disableSignup));
    }
}
