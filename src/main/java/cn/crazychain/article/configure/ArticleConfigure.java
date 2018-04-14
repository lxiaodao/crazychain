/**
 * 
 */
package cn.crazychain.article.configure;

import java.util.HashMap;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.boot.jta.bitronix.PoolingDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import cn.crazychain.transaction.CustomerAtomikosJtaPlatform;

/**
 * @author yang
 *
 */
@Configuration
//@DependsOn("customerJtaTransactionManager")
@EnableJpaRepositories(entityManagerFactoryRef = "articleEntityManagerFactory",
    transactionManagerRef = "customerJtaTransactionManager", basePackages = {"cn.crazychain.article.repository"})

public class ArticleConfigure {
    

	@ConfigurationProperties("second.datasource")
	@Bean(name="articleDataSourceProperties")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }
	

	//@Bean(name = "articleDataSource")
	@Bean(name = "articleDataSource")
	public DataSource articleDataSource() {
	
		MysqlXADataSource mdatasource = new MysqlXADataSource();
		mdatasource.setUrl(secondDataSourceProperties().getUrl());
		mdatasource.setUser(secondDataSourceProperties().getUsername());
		mdatasource.setPassword(secondDataSourceProperties().getPassword());
		
		/*JdbcDataSource h2XaDataSource = new JdbcDataSource();
		h2XaDataSource.setURL(secondDataSourceProperties().getUrl());*/
	    
        //atomikos datasource configure
		com.atomikos.jdbc.AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
	    xaDataSource.setXaDataSource(mdatasource);
	    xaDataSource.setMinPoolSize(5);
		xaDataSource.setMaxPoolSize(30);
		xaDataSource.setUniqueResourceName("axds1");
	
		
		
		//bitronix datasourcepool configure
		/*PoolingDataSourceBean xaDataSource=new PoolingDataSourceBean();
		
		xaDataSource.setDataSource(mdatasource);
		xaDataSource.setMaxPoolSize(30);
		xaDataSource.setUniqueName("axds1");*/
        		

        
		//2018-04-06 分布式事务
		// return sourceProperties().initializeDataSourceBuilder().build();
		return xaDataSource;
	}
	
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		return hibernateJpaVendorAdapter;
	}

	@Bean(name = "articleEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean articleEntityManagerFactory() {
		//return builder.dataSource(dataSource).packages("cn.crazychain.article.domain").persistenceUnit("article").build();
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", CustomerAtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(articleDataSource());
		entityManager.setJpaVendorAdapter(jpaVendorAdapter());
		entityManager.setPackagesToScan("cn.crazychain.article.domain");
		entityManager.setPersistenceUnitName("articlePersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}
	
	
	

	/*@Bean(name = "articleTransactionManager")
	public PlatformTransactionManager articleTransactionManager(
			@Qualifier("articleEntityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
		return new JpaTransactionManager(barEntityManagerFactory);
	}
*/

}



