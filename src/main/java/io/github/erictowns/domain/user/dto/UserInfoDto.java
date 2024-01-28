package io.github.erictowns.domain.user.dto;

import io.github.erictowns.common.validgroups.Search;
import io.github.erictowns.infrastructure.dal.po.UserInfoPo;

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
    private String password;
    private String phone;
    private Integer age;
    private String address;

    public UserInfoPo toPo() {
        UserInfoPo target = new UserInfoPo();
        target.setId(this.id);
        target.setUsername(this.username);
        target.setPassword(this.password);
        target.setUsername(this.username);
        target.setPhone(this.phone);
        target.setAge(this.age);
        target.setAddress(this.address);
        return target;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
