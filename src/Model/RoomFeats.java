package Model;

import Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomFeats {

    private int id;
    private String feat;
    private int room_id;
    private String bed;
    private int area;

    private Room room;

    public RoomFeats(int id, String feat, int room_id, String bed, int area) {
        this.id = id;
        this.feat = feat;
        this.room_id = room_id;
        this.bed = bed;
        this.area = area;
        this.room = Room.getFetch(room_id);
    }

    public RoomFeats() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeat() {
        return feat;
    }

    public void setFeat(String feat) {
        this.feat = feat;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    //oda özellilerini oda id sine db den alan metod
    public static ArrayList<RoomFeats> getListByRoomID( int id){
        ArrayList<RoomFeats> roomFeatsList = new ArrayList<>();
        RoomFeats obj;
        String query = "SELECT * FROM room_feats WHERE room_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new RoomFeats();
                obj.setFeat(rs.getString("feat"));
                obj.setBed(rs.getString("bed"));
                obj.setArea(rs.getInt("area"));
                roomFeatsList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomFeatsList;
    }

    public static boolean add(String feat, int room_id, String bed, int area){
        String query = "INSERT INTO room_feats (feat, room_id, bed, area ) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,feat);
            pr.setInt(2,room_id);
            pr.setString(3, bed);
            pr.setInt(4, area);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static RoomFeats getFetch(int id) {
        RoomFeats obj = null;
        String query = "SELECT * FROM room_feats WHERE room_id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new RoomFeats(rs.getInt("id"), rs.getString("feat"), rs.getInt("room_id"), rs.getString("bed"), rs.getInt("area"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }





}