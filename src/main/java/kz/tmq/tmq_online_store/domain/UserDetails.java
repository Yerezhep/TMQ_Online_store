package kz.tmq.tmq_online_store.domain;

import kz.tmq.tmq_online_store.domain.enums.Gender;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_details")
@Data
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String city;

    private String address;

    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

}
