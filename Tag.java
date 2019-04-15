import greenfoot.*; 

public class Tag extends Actor {
    // Class constructor for making a Tag
    public Tag(String msg, int x_size, int y_size) {
        GreenfootImage img = new GreenfootImage(x_size, y_size);
        img.drawString(msg, x_size / 2, y_size / 2);
        setImage(img);
    }
}
