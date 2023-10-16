package io.github.erictowns.application.user.impl;

import io.github.erictowns.application.user.UserAppService;
import io.github.erictowns.domain.user.dto.UserInfoDto;
import io.github.erictowns.domain.user.service.UserService;
import org.springframework.stereotype.Component;

/**
 * Description: user info application service impl
 *
 * @author EricTowns
 * @date 2023/10/16 16:29
 */
@Component
public class UserAppServiceImpl implements UserAppService {

    public final UserService userService;

    public UserAppServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserInfoDto getUserInfoById(UserInfoDto userInfoDto) {
        return userService.getUserInfoById(userInfoDto);
    }
}
