package bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Group1Bean {//用于存储树形图的知识库数据
    private SimpleIntegerProperty id =new SimpleIntegerProperty();
    private SimpleStringProperty group1_name =new SimpleStringProperty();
    private SimpleIntegerProperty author_id =new SimpleIntegerProperty();
    public List<Group2Bean> g2list=new ArrayList<>();

    public Group1Bean(int id, String group1_name, int author_id) {
        setId(id);
        setGroup1_name(group1_name);
        setAuthor_id(author_id);
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getGroup1_name() {
        return group1_name.get();
    }

    public SimpleStringProperty group1_nameProperty() {
        return group1_name;
    }

    public void setGroup1_name(String group1_name) {
        this.group1_name.set(group1_name);
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

    public List<Group2Bean> getG2list() {
        return g2list;
    }

    public void setG2list(List<Group2Bean> g2list) {
        this.g2list = g2list;
    }

    public Group1Bean(){}

    public int getId() {
        return id.get();
    }
    public String toString(){
        return  getGroup1_name();
    }

}
