package com.assessment.assessment.question;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QuestionDAO {

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

	public Question getQuestion(long id) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			// SQL call
			preparedStatement = connect.prepareStatement(
					"SELECT * from " + schema + ".question JOIN" + " " + schema
							+ ".choice on id = choice_id where id = ? order by choice_val",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();

			Question q = null;
			if (resultSet.first()) {
				q = new Question();
				q.setId(resultSet.getLong("id"));
				q.setDescription(resultSet.getString("desc"));
				q.setArea(resultSet.getLong("area"));
				q.setCategory(resultSet.getLong("category"));
				q.setCompetency(resultSet.getLong("competency"));
				q.setWeight(resultSet.getFloat("weight"));

				List<String> choiceText = new ArrayList();
				List<Integer> choiceLevel = new ArrayList();
				while (!resultSet.isAfterLast()) {
					choiceText.add(resultSet.getString("choice_text"));
					choiceLevel.add(resultSet.getInt("choice_val"));
					resultSet.next();
				}
				q.setChoices(choiceText.toArray(new String[0]));
				q.setLevels(choiceLevel.stream().mapToInt(i -> i).toArray());
			}
			return q;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public List<Question> getAllQuestions() throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			List<Question> qs = null;
			List<String> choiceText;
			List<Integer> choiceLevel;
			Question q;
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// SQL call
			resultSet = statement.executeQuery("SELECT * from " + schema + ".question q JOIN" + " " + schema
					+ ".choice on q.id = choice_id JOIN category ca on ca.id = q.category "
					+ "JOIN competency co on ca.id = co.cat_id  and co.id = q.competency order by q.id, choice_val");

			if (resultSet.first()) {
				qs = new ArrayList<Question>();
				while (!resultSet.isAfterLast()) {
					q = new Question();
					q.setId(resultSet.getLong("id"));
					q.setDescription(resultSet.getString("desc"));
					q.setArea(resultSet.getLong("area"));
					q.setCategory(resultSet.getLong("category"));
					q.setCompetency(resultSet.getLong("competency"));
					q.setWeight(resultSet.getFloat("weight"));
					q.setCatString(resultSet.getString("category_name"));
					q.setCompString(resultSet.getString("competency_name"));
					choiceText = new ArrayList();
					choiceLevel = new ArrayList();
					while (!resultSet.isAfterLast() && q.getId() == resultSet.getLong("id")) {
						choiceText.add(resultSet.getString("choice_text"));
						choiceLevel.add(resultSet.getInt("choice_val"));
						resultSet.next();
					}
					q.setChoices(choiceText.toArray(new String[0]));
					q.setLevels(choiceLevel.stream().mapToInt(i -> i).toArray());
					qs.add(q);
				}
			}

			return qs;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}

	}

	public Question getNextInCategory(long id, long category, String direction, String order) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			Question q = null;
			preparedStatement = connect.prepareStatement(
					"SELECT * from question JOIN choice on choice_id = id where id " + direction
							+ " ? and category = ? order by id " + order + ", choice_val limit 5",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setLong(1, id);
			preparedStatement.setLong(2, category);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.first()) {
				q = new Question();
				q.setId(resultSet.getLong("id"));
				q.setDescription(resultSet.getString("desc"));
				q.setArea(resultSet.getLong("area"));
				q.setCategory(resultSet.getLong("category"));
				q.setCompetency(resultSet.getLong("competency"));
				q.setWeight(resultSet.getFloat("weight"));

				List<String> choiceText = new ArrayList();
				List<Integer> choiceLevel = new ArrayList();
				while (!resultSet.isAfterLast() && q.getId() == resultSet.getLong("id")) {
					choiceText.add(resultSet.getString("choice_text"));
					choiceLevel.add(resultSet.getInt("choice_val"));
					resultSet.next();
				}
				q.setChoices(choiceText.toArray(new String[0]));
				q.setLevels(choiceLevel.stream().mapToInt(i -> i).toArray());
			}
			return q;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public Question updateQuestion(Question q) throws Exception {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			int lowest = Arrays.stream(q.getLevels()).min().getAsInt();
			int highest = Arrays.stream(q.getLevels()).max().getAsInt();
			float newLow = 0;
			float newHigh = 0;
			long catID = -1, compID = -1;
			connect.setAutoCommit(false);

			// SQL call
			preparedStatement = connect.prepareStatement(
					"SELECT ca.id, co.id from category ca join competency co on co.cat_id = ca.id where category_name = ? and competency_name = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, q.getCatString());
			preparedStatement.setString(2, q.getCompString());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.first()) {
				q.setCategory(resultSet.getLong(1));
				q.setCompetency(resultSet.getLong(2));

				preparedStatement = connect.prepareStatement("INSERT into " + schema
						+ ".question values (?, ?, ?, ?, ?) " + "on conflict (id) do update set "
						+ "\"desc\" = ?, area = ?, category = ?, competency = ?");
				preparedStatement.setLong(1, q.getId());
				preparedStatement.setString(2, q.getDescription());
				preparedStatement.setLong(3, q.getArea());

				preparedStatement.setLong(4, q.getCategory());
				preparedStatement.setLong(5, q.getCompetency());
				// Update
				preparedStatement.setString(6, q.getDescription());
				preparedStatement.setLong(7, q.getArea());

				preparedStatement.setLong(8, q.getCategory());
				preparedStatement.setLong(9, q.getCompetency());
				preparedStatement.executeUpdate();

				// If this was an update, we need to clear out the choices and score values
				// before adding the new ones in
				preparedStatement = connect.prepareStatement("SELECT minScore, maxScore, weight, choice_val from "
						+ schema + ".competency co" + "" + " join " + schema
						+ ".question q on (q.category = co.cat_id and q.competency = co.id and q.id = ?) " + "join "
						+ schema
						+ ".choice ch on q.id = ch.choice_id where co.id = ? and co.cat_id = ? order by choice_val desc",
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				preparedStatement.setLong(1, q.getId());
				preparedStatement.setLong(2, q.getCompetency());
				preparedStatement.setLong(3, q.getCategory());
				// preparedStatement.setLong(3, catID);
				// preparedStatement.setLong(2, compID);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.first()) { // preexisting question getting changed, update max and min score for new
											// question
					newHigh = resultSet.getFloat("maxScore")
							- (resultSet.getFloat("weight") * resultSet.getInt("choice_val"))
							+ (q.getWeight() * highest);
					resultSet.last();
					newLow = resultSet.getFloat("minScore")
							- (resultSet.getFloat("weight") * resultSet.getInt("choice_val"))
							+ (q.getWeight() * lowest);
					System.out.println(newHigh + " " + newLow + ", " + lowest + " " + highest);
					preparedStatement = connect.prepareStatement("UPDATE " + schema
							+ ".competency set maxScore = ?, minScore = ? " + "where id = ? and cat_id = ?");
					preparedStatement.setFloat(1, newHigh);
					preparedStatement.setFloat(2, newLow);
					preparedStatement.setLong(3, q.getCompetency());
					preparedStatement.setLong(4, q.getCategory());
					// preparedStatement.setLong(4, catID);
					// preparedStatement.setLong(3, compID);
					preparedStatement.executeUpdate();
				} else { // not a preexisting question, max score and min score are going up
					preparedStatement = connect.prepareStatement(
							"SELECT minScore, maxScore from " + schema + ".competency " + "where id = ? and cat_id = ?",
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					preparedStatement.setLong(1, q.getCompetency());
					preparedStatement.setLong(2, q.getCategory());
					resultSet = preparedStatement.executeQuery();
					if (resultSet.first()) {
						newHigh = resultSet.getFloat("maxScore") + (q.getWeight() * highest);
						newLow = resultSet.getFloat("minScore") + (q.getWeight() * lowest);
						System.out.println(q.getWeight() * highest);
						System.out.println(resultSet.getFloat("maxScore"));
						preparedStatement = connect.prepareStatement("UPDATE " + schema
								+ ".competency set maxScore = ?, minScore = ? " + "where id = ? and cat_id = ?");
						preparedStatement.setFloat(1, newHigh);
						preparedStatement.setFloat(2, newLow);
						preparedStatement.setLong(3, q.getCompetency());
						preparedStatement.setLong(4, q.getCategory());
						preparedStatement.executeUpdate();
					}
				}

				preparedStatement = connect.prepareStatement("DELETE from " + schema + ".choice where choice_id = ?");
				preparedStatement.setLong(1, q.getId());
				preparedStatement.executeUpdate();

				preparedStatement = connect.prepareStatement("INSERT into " + schema + ".choice values (?, ?, ?, ?)");
				for (int i = 0; i < q.getChoices().length; i++) {

					preparedStatement.setLong(1, q.getId());
					preparedStatement.setInt(2, q.getLevels()[i]);
					preparedStatement.setString(3, q.getChoices()[i]);
					preparedStatement.setFloat(4, q.getWeight());
					preparedStatement.executeUpdate();
				}
			}
			connect.commit();
			return q;
			// UPDATE CHOICES, FIND WEIGHTS, SET MAX AND MIN SCORE FOR COMPETENCY

		} catch (Exception e) {
			if (connect != null) {
				connect.rollback();
			}
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
