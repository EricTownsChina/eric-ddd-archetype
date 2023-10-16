package io.github.erictowns.infrastructure.dal.mapper.mysql.mysql01;

import io.github.erictowns.infrastructure.dal.po.UserInfoPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * desc: user mapper
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 22:49
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 根据用户ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserInfoPo selectUserInfoById(String id);

}
