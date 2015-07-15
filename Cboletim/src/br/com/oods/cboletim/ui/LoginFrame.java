/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.oods.cboletim.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
 
public class LoginFrame extends JFrame implements ActionListener
{

    static void SetVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    JLabel l1, l2, l3;
    JTextField tf1;
    JButton btn1;
    JPasswordField p1;
 
    LoginFrame()
    {
        setTitle("Login ");
        setVisible(true);
        setSize(700, 200);
        setLayout(null);
        
 
        l1 = new JLabel("Login CBOLETIM:");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));
 
        l2 = new JLabel("Login:");
        l3 = new JLabel("Senha:");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        btn1 = new JButton("Logar");
 
        l1.setBounds(100, 30, 400, 30);
        l2.setBounds(50, 60, 200, 30);
        l3.setBounds(50, 110, 200, 30);
        tf1.setBounds(300, 60, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        btn1.setBounds(150, 160, 100, 30);
 
        add(l1);
        add(l2);
        add(tf1);
        add(l3);
        add(p1);
        add(btn1);
        btn1.addActionListener(this);
    }
 
    public void actionPerformed(ActionEvent e)//ação do botão login
    {
        showData();//chama função correspondente
    }
 
    public void showData()
    {
        JFrame f1 = new JFrame();//instacia do Jframe
        JLabel l, l0;
 
        String str1 = tf1.getText();
        char[] p = p1.getPassword();
        String str2 = new String(p);
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");//driver oracle
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "cboletim", "cboletim");//banco, login e senha como parametro
            PreparedStatement ps = con.prepareStatement("select * from pessoa where login=? and senha=?");
            ps.setString(1, str1);
            ps.setString(2, str2);
            ResultSet rs = ps.executeQuery();//executa o preparedstatement no banco
            if (rs.next()) //se a condição for bem sucedidada
            {
                               
                PrincipalFrame frame = new PrincipalFrame(); //instancia o menu principal
                frame.setVisible(true);//torna visivel o menu principal
                this.dispose();//formulario de login é apagado
                
 
            } else///se a condição não for bem sucedida
            {
                JOptionPane.showMessageDialog(null,
                   "Usuário ou senha incorreta");//mensagem de erro
            }
        }
        catch (Exception ex)//erro de exceção
        {
            System.out.println(ex);
        }
    }
 
    public static void main(String arr[])//menu principal do projeto
    {
        new LoginFrame();//chama o form de login
    }
}
