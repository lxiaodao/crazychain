package cn.crazychain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

	
	@Test
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

}
