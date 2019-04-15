import greenfoot.*;

public class Background extends World {
    // Boolean variable declaration, misc uses
    // debug_mode is to be set as true if testing
    private boolean game_over = false;
    private boolean debug_mode = false;
    private boolean kidnapped = false;
    private boolean drunk_offer = false;
    private boolean can_go = false;
    private boolean mainmenu_displayed = true;
    private boolean help_displayed = false;
    private boolean offer_type = false;
    private boolean cur_on_pattern = false;
    private boolean[][] patterns = {
    {true, true, false, true, false, false},
    {false, true, true, false, false, true},
    {false, true, false, true, true, false}};
    // All of these functions are boolean because
    // -  then are being used to remember what
    // - game events are happening anda what events
    // - are not happening

    // patterns is a 2d boolean array, true represents
    // - a beneficial drink while false represent malicious,
    // - i chose to use booleans instead of integers because
    // - 1: it is easier for a human to read rather than a series
    // - of 1's 2's and 3's, and 2: because it was easier to implement

    // "GreenfootSound" variable declarations, used for SFX 
    private GreenfootSound BACKGROUND_OST = new GreenfootSound("ost/OST_1.wav");
    private GreenfootSound CUP_DOWN = new GreenfootSound("cup_sfx/PLACE.wav");
    private GreenfootSound SLIDE_L = new GreenfootSound("cup_sfx/SLIDE_L.wav");
    private GreenfootSound SLIDE_R = new GreenfootSound("cup_sfx/SLIDE_R.wav");
    private GreenfootSound WIN_SOUND = new GreenfootSound("result_sound/WIN.wav");
    private GreenfootSound LOSE_SOUND = new GreenfootSound("result_sound/LOSE.wav");
    private GreenfootSound SWALLOW = new GreenfootSound("human_sfx/DRINK_1.wav");
    private GreenfootSound RETCH = new GreenfootSound("human_sfx/RETCH_1.wav");
    private GreenfootSound REJECT = new GreenfootSound("human_sfx/NO_1.wav");
    private GreenfootSound KIDNAP = new GreenfootSound("result_sound/DOOR_SQUEAK.wav");
    // The only way i know how to use dound in java in to use
    // - the class GreenfootSound, these variabled are all in
    // - capitals because they are constants/do not change

    // Integer variable declarations
    private int points = 5;
    private int pattern_index = 0;
    private int pattern_loc = 0;
    private int amount_of_patterns = patterns.length;
    
    private long mainmenu_timer_start;
    private long mainmenu_timer_current;

    // String variable declarations
    private String str_drink_type = "";
    private String[] poten_drink_names = 
    {"?","Tikila Mockingbird","The Good Stuff","Just A Drink",
    "Vodka Cocktail???","Generic Alcohol","Limonchello Maybe","Bobbys Birthday","''Club Soda''",
    "Judes Juice","Funnell Fun","Potato Extract","Bitter Rainbow","Sawyer Sauce","Matty Martini",
    "Brandon Brandy"};
    
    // Instantiating "Offering" class called cur_offer
    private Offering cur_offer;
    
    // Instantiating "Help" class called cur_offer
    private Help help_display;
    
    // Instantiating "Indicator" class called cur_offer
    private Indicator my_indicator;
    
    // Instantiating "Tag" class called cur_offer
    private Tag label;
    
    // Instatiating "MainMenu" class called welcome_menu
    private MainMenu welcome_menu;

