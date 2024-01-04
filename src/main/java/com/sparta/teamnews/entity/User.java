package com.sparta.teamnews.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_name", nullable = false)
    private String profileName;

    @Column(name = "introduction", nullable = false)
    private String introduction;

    @Builder
    public User(String username, String password, String profileName, String introduction) {
        this.username = username;
        this.password = password;
        this.profileName = profileName;
        this.introduction = introduction;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileName(String profilename) {
        this.profileName = profilename;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
