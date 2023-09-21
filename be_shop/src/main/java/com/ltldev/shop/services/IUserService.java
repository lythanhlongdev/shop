package com.ltldev.shop.services;

import com.ltldev.shop.dto.user.LoginDTO;
import com.ltldev.shop.dto.user.UserDTO;
import com.ltldev.shop.exception.DataNotFoundException;
import com.ltldev.shop.models.User;

public interface IUserService {

    User createUser (UserDTO userDTO) throws DataNotFoundException;
    String login (LoginDTO loginDTO);
}
