package com.denysenko.telegramopenapiproject.model;

import lombok.Data;
import org.hibernate.annotations.NaturalId;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;
    @NaturalId
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return this.id != null && this.id.equals(admin.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
