package com.assessment.assessment.outcome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CategoryDAO {

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

	public List<List<Competency>> getMapDimensions() throws Exception { // right now only considers category and
																		// competency.
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			List<List<Competency>> areaCatComp = new ArrayList();
			int areaID, catID;
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// SQL call
			resultSet = statement.executeQuery(
					"SELECT a.id, ca.id, co.id, co.minScore, co.maxScore from area a join category ca on a.id = ca.area_id join competency co on ca.id = co.cat_id");

			if (resultSet.first()) { // ResultSet version of multiple nested loops, going through each area by
										// category by competency
				while (!resultSet.isAfterLast()) {
					areaID = resultSet.getInt(1);
					while (!resultSet.isAfterLast() && areaID == resultSet.getInt(1)) {
						catID = resultSet.getInt(2);
						if (areaCatComp.size() <= catID) {
							areaCatComp.add(catID, new ArrayList<Competency>());
						}
						while (!resultSet.isAfterLast() && catID == resultSet.getInt(2)) { // competency ids are not
																							// stored in a variable, and
																							// are instead used directly
																							// here to create a
																							// competency object
							areaCatComp.get(catID).add(resultSet.getInt(3), new Competency(resultSet.getFloat(4),
									resultSet.getFloat(5), resultSet.getInt(2), catID, 0));
							resultSet.next();
						}
					}
				}

			}
			return areaCatComp;

		} catch (Exception e) {
			throw e;

		} finally {
			close(connect, resultSet, statement);
		}
	}

	public IndividualOutcome indivOutcomeInfo(long cat, long comp) throws Exception { // Gets more information about the
																						// category and competency an
																						// outcome is tied to
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			// SQL call
			preparedStatement = connect.prepareStatement(
					"SELECT ca.id, ca.category_name, co.id, co.competency_name from " + schema + ".category ca "
							+ "JOIN " + schema + ".competency co on ca.id = co.cat_id where ca.id = ? and co.id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, cat);
			preparedStatement.setLong(2, comp);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.first()) { // Not entirely sure why we reobtain the category id and competency id... I
										// guess to be as thorough as possible
				long caid = resultSet.getLong(1);
				String caname = resultSet.getString(2);
				long coid = resultSet.getLong(3);
				String coname = resultSet.getString(4);
				IndividualOutcome indiv = new IndividualOutcome(caid, coid, caname, coname);
				return indiv;
			}
			return null;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public long checkIfNewCategory(OutcomeLoader outLoader) throws Exception { // Checks if a category already exists in
																				// the DB
		// If it does, we get its ID for future SQL calls
		// If not, we create it
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			long catID = -1;
			long outcomeID = -1;

			// SQL call
			preparedStatement = connect.prepareStatement(
					"select category_id from " + schema + ".outcome_map join "
							+ "category on id = category_id where category_name = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, outLoader.getCatName());
			resultSet = preparedStatement.executeQuery();

			if (resultSet.first()) { // Any result indicates that the category exists

				return resultSet.getLong(1);
			} else { // Create new category
						// SQL call
				preparedStatement = connect.prepareStatement(
						"select MAX(category_id), MAX(outcome_id) from " + schema + ".outcome_map",
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.first()) {
					catID = resultSet.getLong(1) + 1;
					outcomeID = resultSet.getLong(2) + 1;
					// SQL call, create the category
					preparedStatement = connect
							.prepareStatement("INSERT into " + schema + ".category " + "values (?, ?, 0)");
					preparedStatement.setLong(1, catID);
					preparedStatement.setString(2, outLoader.getCatName());
					preparedStatement.executeUpdate();
					// SQL call, create the first competency for the category (starts with id = 0)
					preparedStatement = connect
							.prepareStatement("INSERT into " + schema + ".competency " + "values (0, ?, ?, 0, 0)");
					preparedStatement.setString(1, outLoader.getCompName());
					preparedStatement.setLong(2, catID);
					preparedStatement.executeUpdate();
					// SQL call, create a mapping on the outome map
					preparedStatement = connect
							.prepareStatement("INSERT into " + schema + ".outcome_map " + "values(0, ?, 0,?)");
					preparedStatement.setLong(1, catID);
					preparedStatement.setLong(2, outcomeID);
					preparedStatement.executeUpdate();
				}
				return catID;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public long checkIfNewCompetency(OutcomeLoader outLoader, long catID) throws Exception { // Checks for existence of
																								// competency
		// If it does not exist, create it
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			// SQL call
			preparedStatement = connect.prepareStatement(
					"select * from competency co join category ca on co.cat_id = ca.id join outcome_map on category_id = ca.id  and competency_id = co.id where category_name = ? and competency_name = ?;",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, outLoader.getCatName());
			preparedStatement.setString(2, outLoader.getCompName());
			resultSet = preparedStatement.executeQuery();

			if (resultSet.first()) { // If there is a result, competency exists

				return resultSet.getLong(1);
			} else {
				long compID = -1;
				long outcomeID = -1;

				// SQL call
				preparedStatement = connect.prepareStatement(
						"select MAX(competency_id) from " + schema + ".outcome_map join "
								+ "category on id = category_id where id = ?",
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				preparedStatement.setLong(1, catID);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.first()) { // obtain the highest competency id in a category; the new competency will be 1
											// higher
					compID = resultSet.getLong(1) + 1;
				}

				// SQL call
				preparedStatement = connect.prepareStatement("select MAX(outcome_id) from " + schema + ".outcome_map",
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				resultSet = preparedStatement.executeQuery();
				if (resultSet.first()) { // obtain the highest outcome id in the outcome map, assign this competency an
											// id that is 1 higher

					outcomeID = resultSet.getLong(1) + 1;
					preparedStatement = connect
							.prepareStatement("INSERT into " + schema + ".competency " + "values (?, ?, ?, 0, 0)");
					preparedStatement.setLong(1, compID);
					preparedStatement.setString(2, outLoader.getCompName());
					preparedStatement.setLong(3, catID);
					preparedStatement.executeUpdate();
					preparedStatement = connect
							.prepareStatement("INSERT into " + schema + ".outcome_map " + "values (0, ?, ?,?)");
					preparedStatement.setLong(1, catID);
					preparedStatement.setLong(2, compID);
					preparedStatement.setLong(3, outcomeID);
					preparedStatement.executeUpdate();
				}
				return compID;
			}
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
