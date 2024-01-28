package io.github.erictowns.infrastructure.dal.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.erictowns.domain.user.dto.UserInfoDto;

/**
 * desc: user info PO
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 21:45
 */
@TableName("USER_INFO")
public class UserInfoPo {

    @TableId(type = IdType.AUTO)
    private String id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("phone")
    private String phone;

    @TableField("age")
    private Integer age;

    @TableField("address")
    private String address;

    public static UserInfoDto toDto(UserInfoPo userInfoPo) {
        if (userInfoPo == null) {
            return null;
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(userInfoPo.getId());
        userInfoDto.setUsername(userInfoPo.getUsername());
        userInfoDto.setPassword(userInfoPo.getPassword());
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
