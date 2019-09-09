package com.assessment.assessment.answer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.assessment.assessment.outcome.QuestionAnswerGroup;
import com.assessment.assessment.question.Question;
import com.assessment.assessment.session.Session;

@Service
public class AnswerDAO {

	@Value("${connectionString}") // References to vcap are for environment variables
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

	public List<QuestionAnswerGroup> answersBySession(String session) throws SQLException {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			List<QuestionAnswerGroup> ans = null;
			Answer a = null;

			Question q = null;

			List<Integer> choiceLevel = new ArrayList();
			long q_id;

			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			// SQL call
			preparedStatement = connect.prepareStatement(
					"SELECT question_id, `desc`, ans_val, a.area, a.category, a.competency, weight, session_id, choice_val, choice_text from "
							+ schema + ".answer a join " + "" + schema
							+ ".session s on s.id = session_id and urlKey = ? left join " + "" + schema
							+ ".choice on (question_id = choice_id) join question q on question_id = q.id",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setString(1, session);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.first()) {
				ans = new ArrayList();
				while (!resultSet.isAfterLast()) { // Loop through result set, create objects with information about the
													// question and corresponding answer
					q_id = resultSet.getLong("question_id");
					a = new Answer(resultSet.getLong("question_id"), resultSet.getInt("ans_val"));
					q = new Question();
					String cv = null;
					a.setArea(resultSet.getLong("area"));
					a.setCategory(resultSet.getLong("category"));
					a.setCompetency(resultSet.getLong("competency"));
					a.setWeight(resultSet.getFloat("weight"));
					a.setSessionID(resultSet.getInt("session_id"));
					q.setDescription(resultSet.getString("desc")); // this block does not retrieve all of the
																	// information for a question (the endpoints that
																	// use this function only need the actual question
																	// string and the string for the user's response)

					while (!resultSet.isAfterLast() && q_id == resultSet.getLong("question_id")) {
						if (a.getChoice() == resultSet.getInt("choice_val")) { // This value will not always be set. If
																				// user answered 'N/A', the ans_val will
																				// equal -1, and this statement will
																				// never be true
							cv = resultSet.getString("choice_text");
						}

						choiceLevel.add(resultSet.getInt("choice_val"));
						resultSet.next();
					}
					q.setLevels(choiceLevel.stream().mapToInt(i -> i).toArray()); // Integer lists cannot be directly
																					// converted into int arrays for
																					// some reason

					QuestionAnswerGroup qa = new QuestionAnswerGroup(q, a);
					qa.setChoiceText(cv);
					ans.add(qa);
					choiceLevel.clear();

				}
			}
			return ans;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}

	}

	public List<QuestionAnswerGroup> answersBySession(List<Session> sessions, boolean oldDataFlag) throws SQLException {
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			List<QuestionAnswerGroup> ans = null;
			Answer a = null;
			Question q = null;

			List<Integer> choiceLevel = new ArrayList();
			long q_id;
			String queryStr = "";
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);
			for (int i = 0; i < sessions.size(); i++) { // Add in a dynamic amount of variables in the preparedStatement
														// based on the amount of sessions
				if (!sessions.get(i).isOldData() && !oldDataFlag || sessions.get(i).isOldData() && oldDataFlag)
					queryStr = queryStr.concat(" ?,");
			}
			queryStr = queryStr.substring(0, queryStr.length() - 1);
			// SQL call
			preparedStatement = connect.prepareStatement(
					"SELECT question_id, `desc`, ans_val, a.area, a.category, a.competency, weight, session_id, choice_val, choice_text from "
							+ schema + ".answer a join " + "" + schema + ".session s on s.id = session_id and s.id in ("
							+ queryStr + ") left join " + "" + schema
							+ ".choice on (question_id = choice_id) join question q on question_id = q.id",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			int count = 1;
			for (int i = 0; i < sessions.size(); i++) { // set the variables here, rather than in the first loop. I
														// think this is safer.
				if (!sessions.get(i).isOldData() && !oldDataFlag || sessions.get(i).isOldData() && oldDataFlag) {
					preparedStatement.setLong(count, sessions.get(i).getDbId());
					count++;
				}
			}
			resultSet = preparedStatement.executeQuery();

			if (resultSet.first()) {
				ans = new ArrayList();
				while (!resultSet.isAfterLast()) { // Loop through result set, create objects with information about the
													// question and corresponding answer
					q_id = resultSet.getLong("question_id");
					a = new Answer(resultSet.getLong("question_id"), resultSet.getInt("ans_val"));
					q = new Question();
					String cv = null;
					a.setArea(resultSet.getLong("area"));
					a.setCategory(resultSet.getLong("category"));
					a.setCompetency(resultSet.getLong("competency"));
					a.setWeight(resultSet.getFloat("weight"));
					a.setSessionID(resultSet.getInt("session_id"));
					q.setDescription(resultSet.getString("desc")); // this block does not retrieve all of the
																	// information for a question (the endpoints that
																	// use this function only need the actual question
																	// string and the string for the user's response)

					while (!resultSet.isAfterLast() && q_id == resultSet.getLong("question_id")) {
						if (a.getChoice() == resultSet.getInt("choice_val")) { // This value will not always be set. If
																				// user answered 'N/A', the ans_val will
																				// equal -1, and this statement will
																				// never be true
							cv = resultSet.getString("choice_text");
						}

						choiceLevel.add(resultSet.getInt("choice_val"));
						resultSet.next();
					}
					q.setLevels(choiceLevel.stream().mapToInt(i -> i).toArray()); // Integer lists cannot be directly
																					// converted into int arrays for
																					// some reason

					QuestionAnswerGroup qa = new QuestionAnswerGroup(q, a);
					qa.setChoiceText(cv);
					ans.add(qa);
					choiceLevel.clear();
				}
			}
			return ans;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}

	}

	public Answer answerById(long id, int session) throws Exception { // Retrieve an individual answer made during a
																		// specific session
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			Answer a = null;
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			// SQL call
			preparedStatement = connect.prepareStatement(
					"SELECT * from " + schema + ".answer join " + "" + schema + ".choice on (question_id = choice_id) "
							+ "where session_id = ? and question_id = ? limit 1",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setInt(1, session);
			preparedStatement.setLong(2, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.last()) {
				a = new Answer(resultSet.getLong("question_id"), resultSet.getInt("ans_val"));
				a.setArea(resultSet.getLong("area"));
				a.setCategory(resultSet.getLong("category"));
				a.setCompetency(resultSet.getLong("competency"));
				a.setWeight(resultSet.getFloat("weight"));
				a.setSessionID(resultSet.getInt("session_id"));
			}
			return a;

		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public Answer saveAnswer(Answer ans) throws Exception { // Inserts new row in DB
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			// SQL call
			preparedStatement = connect.prepareStatement(
					"INSERT into " + schema + ".answer values" + "(?, ?, ?, ?, ?, ?)", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setInt(1, ans.getSessionID());
			preparedStatement.setLong(2, ans.getId());
			preparedStatement.setInt(3, ans.getChoice());
			preparedStatement.setLong(4, ans.getArea());
			preparedStatement.setLong(5, ans.getCategory());
			preparedStatement.setLong(6, ans.getCompetency());
			preparedStatement.executeUpdate();

			return ans;
		} catch (Exception e) {
			throw e;
		} finally {
			close(connect, resultSet, statement);
		}
	}

	public void deleteAnswer(long id, int session) throws Exception {// deletes answer corresponding to id for a
																		// specific session
		// Called in a situation where the user goes back and changes their answer to a
		// question
		Connection connect = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connectionString = connectionString.replace("service_instance_db", schema);
			connect = DriverManager.getConnection(connectionString, username, password);

			// SQL call
			preparedStatement = connect.prepareStatement(
					"DELETE from " + schema + ".answer " + "where session_id = ? and question_id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setInt(1, session);
			preparedStatement.setLong(2, id);
			preparedStatement.executeUpdate();

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
