package kz.tmq.tmq_online_store.auth.serivce;

import kz.tmq.tmq_online_store.auth.domain.User;

public interface UserService {

    User findUserByEmail(String email);

    User findUserByUsername(String email);

}
