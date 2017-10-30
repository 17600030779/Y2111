package cn.easybuy.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/** 
 * @ClassName: MybatisUtil 
 * @Description: TODO
 * @author: Adminstrators
 * @date: 2017年8月30日 下午2:47:30  
 */
public class MybatisUtil {
	
	private static SqlSessionFactory factory=null;
	
	static{
		try {
			
			InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
			
			factory =new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SqlSession openSqlSession(){
		return factory.openSession(true); //自动提交
	}
	
	public static void closeSqlSession(SqlSession session){
		if(session!=null){
			session.close();
		}
	}
	
}
