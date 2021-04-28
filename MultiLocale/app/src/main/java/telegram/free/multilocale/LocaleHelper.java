package telegram.free.multilocale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleHelper {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";


    public static String getLanguage(Context context) {

        return getPersistedData(context, Locale.getDefault().getLanguage());

    }

    public static Context setLocale(Context context, String language) {

        persist(context, language);

        return updateResourcesLegacy(context, language);

    }
    public static String selectedLanguage(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE,"en");
    }

    public static String getPersistedData(Context context, String defaultLanguage) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);

    }

    private static void persist(Context context, String language) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_LANGUAGE, language);

        editor.apply();

    }

    @SuppressWarnings("deprecation")

    private static Context updateResourcesLegacy(Context context, String language) {

        Locale locale = new Locale(language);

        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();

        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;

    }

}