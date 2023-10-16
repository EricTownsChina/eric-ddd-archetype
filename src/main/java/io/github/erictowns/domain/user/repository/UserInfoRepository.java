package io.github.erictowns.domain.user.repository;

import io.github.erictowns.domain.user.dto.UserInfoDto;

/**
 * desc: user repository
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 21:52
 */
public interface UserInfoRepository {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfoDto getUserInfoById(String userId);

}
