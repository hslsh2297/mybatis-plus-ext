package com.tangzc.mpe.processer.builder;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.squareup.javapoet.ClassName;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.processer.annotation.AutoMapper;
import com.tangzc.mpe.processer.config.ConfigurationKey;
import com.tangzc.mpe.processer.config.MybatisPlusExtProcessConfig;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Arrays;
import java.util.List;

public class MapperBuilder extends BaseBuilder {

    private final Elements elementUtils;

    public MapperBuilder(Filer filer, Messager messager, Types typeUtils, Elements elementUtils, MybatisPlusExtProcessConfig mybatisPlusExtProcessConfig) {
        super(filer, messager, elementUtils, mybatisPlusExtProcessConfig);
        this.elementUtils = elementUtils;
    }


    public String buildMapper(TypeElement element, AutoMapper autoMapper) {

        String entityPackageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        String entityName = element.getSimpleName().toString();

        String suffix = getMapperSuffix(autoMapper);
        String mapperName = getTargetName(autoMapper.value(), entityName, suffix);
        String packageName = getValueOrDefault(autoMapper.packageName(), ConfigurationKey.MAPPER_PACKAGE_NAME);
        String mapperPackageName = getTargetPackageName(element, packageName);

        // 检查文件已经被创建了，自动跳过
        String filePath = mapperPackageName + "." + mapperName;

        if (isExist(mapperPackageName, mapperName)) {
            return filePath;
        }

        ClassName mapperSuperclassName = getMapperSuperclassName(autoMapper);

        String dsAnnoImport = null;
        String dsAnno = null;
        if (autoMapper.withDSAnnotation()) {
            Table table = element.getAnnotation(Table.class);
            if (table != null && !table.dsName().isEmpty()) {
                dsAnnoImport = "import com.baomidou.dynamic.datasource.annotation.DS;";
                dsAnno = "@DS(\"" + table.dsName() + "\")";
            } else {
                warn(entityPackageName + "." + entityName + "缺少@Table的dsName配置，无法为" + mapperPackageName + "." + mapperName + "添加@DS ");
            }
        }

        List<String> lines = Arrays.asList(
                "package " + mapperPackageName + ";",
                "",
                "import " + mapperSuperclassName.getCanonicalName() + ";",
                dsAnnoImport,
                "import " + entityPackageName + "." + entityName + ";",
                "import org.apache.ibatis.annotations.Mapper;",
                "",
                dsAnno,
                "@Mapper",
                "public interface " + mapperName + " extends " + mapperSuperclassName.simpleName() + "<" + entityName + "> {",
                "}"
        );

        writeToFile(filePath, lines);

        return filePath;
    }

    private ClassName getMapperSuperclassName(AutoMapper autoMapper) {

        ClassName mapperSuperclassName = ClassName.get(BaseMapper.class);
        String baseMapperClassName = autoMapper.superclassName();
        if (baseMapperClassName.isEmpty()) {
            baseMapperClassName = mybatisPlusExtProcessConfig.get(ConfigurationKey.MAPPER_SUPERCLASS_NAME);
        }
        if (!baseMapperClassName.isEmpty()) {
            int lastIndexOf = baseMapperClassName.lastIndexOf(".");
            String baseMapperPackageName = baseMapperClassName.substring(0, lastIndexOf);
            String baseMapperName = baseMapperClassName.substring(lastIndexOf + 1);
            mapperSuperclassName = ClassName.get(baseMapperPackageName, baseMapperName);
        }
        return mapperSuperclassName;
    }

    private String getMapperSuffix(AutoMapper autoMapper) {
        String suffix = autoMapper.suffix();
        if ("".equals(suffix)) {
            suffix = this.mybatisPlusExtProcessConfig.get(ConfigurationKey.MAPPER_SUFFIX);
        }
        return suffix;
    }
}
