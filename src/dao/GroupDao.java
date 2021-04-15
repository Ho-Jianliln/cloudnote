package dao;

import bean.Group1Bean;
import bean.Group2Bean;
import util.Resultset_tran;

import java.sql.SQLException;
import java.util.List;

public class GroupDao extends BaseDao {
    public List<Group1Bean> findgroup1(int userid) throws SQLException {//搜索用户下的所有知识库
        executeQuerySQL("select * from group1 where author_id=?",new Object[]{userid});
        return Resultset_tran.to_group1list(rs);
    }
    public List<Group2Bean> findgroup2(int group1id) throws SQLException {//搜索知识库下的所有分组
        executeQuerySQL("select * from group2 where group1_id=?",new Object[]{group1id});
        return Resultset_tran.to_group2list(rs);
    }
    public List<Group1Bean> findnote(int group2id) throws SQLException {
        executeQuerySQL("select id,title,group2_id from note where group2_id=?",new Object[]{group2id});
        return Resultset_tran.to_groupnote(rs);
    }
    public int addgroup1(int userid,String name) throws SQLException {//添加知识库
        executeUpdateSQL("insert into group1 (group1_name,author_id) values (?,?)",new Object[]{name,userid});
        executeQuerySQL("select id from group1 where author_id=? and group1_name=?",new Object[]{userid,name});
        rs.next();
        return rs.getInt(1);
    }
    public int addgroup2(int group1id,String name) throws SQLException {//添加分组
        executeUpdateSQL("insert into group2 (group2_name,group1_id) values (?,?)",new Object[]{name,group1id});
        executeQuerySQL("select id from group2 where group1_id=? and group2_name=?",new Object[]{group1id,name});
        rs.next();
        return rs.getInt(1);
    }
    public int modifigroup1(int userid,String newname,int g1id){//修改知识库名
        return executeUpdateSQL("update group1 set group1_name=? where author_id=? and id=?",new Object[]{newname,userid,g1id});
    }
    public int modifigroup2(int group2id,String newname){//修改分组名
        return executeUpdateSQL("update group2 set group2_name=? where id=?",new Object[]{newname,group2id});
    }
    public int delegroup1(int group1id){//删除知识库
        return executeUpdateSQL("delete from group1 where id=?",new Object[]{group1id});
    }
    public int delegroup2(int group2id){//删除分组
        return executeUpdateSQL("delete from group2 where id=?",new Object[]{group2id});
    }
}
