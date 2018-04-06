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

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrazychainApplicationTests {

	@Test
	public void contextLoads() {
		
		
	}
	 @Autowired
	 private UserRepository userRepo;
	 
	 @Autowired
	 private ArticleRepository articleRepo;
	 
	 
	 @Before
	 public void before(){
		 userRepo.deleteAll();
		 articleRepo.deleteAll();
	 }
	 
	 @After
	 public void after(){
		 assertEquals(1, userRepo.count());
		
		 
		 assertEquals(1, articleRepo.count());
	 }
	 

	
	@Test
	@Transactional
	public void test_insertTwo() {
		User user=new User();
		user.setName("wangx2");
		userRepo.save(user);
		//
		Article entity=new Article();
		entity.setTitle("this is a article about blockchain.");
		articleRepo.save(entity);
		//
		
	}
	
	@Test
	public void test_distributed_transaction(){
		User user=new User();
		user.setName("wangx2");
		userRepo.save(user);
		//
		
		Article entity=new Article();
		entity.setTitle("this is a article about blockchain.");
		articleRepo.save(entity);
	}

}
