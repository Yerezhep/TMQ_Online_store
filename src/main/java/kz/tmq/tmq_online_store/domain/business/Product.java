package kz.tmq.tmq_online_store.domain.business;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String title;

    @Column
    @Type(type = "text")
    @NotNull
    private String description;

    @Column
    @NotNull
    private Double price;

    @Column
    @NotNull
    private boolean status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<CartItem> cartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductImage> images;
}
