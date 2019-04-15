import greenfoot.*;

public class EndStats extends Actor {
    // Long because is has to store time in milliseconds
    private long timer_start;
    private long timer_current;
    
    // Int seconds to wait
    private int wait_time;
    
    // Temp var starts with "_"
    public EndStats(int _wait_time) {
        getImage().setTransparency(0);
        timer_start = System.currentTimeMillis();
        wait_time = _wait_time;
        
        // Sorry (magic numbers)
        setLocation(375, 200);
    }
    
    public void act() {
        timer_current = System.currentTimeMillis();
        
        if (timer_current > timer_start + 1000 * wait_time) {
            getImage().setTransparency(255);
            Greenfoot.stop();
        }
    }    
}
