package cn.crazychain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.crazychain.article.domain.Article;
import cn.crazychain.article.repository.ArticleRepository;
import cn.crazychain.domain.User;
import cn.crazychain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j(topic = "testone")
public class CrazychainApplicationTests {

	
	 @Autowired
	 private UserRepository userRepo;
	 
	 @Autowired
	 private ArticleRepository articleRepo;
	 
	 
	 private boolean isMockFail=false;
	 
	 
	 @Before
	 public void before(){
		 userRepo.deleteAll();
		 articleRepo.deleteAll();
	 }
	 
	 @After
	 public void after(){
		 
		 int number=isMockFail?0:1;
		 log.debug("---this is test's after------"+number+":"+isMockFail);
		 assertEquals(number, userRepo.count());
		 assertEquals(number, articleRepo.count());
		 
	 }
	 

	
	@Test
	@Transactional
	public void test_insertTwo() {
		User user=new User();
		user.setName("wangx2"+System.currentTimeMillis());
		userRepo.save(user);
		//
		Article entity=new Article();
		entity.setTitle("this is a article about blockchain.");
		articleRepo.save(entity);
		//
		
	}
	
	@Test
	@Transactional
	public void test_simple() {
		
		this.isMockFail=true;
		
	}
	@Test(expected=RuntimeException.class)
	@Transactional(rollbackFor = Exception.class)
	public void test_distributed_transaction(){
		this.isMockFail=true;
		log.debug("---test_distributed_transaction------"+":"+isMockFail);
		mockService();
	}

	@Transactional(rollbackFor = Exception.class)
	private void mockService() {
		
		User user=new User();
		user.setName("wangx"+System.currentTimeMillis());
		userRepo.save(user);
		//
		
		Article entity=new Article();
		entity.setTitle("this is a article about blockchain.");
		
		articleRepo.save(entity);
		throw new RuntimeException("This is mock exception.");
	}

}
