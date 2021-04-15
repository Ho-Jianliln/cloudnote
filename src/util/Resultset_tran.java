package util;

import bean.Group1Bean;
import bean.Group2Bean;
import bean.MessageBean;
import bean.NoteBean;
import entity.Note;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Resultset_tran {//把查询结果转化为指定格式
    public static List<User> to_userlist(ResultSet rs) throws SQLException {
        List<User> list = new ArrayList<User>();
        while(rs.next()){
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setType(rs.getString("type"));
            user.setLasttime(rs.getDate("lasttime"));
            user.setBlacklist(rs.getString("blacklist"));
            list.add(user);
        }
        return list;
    }

    public static User to_user(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setKey2(rs.getString("checkkey"));
        user.setType(rs.getString("type"));
        user.setLasttime(rs.getDate("lasttime"));
        user.setBlacklist(rs.getString("blacklist"));
        return user;
    }

    public static List<NoteBean> to_notelist(ResultSet rs) throws SQLException {
        List<NoteBean> list = new ArrayList<NoteBean>();
        while (rs.next()) {
            NoteBean note=new NoteBean();
            note.setNoteid(rs.getInt("noteid"));
            note.setAuthor_id(rs.getInt("author_id"));
            note.setAuthor(rs.getString("author"));
            note.setTitle(rs.getString("title"));
            note.setLike_count(rs.getInt("like_count"));
            note.setLasttime(rs.getDate("lasttime"));
            list.add(note);
        }
        return list;
    }

    public static Note to_note(ResultSet rs) throws SQLException {
        Note note = new Note();
        note.setNoteid(rs.getInt("noteid"));
        note.setAuthor_id(rs.getInt("author_id"));
        note.setAuthor(rs.getString("author"));
        note.setTitle(rs.getString("title"));
        note.setText(rs.getString("text"));
        note.setLike_count(rs.getInt("like_count"));
        note.setAccess(rs.getString("access"));
        note.setBlacklist(rs.getString("blacklist"));
        note.setLasttime(rs.getDate("lasttime"));
        return note;
    }
    public static List<Group1Bean> to_group1list(ResultSet rs) throws SQLException {
        List<Group1Bean> list=new ArrayList<>();
        while (rs.next()) {
            Group1Bean group1=new Group1Bean();
            group1.setId(rs.getInt(1));
            group1.setGroup1_name(rs.getString(2));
            group1.setAuthor_id(rs.getInt(3));
            list.add(group1);
        }
        return list;
    }
    public static List<Group1Bean> to_groupnote(ResultSet rs) throws SQLException {
        List<Group1Bean> list=new ArrayList<>();
        while (rs.next()) {
            Group1Bean group1=new Group1Bean();
            group1.setId(rs.getInt(1));
            group1.setGroup1_name(rs.getString(2));
            group1.setAuthor_id(rs.getInt(3));
            list.add(group1);
        }
        return list;
    }
    public static List<Group2Bean> to_group2list(ResultSet rs) throws SQLException {
        List<Group2Bean> list=new ArrayList<>();
        while (rs.next()) {
            Group2Bean group2=new Group2Bean();
            group2.setId(rs.getInt(1));
            group2.setGroup_name(rs.getString(2));
            group2.setGroup1_id(rs.getInt(3));
            list.add(group2);
        }
        return list;
    }
    public static List<MessageBean> to_announce(ResultSet rs) throws SQLException {
        List<MessageBean> list=new ArrayList<>();
        while (rs.next()) {
            MessageBean message=new MessageBean();
            message.setId(rs.getInt("id"));
            message.setAuthor(rs.getString("author"));
            message.setAuthor_id(rs.getInt("author_id"));
            message.setTitle(rs.getString("title"));
            message.setContext(rs.getString("context"));
            message.setTime(rs.getDate("time"));
            list.add(message);
        }
        return list;
    }
    public static List<MessageBean> to_comment(ResultSet rs) throws SQLException {
        List<MessageBean> list=new ArrayList<>();
        while (rs.next()) {
            MessageBean message=new MessageBean();
            message.setId(rs.getInt("id"));
            message.setAuthor(rs.getString("author"));
            message.setAuthor_id(rs.getInt("author_id"));
            message.setContext(rs.getString("context"));
            message.setTime(rs.getDate("time"));
            list.add(message);
        }
        return list;
    }
}
