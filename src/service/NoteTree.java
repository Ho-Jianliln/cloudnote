package service;

import bean.Group1Bean;
import bean.Group2Bean;
import bean.NoteBean;
import controller.Maincontroller;
import dao.GroupDao;
import dao.NoteDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.List;

public class NoteTree extends Maincontroller {

    public TreeView<String> load(int userid,TreeView tv) throws SQLException {
        GroupDao gd=new GroupDao();
        NoteDao nd=new NoteDao();
        TreeItem<String> root =new TreeItem<>();//根节点
        List<Group1Bean> group1list= gd.findgroup1(userid);//分组数据
        for(Group1Bean g1:group1list){//搜索笔记分组，建立树状图
            TreeItem<String> ti1=new TreeItem<>();
            ti1.setValue(g1.getGroup1_name());
            g1.setG2list(gd.findgroup2(g1.getId()));
            for(Group2Bean g2:g1.getG2list()){
                TreeItem<String> ti2=new TreeItem<>();
                ti2.setValue(g2.getGroup_name());
                g2.setNotelist(nd.search2(userid, g2.getId()));
                for(NoteBean note:g2.getNotelist()){
                    TreeItem<String> tinote=new TreeItem<>();
                    tinote.setValue(note.getTitle());
                    ti2.getChildren().add(tinote);
                }
                ti2.setExpanded(true);
                ti1.getChildren().add(ti2);
            }
            ti1.setExpanded(true);
            root.getChildren().add(ti1);
        }
        tv.setShowRoot(false);//根节点隐藏
        tv.setRoot(root);
        tv.setEditable(true);
        tv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem>() {//监听treeview选择的项
            @Override
            public void changed(ObservableValue<? extends TreeItem> observable, TreeItem oldValue, TreeItem newValue) {
                if(tv.getTreeItemLevel(newValue)==2){//若选择组，记录所选的组
                    for(int i=0;i<group1list.size();i++){
                        for(int n=0;n<group1list.get(i).g2list.size();n++){
                            if(group1list.get(i).g2list.get(n).getGroup_name().equals(newValue.getValue())){
                                divide_group2id=group1list.get(i).g2list.get(n).getId();
                                break;
                            }
                        }
                    }
                }
                if(tv.getTreeItemLevel(newValue)==3){//若选择笔记，记录所选的笔记
                    for(int i=0;i<group1list.size();i++){
                        for(int n=0;n<group1list.get(i).g2list.size();n++){
                            for(int m=0;m<group1list.get(i).g2list.get(n).getNotelist().size();m++)
                                if(group1list.get(i).g2list.get(n).getNotelist().get(m).getTitle().equals(newValue.getValue())){
                                    mynote.setNoteid(group1list.get(i).g2list.get(n).getNotelist().get(m).getNoteid());
                                    break;
                            }
                        }
                    }
                }
            }
        });
        tv.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {//设置treeitem的样式treecell
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new NoteTreeCell(param,group1list);
            }
        });
        gd.closeAll();
        nd.closeAll();
        return tv;
    }
    private final class NoteTreeCell extends TreeCell<String> {//自定义treecell
        private TextField textField;
        private GroupDao gd=new GroupDao();
        private ContextMenu Menu = new ContextMenu();
        private ContextMenu Menu2 = new ContextMenu();
        public NoteTreeCell(TreeView<String> param, List<Group1Bean> group1list){
            MenuItem addMenuItem = new MenuItem("新增分组");
            MenuItem deleMenuItem = new MenuItem("删除分组");
            MenuItem addMenuItemChild =new MenuItem("新增子分组");
            addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {//对不同级别选项右键，显示不同的菜单（组不可新增子分组）
                if(event.getButton()==MouseButton.SECONDARY&&(param.getTreeItemLevel(getTreeItem()))==2){
                    Menu.getItems().add(addMenuItem);
                    Menu.getItems().add(deleMenuItem);
                    setContextMenu(Menu);
                }
                if(event.getButton()==MouseButton.SECONDARY&&(param.getTreeItemLevel(getTreeItem()))==1){
                    Menu2.getItems().add(addMenuItem);
                    Menu2.getItems().add(addMenuItemChild);
                    Menu2.getItems().add(deleMenuItem);
                    setContextMenu(Menu2);
                }
            });
            addMenuItem.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    TreeItem newGroup = new TreeItem<String>("新分组*请重新改名*");
                    getTreeItem().getParent().getChildren().add(newGroup);
                    int level=param.getTreeItemLevel(getTreeItem());
                    if(level==1){//添加知识库
                        try {
                            int newid=gd.addgroup1(user.getId(),"新分组*请重新改名*");
                            group1list.add(new Group1Bean(newid,"新分组*请重新改名*",user.getId()));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                    if(level==2){//在选中的分组旁边添加分组
                        for(int i=0;i<group1list.size();i++){
                            for(int n=0;n<group1list.get(i).getG2list().size();n++){
                                String g2name=group1list.get(i).getG2list().get(n).getGroup_name();
                                if(g2name.equals(getTreeItem().getValue())){
                                    try {
                                        int newid=gd.addgroup2(group1list.get(i).getId(), "新分组*请重新改名*");
                                        group1list.get(i).g2list.add(new Group2Bean(newid,"新分组*请重新改名*",group1list.get(i).getId()));
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            });
            deleMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int level= param.getTreeItemLevel(getTreeItem());
                    if(level==1){//删除知识库
                        for(int i=0;i<group1list.size();i++){
                            System.out.println(group1list.get(i).getGroup1_name());
                            if(group1list.get(i).getGroup1_name().equals(getTreeItem().getValue())){
                                int g1id=group1list.get(i).getId();
                                System.out.println("开删"+g1id);
                                gd.delegroup1(g1id);
                                System.out.println("删了");
                                group1list.remove(i);
                                break;
                            }
                        }
                    }
                    if(level==2){//删除分组
                        for(int i=0;i<group1list.size();i++){
                            for(int n=0;n<group1list.get(i).getG2list().size();n++){
                                if(group1list.get(i).g2list.get(n).getGroup_name().equals(getTreeItem().getValue())){
                                    System.out.println("开删");
                                    gd.delegroup2(group1list.get(i).g2list.get(n).getId());
                                    group1list.get(i).g2list.remove(group1list.get(i).g2list.get(n));
                                    break;
                                }
                            }
                        }
                    }
                    getTreeItem().getParent().getChildren().remove(getTreeItem());
                }
            });
            addMenuItemChild.setOnAction(new EventHandler<ActionEvent>() {//在选中的知识库下添加子分组
                @Override
                public void handle(ActionEvent event) {
                    TreeItem newGroup = new TreeItem<String>("新分组*请重新改名*");
                    getTreeItem().getChildren().add(newGroup);
                    for(int i=0;i<group1list.size();i++){
                        if(group1list.get(i).getGroup1_name().equals(getTreeItem().getValue())){
                            try {
                                int newid=gd.addgroup2(group1list.get(i).getId(),"新分组*请重新改名*");
                                group1list.get(i).g2list.add(new Group2Bean(newid,"新分组*请重新改名*",group1list.get(i).getId()));
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            });
            addEventHandler(KeyEvent.KEY_PRESSED,event -> {//监听分组改名
                if(event.getCode()==KeyCode.ENTER){
                    int level=param.getTreeItemLevel(getTreeItem());
                    if(level==1){//知识库改名
                        for(int i=0;i<group1list.size();i++){
                            if(group1list.get(i).getGroup1_name().equals(getTreeItem().getValue())){
                                gd.modifigroup1(user.getId(),textField.getText(),group1list.get(i).getId());
                                group1list.get(i).setGroup1_name(textField.getText());
                                break;
                            }
                        }
                    }
                    if(level==2){//分组改名
                        for(int i=0;i<group1list.size();i++){
                            for(int n=0;n<group1list.get(i).getG2list().size();n++){
                                if(group1list.get(i).getG2list().get(n).getGroup_name().equals(getTreeItem().getValue())){
                                    gd.modifigroup2(group1list.get(i).getG2list().get(n).getId(),textField.getText());
                                    group1list.get(i).g2list.get(n).setGroup_name(textField.getText());
                                    break;
                                }
                            }
                        }
                    }
                }
            });
            gd.closeAll();
        }
        public String toString(){
            return textField.getText();
        }
        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

}

