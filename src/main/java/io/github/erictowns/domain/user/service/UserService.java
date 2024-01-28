package io.github.erictowns.domain.user.service;

import io.github.erictowns.domain.user.dto.UserInfoDto;

import java.util.List;

/**
 * desc: user service
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 21:49
 */
public interface UserService {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userInfoDto 用户信息DTO
     * @return 用户信息
     */
    UserInfoDto getUserInfoById(UserInfoDto userInfoDto);

    /**
     * 根据ID获取用户名称
     * 先尝试从缓存中获取, 再从数据库获取
     *
     * @param id id
     * @return 用户名称
     */
    String getUsernameById(String id);

    /**
     * 导入
     *
     * @param data 数据
     */
    void doImport(List<UserInfoDto> data);

}
