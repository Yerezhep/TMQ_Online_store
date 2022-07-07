package kz.tmq.tmq_online_store.serivce;

import kz.tmq.tmq_online_store.domain.Role;

public interface RoleService {

    Role findRoleByName(String name);

}
