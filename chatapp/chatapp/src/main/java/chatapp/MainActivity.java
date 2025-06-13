package chatapp;

import utilities.PreferenceManager;
import utilities.Constants;
import utilities.AppCompatActivity;
import activites.Welcome;
import activites.HomeScreen;

public class MainActivity{
    public static void main(String[] args) {
    	PreferenceManager preferenceManager = new PreferenceManager(Constants.KEY_PREFERENCE_NAME);

    	String signedIn = preferenceManager.getString("KEY_IS_SIGNED_IN");
    	if ("true".equals(signedIn)) {
    	    HomeScreen homeScreen = new HomeScreen();
    	    homeScreen.onCreate();
    	} else {
    	    preferenceManager.putString("KEY_IS_SIGNED_IN", "false");
    	    Welcome welcome = new Welcome();
            welcome.onCreate();
    	}
    }
}
