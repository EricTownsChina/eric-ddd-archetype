eric-ddd-archetype  
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
==================

简单的ddd项目代码demo, 可以做成maven archetype, 在创建新项目的时候可以使用  
项目包括了
1. 一些基本的工具类
2. mybatis-plus和redis的数据源demo代码(数据源可配置开关)
3. mybatis完整sql的打印
4. 存储型xss过滤(可配置开关)
5. 重放请求过滤(可配置开关)
6. 默认的缓存实现(caffeine)

制作成本地maven-archetype
---------------

### 1. 在项目路径下执行maven命令
``` maven
mvn archetype:create-from-project
```
成功后会在项目路径下生成target目录, 结构为
```txt
target  
  └─generated-sources  
      └─archetype  
         │- pom.xml  
         └─ ...
```

### 2. 在```target\generated-sources\archetype```路径下执行maven命令  
```maven
maven install

maven archetype:crawl
```

### 3. 在idea新项目创建中添加maven archetype
添加的archetype信息为
```text
groupId: io.github.erictowns
artifactId: eric-ddd-archetype
version: 0.0.1
```
