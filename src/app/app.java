package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class app extends Application
{
    
    @Override
    public void start(Stage stage) throws Exception
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("/vista/Main.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Air Control 1 Click!");
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icono.png")));
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        launch(args);
    }
    
}