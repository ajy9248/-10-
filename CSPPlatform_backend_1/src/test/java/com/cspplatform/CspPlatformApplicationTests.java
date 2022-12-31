package com.cspplatform;

import com.cspplatform.entity.Login;
import com.cspplatform.service.IUserService;
import com.zaxxer.hikari.pool.HikariProxyConnection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class CspPlatformApplicationTests {

	@Autowired //自动装配
	private DataSource dataSource;

	@Test
	void contextLoads() {
	}

	@Test
	/**
	 * 数据库连接池
	 * 1.DBCP
	 * 2.C3P0
	 * 3.Hikari：管理数据库的连接对象
	 * HikariProxyConnection@428468422 wrapping com.mysql.cj.jdbc.ConnectionImpl@35c4697c
	 */
	void getConnection() throws SQLException {
		System.out.println(dataSource.getConnection());
	}


}
