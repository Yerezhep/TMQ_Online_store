package kz.tmq.tmq_online_store.auth.controller;

import kz.tmq.tmq_online_store.auth.dto.auth.*;
import kz.tmq.tmq_online_store.auth.serivce.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registrationRequest, BindingResult bindingResult) {
        RegisterResponse registerResponse = authService.addUser(registrationRequest, bindingResult);
        return new ResponseEntity(registerResponse, HttpStatus.OK);
    }

    @GetMapping("register/activate/{code}")
    public ResponseEntity<String> activate(@PathVariable(name = "code") String code) {
        return new ResponseEntity(authService.activateUser(code), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/forgot/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable(name = "email") String email) {
        return new ResponseEntity<>(authService.sendPasswordResetCode(email), HttpStatus.OK);
    }

    @GetMapping("/reset-password/{code}")
    public ResponseEntity<String> resetPassword(@PathVariable(name = "code") String code) {
        return new ResponseEntity<>(authService.getUserByPasswordResetCode(code), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest, BindingResult bindingResult) {
        return new ResponseEntity<>(authService.resetPassword(resetPasswordRequest, bindingResult), HttpStatus.OK);
    }

}
