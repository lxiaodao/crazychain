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
		//"INSERT into user (name,email) values (?,?)", 0, "foo"
		String sql="INSERT into user (name,email) values (?,?)";
		userRepo.insert(sql, new Object[] {user.getName(),user.getEmail()});
		//
		Article entity=new Article();
		entity.setTitle("this is a article about blockchain.");
		String sql2="INSERT into article (title,content) values (?,?)";
		articleRepo.insert(sql2, new Object[] {entity.getTitle(),null});
		
		if(isfail) {
			mockThrowException();
		}
		
	}
	@Transactional(rollbackFor = Exception.class)
	public void createAll() {
		
		User user=new User();
		user.setName("wangx"+System.currentTimeMillis());
		//"INSERT into user (name,email) values (?,?)", 0, "foo"
		String sql="INSERT into user (name,email) values (?,?)";
		userRepo.insert(sql, new Object[] {user.getName(),user.getEmail()});
		//
		Article entity=new Article();
		entity.setTitle("this is a article about blockchain.");
		String sql2="INSERT into article (title,content) values (?,?)";
		articleRepo.insert(sql2, new Object[] {entity.getTitle(),null});
	
		
	}
	
	private void mockThrowException() {
		throw new RuntimeException("This is mock exception.");
	}

}
