package com.code.mybatis;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.code.common.Constants;
import com.code.core.base.AbstractEnum;
import com.code.core.resolver.BaseEnumSerializer;
import com.code.mybatis.handler.*;
import com.code.utils.CommonUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author ping
 */
@Configuration
@MapperScan({"com.code.dao*"})
public class MybatisPlusConfig {
    /**
     * ???????????????map??????
     */
    public static final Map<String, JavaType> JSON_TYPE_MAP = new HashMap<>(20);
    /**
     * ???????????????map?????????key?????????.??????(table_name.column_name)
     */
    private static final Map<String, Class> JSON_CLASS_MAP = new HashMap<>(20);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // ??????json?????????????????????????????????????????????
    static {
        // ?????????????????????????????????
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIMESTAMP_DESC_STR));
        SimpleModule simpleModule = new SimpleModule();
        // ??????????????????
        simpleModule.addSerializer(AbstractEnum.class, new BaseEnumSerializer());
        objectMapper.registerModule(simpleModule);
    }

    /**
     * ?????????
     */
    @Value("${base.domain.package:com.code.pojo.domain}")
    private String baseDomainPackage;
    @Value("${mybatis.mapper-locations:classpath*:mappers/**/*.xml}")
    private String mybatisMapperLocations;

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * mybatis-plus????????????
     *
     * @return ????????????
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * ?????????????????????SqlSessionFactory
     *
     * @param dds ?????????
     * @return
     * @throws Exception ????????????
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dds) throws Exception {
        MybatisSqlSessionFactoryBean sf = new MybatisSqlSessionFactoryBean();
        sf.setDataSource(dds);
        sf.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mybatisMapperLocations));

        MybatisConfiguration mc = new MybatisConfiguration();
        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        /**
         * ????????????????????????Handler
         */
        mc.setDefaultEnumTypeHandler(AutoEnumTransformHandler.class);
        // json??????
        mc.getTypeHandlerRegistry().register(List.class, JsonTypeHandler.class);
        mc.getTypeHandlerRegistry().register(JsonList.class, JsonTypeHandler.class);
        mc.getTypeHandlerRegistry().register(JsonMap.class, JsonTypeHandler.class);
        mc.getTypeHandlerRegistry().register(Map.class, JsonTypeHandler.class);
        // ??????LocalDateTime??????
        mc.getTypeHandlerRegistry().register(LocalDateTime.class, LocalDateTimeHandler.class);
        mc.getTypeHandlerRegistry().register(LocalDate.class, LocalDateHandler.class);
        // ?????????json??????
        scanJsonFields();
        // ????????????????????????????????????handler
        if (CommonUtil.isNotEmpty(JSON_CLASS_MAP)) {
            for (Map.Entry<String, Class> en : JSON_CLASS_MAP.entrySet()) {
                mc.getTypeHandlerRegistry().register(en.getValue(), JsonObjectHandler.class);
            }
        }
        mc.setSafeResultHandlerEnabled(false);
        sf.setConfiguration(mc);
        sf.setPlugins(paginationInterceptor());

        return sf.getObject();
    }

    /**
     * @throws ClassNotFoundException
     */
    private void scanJsonFields() throws ClassNotFoundException {
        // ?????????domain???????????????
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(TableName.class));
        Set<BeanDefinition> set = provider.findCandidateComponents(baseDomainPackage);
        if (set.isEmpty()) {
            return;
        }
        Iterator<BeanDefinition> iterator = set.iterator();
        Class<?> c;
        String tbName;
        Field[] fields;
        TableName tbAn;
        TableField tfAn;
        JavaType javaType;
        // ?????????????????????
        while (iterator.hasNext()) {
            c = Class.forName(iterator.next().getBeanClassName());
            if ((tbAn = c.getDeclaredAnnotation(TableName.class)) != null && CommonUtil.isNotBlank(tbAn.value())) {
                tbName = tbAn.value();
                fields = c.getDeclaredFields();
                // ???????????????@JsonField???????????????
                for (Field f : fields) {
                    if (f.getAnnotation(JsonField.class) == null) {
                        continue;
                    }
                    if ((tfAn = f.getAnnotation(TableField.class)) == null || CommonUtil.isBlank(tfAn.value())) {
                        throw new IllegalStateException("@JsonField????????????????????????@TableField??????????????????@TableField???value????????????");
                    }
                    // ?????????Map??????List
                    if (Map.class.isAssignableFrom(f.getType()) || List.class.isAssignableFrom(f.getType())) {
                        Type genericType = f.getGenericType();
                        if (genericType instanceof ParameterizedType) {
                            if (f.getType() == List.class) {
                                // ?????????list,????????????????????????????????????ArrayList
                                javaType = objectMapper.getTypeFactory().constructCollectionType(
                                        (Class<? extends Collection>) f.getType(),
                                        (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0]);
                            } else if (f.getType() == Map.class) {
                                // ?????????Map,????????????????????????????????????HashMap
                                javaType = objectMapper.getTypeFactory().constructMapType(
                                        (Class<? extends Map>) f.getType(),
                                        (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0],
                                        (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[1]);
                            } else if (f.getType() == JsonList.class) {
                                javaType = objectMapper.getTypeFactory().constructCollectionType(
                                        (Class<? extends Collection>) f.getType(),
                                        (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0]);
                            } else if (f.getType() == JsonMap.class) {
                                javaType = objectMapper.getTypeFactory().constructMapType(
                                        (Class<? extends Map>) f.getType(),
                                        (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0],
                                        (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[1]);
                            } else {
                                throw new IllegalStateException("????????????????????????");
                            }
                            JSON_TYPE_MAP.put(String.format("%s.%s", tbName, tfAn.value()).toLowerCase(), javaType);
                            JSON_TYPE_MAP.put(String.format("%s.%s", tbName, f.getName()).toLowerCase(), javaType);

                        }
                    } else {
                        JSON_CLASS_MAP.put(String.format("%s.%s", tbName, tfAn.value()).toLowerCase(), f.getType());
                        JSON_CLASS_MAP.put(String.format("%s.%s", tbName, f.getName()).toLowerCase(), f.getType());
                    }
                }
            }
        }
    }
}
