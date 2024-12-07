package web.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@EnableJpaRepositories("web.dao")
@ComponentScan("web")
public class AppConfig {

   @Autowired
   private Environment env;

   @Bean
   public DataSource getDataSource() {
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(env.getProperty("db.driver"));
      dataSource.setUrl(env.getProperty("db.url"));
      dataSource.setUsername(env.getProperty("db.username"));
      dataSource.setPassword(env.getProperty("db.password"));

      dataSource.setInitialSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.initialSize"))));
      dataSource.setMinIdle(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.minIdle"))));
      dataSource.setMaxIdle(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.maxIdle"))));
      dataSource.setMaxTotal(Integer.parseInt(Objects.requireNonNull(env.getProperty("db.maxTotal"))));
      dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(Objects.requireNonNull(env.getProperty("db.minEvictableTimeMillis"))));
      dataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("db.timeBetweenEvictionRunsMillis")));
      dataSource.setValidationQuery(env.getProperty("db.validationQuery"));
      return dataSource;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
      LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
      factoryBean.setDataSource(getDataSource());
      factoryBean.setPackagesToScan("db.jdbc.packageToScan");
      factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      factoryBean.setJpaProperties(getHibernateProperties());
      factoryBean.setPackagesToScan(env.getProperty("db.jdbc.packagesToScan"));

      return factoryBean;
   }
   @Bean
   public PlatformTransactionManager getTransactionManager() {
      JpaTransactionManager jtm = new JpaTransactionManager();
      jtm.setEntityManagerFactory(getEntityManagerFactory().getObject());

      return jtm;
   }

   @Bean
   public Properties getHibernateProperties() {
      Properties props = new Properties();
      props.getProperty("hibernate.dialect");
      props.getProperty("hibernate.show_sql");
      props.getProperty("hibernate.hbm2ddl.auto");
      return props;
   }
}