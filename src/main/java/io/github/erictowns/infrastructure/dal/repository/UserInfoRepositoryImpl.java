package io.github.erictowns.infrastructure.dal.repository;

import io.github.erictowns.domain.user.dto.UserInfoDto;
import io.github.erictowns.domain.user.repository.UserInfoRepository;
import io.github.erictowns.infrastructure.dal.mapper.mysql.mysql01.UserInfoMapper;
import io.github.erictowns.infrastructure.dal.po.UserInfoPo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * desc: user repository impl
 * mapper可能是空的(datasource配置enable不为true的时候)
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 22:51
 */
@Repository
public class UserInfoRepositoryImpl implements UserInfoRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoRepositoryImpl.class);

    private final UserInfoMapper userMapper;

    public UserInfoRepositoryImpl(UserInfoMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserInfoDto getUserInfoById(String id) {
        UserInfoPo userInfoPo;
        if (userMapper == null) {
            userInfoPo = null;
        } else {
            userInfoPo = userMapper.selectById(id);
        }
        return UserInfoPo.toDto(userInfoPo);
    }

    @Override
    public void batchAdd(List<UserInfoPo> data) {
        if (CollectionUtils.isEmpty(data)) {
            LOGGER.warn("batch data is empty.");
        }
        userMapper.insertIgnoreBatchSomeColumn(data);
    }
}
