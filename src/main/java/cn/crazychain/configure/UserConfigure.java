/**
 * 
 */
package cn.crazychain.configure;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author yang
 *
 */
@Configuration
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
	@Bean(name = "userDataSource")
	public DataSource userDataSource() {
		// return DataSourceBuilder.create().build();
		// .type(HikariDataSource.class)
		//return devDataSourceProperties().initializeDataSourceBuilder().build();
		MysqlXADataSource mdatasource = new MysqlXADataSource();
		mdatasource.setUrl(devDataSourceProperties().getUrl());
		mdatasource.setUser(devDataSourceProperties().getUsername());
		mdatasource.setPassword(devDataSourceProperties().getPassword());
		/*
		JdbcDataSource h2XaDataSource = new JdbcDataSource();
		h2XaDataSource.setURL(devDataSourceProperties().getUrl());
*/
		//
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setMaxPoolSize(30);	
		xaDataSource.setXaDataSource(mdatasource);
		xaDataSource.setUniqueResourceName("axds2");
		
		/*PoolingDataSourceBean xaDataSource=new PoolingDataSourceBean();
				xaDataSource.setDataSource(mdatasource);
				xaDataSource.setMaxPoolSize(30);
				
				xaDataSource.setUniqueName("axds2");*/
				
	    return xaDataSource;
	}
	

	 @Bean(name = "userjdbcTemplate")
    public JdbcTemplate userjdbcTemplate() {
		 
		 return new JdbcTemplate(userDataSource());
	 }

    
}

