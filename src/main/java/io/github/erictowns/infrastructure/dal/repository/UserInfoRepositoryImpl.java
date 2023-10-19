package io.github.erictowns.infrastructure.dal.repository;

import io.github.erictowns.domain.user.dto.UserInfoDto;
import io.github.erictowns.domain.user.repository.UserInfoRepository;
import io.github.erictowns.infrastructure.dal.mapper.mysql.mysql01.UserInfoMapper;
import io.github.erictowns.infrastructure.dal.po.UserInfoPo;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * desc: user repository impl
 * mapper可能是空的(datasource配置enable不为true的时候)
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 22:51
 */
@Repository
public class UserInfoRepositoryImpl implements UserInfoRepository {

    private UserInfoMapper userMapper;

    public void setUserMapper(UserInfoMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserInfoDto getUserInfoById(String id) {
        UserInfoPo userInfoPo;
        if (userMapper == null) {
            userInfoPo = null;
        } else {
            userInfoPo = userMapper.selectUserInfoById(id);
        }
        return UserInfoPo.toDto(userInfoPo);
    }
}
