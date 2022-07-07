package kz.tmq.tmq_online_store.dto.profile;

import kz.tmq.tmq_online_store.domain.enums.Gender;
import lombok.Data;

@Data
public class ProfileResponse {

    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String city;
    private String address;
    private String phoneNumber;

}
