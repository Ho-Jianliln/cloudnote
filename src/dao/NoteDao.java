package dao;

import entity.Note;
import util.Resultset_tran;
import view.Alert;
import bean.NoteBean;

import java.sql.SQLException;
import java.util.List;

public class NoteDao extends BaseDao {
    public List<NoteBean> search(int author_id, String author, String title, String access) throws SQLException {
        String author2,title2;//搜索笔记，支持模糊搜索
        if(author.equals("")) author2="";
        else author2="%"+author+"%";
        if(title.equals("")) title2="";
        else title2="%"+title+"%";
        Object[] objects=new Object[]{author_id,author_id,author2,author,title2,title,access,access};
        String sql="select noteid,title,author,author_id,like_count,lasttime from note where"
                +"(author_id=? or ?=0)"+"and (author like ? or ?='')"+"and (title like ? or ?='')"+"and (access=? or ?='') order by lasttime desc";
        executeQuerySQL(sql,objects);
        return Resultset_tran.to_notelist(rs);
    }
    public List<NoteBean> search2(int userid, int group2id) throws SQLException {//根据分组搜索笔记
        executeQuerySQL("select noteid,author_id,author,title,like_count,lasttime,group2_id from note where author_id=? and group2_id=?",new Object[]{userid,group2id});
        return Resultset_tran.to_notelist(rs);
    }

    public int addnote(Note note,int divideid) throws SQLException {
        Object[] objects=new Object[]{note.getAuthor_id(),note.getAuthor(),note.getTitle(),note.getText(),note.getAccess(),divideid};
        executeQuerySQL("select noteid from note where author_id=? and title=?",new Object[]{note.getAuthor_id(),note.getTitle()});
        if(rs.next()){
            Alert.send("标题已经存在");
            return 0;
        }else{
            return executeUpdateSQL("insert into note (author_id,author,title,text,access,like_count,group2_id) values (?,?,?,?,?,0,?)",objects);
        }
    }
    public int delenote(int noteid){
        Object[] objects=new Object[]{noteid};
        return executeUpdateSQL("delete from note where noteid=?",objects);
    }
    public Note opennote(int noteid) throws SQLException {
        executeQuerySQL("select * from note where noteid=?",new Object[]{noteid});
        Note note=new Note();
        note.getResultset(rs);
        return note;
    }

    public int modifinote(Note note,int divideid){//修改笔记并重新分组
        return executeUpdateSQL("update note set title=?,text=?,access=?,group2_id=? where noteid=?",new Object[]{note.getTitle(),note.getText(),note.getAccess(),note.getNoteid(),divideid});
    }
    public int modifinote2(Note note){//修改笔记
        return executeUpdateSQL("update note set title=?,text=?,access=? where noteid=?",new Object[]{note.getTitle(),note.getText(),note.getAccess(),note.getNoteid()});
    }
}
