package chapter6.service;

import static chapter6.utils.CloseableUtil.*;
import static chapter6.utils.DBUtil.*;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.beans.UserMessage;
import chapter6.dao.MessageDao;
import chapter6.dao.UserMessageDao;
import chapter6.logging.InitApplication;

public class MessageService {


    /**
    * ロガーインスタンスの生成
    */
    Logger log = Logger.getLogger("twitter");

    /**
    * デフォルトコンストラクタ
    * アプリケーションの初期化を実施する。
    */
    public MessageService() {
        InitApplication application = InitApplication.getInstance();
        application.init();

    }

    public void insert(Message message) {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

        Connection connection = null;
        try {
            connection = getConnection();
            new MessageDao().insert(connection, message);
            commit(connection);
        } catch (RuntimeException e) {
            rollback(connection);
		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
            throw e;
        } catch (Error e) {
            rollback(connection);
		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
            throw e;
        } finally {
            close(connection);
        }
    }

    public List<UserMessage> select(String userId, String start, String end) {

  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

          final int LIMIT_NUM = 1000;

          Connection connection = null;
          try {
              connection = getConnection();

              Integer id = null;
              if(!StringUtils.isEmpty(userId)) {
                  id = Integer.parseInt(userId);
              }
              String startDate = "";
              String endDate = "";
              if (!StringUtils.isBlank(start)) {
            	  startDate = start + " 00:00:00";
              } else {
            	  startDate = "2026-01-01 00:00:00";
              }
              if (!StringUtils.isBlank(end)) {
            	  endDate = end + " 23:59:59";
              } else {
            	  Date now = new Date();
            	  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	  String stringDateformat = dateformat.format(now);
            	  endDate = stringDateformat;
              }

              List<UserMessage> messages = new UserMessageDao().select(connection, id, startDate, endDate, LIMIT_NUM);
              commit(connection);

              return messages;
          } catch (RuntimeException e) {
              rollback(connection);
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw e;
          } catch (Error e) {
              rollback(connection);
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw e;
          } finally {
              close(connection);
          }
     }

    public void delete(int messageId, int userId) {

  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

          Connection connection = null;
          try {
              connection = getConnection();
              new MessageDao().delete(connection, messageId, userId);
              commit(connection);
          } catch (RuntimeException e) {
              rollback(connection);
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw e;
          } catch (Error e) {
              rollback(connection);
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw e;
          } finally {
              close(connection);
          }
      }

    public Message select(Message messageId) {

    	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
            " : " + new Object(){}.getClass().getEnclosingMethod().getName());

            Connection connection = null;
            try {
                connection = getConnection();
                Message messages = new MessageDao().select(connection, messageId);
                commit(connection);

                return messages;
            } catch (RuntimeException e) {
                rollback(connection);
    		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
                throw e;
            } catch (Error e) {
                rollback(connection);
    		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
                throw e;
            } finally {
                close(connection);
            }
        }

    public void update(Message messageInfo) {

  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
          " : " + new Object(){}.getClass().getEnclosingMethod().getName());

          Connection connection = null;
          try {
              connection = getConnection();
              new MessageDao().update(connection, messageInfo);
              commit(connection);
          } catch (RuntimeException e) {
              rollback(connection);
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw e;
          } catch (Error e) {
              rollback(connection);
  		log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
              throw e;
          } finally {
              close(connection);
          }
      }
}