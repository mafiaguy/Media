/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amedia;


import java.io.File;
import java.net.URL;

import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author Garvit
 */
public class FXMLDocumentController implements Initializable {
    
    private MediaPlayer mediaPlayer;
    
    @FXML
    private Slider s;
    
    @FXML
    private Slider seekslider;
    
    @FXML
    private MediaView mediaview;
    
    
    private String filepath=null;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        FileChooser.ExtensionFilter filter= new FileChooser.ExtensionFilter("Select a file(*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file=fileChooser.showOpenDialog(null);
        filepath=file.toURI().toString();
        
        if(filepath!=null){
            Media media=new Media(filepath);
            mediaPlayer=new MediaPlayer(media);
            mediaview.setMediaPlayer(mediaPlayer);
                DoubleProperty width=mediaview.fitWidthProperty();
                DoubleProperty height=mediaview.fitHeightProperty();
            width.bind(Bindings.selectDouble(mediaview.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaview.sceneProperty(), "height"));
            
            s.setValue(mediaPlayer.getVolume()*100);
            s.valueProperty().addListener(new InvalidationListener(){

                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(s.getValue()/100);
                }
            });
            
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                     seekslider.setValue(newValue.toSeconds());
                }
                
                });
            
                seekslider.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(seekslider.getValue()));
                    mediaPlayer.play();
                }   
                });
            
            mediaPlayer.play();
        }
    }
    
    @FXML
    private void pauseVideo(ActionEvent e){
        mediaPlayer.pause();
    }
    
     @FXML
    private void playVideo(ActionEvent e){
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    
     @FXML
    private void stopVideo(ActionEvent e){
        mediaPlayer.stop();
    }
    
     @FXML
    private void fastVideo(ActionEvent e){
        mediaPlayer.setRate(1.5);
    }
    
     @FXML
    private void fasterVideo(ActionEvent e){
        mediaPlayer.setRate(2);
    }
    
     @FXML
    private void slowVideo(ActionEvent e){
        mediaPlayer.setRate(0.75);
    }
    
     @FXML
    private void slowerVideo(ActionEvent e){
        mediaPlayer.setRate(0.5);
    }
    
     @FXML
    private void exitVideo(ActionEvent e){
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}