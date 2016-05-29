package GUIManager.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.Vector;

/**
 * Created by Mykola on 29.05.2016.
 */
public class GunSystemFileDlg extends DialogFragment
{
    public GunSystemFileDlg()
    {
        m_Form = null;
        m_Listener = null;
        m_Adapter = null;
    }

    public interface IGunSystemFileDlgListener
    {
        public void SetFile(String path);
    }

    public void SetListener(IGunSystemFileDlgListener ls)
    {
        m_Listener = ls;
    }

    public void SetCurrent(String folderPath)
    {

    }

    public void SetCurrent(File folder)
    {
        m_CurrentFolder = folder;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        return null;
    }

    class OnDialogBntClck implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {

        }
    }

    class OnListItemClck implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {

        }
    }

    private void FormList()
    {

    }

    private void ChangeAdapter()
    {

    }

    private void GetFile(File pickedFile)
    {

    }

    private void IntoFolder()
    {

    }

    private void ItemClickExecude()
    {

    }

    private File GetFileFromAdapter()
    {
        return null;
    }

    private View                        m_Form;
    private ListView                    m_ListView;

    private File                        m_CurrentFolder;
    private IGunSystemFileDlgListener   m_Listener;
    private File[]                      m_Files;
    private Vector<String>              m_AdapterList;
    private ArrayAdapter<String>        m_Adapter;

}
