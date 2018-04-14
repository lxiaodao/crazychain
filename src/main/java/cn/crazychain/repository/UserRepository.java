package cn.crazychain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.crazychain.domain.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository("userRepository")
public class UserRepository  {
    @Autowired
    @Qualifier("userjdbcTemplate")
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
