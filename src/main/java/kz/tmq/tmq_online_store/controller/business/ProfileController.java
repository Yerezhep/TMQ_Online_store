package kz.tmq.tmq_online_store.controller.business;

import kz.tmq.tmq_online_store.dto.profile.EditProfileRequest;
import kz.tmq.tmq_online_store.dto.profile.ProfileResponse;
import kz.tmq.tmq_online_store.security.UserPrincipal;
import kz.tmq.tmq_online_store.serivce.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        ProfileResponse profileResponse = userService.getProfile(userPrincipal.getEmail());
        return new ResponseEntity<>(profileResponse, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ProfileResponse> editProfile(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                       @RequestBody EditProfileRequest editProfileRequest) {
       ProfileResponse profileResponse = userService.editProfile(userPrincipal.getEmail(), editProfileRequest);
       return new ResponseEntity<>(profileResponse, HttpStatus.OK);
    }

}
