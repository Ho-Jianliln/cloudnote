package dao;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class LikeDao extends BaseDao {
    public int like(int noteid,int userid){
        executeUpdateSQL("insert into likelist (user_id,note_id) values (?,?)",new Object[]{userid,noteid});
        return executeUpdateSQL("update note set like_count=like_count+1 where noteid=?",new Object[]{noteid});
    }
    public int like_cancel(int noteid,int userid){//取消点赞
        executeUpdateSQL("delete from likelist where user_id=? and note_id=?",new Object[]{userid,noteid});
        return executeUpdateSQL("update note set like_count=like_count-1 where noteid=?",new Object[]{noteid});
    }
    public Set<Integer> findliked(int userid) throws SQLException {//搜索该用户所有点赞
        executeQuerySQL("select * from likelist where user_id=?",new Object[]{userid});
        Set<Integer> likeset=new HashSet<>();
        while(rs.next()){
            likeset.add(rs.getInt("note_id"));
        }
        return likeset;
    }
}
