package io.github.erictowns.domain.user.repository;

import io.github.erictowns.domain.user.dto.UserInfoDto;
import io.github.erictowns.infrastructure.dal.po.UserInfoPo;

import java.util.List;

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

    /**
     * 批量添加
     *
     * @param data 数据
     */
    void batchAdd(List<UserInfoPo> data);

}