    // This class constructor (called when the class in instantiated)
    // - contains world construction code
    public Background() {   
        super(750, 400, 1, false);
        // Setting the volumes of SFX
        BACKGROUND_OST.setVolume(40);
        CUP_DOWN.setVolume(80);
        SLIDE_L.setVolume(90);
        SLIDE_R.setVolume(90);
        WIN_SOUND.setVolume(80);
        LOSE_SOUND.setVolume(80);
        SWALLOW.setVolume(70);
        RETCH.setVolume(90);
        REJECT.setVolume(100);
        // Most of the volumes are different because 1: the audio
        // - volume is different on most clips and 2: because some
        // - sounds need to be louder than others
        
        // Instantiating custom classes
        table propTable = new table();
        acceptArrow aArrow = new acceptArrow();
        refuseArrow rArrow = new refuseArrow();
        my_indicator = new Indicator();
        
        // Adding the custom classes to the world
        addObject(propTable, getWidth() / 2, 200);
        addObject(aArrow, 100, 80);
        addObject(rArrow, 650, 80);
        addObject(my_indicator, getWidth() / 2, 20);
        
        // Calls the GenerateNextOffer function to generate the first
        // - offer
        GenerateNextOffer();
        
        // If the program is in debug mode print out cur_offer details
        if (debug_mode) {
            DebugPrint();
        }   
        
        // Adds the first offer to the world
        addObject(cur_offer, getWidth() / 2, 200);
        
        // Make a new label with the cur_offer's name
        label = new Tag(cur_offer.name, 200, 50);
        addObject(label, getWidth() / 2 - 40, 100);
        
        // Make the main menu
        welcome_menu = new MainMenu();
        addObject(welcome_menu, getWidth() / 2, getHeight() / 2);
        
        setPaintOrder(EndStats.class, Kidnapped.class);
    }
    
    // This function is to generate the next offer. It
    // - is a function because it needs to be called alot
    private void GenerateNextOffer() {
        // If the user is not on a pattern currently or they are
        // - just finishing the pattern then this code runs
        if (!cur_on_pattern || cur_on_pattern && pattern_loc > 5) {
            // This segment selects a random new pattern to start
            cur_on_pattern = true;
            pattern_index = Greenfoot.getRandomNumber(amount_of_patterns);
            pattern_loc = 0;
        }
        
        // The patterns array is formatted in boolean, therefore
        // - i am assigning the offer_type as the selected index.
        offer_type = patterns[pattern_index][pattern_loc];

        // If the offer_type is false, or malicious, then it is
        // - assigned to be a Kidnap drink (8% chance) or a 
        // - regular Malicious drink.
        if(!offer_type) {
            if(Greenfoot.getRandomNumber(100) < 8) {
                str_drink_type = "KID";
            } else {
                str_drink_type = "MAL";
            }
        } else { // However, if offer_type is true it is made a beneficial drink
            str_drink_type = "BEN";
        }

        // The new offer is now assigned and the next segment in
        // - the pattern is prepared. The user can now go
        cur_offer = new Offering(poten_drink_names[Greenfoot.getRandomNumber(poten_drink_names.length)],
        str_drink_type, "CUP" + Greenfoot.getRandomNumber(7) + ".png");

        pattern_loc++;
        can_go = true;
    }
    
    // A function to print out information about the current
    // - offer
    private void DebugPrint() {
        System.out.println(cur_offer.name);
        System.out.println(cur_offer.type + "\n");
        
        System.out.println(points);
    }

    // This function is to update the Red/Green indicator at the top
    // - of the world, which is used to inform the user when they are 
    // - potentially in danger
    private void UpdateIndicator() {
        // If the points are above of equal to 5 then its green, else
        // - it is red
        if (points >= 5) {
            my_indicator.setImage("BENpoints.png");
        } else if (points < 5) {
            my_indicator.setImage("MALpoints.png");
        }
        
        // If they get more than ten point the win sequence is run,
        // - this includes a sound and an image
        if (points > 10) {
            BACKGROUND_OST.stop();
            WIN_SOUND.play();
            Gameover youWin = new Gameover();
            youWin.setImage("youwin.png");
            addObject(youWin, getWidth() / 2, getHeight() / 2);
        } else if (points < 0) {
        // Otherwise, if there points is less than 0 and they didn't
        // - get kidnapped, the losing sequence is run, graphics and 
        // - sound
            if (!kidnapped) {
                BACKGROUND_OST.stop();
                LOSE_SOUND.play();
                Gameover youLose = new Gameover();
                youLose.setImage("youlose.png");
                addObject(youLose, getWidth() / 2, getHeight() / 2);
            }
        }
    }

