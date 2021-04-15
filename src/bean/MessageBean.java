package bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class MessageBean {//存储论坛评论和公告数据
    private SimpleIntegerProperty id=new SimpleIntegerProperty();
    private SimpleIntegerProperty author_id=new SimpleIntegerProperty();
    private SimpleStringProperty author=new SimpleStringProperty();
    private SimpleStringProperty title=new SimpleStringProperty();
    private SimpleStringProperty context=new SimpleStringProperty();
    private Date time;

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getAuthor_id() {
        return author_id.get();
    }

    public SimpleIntegerProperty author_idProperty() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id.set(author_id);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getContext() {
        return context.get();
    }

    public SimpleStringProperty contextProperty() {
        return context;
    }

    public void setContext(String context) {
        this.context.set(context);
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
