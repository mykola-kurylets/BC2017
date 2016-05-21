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
        m_TablesFolderPath = m_UnDefined;
    }

    public static final String m_UnDefined = "?";

    public String GetTablesFolderPath()
    {
        return m_TablesFolderPath;
    }

    public void SetTablesFolderPath(String path)
    {
        m_TablesFolderPath = path;
    }

    public String GetGunSystemFileName()
    {
        return m_SystemFileName;
    }

    public void SetGunSystemFileName(String name)
    {
        m_SystemFileName = name;
    }

    public String GetTablesFolderName()
    {
        return m_TablesFolderName;
    }

    public boolean Load()
    {
        SharedPreferences gPref = m_App.GetPreferances(m_GeneralPrefTagName, MainActivity.MODE_PRIVATE);

        if(!LoadGeneralPreferences(gPref))
            return false;

        String guiPrefname = "bc-2017-gui-pref";//m_App.GetGUIManager().GetPreferencesName();
        SharedPreferences guiPref = m_App.GetPreferances(guiPrefname, MainActivity.MODE_PRIVATE);

        return LoadGUIPreferences(guiPref);
    }

    public boolean Save()
    {
        SharedPreferences gPref = m_App.GetPreferances(m_GeneralPrefTagName, MainActivity.MODE_PRIVATE);

        if(!SaveGeneralPreferences(gPref))
            return false;

        String guiPrefname = "bc-2017-gui-pref";//m_App.GetGUIManager().GetPreferencesName();
        SharedPreferences guiPref = m_App.GetPreferances(guiPrefname, MainActivity.MODE_PRIVATE);

        return SaveGUIPreferences(guiPref);
    }

    private boolean SaveGeneralPreferences(SharedPreferences gPref)
    {
        SharedPreferences.Editor prefEditor = gPref.edit();
        prefEditor.clear();

        // зберігаємо шлях до папки з таблицями
        prefEditor.putString(m_TablesTagName, m_TablesFolderPath);
        // зберігаємо імя файлу останньої вибраної системи
        prefEditor.putString(m_SystemFileTagName, m_SystemFileName);
        // записуємо на диск
        prefEditor.commit();

        return true;
    }

    private boolean SaveGUIPreferences(SharedPreferences guiPref)
    {
        SharedPreferences.Editor prefEditor = guiPref.edit();
        prefEditor.clear();

        // отримуємо налаштування графічного інтерфейсу
        StringsMap guiMapPref = new StringsMap();//m_App.GetGUIManager().GetPreferences();
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

    private boolean LoadGeneralPreferences(SharedPreferences gPref)
    {
        // читаємо шлях до папки з файлами систем
        m_TablesFolderPath = gPref.getString(m_TablesTagName, m_UnDefined);
        // читаємо імя останньо вибраної системи
        m_SystemFileName = gPref.getString(m_SystemFileTagName, m_UnDefined);

        return true;
    }

    private boolean LoadGUIPreferences(SharedPreferences guiPref)
    {
        StringsMap guiMap = new StringsMap();

        // отримуємо налаштування графічного інтерфейсу
        Map<String, ?> guiPrefMap = guiPref.getAll();
        Set<String> keysSet = guiPrefMap.keySet();
        Object []keys = keysSet.toArray();
        for(int i = 0, stape = keys.length; i < stape; ++i){
            String key = (String)keys[i];

            guiMap.put(key, (String)guiPrefMap.get(key));
        }

        //m_App.GetGUIManager().SetPreferences((StringsMap)guiMap.clone());

        guiMap.clear();

        return true;
    }


    private Application m_App;

    private final String m_TablesTagName        = "tables";
    private final String m_SystemFileTagName    = "system-file";
    private final String m_TablesFolderName     = "bctables";
    private final String m_GeneralPrefTagName   = "bc2017-general-pref";

    private String m_TablesFolderPath;
    private String m_SystemFileName;
}
