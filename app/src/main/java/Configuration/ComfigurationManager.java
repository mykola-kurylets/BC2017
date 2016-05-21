package Configuration;

import Application.Application;

/**
 * Created by Mykola on 21.05.2016.
 */
public class ComfigurationManager
{

    public boolean Load()
    {
        return true;
    }

    public boolean Save()
    {
        return true;
    }

//    -ChooseTablesFolder(): String
//    -ChooseGunSystem(): String
//    -IsFolderExists(): boolean
//    -IsGunSystemFileExists(): boolean
//    -CreateTablesFolder(): boolean
//    -ExtructTables(): boolean
//    -LoadGunSystem(): boolean
//    -CreateGUIPreferences(String, StringStringMap)

    private Application m_App;

    private final String m_TablesTagName = "tables";
    private final String m_SystemFileTagName = "system-file";
    private final String m_TablesFolderName = "bctables";

    private String m_TablesFolderPath;
    private String m_SystemName;
}
