package net.sinyoo.cooperation.web.backstage;

import net.sinyoo.cooperation.web.backstage.cache.AccessTokenCache;
import net.sinyoo.cooperation.web.backstage.filter.SessionFilter;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 后台系统
 * yunpengsha
 */
@SpringBootApplication(scanBasePackages="net.sinyoo.cooperation.web.backstage",exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ImportResource(locations = {"dubbo-consumer.xml"})
//@PropertySource(value = {"file:/alidata1/kyb/kyb-cooperation-web-backstage/config/application.properties"})
public class BackstageWebApplication {

    private static Logger logger = Logger.getLogger(BackstageWebApplication.class);

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        SessionFilter sessionFilter = new SessionFilter();
        registrationBean.setFilter(sessionFilter);
        return registrationBean;
    }
    /**
     * Main Start
     */
    public static void main(String[] args) {
        SpringApplication.run(BackstageWebApplication.class, args);
        AccessTokenCache.addCache("623751D996C14763B8D6C9158442C07B",1);
        System.out.println("============= SpringBoot web Start Success =============");
    }
}
