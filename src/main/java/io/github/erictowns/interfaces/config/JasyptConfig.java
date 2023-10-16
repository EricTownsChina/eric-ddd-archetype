package io.github.erictowns.interfaces.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import io.github.erictowns.common.utils.JasyptUtil;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: enable use Jasypt in spring
 *
 * @author EricTowns
 * @date 2023/10/8 15:18
 */
@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 自定义 StringEncryptor，覆盖默认的 StringEncryptor
     * bean 名称是必需的，从 1.5 版开始按名称检测自定义字符串加密程序,默认 bean 名称为：jasyptStringEncryptor
     *
     * @return {@link StringEncryptor}
     */
    @Bean("jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = JasyptUtil.getDefaultStringPBEConfig(applicationName);
        encryptor.setConfig(config);
        return encryptor;
    }

}
