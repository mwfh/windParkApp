package windparkfx.presentationmodel;

import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * @author Mario Wettstein
 */

public class HydroDataImagePM extends Node {
    // Fields
    private Image image;

    // Constructors
    public HydroDataImagePM(String imgUrl){
        try{
            setImage(new Image(imgUrl));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Getters & Setters
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }




}

