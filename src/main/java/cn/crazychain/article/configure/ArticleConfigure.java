/**
 * 
 */
package cn.crazychain.article.configure;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * @author yang
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "articleEntityManagerFactory",
    transactionManagerRef = "customerJtaTransactionManager", basePackages = {"cn.crazychain.article.repository"})
public class ArticleConfigure {
    
	    @Bean(name = "articleDataSourceProperties")
	   
	    @ConfigurationProperties("second.datasource")
	    public DataSourceProperties sourceProperties() {
	        return new DataSourceProperties();
	    }

	@Bean(name = "articleDataSource")
	@ConfigurationProperties(prefix = "second.datasource")
	public DataSource dataSource() {
		// return DataSourceBuilder.create().build();
		// .type(HikariDataSource.class)
		// XADataSource
		MysqlXADataSource mdatasource = new MysqlXADataSource();
		mdatasource.setUrl(sourceProperties().getUrl());
		mdatasource.setUser(sourceProperties().getUsername());
		mdatasource.setPassword(sourceProperties().getPassword());

		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mdatasource);
		xaDataSource.setUniqueResourceName("articleDataSource");
        
		//2018-04-06 分布式事务
		// return sourceProperties().initializeDataSourceBuilder().build();
		return xaDataSource;
	}

	@Bean(name = "articleEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean articleEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("articleDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("cn.crazychain.article.domain").persistenceUnit("article").build();
	}

	/*@Bean(name = "articleTransactionManager")
	public PlatformTransactionManager articleTransactionManager(
			@Qualifier("articleEntityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
		return new JpaTransactionManager(barEntityManagerFactory);
	}
*/

}
