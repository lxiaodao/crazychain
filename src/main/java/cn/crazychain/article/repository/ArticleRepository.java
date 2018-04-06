/**
 * 
 */
package cn.crazychain.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.crazychain.article.domain.Article;

/**
 * @author yang
 *
 */
//@Repository
//@Transactional(value = "customerJtaTransactionManager")
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
