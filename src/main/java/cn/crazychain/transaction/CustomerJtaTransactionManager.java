/**
 * 
 */
package cn.crazychain.transaction;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;

import bitronix.tm.BitronixTransactionManager;

/**
 * 
 * <bean id="transactionManager"
		class="org.springframework.transaction.jta.JtaTransactionManager">
		<property name="transactionManager">
			<bean class="com.atomikos.icatch.jta.UserTransactionManager"
				init-method="init" destroy-method="close">
				<property name="forceShutdown">
					<value>true</value>
				</property>
				<property name="transactionTimeout">
					<value>600</value>
				</property>
			</bean>
		</property>
		<property name="userTransaction">
			<bean class="com.atomikos.icatch.jta.UserTransactionImp" />
		</property>
	</bean>
 * @author yang
 *
 */
@EnableTransactionManagement
@ComponentScan(basePackages="cn.crazychain")
@Component(value = "customerJtaTransactionManager")
public class CustomerJtaTransactionManager extends JtaTransactionManager {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7979368511168270202L;


	//@Qualifier("userTransaction") UserTransaction userTransaction, 
	//@Qualifier("atomikosTransactionManager") TransactionManager transactionManager


    @Autowired
	public CustomerJtaTransactionManager(@Qualifier("userTransaction")UserTransaction userTransaction,
			@Qualifier("atomikosTransactionManager")TransactionManager transactionManager) {
		super(userTransaction,transactionManager);
		
		CustomerAtomikosJtaPlatform.transaction=userTransaction;
		CustomerAtomikosJtaPlatform.transactionManager=transactionManager;
	
	}


}
@Component(value = "userTransaction")
class CusUserTransaction extends BitronixTransactionManager{
	
	
}
//BitronixTransactionManager UserTransactionManager
@Component(value = "atomikosTransactionManager")
class CusTransactionManager extends BitronixTransactionManager{
	
}



