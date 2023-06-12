# 基于  spring spring-cloud-alibaba nacos seata 的分布式事务 项目

### 通过本文你能学会 基于 seata nacos 的分布式事务解决方案 

1. 导入sql 不同的业务表理论上来说是在不同的库中的， 可以一个服务建一个库。 你也可以先放一个库开发测试。 
2. 修改 你本机配置文件中 对应的 ip nacos seata server ip port
3. 搭建 nacos-server docker服务 假设目录在`/docker/docker-compose/nacos`
    - 1 启动 nacos server 进入 /docker/docker-compose/nacos `docker-compose up -d`
    - 2 访问 nacos server (http://192.168.50.15:8848/nacos) 账号密码都是`nacos`
    - 2 运行`nacos-config.sh`导入 seata 的配置到 nacos中对应的命名空间`namespace`中 主要是`db`部分
    - [官方导入脚本代码](https://github.com/seata/seata/blob/1.4.2/script/config-center/README.md)
    - [我用官方脚本导入报错。 107行的配置数据，我参考脚本用postman自己手动一条一条导入的。。。官方不出直接能通过UI一键导入的数据也是很无语]
   ```text
   #For details about configuration items, see https://seata.io/zh-cn/docs/user/configurations.html
   #Transport configuration, for client and server
   transport.type=TCP
   transport.server=NIO
   transport.heartbeat=true
   transport.enableTmClientBatchSendRequest=false
   transport.enableRmClientBatchSendRequest=true
   transport.enableTcServerBatchSendResponse=false
   transport.rpcRmRequestTimeout=30000
   transport.rpcTmRequestTimeout=30000
   transport.rpcTcRequestTimeout=30000
   transport.threadFactory.bossThreadPrefix=NettyBoss
   transport.threadFactory.workerThreadPrefix=NettyServerNIOWorker
   transport.threadFactory.serverExecutorThreadPrefix=NettyServerBizHandler
   transport.threadFactory.shareBossWorker=false
   transport.threadFactory.clientSelectorThreadPrefix=NettyClientSelector
   transport.threadFactory.clientSelectorThreadSize=1
   transport.threadFactory.clientWorkerThreadPrefix=NettyClientWorkerThread
   transport.threadFactory.bossThreadSize=1
   transport.threadFactory.workerThreadSize=default
   transport.shutdown.wait=3
   transport.serialization=seata
   transport.compressor=none
   
   #Transaction routing rules configuration, only for the client
   service.vgroupMapping.default_tx_group=default
   #If you use a registry, you can ignore it
   service.default.grouplist=192.168.50.15:8091
   service.enableDegrade=false
   service.disableGlobalTransaction=false
   
   #Transaction rule configuration, only for the client
   client.rm.asyncCommitBufferLimit=10000
   client.rm.lock.retryInterval=10
   client.rm.lock.retryTimes=30
   client.rm.lock.retryPolicyBranchRollbackOnConflict=true
   client.rm.reportRetryCount=5
   client.rm.tableMetaCheckEnable=true
   client.rm.tableMetaCheckerInterval=60000
   client.rm.sqlParserType=druid
   client.rm.reportSuccessEnable=false
   client.rm.sagaBranchRegisterEnable=false
   client.rm.sagaJsonParser=fastjson
   client.rm.tccActionInterceptorOrder=-2147482648
   client.tm.commitRetryCount=5
   client.tm.rollbackRetryCount=5
   client.tm.defaultGlobalTransactionTimeout=60000
   client.tm.degradeCheck=false
   client.tm.degradeCheckAllowTimes=10
   client.tm.degradeCheckPeriod=2000
   client.tm.interceptorOrder=-2147482648
   client.undo.dataValidation=true
   client.undo.logSerialization=jackson
   client.undo.onlyCareUpdateColumns=true
   server.undo.logSaveDays=7
   server.undo.logDeletePeriod=86400000
   client.undo.logTable=undo_log
   client.undo.compress.enable=true
   client.undo.compress.type=zip
   client.undo.compress.threshold=64k
   #For TCC transaction mode
   tcc.fence.logTableName=tcc_fence_log
   tcc.fence.cleanPeriod=1h
   
   #Log rule configuration, for client and server
   log.exceptionRate=100
   
   #Transaction storage configuration, only for the server. The file, db, and redis configuration values are optional.
   store.mode=db
   store.lock.mode=file
   store.session.mode=file
   #Used for password encryption
   store.publicKey=
   
   #If `store.mode,store.lock.mode,store.session.mode` are not equal to `file`, you can remove the configuration block.
   store.file.dir=file_store/data
   store.file.maxBranchSessionSize=16384
   store.file.maxGlobalSessionSize=512
   store.file.fileWriteBufferCacheSize=16384
   store.file.flushDiskMode=async
   store.file.sessionReloadReadSize=100
   
   #These configurations are required if the `store mode` is `db`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `db`, you can remove the configuration block.
   store.db.datasource=druid
   store.db.dbType=mysql
   store.db.driverClassName=com.mysql.cj.jdbc.Driver
   store.db.url=jdbc:mysql://192.168.50.15:3306/seata_dev?rewriteBatchedStatements=true&&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false
   store.db.user=root
   store.db.password=root
   store.db.minConn=5
   store.db.maxConn=50
   store.db.globalTable=global_table
   store.db.branchTable=branch_table
   store.db.distributedLockTable=distributed_lock
   store.db.queryLimit=100
   store.db.lockTable=lock_table
   store.db.maxWait=5000
   
   #These configurations are required if the `store mode` is `redis`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `redis`, you can remove the configuration block.
   store.redis.mode=single
   store.redis.single.host=127.0.0.1
   store.redis.single.port=6379
   store.redis.sentinel.masterName=
   store.redis.sentinel.sentinelHosts=
   store.redis.maxConn=10
   store.redis.minConn=1
   store.redis.maxTotal=100
   store.redis.database=0
   store.redis.password=
   store.redis.queryLimit=100
   
   #Transaction rule configuration, only for the server
   server.recovery.committingRetryPeriod=1000
   server.recovery.asynCommittingRetryPeriod=1000
   server.recovery.rollbackingRetryPeriod=1000
   server.recovery.timeoutRetryPeriod=1000
   server.maxCommitRetryTimeout=-1
   server.maxRollbackRetryTimeout=-1
   server.rollbackRetryTimeoutUnlockEnable=false
   server.distributedLockExpireTime=10000
   server.xaerNotaRetryTimeout=60000
   server.session.branchAsyncQueueSize=5000
   server.session.enableBranchAsyncRemove=false
   server.enableParallelRequestHandle=false
   
   #Metrics configuration, only for the server
   metrics.enabled=false
   metrics.registryType=compact
   metrics.exporterList=prometheus
   metrics.exporterPrometheusPort=9898
   ```
4. 搭建 seata-server docker服务 假设目录在`/docker/docker-compose/seata`
    - 1  为了复制要修改的配置目录 先用docker run 启动一个默认的seata-server 
    - 2 启动一个临时seata server ，复制要修改的资源目录 
    ```shell
    # 
    docker run -d -rm --name seata-temp seataio/seata-server:1.6.1
    docker cp seata-temp:/seata-server/resources /docker/docker-compose/seata/resources
    ```
    - 3 参考 `/docker/docker-compose/seata/resources/application.example.yml` 修改seata-server 配置文件 `/docker/docker-compose/seata/resources/application.yml`
    ```yaml
   server:
     port: 7091

   spring:
     application:
       name: seata-server

   logging:
     config: classpath:logback-spring.xml
     file:
       path: ${user.home}/logs/seata
     extend:
       logstash-appender:
         destination: 127.0.0.1:4560
       kafka-appender:
         bootstrap-servers: 127.0.0.1:9092
         topic: logback_to_logstash
   
   console:
     user:
       username: seata
       password: seata
   
   seata:
     config:
       # support: nacos, consul, apollo, zk, etcd3
       type: nacos
       nacos:
         server-addr: 192.168.50.15:8848
         namespace: 82eca4a8-ef2f-488e-9ebd-cb70bb2f060a
         group: SEATA_GROUP
         username: nacos
         password: nacos
         context-path:
         ##if use MSE Nacos with auth, mutex with username/password attribute
         #access-key:
         #secret-key:
         data-id: seata.properties
     
     registry:
       # support: nacos, eureka, redis, zk, consul, etcd3, sofa
       type: nacos
       nacos:
         application: seata-server
         server-addr: 192.168.50.15:8848
         group: SEATA_GROUP
         namespace: 82eca4a8-ef2f-488e-9ebd-cb70bb2f060a
         cluster: default
         username: nacos
         password: nacos
         context-path:
         ##if use MSE Nacos with auth, mutex with username/password attribute
         #access-key:
         #secret-key:
     
     store:
       # support: file 、 db 、 redis
       mode: db
       db:
         datasource: druid
         db-type: mysql
         driver-class-name: com.mysql.cj.jdbc.Driver
         url: jdbc:mysql://192.168.50.15:3306/seata_dev?rewriteBatchedStatements=true&&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false
         user: root
         password: root
         min-conn: 10
         max-conn: 100
         global-table: global_table
         branch-table: branch_table
         lock-table: lock_table
         distributed-lock-table: distributed_lock
         query-limit: 1000
         max-wait: 5000
   
     server:
       service-port: 8091 #If not configured, the default is '${server.port} + 1000'
     security:
       secretKey: SeataSecretKey0c382ef121d778043159209298fd40bf3850a017
       tokenValidityInMilliseconds: 1800000
       ignore:
       urls: /,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-fe/public/**,/api/v1/auth/login
   ```
    - 4 停止临时的seata-temp
   ```shell
   docker stop seata-temp
   ```
   - 5 启动 seata server 进入/docker/docker-compose/seata 运行 `docker-compose up -d`
5. 访问 seata server (http://192.168.50.15:7091/#/TransactionInfo) 账号密码都是 `seata` 
6. 初始化项目sql
> 请注意每个使用`seata-server`的微服务的数据库都必须得有 `undo_log` 表！
```mysql
-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) DEFAULT NULL,
    `amount`  double(14, 2
                  ) DEFAULT '0.00',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account`
VALUES ('1', '1', '4000.00');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `order_no`       varchar(255) DEFAULT NULL,
    `user_id`        varchar(255) DEFAULT NULL,
    `commodity_code` varchar(255) DEFAULT NULL,
    `count`          int(11) DEFAULT '0',
    `amount`         double(14, 2
                         ) DEFAULT '0.00',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_stock`;
CREATE TABLE `t_stock`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `commodity_code` varchar(255) DEFAULT NULL,
    `name`           varchar(255) DEFAULT NULL,
    `count`          int(11) DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `commodity_code` (`commodity_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_stock
-- ----------------------------
INSERT INTO `t_stock`
VALUES ('1', 'C202306121800', '水杯', '1000');

-- ----------------------------
-- Table structure for undo_log
-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20) NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11) NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
``` 
请求前 DB 数据如下

`t_account` 表数据

| id | user_id | account |
   |----|---------|---------|
| 1  | 1       | 4000    |


`t_account` 表数据

| id | commodity_code | name | count |
   |----|----------------|------|-------|
| 1  | C202306121800  | 水杯   | 1000  |

`t_order` 表无数据生成

7. 启动 微服务 的 business account stock order
8. 访问 nacos 检查 当前的服务是否都正确注册到 nacos server
9. GET 请求 http://127.0.0.1/business/toOrder?userId=1&commodityCode=C201901140001&count=2000&amount=4000


### 服务入口为 `business` 服务下的  `BusinessController`
```java
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private OrderFeign orderFeign;
    @Resource
    private StockFeign stockFeign;
    @Resource
    private AccountFeign accountFeign;


    @GlobalTransactional
    @RequestMapping("/toOrder")
    public void toOrder (String userId, String commodityCode, Integer count, BigDecimal amount) {
        accountFeign.reduce(userId, amount);
        stockFeign.deduct(commodityCode, count);
        orderFeign.add(userId, commodityCode, count, amount);
    }
}
```

### business 服务日志如下business 服务日志如下
```shell

