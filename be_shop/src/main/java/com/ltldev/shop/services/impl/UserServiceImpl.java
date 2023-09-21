package com.ltldev.shop.services.impl;

import com.ltldev.shop.dto.user.LoginDTO;
import com.ltldev.shop.dto.user.UserDTO;
import com.ltldev.shop.exception.DataNotFoundException;
import com.ltldev.shop.models.Role;
import com.ltldev.shop.models.User;
import com.ltldev.shop.repositorys.RoleRepository;
import com.ltldev.shop.repositorys.UserRepository;
import com.ltldev.shop.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
        return userRepository.save(newUser);
    }

    @Override
    public String login(LoginDTO loginDTO) {
        return null;
    }
}
