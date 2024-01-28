package io.github.erictowns.interfaces.api;

import io.github.erictowns.application.user.UserAppService;
import io.github.erictowns.domain.user.dto.UserInfoDto;
import io.github.erictowns.domain.user.service.UserService;
import io.github.erictowns.interfaces.entity.Resp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: user interface
 *
 * @author EricTownsChina@outlook.com
 * @date 2023-10-03 21:31
 */
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    private final UserAppService userAppService;

    public UserController(UserService userService,
                          UserAppService userAppService) {
        this.userService = userService;
        this.userAppService = userAppService;
    }

    @PostMapping("/get")
    public Resp getUserInfo(@RequestBody UserInfoDto userInfo) {
        UserInfoDto userInfoDto = userAppService.getUserInfoById(userInfo);
        return Resp.success(userInfoDto);
    }

    @GetMapping("/name/{id}")
    public Resp getUsername(@PathVariable String id) {
        String username = userService.getUsernameById(id);
        return Resp.success(username);
    }

    @PostMapping("/import")
    public Resp doImport(@RequestBody List<UserInfoDto> data) {
        userService.doImport(data);
        return Resp.success();
    }

}
