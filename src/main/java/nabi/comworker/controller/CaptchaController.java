package nabi.comworker.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nabi.comworker.captchaservice.CaptchaService;

@RestController
@RequestMapping("/api/v1/captcha")
public class CaptchaController {

	@Autowired
    private CaptchaService captchaService;
	

    /**
     * Generate Captcha Image
     */
    @GetMapping("/generate")
    public ResponseEntity<Map<String, String>> getCaptcha() throws IOException {
        Map<String, String> captcha = captchaService.generateCaptcha();
        return ResponseEntity.ok(captcha);
    }

    /**
     * Validate Captcha
     */
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateCaptcha(
            @RequestParam String captchaKey,
            @RequestParam String userInput) {

        boolean valid = captchaService.validateCaptcha(captchaKey, userInput);

        Map<String, Object> response = new HashMap<>();
        response.put("valid", valid);

        return ResponseEntity.ok(response);
    }

}
