package zkxy;

import com.baomidou.mybatisplus.mapper.ISqlInjector;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@MapperScan("zkxy.repository")
@EnableTransactionManagement
@Configuration
public class ZkxyApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//         注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(ZkxyApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ZkxyApplication.class, args);
    }


    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();

    }

    /**
     * 注入sql注入器
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
//
//    @Bean("mybatisSqlSession")
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ResourceLoader resourceLoader, GlobalConfiguration globalConfiguration) throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
//        MybatisConfiguration configuration = new MybatisConfiguration();
////        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
////        configuration.setJdbcTypeForNull(JdbcType.NULL);
//        //*注册Map 下划线转驼峰*
//        configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
//
//        sqlSessionFactory.setConfiguration(configuration);
//        //...其他配置
//        return sqlSessionFactory.getObject();
//    }
}
