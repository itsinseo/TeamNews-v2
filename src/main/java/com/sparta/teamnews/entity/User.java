package com.sparta.teamnews.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity     //Entity클래스
@Getter
@Setter
@Table(name = "user")  //DB제작시 추가
@NoArgsConstructor  //기본 생성자
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @Column(name = "username", nullable = false, unique = true)
    private String username;
  
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profilename", nullable = false)
    private String profilename;

    @Column(name = "introduce", nullable = false)
    private String introduce;


    public User(String username, String password, String profilename, String introduce) {
        this.username = username;
        this.password = password;
        this.profilename = profilename;
        this.introduce = introduce;

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

}
