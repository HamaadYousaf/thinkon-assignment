package com.thinkon.user_management;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Stores the information of the user mapping to the "users" table in the database */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty private String username;

    @NotEmpty private String firstName;

    @NotEmpty private String lastName;

    @NotEmpty private String email;

    @NotEmpty private String phone;
}
