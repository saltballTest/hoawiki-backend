package top.horizonask.hoawiki.ossstorage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @time: 2022/2/11 5:09
 */

@Component
@ConfigurationProperties(prefix = "oss")
public class OssConfig {
    public String getWeChatEnv() {
        return this.WeChatEnv;
    }

    public void setWeChatEnv(String WeChatEnv) {
        this.WeChatEnv = WeChatEnv;
    }

    private String WeChatEnv;
}
