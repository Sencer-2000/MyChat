package chatapp;

import chatapp.utilities.PreferenceManager;
import chatapp.utilities.Constants;
import chatapp.activities.Welcome;
import chatapp.activities.Home;

public class Main{
    public static void main(String[] args) {
    	PreferenceManager preferenceManager = new PreferenceManager(Constants.KEY_PREFERENCE_NAME);

    	String signedIn = preferenceManager.getString("KEY_IS_SIGNED_IN");
    	if ("true".equals(signedIn)) {
    	    Home home = new Home();
    	    home.onCreate();
    	} else {
    	    preferenceManager.putString("KEY_IS_SIGNED_IN", "false");
    	    Welcome welcome = new Welcome();
            welcome.onCreate();
    	}
    }
}