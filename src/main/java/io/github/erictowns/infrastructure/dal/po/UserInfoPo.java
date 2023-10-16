package io.github.erictowns.infrastructure.dal.po;

import io.github.erictowns.domain.user.dto.UserInfoDto;

/**
 * desc: user info PO
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 21:45
 */
public class UserInfoPo {

    private String id;
    private String username;
    private Integer age;
    private String address;

    public static UserInfoDto toDto(UserInfoPo userInfoPo) {
        if (userInfoPo == null) {
            return null;
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(userInfoPo.getId());
        userInfoDto.setUsername(userInfoPo.getUsername());
        userInfoDto.setAge(userInfoPo.getAge());
        userInfoDto.setAddress(userInfoPo.getAddress());
        return userInfoDto;
    }

    public String getId() {
        return id;
    }

    public UserInfoPo setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserInfoPo setUsername(String username) {
        this.username = username;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserInfoPo setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserInfoPo setAddress(String address) {
        this.address = address;
        return this;
    }
}
