/**
 * 
 */
package cn.crazychain.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crazychain.article.domain.Article;
import cn.crazychain.article.repository.ArticleRepository;
import cn.crazychain.domain.User;
import cn.crazychain.repository.UserRepository;

/**
 * @author yang
 *
 */
@Service("businessService")
public class BusinessService {
	 @Autowired
	 private UserRepository userRepo;
	 
	 @Autowired
	 private ArticleRepository articleRepo;
	
	@Transactional(rollbackFor = Exception.class)
	public void createArticle(String content,boolean isfail) {
		
		User user=new User();
		user.setName("wangx"+System.currentTimeMillis());
		userRepo.save(user);
		//
		Article entity=new Article();
		entity.setTitle("this is a article about blockchain.");
		articleRepo.save(entity);
		if(isfail) {
			mockThrowException();
		}
		
	}
	@Transactional(rollbackFor = Exception.class)
	public void createAll() {
		
		User user=new User();
		user.setName("wangx"+System.currentTimeMillis());
		userRepo.save(user);
		//
		Article entity=new Article();
		entity.setTitle("this is a article about blockchain.");
		articleRepo.save(entity);
		
		
	}
	
	private void mockThrowException() {
		throw new RuntimeException("This is mock exception.");
	}

}
