package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import chapter6.beans.Comment;
import chapter6.beans.UserComment;
import chapter6.exception.SQLRuntimeException;
import chapter6.logging.InitApplication;

public class CommentDao {

	/**
	    * ロガーインスタンスの生成
	    */
	    Logger log = Logger.getLogger("twitter");

	    /**
	    * デフォルトコンストラクタ
	    * アプリケーションの初期化を実施する。
	    */
	    public CommentDao() {
	        InitApplication application = InitApplication.getInstance();
	        application.init();

	    }

	    public void insert(Connection connection, Comment commentInfo) {

		  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
	        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	        PreparedStatement ps = null;
	        try {
	            StringBuilder sql = new StringBuilder();
	            sql.append("INSERT INTO comments ( ");
	            sql.append("    text, ");
	            sql.append("    user_id, ");
	            sql.append("    message_id, ");
	            sql.append("    created_date, ");
	            sql.append("    updated_date ");
	            sql.append(") VALUES ( ");
	            sql.append("    ?, ");
	            sql.append("    ?, ");
	            sql.append("    ?, ");
	            sql.append("    CURRENT_TIMESTAMP, ");
	            sql.append("    CURRENT_TIMESTAMP ");
	            sql.append(")");

	            ps = connection.prepareStatement(sql.toString());

	            ps.setString(1, commentInfo.getText());
	            ps.setInt(2, commentInfo.getUserId());
	            ps.setInt(3, commentInfo.getMessageId());

	            ps.executeUpdate();
	        } catch (SQLException e) {
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
	            throw new SQLRuntimeException(e);
	        } finally {
	            close(ps);
	        }
	    }

	    public List<UserComment> select(Connection connection, Integer userId, Integer messageId,  int num) {

	  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
	          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	          PreparedStatement ps = null;
	          try {
	              StringBuilder sql = new StringBuilder();
	              sql.append("SELECT ");
	              sql.append("    comments.id as id, ");
	              sql.append("    comments.text as text, ");
	              sql.append("    comments.user_id as user_id, ");
	              sql.append("    comments.message_id as message_id, ");
	              sql.append("    users.account as account, ");
	              sql.append("    users.name as name, ");
	              sql.append("    comments.created_date as created_date ");
	              sql.append("FROM comments ");
	              sql.append("INNER JOIN users ");
	              sql.append("ON comments.user_id = users.id ");
	              sql.append("INNER JOIN messages ");
	              sql.append("ON comments.message_id = messages.id ");

	              if (userId != null) {
	            	    sql.append("WHERE users.id = ? ");
	              }
	              if (messageId != null) {
	            	    sql.append("AND messages.id = ? ");
	              }


	              sql.append("ORDER BY created_date DESC limit " + num);

	              ps = connection.prepareStatement(sql.toString());

	              int index = 1;
	              if (userId != null) {
	                  ps.setInt(index++, userId);
	              }
	              if (messageId != null) {
	                  ps.setInt(index++, messageId);
	              }

	              ResultSet rs = ps.executeQuery();

	              List<UserComment> comments = toUserComment(rs);
	              return comments;
	          } catch (SQLException e) {
	  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
	              throw new SQLRuntimeException(e);
	          } finally {
	              close(ps);
	          }
	      }

	      private List<UserComment> toUserComment(ResultSet rs) throws SQLException {


	  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
	          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	          List<UserComment> comments = new ArrayList<UserComment>();
	          try {
	              while (rs.next()) {
	            	  UserComment comment = new UserComment();
	                  comment.setId(rs.getInt("id"));
	                  comment.setText(rs.getString("text"));
	                  comment.setUserId(rs.getInt("user_id"));
	                  comment.setMessageId(rs.getInt("message_id"));
	                  comment.setAccount(rs.getString("account"));
	                  comment.setCreatedDate(rs.getTimestamp("created_date"));
	                  comments.add(comment);
	              }
	              return comments;
	          } finally {
	              close(rs);
	          }
	      }
}
