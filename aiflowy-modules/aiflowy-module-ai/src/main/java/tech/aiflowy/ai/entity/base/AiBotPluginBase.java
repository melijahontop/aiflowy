package tech.aiflowy.ai.entity.base;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


public class AiBotPluginBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 插件id
     */
    @Id(keyType = KeyType.Generator, value = "snowFlakeId", comment = "插件id")
    private Long id;

    /**
     * 图标地址
     */
    @Column(comment = "图标地址")
    private String icon;

    /**
     * 名称
     */
    @Column(comment = "名称")
    private String name;

    /**
     * 描述
     */
    @Column(comment = "描述")
    private String description;

    /**
     * 类型
     */
    @Column(comment = "类型")
    private Integer type;

    /**
     * 基础URL
     */
    @Column(comment = "基础URL")
    private String baseUrl;

    /**
     * 请求头
     */
    @Column(comment = "请求头")
    private String headers;

    /**
     * 认证类型
     */
    @Column(comment = "认证类型")
    private String authType;

    /**
     * apiKey
     */
    @Column(comment = "apiKey")
    private String apiKey;

    /**
     * 创建时间
     */
    @Column(comment = "创建时间")
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
