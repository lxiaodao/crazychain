/**
 * 
 */
package cn.crazychain.configure;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/**
 * @author yang
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "userEntityManagerFactory",
    transactionManagerRef = "userTransactionManager", basePackages = {"cn.crazychain.repository"})
public class ChaindbConfigure {
	
	    //cn.crazychain.article.repository
	  @Bean(name = "userDataSourceProperties")
	    @Primary
	    @ConfigurationProperties("spring.datasource")
	    public DataSourceProperties devDataSourceProperties() {
	        return new DataSourceProperties();
	    }
	@Bean(name = "userDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		// return DataSourceBuilder.create().build();
		// .type(HikariDataSource.class)
		return devDataSourceProperties().initializeDataSourceBuilder().build();
	}
	

	@Bean(name = "userEntityManagerFactory")
	@Primary
	public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("userDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("cn.crazychain.domain").persistenceUnit("user").build();
	}

	@Bean(name = "userTransactionManager")
	@Primary
	public PlatformTransactionManager userTransactionManager(
			@Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
