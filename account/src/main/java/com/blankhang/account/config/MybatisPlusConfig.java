package com.blankhang.account.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置类
 *
 * @author blank
 * @since 1.0.0
 */
@Slf4j
@Configuration
@MapperScan("com.blankhang.account.mapper")
public class MybatisPlusConfig {

    /**
     * <a href="https://baomidou.com/pages/97710a/">分页插件</a>
     * <p>
     * 支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     * 整数类型下 newVersion = oldVersion + 1
     * newVersion 会回写到 entity 中
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     * 在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
     *  <a href="https://baomidou.com/guide/interceptor-optimistic-locker.html">乐观锁插件</a>
     * <p>
     *
     * <a href="https://baomidou.com/guide/interceptor-block-attack.html">防全表更新与删除插件</a>
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 乐观锁插件
        // 需要在实体类的字段上加上@Version注解后  使用 updateById(id) 与 update(entity, wrapper) 方法生效
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防全表更新与删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 分页插件 需要放在插件执行链最后
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        log.info("MybatisPlus 乐观锁 防全表更新与删除插件 分页拦截器");
        return interceptor;
    }


    /**
     * 分页插件，自动识别数据库类型 <a href="https://baomidou.com/guide/interceptor-pagination.html">...</a>
     */
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置数据库类型为mysql
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(-1L);
        return paginationInnerInterceptor;
    }


    /**
     * 注入sql注入器
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }


}
