//package com.xiaoxialemi.scmybatisplus.generator;
//
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.config.ConstVal;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.OutputFile;
//import com.baomidou.mybatisplus.generator.config.TemplateConfig;
//import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.util.FileUtils;
//import com.baomidou.mybatisplus.generator.util.RuntimeUtils;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.Function;
//
///**
// * @author xiaoxialemi
// * @Description
// * @createTime 2021年07月07日 10:13:00
// */
//public class AbstractTemplateEngine {
//    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    /**
//     * 配置信息
//     */
//    private ConfigBuilder configBuilder;
//
//
//    /**
//     * 模板引擎初始化
//     */
//    @NotNull
//    public abstract AbstractTemplateEngine init(@NotNull ConfigBuilder configBuilder);
//
//    /**
//     * 输出实体文件
//     *
//     * @param tableInfo 表信息
//     * @param objectMap 渲染数据
//     * @since 3.5.0
//     */
//    protected void outputEntity(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
//        String entityName = tableInfo.getEntityName();
//        String entityPath = getPathInfo(OutputFile.entity);
//        if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(entityPath)) {
//            getTemplateFilePath(template -> template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())).ifPresent((entity) -> {
//                String entityFile = String.format((entityPath + File.separator + "%s" + suffixJavaOrKt()), entityName);
//                outputFile(new File(entityFile), objectMap, entity);
//            });
//        }
//    }
//
//    /**
//     * 输出Mapper文件(含xml)
//     *
//     * @param tableInfo 表信息
//     * @param objectMap 渲染数据
//     * @since 3.5.0
//     */
//    protected void outputMapper(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
//        // MpMapper.java
//        String entityName = tableInfo.getEntityName();
//        String mapperPath = getPathInfo(OutputFile.mapper);
//        if (StringUtils.isNotBlank(tableInfo.getMapperName()) && StringUtils.isNotBlank(mapperPath)) {
//            getTemplateFilePath(TemplateConfig::getMapper).ifPresent(mapper -> {
//                String mapperFile = String.format((mapperPath + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
//                outputFile(new File(mapperFile), objectMap, mapper);
//            });
//        }
//        // MpMapper.xml
//        String xmlPath = getPathInfo(OutputFile.mapperXml);
//        if (StringUtils.isNotBlank(tableInfo.getXmlName()) && StringUtils.isNotBlank(xmlPath)) {
//            getTemplateFilePath(TemplateConfig::getXml).ifPresent(xml -> {
//                String xmlFile = String.format((xmlPath + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
//                outputFile(new File(xmlFile), objectMap, xml);
//            });
//        }
//    }
//
//    /**
//     * 输出service文件
//     *
//     * @param tableInfo 表信息
//     * @param objectMap 渲染数据
//     * @since 3.5.0
//     */
//    protected void outputService(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
//        // IMpService.java
//        String entityName = tableInfo.getEntityName();
//        String servicePath = getPathInfo(OutputFile.service);
//        if (StringUtils.isNotBlank(tableInfo.getServiceName()) && StringUtils.isNotBlank(servicePath)) {
//            getTemplateFilePath(TemplateConfig::getService).ifPresent(service -> {
//                String serviceFile = String.format((servicePath + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()), entityName);
//                outputFile(new File(serviceFile), objectMap, service);
//            });
//        }
//        // MpServiceImpl.java
//        String serviceImplPath = getPathInfo(OutputFile.serviceImpl);
//        if (StringUtils.isNotBlank(tableInfo.getServiceImplName()) && StringUtils.isNotBlank(serviceImplPath)) {
//            getTemplateFilePath(TemplateConfig::getServiceImpl).ifPresent(serviceImpl -> {
//                String implFile = String.format((serviceImplPath + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()), entityName);
//                outputFile(new File(implFile), objectMap, serviceImpl);
//            });
//        }
//    }
//
//    /**
//     * 输出controller文件
//     *
//     * @param tableInfo 表信息
//     * @param objectMap 渲染数据
//     * @since 3.5.0
//     */
//    protected void outputController(@NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
//        // MpController.java
//        String controllerPath = getPathInfo(OutputFile.controller);
//        if (StringUtils.isNotBlank(tableInfo.getControllerName()) && StringUtils.isNotBlank(controllerPath)) {
//            getTemplateFilePath(TemplateConfig::getController).ifPresent(controller -> {
//                String entityName = tableInfo.getEntityName();
//                String controllerFile = String.format((controllerPath + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()), entityName);
//                outputFile(new File(controllerFile), objectMap, controller);
//            });
//        }
//    }
//
//    /**
//     * 输出文件
//     *
//     * @param file         文件
//     * @param objectMap    渲染信息
//     * @param templatePath 模板路径
//     * @since 3.5.0
//     */
//    protected void outputFile(@NotNull File file, @NotNull Map<String, Object> objectMap, @NotNull String templatePath) {
//        if (isCreate(file)) {
//            try {
//                // 全局判断【默认】
//                boolean exist = file.exists();
//                if (!exist) {
//                    File parentFile = file.getParentFile();
//                    FileUtils.forceMkdir(parentFile);
//                }
//                writer(objectMap, templatePath, file);
//            } catch (Exception exception) {
//                throw new RuntimeException(exception);
//            }
//        }
//    }
//
//    /**
//     * 获取模板路径
//     *
//     * @param function function
//     * @return 模板路径
//     * @since 3.5.0
//     */
//    @NotNull
//    protected Optional<String> getTemplateFilePath(@NotNull Function<TemplateConfig, String> function) {
//        TemplateConfig templateConfig = getConfigBuilder().getTemplateConfig();
//        String filePath = function.apply(templateConfig);
//        if (StringUtils.isNotBlank(filePath)) {
//            return Optional.of(templateFilePath(filePath));
//        }
//        return Optional.empty();
//    }
//
//    /**
//     * 获取路径信息
//     *
//     * @param outputFile 输出文件
//     * @return 路径信息
//     */
//    @Nullable
//    protected String getPathInfo(@NotNull OutputFile outputFile) {
//        return getConfigBuilder().getPathInfo().get(outputFile);
//    }
//
//    /**
//     * 批量输出 java xml 文件
//     */
//    @NotNull
//    public AbstractTemplateEngine batchOutput() {
//        try {
//            ConfigBuilder config = this.getConfigBuilder();
//            List<TableInfo> tableInfoList = config.getTableInfoList();
//            tableInfoList.forEach(tableInfo -> {
//                Map<String, Object> objectMap = this.getObjectMap(config, tableInfo);
//                Optional.ofNullable(config.getInjectionConfig()).ifPresent(t -> t.beforeOutputFile(tableInfo, objectMap));
//                // Mp.java
//                outputEntity(tableInfo, objectMap);
//                // mapper and xml
//                outputMapper(tableInfo, objectMap);
//                // service
//                outputService(tableInfo, objectMap);
//                // MpController.java
//                outputController(tableInfo, objectMap);
//            });
//        } catch (Exception e) {
//            throw new RuntimeException("无法创建文件，请检查配置信息！", e);
//        }
//        return this;
//    }
//
//    /**
//     * 输出文件
//     *
//     * @param objectMap    渲染数据
//     * @param templatePath 模板路径
//     * @param outputFile   输出文件
//     * @throws Exception ex
//     * @deprecated 3.5.0
//     */
//    @Deprecated
//    protected void writerFile(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
//        if (StringUtils.isNotBlank(templatePath)) this.writer(objectMap, templatePath, outputFile);
//    }
//
//    /**
//     * 将模板转化成为文件
//     *
//     * @param objectMap    渲染对象 MAP 信息
//     * @param templatePath 模板文件
//     * @param outputFile   文件生成的目录
//     * @see #writer(Map, String, File)
//     * @deprecated 3.5.0
//     */
//    @Deprecated
//    public void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull String outputFile) throws Exception {
//
//    }
//
//    /**
//     * 将模板转化成为文件
//     *
//     * @param objectMap    渲染对象 MAP 信息
//     * @param templatePath 模板文件
//     * @param outputFile   文件生成的目录
//     * @throws Exception 异常
//     * @since 3.5.0
//     */
//    public void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull File outputFile) throws Exception {
//        this.writer(objectMap, templatePath, outputFile.getPath());
//        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
//    }
//
//    /**
//     * 打开输出目录
//     */
//    public void open() {
//        String outDir = getConfigBuilder().getGlobalConfig().getOutputDir();
//        if (StringUtils.isBlank(outDir) || !new File(outDir).exists()) {
//            System.err.println("未找到输出目录：" + outDir);
//        } else if (getConfigBuilder().getGlobalConfig().isOpen()) {
//            try {
//                RuntimeUtils.openDir(outDir);
//            } catch (IOException e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//    }
//
//    /**
//     * 渲染对象 MAP 信息
//     *
//     * @param config    配置信息
//     * @param tableInfo 表信息对象
//     * @return ignore
//     */
//    @NotNull
//    public Map<String, Object> getObjectMap(@NotNull ConfigBuilder config, @NotNull TableInfo tableInfo) {
//        GlobalConfig globalConfig = config.getGlobalConfig();
//        Map<String, Object> controllerData = config.getStrategyConfig().controller().renderData(tableInfo);
//        Map<String, Object> objectMap = new HashMap<>(controllerData);
//        Map<String, Object> mapperData = config.getStrategyConfig().mapper().renderData(tableInfo);
//        objectMap.putAll(mapperData);
//        Map<String, Object> serviceData = config.getStrategyConfig().service().renderData(tableInfo);
//        objectMap.putAll(serviceData);
//        Map<String, Object> entityData = config.getStrategyConfig().entity().renderData(tableInfo);
//        objectMap.putAll(entityData);
//        objectMap.put("config", config);
//        objectMap.put("package", config.getPackageConfig().getPackageInfo());
//        objectMap.put("author", globalConfig.getAuthor());
//        objectMap.put("kotlin", globalConfig.isKotlin());
//        objectMap.put("swagger", globalConfig.isSwagger());
//        objectMap.put("date", globalConfig.getCommentDate());
//        // 存在 schemaName 设置拼接 . 组合表名
//        String schemaName = config.getDataSourceConfig().getSchemaName();
//        if (StringUtils.isNotBlank(schemaName)) {
//            schemaName += ".";
//            tableInfo.setConvert(true);
//        } else {
//            schemaName = "";
//        }
//        objectMap.put("schemaName", schemaName);
//        objectMap.put("table", tableInfo);
//        objectMap.put("entity", tableInfo.getEntityName());
//        return objectMap;
//    }
//
//    /**
//     * 模板真实文件路径
//     *
//     * @param filePath 文件路径
//     * @return ignore
//     */
//    @NotNull
//    public abstract String templateFilePath(@NotNull String filePath);
//
//    /**
//     * 检测文件是否存在
//     *
//     * @return 文件是否存在
//     * @deprecated 3.5.0
//     */
//    @Deprecated
//    protected boolean isCreate(String filePath) {
//        return isCreate(new File(filePath));
//    }
//
//    /**
//     * 检查文件是否创建文件
//     *
//     * @param file 文件
//     * @return 是否创建文件
//     * @since 3.5.0
//     */
//    protected boolean isCreate(@NotNull File file) {
//        // 全局判断【默认】
//        return !file.exists() || getConfigBuilder().getGlobalConfig().isFileOverride();
//    }
//
//    /**
//     * 文件后缀
//     */
//    protected String suffixJavaOrKt() {
//        return getConfigBuilder().getGlobalConfig().isKotlin() ? ConstVal.KT_SUFFIX : ConstVal.JAVA_SUFFIX;
//    }
//
//    @NotNull
//    public ConfigBuilder getConfigBuilder() {
//        return configBuilder;
//    }
//
//    @NotNull
//    public AbstractTemplateEngine setConfigBuilder(@NotNull ConfigBuilder configBuilder) {
//        this.configBuilder = configBuilder;
//        return this;
//    }
//}
