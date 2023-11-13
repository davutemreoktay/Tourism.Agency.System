package View;

import Helper.Helper;
import Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class AdminGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wrapper2;
    private JTabbedPane tab_operator;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton exitButton;
    private DefaultTableModel mdl_user_list;
    private Object [] row_user_list;
    public AdminGUI(){
        add(wrapper2);
        setSize(800,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldiniz");
        //ModelUserList
        mdl_user_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0){
                    return false;
                }
                 return super.isCellEditable(row,column);

            }
        };
        Object[] col_user_list={"ID","Ad Soyad","Kullanıcı Adı","Şifre","Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list=new Object[col_user_list.length];

        loadUserModel();
        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String selected_user_id=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString();
                fld_user_id.setText(selected_user_id);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }

        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType()== TableModelEvent.UPDATE){
                int user_id=Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                String user_name=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                String user_uname=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                String user_pass=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                String user_type=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString();

                if (User.update(user_id,user_name,user_uname,user_pass,user_type)){
                    Helper.showMsg("done");
                    loadUserModel();
                }else {
                    Helper.showMsg("error");
                }
            }
        });




        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name)||Helper.isFieldEmpty(fld_user_uname)||Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMsg("fill");
            }else {
                String name=fld_user_name.getText();
                String uname=fld_user_uname.getText();
                String pass=fld_user_pass.getText();
                String type=cmb_user_type.getSelectedItem().toString();
                if (User.add(name,uname,pass,type)){
                    Helper.showMsg("done");
                    loadUserModel();
                }else {
                    Helper.showMsg("error");
                }
                fld_user_name.setText(null);
                fld_user_uname.setText(null);
                fld_user_pass.setText(null);
            }


        });
        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)){
                Helper.showMsg("fill");
            }
            else {
                if (Helper.confirm("sure")){
                    int user_id=Integer.parseInt(fld_user_id.getText());
                    if (User.delete(user_id)){
                        Helper.showMsg("done");
                        loadUserModel();
                    }else {
                        Helper.showMsg("error");
                    }
                }
            }
        });
        btn_user_sh.addActionListener(e -> {
            String name=fld_user_name.getText();
            String uname=fld_user_uname.getText();
            String type=cmb_user_type.getSelectedItem().toString();
            String query=User.searchQuery(name,uname,type);
            ArrayList<User> searchingUser=User.searchUserList(query);
            //loadUserModel(User.searchUserList(query));

        });
        exitButton.addActionListener(e -> {
            dispose();
            LoginGUI logGUI = new LoginGUI();
        });

    }
    public void loadUserModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj: User.getList()){
            int i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUname();
            row_user_list[i++]=obj.getPass();
            row_user_list[i++]=obj.getType();
            mdl_user_list.addRow(row_user_list);
        }

    }

    public static void main(String[] args) {
        AdminGUI a =new AdminGUI();
    }
}
