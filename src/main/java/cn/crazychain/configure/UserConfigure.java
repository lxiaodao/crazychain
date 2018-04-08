/**
 * 
 */
package cn.crazychain.configure;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.zaxxer.hikari.HikariDataSource;

import cn.crazychain.transaction.CustomerAtomikosJtaPlatform;

/**
 * @author yang
 *
 */
@Configuration
@DependsOn("customerJtaTransactionManager")
@EnableJpaRepositories(entityManagerFactoryRef = "userEntityManagerFactory",
    transactionManagerRef = "customerJtaTransactionManager", basePackages = {"cn.crazychain.repository"})
public class UserConfigure {
	
	    //cn.crazychain.article.repository
	  @Bean(name = "userDataSourceProperties")
	  @Primary
	  @ConfigurationProperties(prefix = "user.datasource")
	    public DataSourceProperties devDataSourceProperties() {
	        return new DataSourceProperties();
	    }
	//@Bean(name = "userDataSource")
	@Primary
	//@ConfigurationProperties(prefix = "user.datasource")
	@Bean(name = "userDataSource", initMethod = "init", destroyMethod = "close")
	public DataSource userDataSource() {
		// return DataSourceBuilder.create().build();
		// .type(HikariDataSource.class)
		//return devDataSourceProperties().initializeDataSourceBuilder().build();
		/*MysqlXADataSource mdatasource = new MysqlXADataSource();
		mdatasource.setUrl(devDataSourceProperties().getUrl());
		mdatasource.setUser(devDataSourceProperties().getUsername());
		mdatasource.setPassword(devDataSourceProperties().getPassword());*/
		
		JdbcDataSource h2XaDataSource = new JdbcDataSource();
		h2XaDataSource.setURL(devDataSourceProperties().getUrl());

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(h2XaDataSource);
		xaDataSource.setMaxPoolSize(30);
		xaDataSource.setUniqueResourceName("axds2");
		return xaDataSource;
	}
	
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		return hibernateJpaVendorAdapter;
	}
	

	@Bean(name = "userEntityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(@Qualifier("userDataSource") DataSource dataSource ) {
		//return builder.dataSource(dataSource).packages("cn.crazychain.domain").persistenceUnit("user").build();
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", CustomerAtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(dataSource);
		entityManager.setJpaVendorAdapter(jpaVendorAdapter());
		entityManager.setPackagesToScan("cn.crazychain.domain");
		entityManager.setPersistenceUnitName("userPersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
		
	}
/*
	@Bean(name = "userTransactionManager")
	@Primary
	public PlatformTransactionManager userTransactionManager(
			@Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}*/

}
@ConfigurationProperties("user.datasource")
class UserDataSourceProperties extends DataSourceProperties{
	
}
