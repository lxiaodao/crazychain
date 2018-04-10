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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author yang
 *
 */
@Configuration

@EnableJpaRepositories(entityManagerFactoryRef = "articleEntityManagerFactory",
    transactionManagerRef = "chainedTransactionManager", basePackages = {"cn.crazychain.article.repository"})

public class ArticleConfigure {
    

	@ConfigurationProperties("second.datasource")
	@Bean(name="articleDataSourceProperties")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

	@Bean(name = "articleDataSource")
	public DataSource articleDataSource() {
	
		return secondDataSourceProperties().initializeDataSourceBuilder().build();
		
	}
	
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		return hibernateJpaVendorAdapter;
	}

	@Bean(name = "articleEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean articleEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		LocalContainerEntityManagerFactoryBean entityManager=builder.dataSource(articleDataSource()).packages("cn.crazychain.article.domain").persistenceUnit("articlePersistenceUnit").build();
		
		entityManager.setJpaVendorAdapter(jpaVendorAdapter());

		return entityManager;
	}



}



