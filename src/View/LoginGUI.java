package View;

import Helper.Helper;
import Model.Admin;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_user_uname;
    private JPasswordField fld_user_pass;
    private JButton btn_user_login;

    public LoginGUI(){
        add(wrapper);

        setSize(400,400);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        btn_user_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_user_uname)||Helper.isFieldEmpty(fld_user_pass)){
                    Helper.showMsg("fill");
                }else {
                    User u = User.getFetch(fld_user_uname.getText(), fld_user_pass.getText());
                    if (u == null) {
                        Helper.showMsg("Kullanıcı Bulunamadı");
                    } else {
                        switch (u.getType()) {
                            case "admin":
                                AdminGUI opGUI = new AdminGUI();
                                break;
                            case "agent":
                                AgentGUI agGUI = new AgentGUI();
                                break;
                        }
                        dispose();
                    }
                }
            }
        });
    }


    public static void main(String[] args) {
        LoginGUI log =new LoginGUI();
    }
}
