package View;

import Helper.Helper;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HotelGUI extends JFrame{
    private JPanel wrapperr;
    private JPanel pnl_hotel_add_top;
    private JPanel pnl_hotel_add_left;
    private JTextField fld_hotel_name;
    private JComboBox cmb_hotel_star;
    private JTextArea txtarea_hotel_feat;
    private JTextArea txtarea_hotel_address;
    private JTextField fld_hotel_phone;
    private JTextField fld_hotel_email;
    private JPanel pnl_hotel_add_right;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private JRadioButton radioButton7;
    private JTextField fld_hotel_season_start1;
    private JTextField fld_hotel_season_end1;
    private JTextField fld_hotel_season_start2;
    private JTextField fld_hotel_season_end2;
    private JButton btn_hotel_add;
    private JPanel wrapper;
    private JLabel lbl_welcome;

    private DefaultTableModel mdl_tbl_list;
    private Object [] row_user_list;
    private final Agent agent;
    private String select_star;
    private int added_hotel_id;

    public HotelGUI(Agent agent){
        this.agent=agent;
        add(wrapperr);
        setSize(800,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        radioButton1.setText(Helper.hotelType("1"));
        radioButton2.setText(Helper.hotelType("2"));
        radioButton3.setText(Helper.hotelType("3"));
        radioButton4.setText(Helper.hotelType("4"));
        radioButton5.setText(Helper.hotelType("5"));
        radioButton6.setText(Helper.hotelType("6"));
        radioButton7.setText(Helper.hotelType("7"));

        //lbl_welcome.setText("Hoşgeldiniz");
        mdl_tbl_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0){
                    return false;
                }
                return super.isCellEditable(row,column);

            }
        };
        Object[] col_hotel_list={"ID","Adı","Adres","Mail","Telefon","Özellikler","Yıldız"};
        mdl_tbl_list.setColumnIdentifiers(col_hotel_list);
        row_user_list=new Object[col_hotel_list.length];

        select_star = cmb_hotel_star.getSelectedItem().toString();



        btn_hotel_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hotel_name) || Helper.isAreaEmpty(txtarea_hotel_feat) ||
                    Helper.isAreaEmpty(txtarea_hotel_address) || Helper.isFieldEmpty(fld_hotel_phone) ||
                    Helper.isFieldEmpty(fld_hotel_email) ||
                    (!radioButton1.isSelected() && !radioButton2.isSelected() && !radioButton3.isSelected() &&
                            !radioButton4.isSelected() && !radioButton5.isSelected() && !radioButton6.isSelected() &&
                            !radioButton7.isSelected()) ||
                    Helper.isFieldEmpty(fld_hotel_season_start1) || Helper.isFieldEmpty(fld_hotel_season_end1)) {
                Helper.showMsg("fill");
            }
            else {
                String name = fld_hotel_name.getText();
                String star = (String) cmb_hotel_star.getSelectedItem();
                String feat = txtarea_hotel_feat.getText();
                String address = txtarea_hotel_address.getText();
                String phone = fld_hotel_phone.getText();
                String email = fld_hotel_email.getText();
                String season_start1 = fld_hotel_season_start1.getText();
                String season_end1 = fld_hotel_season_end1.getText();
                String season_start2 = fld_hotel_season_start2.getText();
                String season_end2 = fld_hotel_season_end2.getText();

                if (Hotel.add(name, star, feat ,address, phone, email)){
                    Hotel addedHotel = Hotel.getFetch(email);
                    added_hotel_id = addedHotel.getId();

                    for (int i = 1; i<=7; i++){  //hotel feat ekleme
                        switch (i){
                            case 1:
                                if (radioButton1.isSelected()){
                                    HotelType.add(radioButton1.getText(), added_hotel_id);
                                }
                                break;
                            case 2:
                                if (radioButton2.isSelected()){
                                    HotelType.add(radioButton2.getText(), added_hotel_id);
                                }
                                break;
                            case 3:
                                if (radioButton3.isSelected()){
                                    HotelType.add(radioButton3.getText(), added_hotel_id);
                                }
                                break;
                            case 4:
                                if (radioButton4.isSelected()){
                                    HotelType.add(radioButton4.getText(), added_hotel_id);
                                }
                                break;
                            case 5:
                                if (radioButton5.isSelected()){
                                    HotelType.add(radioButton5.getText(), added_hotel_id);
                                }
                                break;
                            case 6:
                                if (radioButton6.isSelected()){
                                    HotelType.add(radioButton6.getText(), added_hotel_id);
                                }
                                break;
                            case 7:
                                if (radioButton7.isSelected()){
                                    HotelType.add(radioButton7.getText(), added_hotel_id);
                                }
                                break;
                        }
                    }

                    HotelSeason.add(season_start1, season_end1, added_hotel_id);

                    if (!Helper.isFieldEmpty(fld_hotel_season_start2) && !Helper.isFieldEmpty(fld_hotel_season_end2)){
                        HotelSeason.add(season_start2,season_end2,added_hotel_id);
                    }

                    Helper.showMsg("done");
                    fld_hotel_name.setText(null);
                    cmb_hotel_star.setSelectedIndex(0);
                    txtarea_hotel_feat.setText(null);
                    txtarea_hotel_address.setText(null);
                    fld_hotel_phone.setText(null);
                    fld_hotel_email.setText(null);
                    fld_hotel_season_start1.setText(null);
                    fld_hotel_season_end1.setText(null);
                    fld_hotel_season_start2.setText(null);
                    fld_hotel_season_end2.setText(null);
                    radioButton1.setSelected(false);
                    radioButton2.setSelected(false);
                    radioButton3.setSelected(false);
                    radioButton4.setSelected(false);
                    radioButton5.setSelected(false);
                    radioButton6.setSelected(false);
                    radioButton6.setSelected(false);
                }
            }

        });
    }
}
