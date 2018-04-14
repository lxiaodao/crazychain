/**
 * 
 */
package cn.crazychain.article.configure;

import java.util.HashMap;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.jta.bitronix.PoolingDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * @author yang
 *
 */
@Configuration
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
		
		xaDataSource.setMaxPoolSize(30);
		xaDataSource.setUniqueResourceName("axds1");
	
		return xaDataSource;
	}
	
	
	 @Bean(name = "twojdbcTemplate")
     public JdbcTemplate twojdbcTemplate() {
		 
		 return new JdbcTemplate(articleDataSource());
	 }
	

	    

}



