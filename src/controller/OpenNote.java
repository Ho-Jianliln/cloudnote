package controller;

import dao.NoteDao;
import entity.Note;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.util.Callback;
import util.SplitString;

import java.sql.SQLException;
import java.util.List;

public class OpenNote extends Maincontroller{

    @FXML
    private Pagination pagination;

    @FXML
    private Label title_content;

    private Note note1=new Note();

    public void initialize() throws SQLException {
        NoteDao notedao=new NoteDao();
        if(choice ==1) note1=notedao.opennote(mynote.getNoteid());//打开自己的笔记
        System.out.println(mynote.getTitle());
        if(choice ==2) note1=notedao.opennote(sharenote.getNoteid());//打开分享空间的笔记
        pagination.setPageCount((note1.getText().length()/850+1));//设置页数
        title_content.setText(note1.getTitle()+"       "+note1.getAuthor()+"       获赞:"+note1.getLike_count()+"      "+note1.getLasttime());
        List<String> content=SplitString.getStrList(note1.getText(),850);//分割笔记内容，每段850字
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                TextArea text=new TextArea(content.get(param));//根据选择的页数显示内容
                text.setFont(Font.font(18));
                return text;
            }
        });
        notedao.closeAll();
    }
}
