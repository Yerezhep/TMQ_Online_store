package kz.tmq.tmq_online_store.serivce;

import kz.tmq.tmq_online_store.domain.User;
import kz.tmq.tmq_online_store.dto.profile.EditProfileRequest;
import kz.tmq.tmq_online_store.dto.profile.ProfileResponse;

public interface UserService {

    User findById(Long id);

    User findByEmail(String email);

    User findByUsername(String email);

    User findByEmailOrUsername(String emailOrUsername);

    User findByPasswordResetCode(String passwordResetCode);

    User findByActivationCode(String activationCode);

    ProfileResponse getProfile(String email);

    ProfileResponse editProfile(String email, EditProfileRequest editProfileRequest);

}
