
package tech.aiflowy.ai.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wechat.mp")
public class WeChatProperties {

    private boolean enabled = false;
    private String appId;
    private String secret;
    private String token;
    private String aesKey;

    
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getAesKey() {
        return aesKey;
    }
    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    

}
