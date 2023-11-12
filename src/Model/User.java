package Model;

import Helper.DBConnector;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String uname;
    private String pass;
    private String type;

    public User(){}

    public User(int id, String name, String uname, String pass, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> getList(){
        ArrayList<User>userList=new ArrayList<>();
        String query="SELECT * FROM user";
        User obj;
        try  {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj =new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("Name"));
                obj.setUname(rs.getString("Uname"));
                obj.setPass(rs.getString("Pass"));
                obj.setType(rs.getString("Type"));
                userList.add(obj);
            }
            rs.close();
            st.close();
        } catch (SQLException throwables) {
            throw new RuntimeException();
        }
        return userList;
    }
    public static boolean add(String name,String uname,String pass,String type){
        String query ="INSERT INTO user(name,uname,pass,type) VALUES(?,?,?,?)";
        User findUser=User.getFetch(uname);
        if (findUser !=null){
            Helper.showMsg("Bu kullanıcı adı daha önceden eklenmiş.\nLütfen farklı bir kullanıcı adı giriniz.");
            return false;
        }
        boolean key=true;
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);
            //pr.close();
            int response= pr.executeUpdate();
            if (response==-1){
                Helper.showMsg("error");
            }
            key= response !=-1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return key;
    }
    public static User getFetch(String uname){
        User obj =null;
        String  query="SELECT *FROM user WHERE uname=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,uname);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj =new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("Name"));
                obj.setUname(rs.getString("Uname"));
                obj.setPass(rs.getString("Pass"));
                obj.setType(rs.getString("Type"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static User getFetch(String uname,String pass){
        User obj =null;
        String  query="SELECT *FROM user WHERE uname=? AND pass=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,uname);
            pr.setString(2,pass);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                switch (rs.getString("type")){
                    case "operator":
                        obj=new Admin();
                        break;
                    default:
                        obj =new User();
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("Name"));
                obj.setUname(rs.getString("Uname"));
                obj.setPass(rs.getString("Pass"));
                obj.setType(rs.getString("Type"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static User getFetch(int id ){
        User obj =null;
        String  query="SELECT * FROM user WHERE id=?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();
            if (rs.next()){
                obj =new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("Name"));
                obj.setUname(rs.getString("Uname"));
                obj.setPass(rs.getString("Pass"));
                obj.setType(rs.getString("Type"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
    public static boolean delete(int id){
        String query = "DELETE FROM user WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean update(int id,String name ,String uname,String pass,String type){
        String query="UPDATE user SET name=?,uname=?,pass=?,type=? WHERE id=?";
        User findUser=User.getFetch(uname);
        if (findUser !=null && findUser.getId() != id){
            Helper.showMsg("Bu kullanıcı adı daha önceden eklenmiş.\nLütfen farklı bir kullanıcı adı giriniz.");
            return false;
        }
        try {
            PreparedStatement pr =DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate()!=-1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    public static ArrayList<User> searchUserList(String query) {
        ArrayList<User> userList = new ArrayList<>();
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("Name"));
                obj.setUname(rs.getString("Uname"));
                obj.setPass(rs.getString("Pass"));
                obj.setType(rs.getString("Type"));
                userList.add(obj);
            }
            rs.close();
            st.close();
        } catch (SQLException throwables) {
            throw new RuntimeException();
        }
        return userList;
    }
    public static String searchQuery(String name,String uname,String type){
        String query="SELECT * FROM user WHERE uname LIKE '%{{uname}}%' AND name LIKE '%{{name}}%'";
        query=query.replace("{{name}}",name);
        query=query.replace("{{uname}}",uname);
        if (!type.isEmpty()){
            query +="AND type='{{type}}'";
            query=query.replace("{{type}}",type);
        }
        System.out.println(query);
        return query;
    }

}
