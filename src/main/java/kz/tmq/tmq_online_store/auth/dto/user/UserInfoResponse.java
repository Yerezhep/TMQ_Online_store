package kz.tmq.tmq_online_store.auth.dto.user;

import kz.tmq.tmq_online_store.auth.domain.enums.Gender;
import lombok.Data;

@Data
public class UserInfoResponse {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String city;
    private String address;
    private String phoneNumber;

}
