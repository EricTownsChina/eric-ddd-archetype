package io.github.erictowns.infrastructure.dal.plugins;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * Description: SQL注入器,添加了批量注入的方法
 *
 * @author EricTowns
 * @date 2024/1/28 23:12
 */
public class MybatisSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        methodList.add(new InsertIgnoreBatchAllColumn(e -> !e.isLogicDelete()));
        return methodList;
    }
}
