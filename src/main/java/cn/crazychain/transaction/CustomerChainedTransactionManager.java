package cn.crazychain.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @author Dave Syer
 *
 */
@EnableTransactionManagement
@ComponentScan(basePackages="cn.crazychain")
@Component(value = "chainedTransactionManager")
public class CustomerChainedTransactionManager extends ChainedTransactionManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4566530482276876570L;
	
	
	
	
	/* @Autowired
	public ChainedTransactionManager(@Qualifier("articleTransactionManager") PlatformTransactionManager manager1,
				@Qualifier("userTransactionManager") PlatformTransactionManager transactionManager) {
		 transactionManagers.add(manager1);
		 transactionManagers.add(transactionManager);
		 Collections.reverse(transactionManagers);
		
		}
	*/
	 @Autowired
	public CustomerChainedTransactionManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory,
				@Qualifier("articleEntityManagerFactory") EntityManagerFactory articleEntityManagerFactory) {
		 super(new PlatformTransactionManager[] {new JpaTransactionManager(entityManagerFactory),new JpaTransactionManager(articleEntityManagerFactory)});
		
			
	}



}
