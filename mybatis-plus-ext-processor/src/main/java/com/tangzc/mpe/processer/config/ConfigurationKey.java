package com.tangzc.mpe.processer.config;

import com.tangzc.mpe.autotable.annotation.Table;

/**
 * 配置键值。
 */
public enum ConfigurationKey {

    /**
     * 是否全局entity开启自动创建。
     */
    GLOBAL_ENABLE("processor.global.enable", "false"),

    /**
     * 全局entity开启自动创建的标志注解。
     */
    GLOBAL_ENABLE_ANNOTATION("processor.global.annotation", Table.class.getName()),

    /**
     * 包含实体所有字段的类的名称后缀。
     */
    ENTITY_DEFINE_SUFFIX("processor.entityDefineSuffix", "Define"),

    /**
     * 包含实体父类的字段是否使用严格模式：只有protected或者public修饰的可悲子类继承的才算。
     */
    ENTITY_DEFINE_STRICT_EXTENDS("processor.entityDefineStrictExtends", "false"),

    /**
     * <p>包含实体所有字段的类生成后的路径，支持相对路径的写法
     * <p>比如：..define，..表示在上一级目录，即实体所在目录同级的define目录下
     * <p>比如：. 表示在实体所在的目录下
     */
    ENTITY_DEFINE_PACKAGE_NAME("processor.entityDefinePackageName", "."),


    /**
     * 生成的Mapper的后缀。
     */
    MAPPER_SUFFIX("processor.mapperSuffix", "Mapper"),

    /**
     * <p>mapper生成后的路径，支持相对路径的写法
     * <p>比如：..mapper，..表示在上一级目录，即实体所在目录同级的mapper目录下
     * <p>比如：. 表示在实体所在的目录下
     */
    MAPPER_PACKAGE_NAME("processor.mapperPackageName", "."),

    /**
     * <p>指定的Mapper的父类，通常用于自定义Mapper的场景
     * <p>要求：
     * <p>1、值需要是类的全路径
     * <p>2、自定义的父类Mapper必须继承自com.baomidou.mybatisplus.core.mapper.BaseMapper
     * <p>3、自定义的父类Mapper必须保留泛型T
     */
    MAPPER_SUPERCLASS_NAME("processor.mapperSuperclassName", ""),

    /**
     * 生成的Repository的后缀。
     */
    REPOSITORY_SUFFIX("processor.repositorySuffix", "Repository"),

    /**
     * <p>Repository生成后的路径，支持相对路径的写法
     * <p>比如：..repository，..表示在上一级目录，即实体所在目录同级的repository目录下
     * <p>比如：. 表示在实体所在的目录下
     */
    REPOSITORY_PACKAGE_NAME("processor.repositoryPackageName", ".");

    /**
     * 获取配置键。
     */
    private final String configKey;
    /**
     * 获取配置默认值。
     */
    private final String defaultValue;

    ConfigurationKey(String configKey, String defaultValue) {
        this.configKey = configKey;
        this.defaultValue = defaultValue;
    }

    public String getConfigKey() {
        return configKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
