package kz.tmq.tmq_online_store.auth.controller;

import kz.tmq.tmq_online_store.auth.dto.user.UserInfoResponse;
import kz.tmq.tmq_online_store.auth.security.UserPrincipal;
import kz.tmq.tmq_online_store.auth.serivce.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserInfoResponse userInfoResponse = userService.getUserInfo(userPrincipal.getEmail());
        return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
    }

}
