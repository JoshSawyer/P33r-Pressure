import greenfoot.*;

public class BlackScreen extends Actor {
    // Variable declaration
    private long start_time;
    private long cur_time;
    
    private boolean complete = false;
    
    private GreenfootSound DOOR_SHUT = new GreenfootSound("result_sound/DOOR_SHUT.wav");
    
    // When BlackScreen() is called start_time is assigned
    // - and the volume for an SFX is set to max
    public BlackScreen() {
        start_time = System.currentTimeMillis();
        DOOR_SHUT.setVolume(100);
    }
    
    // This function shows the kidnap label and plays
    // - SFX
    public void ShowKidnap() {
        Kidnapped kidnapLabel = new Kidnapped();
        getWorld().addObject(kidnapLabel, getWorld().getWidth() / 2, getWorld().getHeight() / 2);
        DOOR_SHUT.play();
    }
    
    // This contains a timer for showing the "kidnapped"
    // - text
    public void act()  {
        cur_time = System.currentTimeMillis();
        if (cur_time >= start_time + 4000 && !complete) {
            ShowKidnap();
            complete = true;
        }
    }    
}
