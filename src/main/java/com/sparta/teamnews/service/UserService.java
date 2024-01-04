package com.sparta.teamnews.service;

import com.sparta.teamnews.entity.User;
import com.sparta.teamnews.jwt.JwtUtil;
import com.sparta.teamnews.repository.UserRepository;
import com.sparta.teamnews.security.UserDetailsImpl;
import com.sparta.teamnews.service.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String profilename = requestDto.getProfilename();
        String introduce = requestDto.getIntroduce();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
//        String email = requestDto.getEmail();
//        Optional<User> checkEmail = userRepository.findByEmail(email);
//        if (checkEmail.isPresent()) {
//            throw new IllegalArgumentException("중복된 Email 입니다.");
//        }

        // 사용자 등록
        User user = new User(username, password, profilename, introduce);
        userRepository.save(user);
    }

    public UserResponseDto loginUser(UserRequestDto userRequestDto, HttpServletResponse response) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        User user = findUser(username);

        // password 체크
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("password 오류입니다.");
        }

        // JWT 생성 및 헤더에 추가
        String token = jwtUtil.createToken(username);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return new UserResponseDto("로그인 성공", HttpStatus.OK.value());
    }

    public UserResponseDto logoutUser(HttpServletRequest request) {
        // Redis 연동 후
        // 로그아웃 요청 JWT 를 블랙리스트에 등록

        return new UserResponseDto("로그아웃 성공, 테스트용 반환임 제거 필요", HttpStatus.OK.value());
    }

    // username 으로 사용자 찾기 + null 체크
    public User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("해당 username 이 존재하지 않습니다.")
        );
    }
    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 id값이 존재하지 않습니다.")
        );
    }

    public UserResponseDto getProfile(UserDetailsImpl userDetails) {
        User user = findUser(userDetails.getId());

        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateProfile(ProfileRequestDto profileRequestDto, UserDetailsImpl userDetails) {
        String modifyprofilename = profileRequestDto.getModifyprofilename();
        String modifyintroduce = profileRequestDto.getModifyintroduce();

        User user = findUser(userDetails.getId());  //id를 이용해 user찾기
        user.setProfilename(modifyprofilename);
        user.setIntroduce(modifyintroduce);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return userResponseDto;             //Dto 리턴
    }

    @Transactional
    public UserResponseDto updatePassword(PwdRequestDto pwdRequestDto, UserDetailsImpl userDetails) { //확일할 패스워드와 유저정보 같이 받아옴
        String password = pwdRequestDto.getPassword();
        String modifyPassword = passwordEncoder.encode(pwdRequestDto.getModifyPassword());//패스워드 인코딩
        if(!passwordEncoder.matches(password,userDetails.getPassword())){ //입력한 패스워드와 유저의 패스워드가 맞는지 확인
            throw new IllegalArgumentException("수정확인을 위해 필요한 비밀번호가 틀립니다.");
        }

        User user = findUser(userDetails.getId());  //id를 이용해 user찾기
        user.setPassword(modifyPassword);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        //쿠키 제거 해주기 Logout메서드를 부르면 해결될 듯 하다.

        return userResponseDto;             //Dto 리턴
    }
}
