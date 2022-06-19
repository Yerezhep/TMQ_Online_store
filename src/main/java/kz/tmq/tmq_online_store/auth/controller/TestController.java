package kz.tmq.tmq_online_store.auth.controller;

import kz.tmq.tmq_online_store.auth.exception.ExceptionHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController extends ExceptionHandling {

    @GetMapping("")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Works", HttpStatus.OK);
    }

}
