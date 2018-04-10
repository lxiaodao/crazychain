/**
 * 
 */
package cn.crazychain.service;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import cn.crazychain.article.repository.ArticleRepository;
import cn.crazychain.repository.UserRepository;
import cn.crazychain.service.BusinessService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yang
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j(topic = "testone")
public class BusinessServiceTest {
	
	@Autowired
	 private UserRepository userRepo;
	 
	 @Autowired
	 private ArticleRepository articleRepo;
	 
	 @Autowired
	 private BusinessService businessService;
	 
	 
	 private boolean isMockFail=false;
	
	 @Before
	 public void before(){
		 userRepo.deleteAll();
		 articleRepo.deleteAll();
	 }
	 
	 @After
	 public void after(){
		 int result=isMockFail?0:1;
		 assertEquals(result, userRepo.count());
		 assertEquals(result, articleRepo.count());
		 log.debug("---this is test's after------"+result);
	 }
	 
	
	
	 @Rollback(false)
	 @Test(expected=RuntimeException.class)
	 public void test_createArticle_txfail() {
		 isMockFail=true;
		 log.debug("------BusinessServiceTest------test_createArticle_txfail");
		 businessService.createArticle("This is business.", isMockFail);
		 log.debug("------BusinessServiceTest------test_createArticle_txfail end");
		 
	 }
	
	

}
