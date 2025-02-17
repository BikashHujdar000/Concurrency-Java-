package com.example.bikash.SpringTest.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType name;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
//    private Set<User> users = new HashSet<>();

}
