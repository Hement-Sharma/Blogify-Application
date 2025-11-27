package com.codeWithhHemant.blog.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="user_name",nullable=false,length = 100)
    private String name;

    @Column(name="user_email",nullable=false,length = 30)
    private String email;

    @Column(name="user_password",nullable=false,length = 150)
    private String password;

    @Column(name="about_user",nullable=false,length = 200)
    private String about;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name="uId",referencedColumnName = "user_id"),
                                  inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
    private List<Role> roles;
}
