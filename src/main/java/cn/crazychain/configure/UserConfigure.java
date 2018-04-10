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
import org.springframework.boot.jta.bitronix.PoolingDataSourceBean;
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



/**
 * @author yang
 *
 */
@Configuration

@EnableJpaRepositories(entityManagerFactoryRef = "userEntityManagerFactory",
    transactionManagerRef = "chainedTransactionManager", basePackages = {"cn.crazychain.repository"})
public class UserConfigure {
	
	    //cn.crazychain.article.repository
	  @Bean(name = "userDataSourceProperties")
	  @Primary
	  @ConfigurationProperties(prefix = "user.datasource")
	    public DataSourceProperties devDataSourceProperties() {
	        return new DataSourceProperties();
	    }
	
	@Primary

	@Bean(name = "userDataSource")
	public DataSource userDataSource() {
		// return DataSourceBuilder.create().build();
		// .type(HikariDataSource.class)
		return devDataSourceProperties().initializeDataSourceBuilder().build();
	
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
	public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(EntityManagerFactoryBuilder builder ) {
		
		LocalContainerEntityManagerFactoryBean entityManager= builder.dataSource(userDataSource()).packages("cn.crazychain.domain").persistenceUnit("userPersistenceUnit").build();
		entityManager.setJpaVendorAdapter(jpaVendorAdapter());

		return entityManager;
		
	}



}

