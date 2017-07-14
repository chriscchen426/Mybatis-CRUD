package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import domain.User;

public class MybatisFirst {
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void init() throws IOException {
		String resource = "SqlMapConfig.xml";
		
		InputStream inputStream = Resources.getResourceAsStream(resource);
		
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	@Test
	public void testFindUserById() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		User user = null;
		
		try {
			user = sqlSession.selectOne("test.findUserById", 1);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		System.out.println(user);
	}
	
	@Test
	public void testFindUserByName() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		List<User> list = null;
		
		try {
			list = sqlSession.selectList("test.findUserByName", "updateName");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		System.out.println(list.get(0).getUsername());
	}
	
	@Test
	public void testInsertUser() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		User user = new User();
		user.setUsername("insertName");
		user.setBirthday(new Date());
		user.setAddress("insertAddress");
		user.setSex("male");
		try {
			sqlSession.insert("test.insertUser", user);
			sqlSession.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
	}
	
//	@Test
//	public void testDeleteUser() {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
//		
//		try {
//			sqlSession.delete("test.deleteUser", 16);
//			sqlSession.commit();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}finally {
//			sqlSession.close();
//		}
//	}
	
	@Test
	public void testUpdateUser() {
SqlSession sqlSession = sqlSessionFactory.openSession();
		
		User user = new User();
		user.setId(1);
		user.setUsername("updateName");
		
		try {
			sqlSession.update("test.updateUser", user);
			sqlSession.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		
		System.out.println("User id = " + user.getUsername());
	}
}
