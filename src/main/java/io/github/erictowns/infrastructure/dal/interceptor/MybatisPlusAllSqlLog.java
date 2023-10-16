package io.github.erictowns.infrastructure.dal.interceptor;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

/**
 * Description: 打印完整的sql(带参数的sql) <br/>
 * <a href="https://blog.csdn.net/weixin_44802870/article/details/124884233">MyBatis-Plus 配置 及 MyBatis-Plus下打印完整不带？的sql</a><br/>
 * <a href="https://blog.csdn.net/liufang1991/article/details/51243642?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0-51243642-blog-124884233.235^v38^pc_relevant_yljh&spm=1001.2101.3001.4242.1&utm_relevant_index=3">Mybatis打印可执行mysql语句(工具和拦截器两种方式)</a>
 *
 * @author lrq, EricTowns
 * @date 2023/10/9 20:36
 */
public class MybatisPlusAllSqlLog implements InnerInterceptor {

    public static final Logger log = LoggerFactory.getLogger(MybatisPlusAllSqlLog.class);

    /**
     * 打印完整sql信息
     *
     * @param boundSql  绑定信息
     * @param ms        表达式映射
     * @param parameter 参数信息
     */
    private static void logInfo(BoundSql boundSql, MappedStatement ms, Object parameter) {
        try {
            String sql = showSql(ms.getConfiguration(), boundSql);
            log.info("\r\n\t\tsqlId: {} " +
                            "\r\n\t\tparam: {}" +
                            "\r\n\t\tsql  : {}\r\n",
                    ms.getId(),
                    parameter,
                    sql);
        } catch (RuntimeException e) {
            log.error("打印完整sql异常: ", e);
        }
    }

    /**
     * 获取完整的sql(?的替换)
     *
     * @param configuration 表达式映射的配置
     * @param boundSql      绑定信息
     * @return 完整的sql
     */
    public static String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!CollectionUtils.isEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?",
                        Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 根据参数类型对参数进行处理
     * 如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理
     *
     * @param obj 参数值
     * @return 参数展示值
     */
    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    @Override
    public void beforeQuery(Executor executor,
                            MappedStatement ms,
                            Object parameter,
                            RowBounds rowBounds,
                            ResultHandler resultHandler,
                            BoundSql boundSql) {
        logInfo(boundSql, ms, parameter);
    }

    @Override
    public void beforeUpdate(Executor executor,
                             MappedStatement ms,
                             Object parameter) {
        BoundSql boundSql = ms.getBoundSql(parameter);
        logInfo(boundSql, ms, parameter);
    }

}
