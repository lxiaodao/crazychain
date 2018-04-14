package cn.crazychain;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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
		 //userRepo.deleteAll();
		 //articleRepo.deleteAll();
		 userRepo.delete("delete from user");
		 articleRepo.delete("delete from article");
	 }
	 
	 @After
	 public void after(){
		 
		 int number=isMockFail?0:1;
		 log.debug("---this is test's after------"+number+":"+isMockFail);
		 assertEquals(number, userRepo.queryCount("select count(*) from user", null));
		 assertEquals(number, articleRepo.queryCount("select count(*) from article", null));
		 
	 }
	 

	
	@Test
	@Transactional
	@Rollback(false)
	public void test_insertTwo() {
		User user=new User();
		user.setName("wangx2"+System.currentTimeMillis());
		String sql="INSERT into user (name,email) values (?,?)";
		userRepo.insert(sql, new Object[] {user.getName(),user.getEmail()});
		//
		Article entity=new Article();
		entity.setTitle("this is a article about blockchain.");
		String sql2="INSERT into article (title,content) values (?,?)";
		articleRepo.insert(sql2, new Object[] {entity.getTitle(),null});
		//
		
	}
	
	@Test
	@Rollback(false)
	public void test_simple() {
		
		this.isMockFail=true;
		
	}
	
}
