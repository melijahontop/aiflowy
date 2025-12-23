package tech.aiflowy.codegen;

import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.core.handler.FastjsonTypeHandler;
import com.mybatisflex.core.keygen.KeyGenerators;
import tech.aiflowy.common.entity.DateEntity;
import tech.aiflowy.common.entity.DateTreeEntity;
import tech.aiflowy.common.entity.TreeEntity;
import tech.aiflowy.common.util.StringUtil;

public class Util {


    public static GlobalConfig createBaseConfig() {
        return createBaseConfig("options");
    }


    public static GlobalConfig createBaseConfig(String optionsColumns) {

        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("tb_");

        //设置生成 entity
        globalConfig.setEntityGenerateEnable(true);

        globalConfig.getEntityConfig().setWithBaseClassEnable(true);
        globalConfig.getEntityConfig().setBaseOverwriteEnable(true);
        globalConfig.getEntityConfig().setColumnCommentEnable(true);


        globalConfig.setMapperGenerateEnable(true);
        globalConfig.setServiceGenerateEnable(true);
        globalConfig.setServiceImplGenerateEnable(true);


        // 设置 entity 父类
        globalConfig.setEntitySuperClassFactory(table -> {
            if (table.containsColumn("id", "pid") || table.containsColumn("id","parent_id")) {
                if (table.containsColumn("created", "modified")) {
                    return DateTreeEntity.class;
                } else {
                    return TreeEntity.class;
                }
            } else if (table.containsColumn("created", "modified")) {
                return DateEntity.class;
            }
            return null;
        });

        // 不生成 Controller
        globalConfig.setControllerGenerateEnable(false);
//        globalConfig.setControllerOverwriteEnable(false);
//        try {
//            ClassPathResource cpr = new ClassPathResource("templates/controller.tpl");
//            globalConfig.setControllerTemplatePath(cpr.getFile().getAbsolutePath());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        ColumnConfig pkColumnConfig = new ColumnConfig();
        pkColumnConfig.setPrimaryKey(true);
        pkColumnConfig.setKeyType(KeyType.Generator);
        pkColumnConfig.setKeyValue(KeyGenerators.snowFlakeId);
        pkColumnConfig.setColumnName("id");


        ColumnConfig tenantColumnConfig = new ColumnConfig();
        tenantColumnConfig.setTenantId(true);
        tenantColumnConfig.setColumnName("tenant_id");

        ColumnConfig logicDeleteColumnConfig = new ColumnConfig();
        logicDeleteColumnConfig.setLogicDelete(true);
        logicDeleteColumnConfig.setColumnName("is_deleted");

        globalConfig.setColumnConfig(pkColumnConfig);
        globalConfig.setColumnConfig(tenantColumnConfig);
        globalConfig.setColumnConfig(logicDeleteColumnConfig);


        String[] optionsColumnArray = optionsColumns.split(",");
        for (String optionsColumn : optionsColumnArray) {
            if (StringUtil.hasText(optionsColumn)) {
                ColumnConfig optionsColumnConfig = new ColumnConfig();
                optionsColumnConfig.setPropertyType("java.util.Map<String, Object>");
                optionsColumnConfig.setTypeHandler(FastjsonTypeHandler.class);
                optionsColumnConfig.setColumnName(optionsColumn.trim());
                globalConfig.setColumnConfig(optionsColumnConfig);
            }
        }


        return globalConfig;
    }


}
