package kz.tmq.tmq_online_store.auth.serivce;

import kz.tmq.tmq_online_store.auth.domain.Role;

public interface RoleService {

    Role findRoleByName(String name);

}
