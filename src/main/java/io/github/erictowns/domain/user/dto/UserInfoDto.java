package io.github.erictowns.domain.user.dto;

import io.github.erictowns.common.validgroups.Search;

import javax.validation.constraints.NotEmpty;

/**
 * desc: user info DTO
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 21:47
 */
public class UserInfoDto {

    @NotEmpty(message = "用户ID不能为空!", groups = Search.class)
    private String id;
    private String username;
    private Integer age;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
