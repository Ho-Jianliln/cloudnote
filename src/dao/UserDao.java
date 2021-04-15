package dao;

import entity.User;
import util.Resultset_tran;
import view.Alert;

import java.sql.SQLException;
import java.util.List;


public class UserDao extends BaseDao {

    public User judgepass(User user) throws SQLException {//验证账号密码
        String sql="select * from userlist where name=? and checkkey=? and type=?";
        executeQuerySQL(sql,new Object[]{user.getName(),user.getKey2(),user.getType()});
        if (rs.next()) {
            return Resultset_tran.to_user(rs);
        } else {
            Alert.send("登录失败，请检查账号密码");
            return null;
        }
    }
    public void updatetime(int userid){//更新最后登录时间
        executeUpdateSQL("update userlist set lasttime=NOW() where id=?",new Object[]{userid});
    }
    public List<User> findusers(String name) throws SQLException {//根据用户名搜索用户列表，支持模糊搜索
        String name2;
        if(name.equals("")) name2="";
        else name2="%"+name+"%";
        executeQuerySQL("select id,name,type,lasttime,blacklist from userlist where (name like ? or ?='')",new Object[]{name2,name});
        return Resultset_tran.to_userlist(rs);
    }

    public int finduser(String username) throws SQLException {//根据用户名搜索用户
        executeQuerySQL("select id from userlist where name=?",new Object[]{username});
        rs.next();
        return rs.getInt(1);
    }

    public int adduser(User user) throws SQLException {
        executeQuerySQL("select id from userlist where name=?",new Object[]{user.getName()});
        if(rs.next()){
            return 2;
        }
        else {
            int count=executeUpdateSQL("insert into userlist (name,checkkey,type,blacklist) values (?,?,'user','')",new Object[]{user.getName(),user.getKey2()});
            return count;
        }
    }

    public void resetname(User user,String newname) throws SQLException {
        executeQuerySQL("select name from userlist where name=?",new Object[]{newname});
        if(rs.next()){
            Alert.send("账号名已存在！");
        }else{
            int count=executeUpdateSQL("update userlist set name=? where id=?",new Object[]{newname,user.getId()});
            if(count==1)Alert.send("改名成功");
            else Alert.send("改名失败");
        }
        closeAll();
    }

    public int resetpass(User user,String newpass) throws SQLException {//修改密码
        int check=judgepass(user).getId();
        if(check!=0) {
            user.setPass(newpass);
            return executeUpdateSQL("update userlist set checkkey=? where id=?", new Object[]{user.getKey2(), user.getId()});
        }else{
            return 0;
        }
    }

    public int set_black(int userid){//设置黑名单
        return executeUpdateSQL("update userlist set blacklist='yes' where id=?", new Object[]{userid});
    }

    public int offset_black(int userid){//取消黑名单
        return executeUpdateSQL("update userlist set blacklist=null where id=?", new Object[]{userid});
    }
}
