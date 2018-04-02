/**
 * 
 */
package cn.crazychain.article.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author yang
 *
 */
@Entity
@Table(name="article")
public class Article {
	
	    @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Integer id;

	    private String title;

	    private String content;
	    
	    private Date createTime;
	    
	    public void setTitle(String _title){
	    	
	    	this.title=_title;
	    }
	    
	    public String getTitle(){
	    	return this.title;
	    }
	    
	    
	  


}
