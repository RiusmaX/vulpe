package org.vulpe.model.jdbc;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JdbcTest extends VulpeBaseJdbcEntity {

	private Long id;

	private String name;
	
	public static void main(String[] args) {
//		JdbcTest jdbcTest = new JdbcTest(101L, "Testx");
//		jdbcTest.merge();
		JdbcTest jdbcTest = new JdbcTest();
//		jdbcTest.setId(100L);
//		jdbcTest.setName("Test");
//		jdbcTest.configure("id", "<>").configure("name", "likeEnd");
		List<JdbcTest> list = jdbcTest.all();
		for (JdbcTest jdbcTest2 : list) {
			System.out.println(jdbcTest2);
		}
	}

}