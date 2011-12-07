package org.vulpe.model.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.vulpe.commons.util.VulpeReflectUtil;

@SuppressWarnings( { "unchecked" })
public class VulpeBaseJdbcEntity {

	protected static final Logger LOG = Logger.getLogger(VulpeBaseJdbcEntity.class);

	private static VulpeNamedParameterJdbcTemplate template;

	private Map<String, String> configuration = new HashMap<String, String>();

	public VulpeBaseJdbcEntity() {
		init();
	}

	private void init() {
		final ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		final DriverManagerDataSource dataSource = (DriverManagerDataSource) context
				.getBean("dataSource");
		template = new VulpeNamedParameterJdbcTemplate(dataSource);
	}

	protected <T> T all() {
		final BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(
				this);
		final StringBuilder query = new StringBuilder("select * from ");
		query.append(this.getClass().getSimpleName().toLowerCase());
		if (namedParameters != null) {
			int count = 0;
			StringBuilder filter = new StringBuilder();
			for (String propertyName : namedParameters.getReadablePropertyNames()) {
				if ("class".equals(propertyName) || namedParameters.getValue(propertyName) == null) {
					continue;
				}
				if (count > 0) {
					filter.append(" and ");
				}
				String operator = "=";
				if (configuration.containsKey(propertyName)) {
					operator = configuration.get(propertyName);
				}
				if ("like".equals(operator) || "likeStart".equals(operator)
						|| "likeEnd".equals(operator)) {
					final Object object = VulpeReflectUtil.getFieldValue(VulpeReflectUtil
							.getFieldValue(namedParameters, "beanWrapper"), "object");
					Object value = namedParameters.getValue(propertyName);
					if ("like".equals(operator)) {
						value = "%" + value + "%";
					} else if ("likeStart".equals(operator)) {
						value = value + "%";
					} else if ("likeEnd".equals(operator)) {
						value = "%" + value;
					}
					operator = "like";
					VulpeReflectUtil.setFieldValue(object, propertyName, value);
				}
				filter.append(propertyName).append(" ").append(operator).append(" ");
				filter.append(":").append(propertyName);
				// System.out.println(propertyName);
				++count;
			}
			if (count > 0) {
				query.append(" where ").append(filter.toString());
			}
		}
		LOG.debug(query.toString());
		final List<Map<String, Object>> result = template.queryForList(query.toString(),
				namedParameters);
		final List<Object> list = new ArrayList<Object>();
		for (final Map<String, Object> map : result) {
			LOG.debug("Object: " + map);
			try {
				final Object object = this.getClass().newInstance();
				for (final String propertyName : namedParameters.getReadablePropertyNames()) {
					if ("class".equals(propertyName)) {
						continue;
					}
					if (map.containsKey(propertyName)) {
						VulpeReflectUtil.setFieldValue(object, propertyName, map.get(propertyName));
					}
				}
				list.add(object);
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		return (T) list;
	}

	protected void merge() {
		final StringBuilder query = new StringBuilder("insert into ");
		query.append(this.getClass().getSimpleName().toLowerCase());
		final BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(
				this);
		if (namedParameters != null) {
			int count = 0;
			StringBuilder columns = new StringBuilder();
			StringBuilder values = new StringBuilder();
			for (final String propertyName : namedParameters.getReadablePropertyNames()) {
				if ("class".equals(propertyName)) {
					continue;
				}
				if (count > 0) {
					columns.append(", ");
					values.append(", ");
				}
				columns.append(propertyName);
				values.append(":").append(propertyName);
				++count;
			}
			query.append(" (").append(columns.toString()).append(") values (").append(
					values.toString()).append(")");
		}
		// String query = "update jdbctest set name = :name where id = :id";
		template.update(query.toString(), namedParameters);
	}

	protected void delete() {
		final StringBuilder query = new StringBuilder("delete from ");
		query.append(this.getClass().getSimpleName().toLowerCase());
		query.append(" where id = :id");
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(this);
		template.update(query.toString(), namedParameters);
	}

	public VulpeBaseJdbcEntity configure(String propertyName, String value) {
		configuration.put(propertyName, value);
		return this;
	}
}
