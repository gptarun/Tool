package com.assessment.assessment.demographic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class DemographicDAO {

	@Value("${connectionString}")
	private String connectionString;
	@Value("${schemaName}") // Generally going to be 'one_assessment', but the name of the schema might
							// change
	private String schema;
	@Value("${spring.security.user.name}")
	private String username;
	@Value("${spring.security.user.password}")
	private String password;

	@Autowired
	Environment env;
//	private Connection connect = null;
//	private Statement statement = null;
//	private PreparedStatement preparedStatement = null;
//	private ResultSet resultSet = null;

	public long importTeam(Demographic team) throws Exception { // For adding a list of team to the database, whenever
																// we get around to doing that
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection("jdbc:" + connectionString);

			// SQL call
			preparedStatement = connect.prepareStatement(
					"INSERT into " + schema + ".team values (?, ?, ?)"
							+ "on duplicate key update name = ?, last_assessment = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, team.getId());
			preparedStatement.setString(2, team.getName());
			preparedStatement.setDate(3, new java.sql.Date(new Date().getTime()));
			preparedStatement.setString(4, team.getName());
			preparedStatement.setDate(5, new java.sql.Date(new Date().getTime()));
			preparedStatement.executeUpdate();

			return team.getId();
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public List<Portfolio> getPortfolios() throws Exception { // Retrieves the organizational portfolios that the users
																// pick from on the signup page
		System.out.println(
				env.getProperty("vcap.services.one-assessment-test-db.credentials.connection.postgres.composed"));
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			List<Portfolio> portList = new ArrayList();
			String vert = null;
			List<String> port = new ArrayList();
			List<Integer> id = new ArrayList();
			statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// SQL call
			resultSet = statement.executeQuery("SELECT * from " + schema + ".portfolio");

			if (resultSet.first()) {
				while (!resultSet.isAfterLast()) {
					vert = resultSet.getString(3);
					while (!resultSet.isAfterLast() && vert.equals(resultSet.getString(3))) { // If fields are added to
																								// the portfolio table,
																								// this part might need
																								// to be changed
						port.add(resultSet.getString(2));
						id.add(resultSet.getInt(1));
						resultSet.next();
					}
					portList.add(new Portfolio(vert, port, id));
					port = new ArrayList();
					id = new ArrayList();
				}

			}
			return portList;
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
