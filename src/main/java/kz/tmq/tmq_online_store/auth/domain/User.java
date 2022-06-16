package kz.tmq.tmq_online_store.auth.domain;

import kz.tmq.tmq_online_store.auth.domain.enums.AuthProvider;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String activationCode;

    private String passwordResetCode;

    @Column(nullable = false)
    private boolean isActive = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;

}
