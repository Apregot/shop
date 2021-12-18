package com.melnikovsavva.shop.users;

import com.melnikovsavva.shop.orders.OrderEntity;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "USER")
public class UserEntity {
    @Setter(AccessLevel.NONE)
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @Setter(AccessLevel.PROTECTED)
    @Column(name = "number", nullable = false, unique = true)
    @NaturalId(mutable = true)
    private String number;

    @Setter(AccessLevel.PROTECTED)
    @Column(name = "name")
    private String name;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @Column(name = "name")
    private List<OrderEntity> orders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
