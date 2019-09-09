package com.assessment.assessment.outcome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service // lets you autowire things and not have to instantiate
public class OutcomeDAO {

	@Autowired
	private CategoryDAO cDao;

	@Value("${connectionString}")
	private String connectionString;
	@Value("${schemaName}") // Generally going to be 'one_assessment', but the name of the schema might
							// change
	private String schema;
	@Value("${spring.security.user.name}")
	private String username;
	@Value("${spring.security.user.password}")
	private String password;
//	private Connection connect = null;
//	private Statement statement = null;
//	private PreparedStatement preparedStatement = null;
//	private ResultSet resultSet = null;

	public List<Outcome> getAllOutcomes() throws Exception { // Obtains all possible outcomes for filtering down
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			List<Outcome> o = null;
			Outcome out = null;
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// SQL call
			resultSet = statement.executeQuery("SELECT * from " + schema + ".outcome_map join  " + "" + schema
					+ ".outcome on outcome_id = id join category ca on ca.id = category_id join competency co on co.id = competency_id and co.cat_id = ca.id order by outcome_id, level");
			if (resultSet.first()) {
				o = new ArrayList<Outcome>();
				String[] outcomesArr;
				int currentId;
				while (!resultSet.isAfterLast()) { // Create objects for the outcomes on the backend
					out = new Outcome(resultSet.getLong("area_id"), resultSet.getLong("category_id"),
							resultSet.getLong("competency_id"));
					out.setCategoryName(resultSet.getString("category_name"));
					out.setCompetencyName(resultSet.getString("competency_name"));
					currentId = resultSet.getInt("outcome_id");
					outcomesArr = new String[5];
					while (!resultSet.isAfterLast() && currentId == resultSet.getInt("outcome_id")) { // Each outcome
																										// has an array
																										// of Strings
																										// for the
																										// action item
																										// text
						outcomesArr[(resultSet.getInt("level")) - 1] = resultSet.getString("outcome_val").replace('"',
								'\'');
						resultSet.next();
					}
					out.setOutcomes(outcomesArr);
					o.add(out);
				}
			}
			return o;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public List<Outcome> getAllModels() throws Exception { // Retrieves all possible models
		// Models are based on the Outcome class, so the code is largely the same, but
		// the SQL is different
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			List<Outcome> m = null;
			Model out = null;
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// SQL call
			resultSet = statement.executeQuery("SELECT * from " + schema + ".outcome_map join  " + "" + schema
					+ ".model on outcome_id = id join category ca on ca.id = category_id join competency co on co.id = competency_id and co.cat_id = ca.id order by outcome_id, level");
			if (resultSet.first()) { // Creates all the Model objects
				m = new ArrayList<Outcome>();
				String[] outcomesArr;
				int currentId;
				while (!resultSet.isAfterLast()) {
					out = new Model(resultSet.getLong("area_id"), resultSet.getLong("category_id"),
							resultSet.getLong("competency_id"));
					out.setCategoryName(resultSet.getString("category_name"));
					out.setCompetencyName(resultSet.getString("competency_name"));
					currentId = resultSet.getInt("outcome_id");
					outcomesArr = new String[5];
					while (!resultSet.isAfterLast() && currentId == resultSet.getInt("outcome_id")) {
						outcomesArr[(resultSet.getInt("level")) - 1] = resultSet.getString("model_val").replace('"',
								'\'');
						resultSet.next();
					}
					out.setOutcomes(outcomesArr);
					m.add(out);
				}
			}
			return m;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public OutcomeLoader newOutcome(OutcomeLoader outLoader) throws Exception { // Creating outcomes
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			// Creation of new categories and competencies is handled here if they do not
			// already exist
			long catID = cDao.checkIfNewCategory(outLoader);
			long compID = cDao.checkIfNewCompetency(outLoader, catID);
			long outID = -1;

			// SQL call
			preparedStatement = connect.prepareStatement(
					"SELECT outcome_id from " + schema + ".outcome_map where category_id = ? and competency_id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, catID);
			preparedStatement.setLong(2, compID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				outID = resultSet.getLong(1);
			}
			preparedStatement = connect.prepareStatement("INSERT into " + schema + ".outcome values (?,?,?) "
					+ "on conflict (id, level) do update set outcome_val = CONCAT(outcome.outcome_val, ?)");
			for (int i = 1; i <= outLoader.getOutcomes().length; i++) {
				preparedStatement.setLong(1, outID);
				preparedStatement.setInt(2, i);
				preparedStatement.setString(3, "<li>" + outLoader.getOutcomes()[(i - 1)] + "</li>");
				preparedStatement.setString(4, "<li>" + outLoader.getOutcomes()[(i - 1)] + "</li>"); // There are
																										// multiple
																										// lines for
																										// Learning in
																										// the excel
																										// sheet we pull
																										// from, so they
																										// have to be
																										// concatenated
				preparedStatement.executeUpdate();
				System.out.println(outLoader.getCompName());

			}
			return outLoader;

		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public OutcomeLoader newModel(OutcomeLoader outLoader) throws Exception { // Creates model in DB
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			long catID = cDao.checkIfNewCategory(outLoader);
			long compID = cDao.checkIfNewCompetency(outLoader, catID);
			long outID = -1;
			preparedStatement = connect.prepareStatement(
					"SELECT outcome_id from " + schema + ".outcome_map where category_id = ? and competency_id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, catID);
			preparedStatement.setLong(2, compID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				outID = resultSet.getLong(1);
			}
			preparedStatement = connect.prepareStatement("INSERT into " + schema + ".model values (?,?,?) "
					+ "on conflict (id, level) do update set model_val = ?");
			for (int i = 1; i <= 5; i++) {

				preparedStatement.setLong(1, outID);
				preparedStatement.setInt(2, i);
				preparedStatement.setString(3, outLoader.getOutcomes()[(i - 1)]);
				preparedStatement.setString(4, outLoader.getOutcomes()[(i - 1)]); // There is only 1 model per
																					// competency, so no need to
																					// concatenate lines
				preparedStatement.executeUpdate();
				// System.out.println(outLoader.getCompName());

			}
			return outLoader;

		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public OutcomeLoader updateOutcome(OutcomeLoader outLoader) throws Exception { // Updating preexisting outcomes
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			long catID = cDao.checkIfNewCategory(outLoader);
			long compID = cDao.checkIfNewCompetency(outLoader, catID);
			long outID = -1;
			preparedStatement = connect.prepareStatement(
					"SELECT outcome_id from " + schema + ".outcome_map where category_id = ? and competency_id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, catID);
			preparedStatement.setLong(2, compID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				outID = resultSet.getLong(1);
			}
			preparedStatement = connect.prepareStatement("INSERT into " + schema + ".outcome values (?,?,?) "
					+ "on conflict (id, level) do update set outcome_val =  ?");
			for (int i = 1; i <= 5; i++) {

				preparedStatement.setLong(1, outID);
				preparedStatement.setInt(2, i);
				preparedStatement.setString(3, outLoader.getOutcomes()[(i - 1)]);
				preparedStatement.setString(4, outLoader.getOutcomes()[(i - 1)]);
				preparedStatement.executeUpdate();

			}
			return outLoader;

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