    // This function is called when the player gets kidnapped, it plays
    // - SFX and shows a black screen, the background music stops
    private void GetKidnapped() {
        kidnapped = true;
        removeObject(cur_offer);
        BACKGROUND_OST.stop();

        KIDNAP.play();
        addObject(new BlackScreen(), getWidth() / 2, getHeight() / 2);
    }
    
    // act() is called every time the game engine iterates/refreshes
    // -  i am using it to detect input
    public void act() {
        if (game_over) {
            return;
        }
        
        // If the boolean value kidnapped is true act() is returned
        // - immediately
        if (kidnapped) {
            return;
        }
        
        if (mainmenu_displayed) {
            if (Greenfoot.isKeyDown("enter")) {
                removeObject(welcome_menu);
                help_display = new Help();
                addObject(help_display, getWidth() / 2, getHeight() / 2);
                
                mainmenu_displayed = false;
                help_displayed = true;
                
                mainmenu_timer_start = System.currentTimeMillis();
            }
            return;
        }
        
        // If the help page is displayed and the user has not pressed
        // - enter then act is escaped, if they have pressed enter
        // - it removes the help display
        if (help_displayed) {
            mainmenu_timer_current = System.currentTimeMillis();
            if (Greenfoot.isKeyDown("enter") && mainmenu_timer_current > mainmenu_timer_start + 1500) {
                removeObject(help_display);
                BACKGROUND_OST.playLoop();
                
                help_displayed = false;
            }
            return;
        }
        
        // The controls, if there is a current offer and the program is
        // - waiting for user input. If the user enters left they have
        // - accepted the offer, the drink slides left and the user input
        // - is ignored untill the program is ready again
        if (can_go && cur_offer.getWorld() != null && !help_displayed) {
            if (Greenfoot.isKeyDown("left")) {
                can_go = false;
                removeObject(label);
                drunk_offer = true;
                cur_offer.Slide(5, false, 90);
                SLIDE_L.play();

                // If the offer was malicious they lose 2 points
                if (cur_offer.type == "MAL") {
                    points -= 2;
                } else if (cur_offer.type == "BEN") {
                // If the offer was beneficial they gain 1 point
                    points++;
                }

                // The red/green indicator is updated
                UpdateIndicator();
            } else if (Greenfoot.isKeyDown("right")) { // If they press right they have denied the drink
                // Play the REJECT sound and ignore input
                REJECT.play();
                can_go = false;
                
                // Slides the offer away and if the offer is beneficial
                // - lose one point
                removeObject(label);
                cur_offer.Slide(5, true, 90);
                SLIDE_R.play();

                if (cur_offer.type == "BEN") {
                    points--;
                }

                // Update red/green indicator
                UpdateIndicator();
            }
        }
        
        // If the current offer is off screen the reaction sound effect is
        // - played and the object is removed, (sorry for magic numbers)
        if (cur_offer.getX() > 824 || cur_offer.getX() < -74) {
            if (cur_offer.type == "BEN" && drunk_offer) {
                SWALLOW.play();
            } else if (cur_offer.type == "MAL" && drunk_offer) {
                RETCH.play();
            }
            
            removeObject(cur_offer);
            
            // If they drink a kidnap drink the kidnapped function is called
            if (cur_offer.type == "KID" && drunk_offer) {
                GetKidnapped();
                kidnapped = true;
                game_over = true;
                addObject(new EndStats(6), getWidth() / 2, getHeight() / 2);
            }
            
            // If they won or lost they the game stops
            if (points > 10 || points < 0) {
                addObject(new EndStats(2), getWidth() / 2, getHeight() / 2);
                game_over = true;
                return;
            }

            // Calls GenerateNextOffer()
            GenerateNextOffer();
            
            // If debug_mode is true it prints debug information
            if (debug_mode) {
                DebugPrint();
            }  
            
            // If you didn't get kidnapped it spawns the new offer and
            // - adds the label and plays SFX
            if (!kidnapped) {
                addObject(cur_offer, getWidth() / 2, 200);
                CUP_DOWN.play();
            
                label = new Tag(cur_offer.name, 200, 50);
                addObject(label, getWidth() / 2 - 42, 100);
                drunk_offer = false;
            }
        }
    }
}
