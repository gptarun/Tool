package com.assessment.assessment.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service // lets you autowire things and not have to instantiate
public class SessionDAO {

	@Value("${connectionString}")
	private String connectionString;
	@Value("${schemaName}")
	private String schema;
	@Value("${spring.security.user.name}")
	private String username;
	@Value("${spring.security.user.password}")
	private String password;
//	private Connection connect = null;
//	private Statement statement = null;
//	private PreparedStatement preparedStatement = null;
//	private ResultSet resultSet = null;

	public Session createSession(Session session, String key, int portfolio) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			long portID = portfolio;
			statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// SQL call
//			preparedStatement = connect.prepareStatement("select id from "+schema+".portfolio where portfolio = ?",ResultSet.TYPE_SCROLL_SENSITIVE, 
//                    ResultSet.CONCUR_UPDATABLE);
//			preparedStatement.setString(1, portfolio);
//			resultSet = preparedStatement.executeQuery();
//			if(resultSet.first()) {
//				portID = resultSet.getLong(1);
//			}

			// SQL call
			preparedStatement = connect.prepareStatement("insert into " + schema
					+ ".session (team_id, email, createdOn, urlKey, IT_portfolio, team_name) values (?, ?, ?, ?, ?, ?)",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setInt(1, 1);
			preparedStatement.setString(2, session.getEmail());
			preparedStatement.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.setString(4, key);
			preparedStatement.setLong(5, portID);
			preparedStatement.setString(6, session.getSessionID());
			preparedStatement.executeUpdate();

			// SQL call
			preparedStatement = connect.prepareStatement("SELECT id from " + schema + ".session where urlKey = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, key);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.last()) {
				long sid = resultSet.getLong(1);
				System.out.println(sid);
				session.setDbId(sid);
				session.setUrl(key);
			}

			return session;

		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);

		}
	}

	public List<Session> sessionHistory(String urlKey) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			List<Session> history = new ArrayList();
			preparedStatement = connect.prepareStatement(
					"SELECT c.team_name, c.id, c.urlKey, c.completedOn, c.session_cat from session c "
							+ "LEFT OUTER JOIN session c2 on c.session_cat = c2.session_cat and c.team_name = c2.team_name and c.completedOn < c2.completedOn"
							+ " WHERE c2.completedOn is null and c.completedOn is not null and c.session_cat is not null and c.team_name = (SELECT team_name from session where urlKey = ?);",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, urlKey);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.first()) {
				System.out.println(resultSet.getString(3));
				while (!resultSet.isAfterLast()) {
					long id = resultSet.getLong(2);
					String url = resultSet.getString(3);
					Date completedOn = resultSet.getDate(4);
					long cat = resultSet.getLong(5);
					boolean oldData = false;
					history.add(new Session(id, urlKey, completedOn, cat, oldData));
					resultSet.next();
				}
			}
			return history;

		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public Session updateSession(Session session, long id) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			preparedStatement = connect.prepareStatement(
					"UPDATE " + schema + ".session set location = ?, size = ? where id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, session.getLocation());
			preparedStatement.setInt(2, session.getSize());
			preparedStatement.setLong(3, id);
			preparedStatement.executeUpdate();

			return session;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public String setCategory(long category, long sessionId, String team) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			preparedStatement = connect.prepareStatement(
					"UPDATE " + schema + ".session set session_cat = ? where id = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, category);
			preparedStatement.setLong(2, sessionId);
			preparedStatement.executeUpdate();

//			preparedStatement = connect.prepareStatement("DELETE from "+schema+".session where session_cat = ? and team_name = ? and id != ?"); //delete team's previous session for category
//			preparedStatement.setLong(1, category);
//			preparedStatement.setString(2, team);
//			preparedStatement.setLong(3, sessionId);
//			preparedStatement.executeUpdate();
//			
			return team;

		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public String closeSession(String urlKey) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			preparedStatement = connect.prepareStatement(
					"UPDATE " + schema + ".session set completedOn = ? where urlKey = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.setString(2, urlKey);
			preparedStatement.executeUpdate();
			return urlKey;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public Session getSession(long id) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			preparedStatement = connect.prepareStatement("SELECT * from " + schema + ".session where id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			Session s = null;
			if (resultSet.last()) {
				s = new Session(resultSet.getLong(1), resultSet.getString(6), resultSet.getString(3),
						resultSet.getDate(4), resultSet.getDate(5));
			}
			return s;

		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);

		}
	}

	public int getIdByUrl(String urlKey) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			int id = -1;
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			preparedStatement = connect.prepareStatement("SELECT id from " + schema + ".session where urlKey = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, urlKey);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.last()) {
				id = resultSet.getInt(1);
			}
			return id;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public Session getEmailByUrl(String urlKey) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String email = null;
			Session s = new Session();
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			preparedStatement = connect.prepareStatement(
					"SELECT email, team_name from " + schema + ".session where urlKey = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, urlKey);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.last()) {
				s.setEmail(resultSet.getString(1));
				s.setTeamName(resultSet.getString(2));
			}
			return s;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public List<String> teamWorks(List<String> works, long id) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			preparedStatement = connect.prepareStatement(
					"INSERT into " + schema + ".session_works values (default, ?, ?)", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(2, id);
			for (int i = 0; i < works.size(); i++) {
				preparedStatement.setString(1, works.get(i));
				preparedStatement.executeUpdate();
			}
			return works;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	private void close(Connection connect, ResultSet resultSet, Statement statement) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}