2023-06-12 18:29:06.317  INFO 33872 --- [p-nio-80-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2023-06-12 18:29:06.317  INFO 33872 --- [p-nio-80-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2023-06-12 18:29:06.317  INFO 33872 --- [p-nio-80-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 0 ms
2023-06-12 18:29:06.347  INFO 33872 --- [p-nio-80-exec-1] io.seata.tm.TransactionManagerHolder     : TransactionManager Singleton io.seata.tm.DefaultTransactionManager@606d2bad
2023-06-12 18:29:06.383  INFO 33872 --- [p-nio-80-exec-1] i.seata.tm.api.DefaultGlobalTransaction  : Begin new global transaction [192.168.50.15:8091:18416337030565889]
2023-06-12 18:29:07.216  INFO 33872 --- [p-nio-80-exec-1] i.seata.tm.api.DefaultGlobalTransaction  : Suspending current transaction, xid = 192.168.50.15:8091:18416337030565889
2023-06-12 18:29:07.217  INFO 33872 --- [p-nio-80-exec-1] i.seata.tm.api.DefaultGlobalTransaction  : [192.168.50.15:8091:18416337030565889] rollback status: Rollbacked
2023-06-12 18:29:07.229 ERROR 33872 --- [p-nio-80-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is feign.FeignException$InternalServerError: [500] during [GET] to [http://stock/stock/deduct?commodityCode=C201901140001&count=2000] [StockFeign#deduct(String,Integer)]: [{"timestamp":"2023-06-12T10:29:07.147+00:00","status":500,"error":"Internal Server Error","path":"/stock/deduct"}]] with root cause

feign.FeignException$InternalServerError: [500] during [GET] to [http://stock/stock/deduct?commodityCode=C201901140001&count=2000] [StockFeign#deduct(String,Integer)]: [{"timestamp":"2023-06-12T10:29:07.147+00:00","status":500,"error":"Internal Server Error","path":"/stock/deduct"}]
	at feign.FeignException.serverErrorStatus(FeignException.java:250) ~[feign-core-11.10.jar:na]
	at feign.FeignException.errorStatus(FeignException.java:197) ~[feign-core-11.10.jar:na]
	at feign.FeignException.errorStatus(FeignException.java:185) ~[feign-core-11.10.jar:na]
	...
```
### account 服务日志如下
```shell

2023-06-12 18:29:06.494  INFO 8076 --- [p-nio-81-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2023-06-12 18:29:06.494  INFO 8076 --- [p-nio-81-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2023-06-12 18:29:06.495  INFO 8076 --- [p-nio-81-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@46fda57c] was not registered for synchronization because synchronization is not active
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by com.baomidou.mybatisplus.core.toolkit.SetAccessibleAction (file:/D:/WORK/apache-maven-REPO/com/baomidou/mybatis-plus-core/3.5.3.1/mybatis-plus-core-3.5.3.1.jar) to field java.lang.invoke.SerializedLambda.capturingClass
WARNING: Please consider reporting this to the maintainers of com.baomidou.mybatisplus.core.toolkit.SetAccessibleAction
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
JDBC Connection [io.seata.rm.datasource.ConnectionProxy@7ad2c4a1] will not be managed by Spring
==>  Preparing: SELECT id,user_id,amount FROM t_account WHERE (user_id = ?)
==> Parameters: 1(String)
<==    Columns: id, user_id, amount
<==        Row: 1, 1, 4000.0
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@46fda57c]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@435cfb65] was not registered for synchronization because synchronization is not active
JDBC Connection [io.seata.rm.datasource.ConnectionProxy@45e34ff] will not be managed by Spring
original SQL: UPDATE t_account  SET user_id=?,
amount=?  WHERE id=?
SQL to parse, SQL: UPDATE t_account  SET user_id=?,
amount=?  WHERE id=?
parse the finished SQL: UPDATE t_account SET user_id = ?, amount = ? WHERE id = ?
==>  Preparing: UPDATE t_account SET user_id=?, amount=? WHERE id=?
==> Parameters: 1(String), 0.0(BigDecimal), 1(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@435cfb65]
2023-06-12 18:29:07.174  INFO 8076 --- [h_RMROLE_1_1_32] i.s.c.r.p.c.RmBranchRollbackProcessor    : rm handle branch rollback process:xid=192.168.50.15:8091:18416337030565889,branchId=18416337030565890,branchType=AT,resourceId=jdbc:mysql://192.168.50.15:3306/seata_account,applicationData=null
2023-06-12 18:29:07.176  INFO 8076 --- [h_RMROLE_1_1_32] io.seata.rm.AbstractRMHandler            : Branch Rollbacking: 192.168.50.15:8091:18416337030565889 18416337030565890 jdbc:mysql://192.168.50.15:3306/seata_account
2023-06-12 18:29:07.207  INFO 8076 --- [h_RMROLE_1_1_32] i.s.r.d.undo.AbstractUndoLogManager      : xid 192.168.50.15:8091:18416337030565889 branch 18416337030565890, undo_log deleted with GlobalFinished
2023-06-12 18:29:07.208  INFO 8076 --- [h_RMROLE_1_1_32] io.seata.rm.AbstractRMHandler            : Branch Rollbacked result: PhaseTwo_Rollbacked
```
### order 服务日志如下 报了库存不足Not enough stock的异常
```shell

JDBC Connection [io.seata.rm.datasource.ConnectionProxy@51d520a6] will not be managed by Spring
==>  Preparing: SELECT id,name,commodity_code,count FROM t_stock WHERE (commodity_code = ?)
==> Parameters: C201901140001(String)
<==    Columns: id, name, commodity_code, count
<==        Row: 1, 水杯, C201901140001, 1000
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@47dd5002]
2023-06-12 18:29:07.139 ERROR 34704 --- [p-nio-83-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.RuntimeException: Not enough stock] with root cause

java.lang.RuntimeException: Not enough stock
	at com.blankhang.stock.service.impl.StockServiceImpl.deduce(StockServiceImpl.java:42) ~[classes/:na]
	at com.blankhang.stock.service.impl.StockServiceImpl$$FastClassBySpringCGLIB$$f693087.invoke(<generated>) ~[classes/:na]
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.3.23.jar:5.3.23]
	...
```
### seata-server 日志如下
```shell

seata-seata-server-1  | 18:29:06.381  INFO --- [     batchLoggerPrint_1_1] i.s.c.r.p.server.BatchLogHandler         : timeout=60000,transactionName=toOrder(java.lang.String, java.lang.String, java.lang.Integer, java.math.BigDecimal),clientIp:192.168.50.2,vgroup:default_tx_group
seata-seata-server-1  | 18:29:06.408  INFO --- [rverHandlerThread_1_5_500] i.s.s.coordinator.DefaultCoordinator     : Begin new global transaction applicationId: business,transactionServiceGroup: default_tx_group, transactionName: toOrder(java.lang.String, java.lang.String, java.lang.Integer, java.math.BigDecimal),timeout:60000,xid:192.168.50.15:8091:18416337030565889
seata-seata-server-1  | 18:29:06.835  INFO --- [     batchLoggerPrint_1_1] i.s.c.r.p.server.BatchLogHandler         : SeataMergeMessage xid=192.168.50.15:8091:18416337030565889,branchType=AT,resourceId=jdbc:mysql://192.168.50.15:3306/seata_account,lockKey=t_account:1
seata-seata-server-1  | ,clientIp:192.168.50.2,vgroup:default_tx_group
seata-seata-server-1  | 18:29:06.857  INFO --- [rverHandlerThread_1_6_500] i.seata.server.coordinator.AbstractCore  : Register branch successfully, xid = 192.168.50.15:8091:18416337030565889, branchId = 18416337030565890, resourceId = jdbc:mysql://192.168.50.15:3306/seata_account ,lockKeys = t_account:1
seata-seata-server-1  | 18:29:07.196  INFO --- [     batchLoggerPrint_1_1] i.s.c.r.p.server.BatchLogHandler         : xid=192.168.50.15:8091:18416337030565889,extraData=null,clientIp:192.168.50.2,vgroup:default_tx_group
seata-seata-server-1  | 18:29:07.240  INFO --- [rverHandlerThread_1_7_500] io.seata.server.coordinator.DefaultCore  : Rollback branch transaction successfully, xid = 192.168.50.15:8091:18416337030565889 branchId = 18416337030565890
seata-seata-server-1  | 18:29:07.241  INFO --- [rverHandlerThread_1_7_500] io.seata.server.coordinator.DefaultCore  : Rollback global transaction successfully, xid = 192.168.50.15:8091:18416337030565889.
```
请求后 DB 数据如下

`t_account` 表数据

| id | user_id | account |
|----|---------|---------|
| 1  | 1       | 4000    |

`t_account` 表数据

| id | commodity_code | name | count |
|----|----------------|------|-------|
| 1  | C202306121800  | 水杯   | 1000  |

`t_order` 表无数据生成

通过服务日志、seata-server日志、db 可以发现，seata提示事务全局回滚成功， db 数据未修改，到此分布式事务完成。  
在使用seata后 如果要使用分布式事务，只需要在入口方法中加上`@GlobalTransactional`注解即可。
> 如果要结合本地事务一起使用的话，本地的事务只能被`@GlobalTransactional`注解内的方法包含， 否则会导致分布式事务失败！  
> 下面是正确用法
```java
public class test {
    
    @Resource
    private FeignA feignA;
    @Resource
    private FeignB feignB;
    
    @GlobalTransactional
    public void startGlobalTX(){
        feignA.doSomething();
        txLocal();
        feignB.doSomething();
    }
    
    @Transactional
    public void txLocal(){
       System.out.println('local tx...');
    }
}
```
> 下面是错误用法
```java
public class test {
    
    @Resource
    private FeignA feignA;
    @Resource
    private FeignB feignB;
    
    
    @Transactional
    public void txLocal(){
       startGlobalTX();
       System.out.println('local tx...');
    }
    
    @GlobalTransactional
    public void startGlobalTX(){
        feignA.doSomething();
        feignB.doSomething();
    }
}
```
[代码在github](https://github.com/blankhang/seata-nacos-cloud)

参考来源

[Seata安装与使用 https://www.cnblogs.com/wt20/p/17158267.html](https://www.cnblogs.com/wt20/p/17158267.html)  
[docker-compose 部署 Seata Server](https://seata.io/zh-cn/docs/ops/deploy-by-docker-compose.html)  
[官方文档](https://seata.io/zh-cn/docs/overview/faq.html)
