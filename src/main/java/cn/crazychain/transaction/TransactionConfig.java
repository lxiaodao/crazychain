package cn.crazychain.transaction;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="cn.crazychain")
public class TransactionConfig {



	@Bean(name = "userTransaction")
	public UserTransaction userTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(10000);
		//return new BitronixTransactionManager();
		return userTransactionImp;
	}
    
	
	@Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
	//@Bean(name = "atomikosTransactionManager")
	public TransactionManager atomikosTransactionManager() throws Throwable {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setForceShutdown(false);
         
        //return TransactionManagerServices.getTransactionManager();
		return userTransactionManager;
	}

	@Bean(name = "customerJtaTransactionManager")
	@DependsOn({ "userTransaction", "atomikosTransactionManager" })
	public PlatformTransactionManager transactionManager() throws Throwable {
		UserTransaction userTransaction = userTransaction();
      
		TransactionManager atomikosTransactionManager = atomikosTransactionManager();
     

		
		return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
	}

}
