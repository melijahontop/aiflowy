package tech.aiflowy.ai.entity;

import com.mybatisflex.annotation.Table;
import tech.aiflowy.ai.entity.base.BotModelBase;

import java.util.Map;

/**
 * 实体类。
 *
 * @author michael
 * @since 2024-08-28
 */

@Table("tb_bot_model")
public class BotModel extends BotModelBase {

    public Object getOption(String key) {
        Map<String, Object> options = super.getOptions();
        return options == null ? null : options.get(key);
    }

    public Object getOptionOrDefault(String key, Object defaultValue) {
        Map<String, Object> options = super.getOptions();
        return options == null ? defaultValue : options.getOrDefault(key, defaultValue);
    }

}
