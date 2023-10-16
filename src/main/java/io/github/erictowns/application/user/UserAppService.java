package io.github.erictowns.application.user;

import io.github.erictowns.domain.user.dto.UserInfoDto;

/**
 * Description: user info application service
 *
 * @author EricTowns
 * @date 2023/10/16 16:28
 */
public interface UserAppService {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userInfoDto 用户信息DTO
     * @return 用户信息
     */
    UserInfoDto getUserInfoById(UserInfoDto userInfoDto);

}
