package heckmeck.server;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageTexts {

	private String mLanguage;
	private static ResourceBundle mBundle;

	public MessageTexts(String language) {
		mLanguage = language;

		setFile();

	}

	private void setFile() {

		if (mLanguage.equals("Deutsch")) {
			mBundle = ResourceBundle.getBundle("messages", Locale.GERMAN);
		} else {
			mBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
		}

	}
	
	public static ResourceBundle getFile() {
		return mBundle;
	}
	
	public static String getMessage( String id ){
		return mBundle.getString( id );
	}

}
