import greenfoot.*;

public class Offering extends Actor {
    // Variable declaration
    public String name;
    public String type;
    
    private boolean sleep_now = false;
    private boolean direction;

    private long start_time;
    private long cur_time;
    
    private int speed;
    private int iterations;

    // Class constructor that receives _name, _type and _Image as strings
    // - when it is instantiated
    public Offering(String _name, String _type, String _image) {
        name = _name;
        type = _type;
        setImage(_image);
    }
    
    // Receive the specifications for sliding the cup off the screen. _speed
    // - determines the pixels per act, _direction is a boolean left = false and
    // - right = true, iterations is the amount of times to do a move cycle
    public void Slide(int _speed, boolean _direction, int _iterations) {
        sleep_now = true;
        start_time = System.currentTimeMillis();
        direction = _direction;
        speed = _speed;
        iterations = _iterations;
    }
    
    // If sleep_now is true then a timer is started, this timer is also responsible
    // - for moving the cup. If all the iterations have run sleep_now becomes false
    public void act() {
        if (sleep_now) {
            cur_time = System.currentTimeMillis();
            
            if (cur_time >= 10 + start_time) {
                if (direction) {
                    move(speed);
                } else if (!direction) {
                    move(-speed);
                }
                
                start_time = System.currentTimeMillis();
                iterations--;
            }
            
            if (iterations <= 0) {
                sleep_now = false;
            }
        }
    }
}
