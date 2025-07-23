
package tech.aiflowy.ai.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(WeChatProperties.class)
@ConditionalOnProperty(prefix = "weChat.mp", name = "enabled", havingValue = "true", matchIfMissing = false)
public class WeChatConfig {


    @Bean
    public WxMpService wxMpService(WeChatProperties properties) {
        System.out.println(">>>> 启用微信服务");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage(properties));
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(WeChatProperties properties) {
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(properties.getAppId());
        configStorage.setSecret(properties.getSecret());
        configStorage.setToken(properties.getToken());
        if (StringUtils.hasLength(properties.getAesKey())) {
            configStorage.setAesKey(properties.getAesKey());
        }
        return configStorage;
    }
}
