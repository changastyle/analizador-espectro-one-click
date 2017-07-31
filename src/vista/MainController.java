package vista;

import com.jcraft.jsch.Channel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Ubiquiti;

public class MainController 
{
    
   @FXML TextField txtIP;
   @FXML TextField txtUsuario;
   @FXML TextField txtPassword;
   @FXML Button btnConectar;

    
    @FXML public void initialize()
    {
        //System.out.println("arranco..");
        btnConectar.setDisable(true);
    }
    
    @FXML public void clickBtnConectar()
    {
        //System.out.println("Click! :  " +  txtIP.getText() + " | " + txtUsuario.getText() + " | "+ txtPassword.getText());
        
        
        String direccionIP = txtIP.getText();
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();
        
        //IP YA ESTÁ VERIFICADA
        System.out.println("direccionIP: " + direccionIP);
        if(usuario.trim().equalsIgnoreCase(""))
        {
            usuario = "root";
        }
        if(password.trim().equalsIgnoreCase(""))
        {
            password = "tecacc";
        }
        System.out.println("Me conectaré @ " + direccionIP + " | " + usuario + " | " + password);

        model.Ubiquiti ubiquiti = new Ubiquiti(direccionIP, "ubqt",  usuario, password);

        conectar(ubiquiti);

    }
    private static void conectar(Ubiquiti ubiquiti)
    {
        Channel canal = NETWORK.SSH.sshExec(ubiquiti.getDireccionIP(), 22 , ubiquiti.getUsuario(), ubiquiti.getPassword());
        NETWORK.SSH.enviarComando(canal, "/bin/sh /sbin/airview service");
        System.out.println("canal: " + canal);
        CMD.Consola.executar("java -jar airview.jar ubnt://"+ubiquiti.getDireccionIP()+":18888");
    }
    @FXML
    public boolean verificarFormatoIP()
    {
        boolean fallo = false;
        
        String ip = txtIP.getText();
        String ipSplited[] = ip.split(Pattern.quote("."));
        
        if( ip != null)
        {
            if( ipSplited.length  == 4)
            {
                for(int i = 0 ; i < ipSplited.length ; i++) 
                {
                    try
                    {
                        int numero = Integer.parseInt(ipSplited[i]);
                        if(numero < 0 || numero > 255)
                        {
                            fallo = true;
                        }
                    }
                    catch(Exception e)
                    {
                        fallo = true;
                    }
                    //System.out.println("" + ipSplited[i] + " | " + !fallo);
                }
            }
            else
            {
                fallo = true;
            }
        }
        
        if(fallo)
        {
            //btnConectar.setText("ERROR CON LA IP");
            btnConectar.setDisable(true);
        }
        else
        {
            btnConectar.setDisable(false);
            btnConectar.setText("Conectar con "+ip + "");
        }
        
        return !fallo;
    }
    
}
