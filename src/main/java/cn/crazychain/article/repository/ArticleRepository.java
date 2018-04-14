/**
 * Copyright (c) 2011-2013 iTel Technology Inc,All Rights Reserved.
 */

package cn.crazychain.article.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;


/**
 * 关系数据库访问实现
 * 
 * @ClassName: MySQLAccessService
 * @Description:
 * @author Yang
 * @date 2013年10月28日 下午4:53:10
 *       
 */
@Repository("articleRepository")
public class ArticleRepository 
{
    
    @Autowired
    @Qualifier("twojdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate()
    {
        return jdbcTemplate;
    }
 
 
    public int insert(String sql, Object... params)
    {
        return jdbcTemplate.update(sql, params);
    }
    
  
    public Number insertAndReturnKey(final String sql, final Object... params)
    {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator()
        {
            
            public PreparedStatement createPreparedStatement(Connection con)
                throws SQLException
            {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < params.length; i++)
                {
                    ps.setObject(i + 1, params[i]);
                }
                return ps;
                
            }
        }, generatedKeyHolder);
        
        return generatedKeyHolder.getKey();
        
    }
    
   
    public int update(String sql, Object... params)
    {
        return jdbcTemplate.update(sql, params);
    }
    
  
    public int delete(String sql, Object... params)
    {
    	return params==null?jdbcTemplate.update(sql):jdbcTemplate.update(sql, params);
    }
    
    
    public <T> List<T> queryByMapper(String sql, Object[] values, RowMapper<T> rowMapper)
    {
        return jdbcTemplate.query(sql, values, rowMapper);
    }
    
   
    public int queryCount(String sql, Object... values)
    {
        return jdbcTemplate.queryForObject(sql, values, Integer.class);
    }
    
   
    public int[] insertOrUpdateAll(String sql, List<Object[]> valuesList)
    {
        return jdbcTemplate.batchUpdate(sql, valuesList);
    }
    
   
    public <T> T queryOne(String sql, Object[] values, RowMapper<T> rowMapper)
    {
        return jdbcTemplate.queryForObject(sql, values, rowMapper);
    }
    
}
