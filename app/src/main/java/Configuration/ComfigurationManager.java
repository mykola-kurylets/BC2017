package Configuration;

import android.content.SharedPreferences;

import com.kurylets.mykola.bc2017.MainActivity;

import java.util.Map;
import java.util.Set;

import Application.Application;
import Utilities.StringsMap;

/**
 * Created by Mykola on 21.05.2016.
 */
public class ComfigurationManager
{

    public ComfigurationManager(Application app)
    {
        m_App = app;
        m_SystemFileName = m_UnDefined;
    }

    public static final String m_UnDefined = "?"; // константа невизначеного файлу

    // отримати імя файлу вогневої системи
    public String GetGunSystemFileName()
    {
        return m_SystemFileName;
    }

    // встановити імя файлу вогневої системи
    public void SetGunSystemFileName(String name)
    {
        m_SystemFileName = name;
    }

    // отримати імя папки з таблицями
    public String GetTablesFolderName()
    {
        return m_TablesFolderName;
    }

    // отирмати поточний режим
    public int GetCurrentMode()
    {
        return m_CurrentMode;
    }

    // встановити поточний режим
    public void SetCurentMode(int mode)
    {
        m_CurrentMode = mode;
    }

    // завантажити налаштування
    public boolean Load()
    {
        if(!LoadGeneralPreferences())
            return false;

        return LoadGUIPreferences();
    }

    // завантажити загальні налаштування
    public boolean LoadGeneralPreferences()
    {
        // читаємо імя останньо вибраної системи
        SharedPreferences gPref = m_App.GetPreferances(m_GeneralPrefTagName, MainActivity.MODE_PRIVATE);
        if(gPref == null)
            return false;

        m_SystemFileName = gPref.getString(m_SystemFileTagName, m_UnDefined);

        m_CurrentMode = gPref.getInt(m_ModeTagName, MainActivity.m_DayModeTheme);

        return true;
    }

    // завантажити налаштування графічного інтерфейсу
    public boolean LoadGUIPreferences()
    {
        SharedPreferences guiPref = m_App.GetPreferances(m_App.GetGUIManager().GetPreferencesName(), MainActivity.MODE_PRIVATE);
        if(guiPref == null)
            return false;

        StringsMap guiMap = new StringsMap();

        // отримуємо налаштування графічного інтерфейсу
        Map<String, ?> guiPrefMap = guiPref.getAll();
        Set<String> keysSet = guiPrefMap.keySet();
        Object []keys = keysSet.toArray();
        for(int i = 0, stape = keys.length; i < stape; ++i){
            String key = (String)keys[i];

            guiMap.put(key, (String)guiPrefMap.get(key));
        }

        m_App.GetGUIManager().SetPreferences((StringsMap)guiMap.clone());

        guiMap.clear();

        return true;
    }

    // зберегти налаштування
    public boolean Save()
    {
        SharedPreferences gPref = m_App.GetPreferances(m_GeneralPrefTagName, MainActivity.MODE_PRIVATE);

        if(!SaveGeneralPreferences(gPref))
            return false;

        String guiPrefname = m_App.GetGUIManager().GetPreferencesName();
        SharedPreferences guiPref = m_App.GetPreferances(guiPrefname, MainActivity.MODE_PRIVATE);

        return SaveGUIPreferences(guiPref);
    }

    // зберегти загальні налаштування
    private boolean SaveGeneralPreferences(SharedPreferences gPref)
    {
        SharedPreferences.Editor prefEditor = gPref.edit();
        prefEditor.clear();

        // зберігаємо імя файлу останньої вибраної системи
        prefEditor.putString(m_SystemFileTagName, m_SystemFileName);
        // зберігаємо поточний режим
        prefEditor.putInt(m_ModeTagName, m_CurrentMode);
        // записуємо на диск
        prefEditor.commit();

        return true;
    }

    // зберегти налаштування графічного інтерфейсу
    private boolean SaveGUIPreferences(SharedPreferences guiPref)
    {
        SharedPreferences.Editor prefEditor = guiPref.edit();
        prefEditor.clear();

        // отримуємо налаштування графічного інтерфейсу
        StringsMap guiMapPref = new StringsMap();
        m_App.GetGUIManager().GetPreferences(guiMapPref);

        if(guiMapPref.isEmpty())
            return false;

        Set<String> keysSet = guiMapPref.keySet();
        Object []keys = keysSet.toArray();
        for(int i = 0, stape = keys.length; i < stape; ++i){
            String key = (String)keys[i];
            prefEditor.putString(key, guiMapPref.get(key));
        }

        prefEditor.commit();

        guiMapPref.clear();

        return true;
    }


    // посилання на класс програми
    private Application m_App;

    // тег для зберігання імені файлу
    private final String m_SystemFileTagName    = "system-file";
    // тег для зберігання імені папки
    private final String m_TablesFolderName     = "bctables";
    // тег для зберігання загальних налаштувань
    private final String m_GeneralPrefTagName   = "bc2017-general-pref";
    // тге для зберігання поточного режиму
    private final String m_ModeTagName          = "cur-mode-name";

    // імя файлу вогневої системи
    private String       m_SystemFileName;
    // поточний режим
    private int          m_CurrentMode;
}
