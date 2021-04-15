package service;

import dao.NoteDao;
import entity.Note;
import view.Alert;

import java.sql.SQLException;

public class SaveText {
    public static int addNote(Note note, int divideid) throws SQLException {
        if(note.getTitle()!=null&&note.getText()!=null){
            NoteDao notedao=new NoteDao();
            int i=0;
            if(divideid==0) Alert.send("分组不能为空");
            else i=notedao.addnote(note,divideid);
            notedao.closeAll();
            return i;
        }else{
            Alert.send("标题或内容不能为空");
        }
        return 0;
    }
    public static int modifiNote(Note note,int divideid) throws SQLException {
        if(note.getTitle()!=null&&note.getText()!=null){
            NoteDao notedao=new NoteDao();
            int i;
            if(divideid==0) i=notedao.modifinote2(note);
            else i=notedao.modifinote(note,divideid);
            notedao.closeAll();
            return i;
        }else{
            Alert.send("标题或内容不能为空");
        }
        return 0;
    }

}
