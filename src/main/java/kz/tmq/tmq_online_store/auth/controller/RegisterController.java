package kz.tmq.tmq_online_store.auth.controller;

import kz.tmq.tmq_online_store.auth.dto.register.RegisterRequest;
import kz.tmq.tmq_online_store.auth.dto.register.RegisterResponse;
import kz.tmq.tmq_online_store.auth.serivce.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {

    private final AuthService authService;

    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registrationRequest, BindingResult bindingResult) {
        RegisterResponse registerResponse = authService.addUser(registrationRequest, bindingResult);
        return ResponseEntity.ok(registerResponse);
    }

}
