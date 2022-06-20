package kz.tmq.tmq_online_store.auth.serivce;

import kz.tmq.tmq_online_store.auth.domain.User;
import kz.tmq.tmq_online_store.auth.dto.user.UserInfoResponse;

public interface UserService {

    User findById(Long id);

    User findByEmail(String email);

    User findByUsername(String email);

    User findByEmailOrUsername(String emailOrUsername);

    User findByPasswordResetCode(String passwordResetCode);

    User findByActivationCode(String activationCode);

    UserInfoResponse getUserInfo(String email);

}
