package com.ltldev.shop.services.impl;

import com.ltldev.shop.dto.user.LoginDTO;
import com.ltldev.shop.dto.user.UserDTO;
import com.ltldev.shop.exception.BadCredentialsException;
import com.ltldev.shop.exception.DataNotFoundException;
import com.ltldev.shop.models.Role;
import com.ltldev.shop.models.User;
import com.ltldev.shop.repositorys.RoleRepository;
import com.ltldev.shop.repositorys.UserRepository;
import com.ltldev.shop.services.IUserService;
import com.ltldev.shop.components.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        // check phone
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataNotFoundException("Phone number already exists");
        }
        // convert userDTO => user
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .password(userDTO.getPassword())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not fount "));
        newUser.setRole(role);
        // Check if user not have facebookId and googleId,  hashcode pass
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String endCodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(endCodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public User login(LoginDTO loginDTO) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(loginDTO.getPhoneNumber());
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid phone number / password");
        }

        return optionalUser.get();// user name and pass
    }

    @Override
    public String loginToken(LoginDTO loginDTO) throws Exception {
        // check account
        Optional<User> optionalUser = userRepository.findByPhoneNumber(loginDTO.getPhoneNumber());
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid phone number / password");
        }
        // return optionalUser.get() but return jwt token ?
        User existingUser = optionalUser.get();
        // check pass
        if (existingUser.getFacebookAccountId() == 0 && existingUser.getGoogleAccountId() == 0){
            if (!passwordEncoder.matches(loginDTO.getPassword(),existingUser.getPassword())){
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getPhoneNumber(), loginDTO.getPassword(), existingUser.getAuthorities());
        // authentication security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);// user name and pass
    }
}
