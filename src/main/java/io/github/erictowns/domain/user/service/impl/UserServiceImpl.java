package io.github.erictowns.domain.user.service.impl;

import io.github.erictowns.common.utils.ValidationUtil;
import io.github.erictowns.common.validgroups.Search;
import io.github.erictowns.domain.user.dto.UserInfoDto;
import io.github.erictowns.domain.user.repository.UserInfoRepository;
import io.github.erictowns.domain.user.service.UserService;
import io.github.erictowns.infrastructure.dal.po.UserInfoPo;
import io.github.erictowns.infrastructure.dal.repository.UserInfoCacheRepositoryImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * desc: user service impl
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 21:50
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoCacheRepositoryImpl userInfoCacheRepository;

    public UserServiceImpl(UserInfoRepository userInfoRepository,
                           UserInfoCacheRepositoryImpl userInfoCacheRepository) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoCacheRepository = userInfoCacheRepository;
    }

    @Override
    public UserInfoDto getUserInfoById(UserInfoDto userInfoDto) {
        ValidationUtil.validate(userInfoDto, Search.class);
        String userId = userInfoDto.getId();
        return userInfoRepository.getUserInfoById(userId);
    }

    @Override
    public String getUsernameById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        // get from cache
        String username = userInfoCacheRepository.get(id);
        if (StringUtils.isEmpty(username)) {
            // get from db
            UserInfoDto userInfo = userInfoRepository.getUserInfoById(id);
            if (userInfo != null) {
                username = userInfo.getUsername();
                userInfoCacheRepository.set(id, username);
            }
        }
        return username;
    }

    @Override
    public void doImport(List<UserInfoDto> data) {
        List<UserInfoPo> polist = data.stream().map(UserInfoDto::toPo).collect(Collectors.toList());

        userInfoRepository.batchAdd(polist);
    }
}
