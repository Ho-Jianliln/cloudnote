package dao;

import bean.MessageBean;
import entity.User;
import util.Resultset_tran;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MessageDao extends BaseDao {
    public List<MessageBean> find_announce(Date date) throws SQLException {//查询最后一次登录后发布的公告
        executeQuerySQL("select * from announcement where time>=?",new Object[]{date});
        return Resultset_tran.to_announce(rs);
    }
    public List<MessageBean> find_allannounce() throws SQLException {//查询所有公告
        executeQuerySQL("select * from announcement",new Object[]{});
        return Resultset_tran.to_announce(rs);
    }
    public int add_announce(User user,String title,String context){//添加公告
        return executeUpdateSQL("insert into announcement (author,author_id,title,context) values (?,?,?,?)",new Object[]{user.getName(),user.getId(),title,context});
    }
    public List<MessageBean> find_comment() throws SQLException {//查询所有评论
        executeQuerySQL("select * from comment",new Object[]{});
        return Resultset_tran.to_comment(rs);
    }
    public int add_comment(User user,String context){//添加评论
        return executeUpdateSQL("insert into comment (author,author_id,context) values (?,?,?)",new Object[]{user.getName(),user.getId(),context});
    }
}
