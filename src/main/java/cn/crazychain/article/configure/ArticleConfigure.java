/**
 * 
 */
package cn.crazychain.article.configure;

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
@EnableJpaRepositories(entityManagerFactoryRef = "articleEntityManagerFactory",
    transactionManagerRef = "articleTransactionManager", basePackages = {"cn.crazychain.article.repository"})
public class ArticleConfigure {
    
	    @Bean(name = "articleDataSourceProperties")
	   
	    @ConfigurationProperties("second.datasource")
	    public DataSourceProperties sourceProperties() {
	        return new DataSourceProperties();
	    }

	@Bean(name = "articleDataSource")
	@ConfigurationProperties(prefix = "second.datasource")
	public DataSource dataSource() {
		//return DataSourceBuilder.create().build();
		//.type(HikariDataSource.class)
		
		return sourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean(name = "articleEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean articleEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("articleDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("cn.crazychain.article.domain").persistenceUnit("article").build();
	}

	@Bean(name = "articleTransactionManager")
	public PlatformTransactionManager articleTransactionManager(
			@Qualifier("articleEntityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
		return new JpaTransactionManager(barEntityManagerFactory);
	}


}
