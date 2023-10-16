package io.github.erictowns.domain.user.repository;

/**
 * Description: user info cache repo
 *
 * @author EricTowns
 * @date 2023/10/10 16:36
 */
public interface UserInfoCacheRepository {

    String getUserName(String userId);

    void setUserName(String userId, String username);

}
