package trip.trip.com.worldcup;

import java.io.Serializable;

public class Celebrity implements Serializable {

    private String name;
    private String imagePath;

    public Celebrity(String name, String imagePath){
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName(){
        return name;
    }

    public String getImagePath(){
        return imagePath;
    }

}
