package kz.tmq.tmq_online_store.dto.profile;

import kz.tmq.tmq_online_store.domain.enums.Gender;
import lombok.Data;

@Data
public class EditProfileRequest {

    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private String city;
    private String address;
    private String phoneNumber;

}
