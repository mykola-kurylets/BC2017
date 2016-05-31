package GUIManager.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kurylets.mykola.bc2017.R;

import java.io.File;
import java.util.Vector;

import TableHolder.GunSystemLoader;

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
        m_ListView = null;
        m_AdapterList = new Vector<String>();
        m_GunSystemLoader = new GunSystemLoader();
        m_Files = new Vector<File>();
        m_ChoosedFile = null;
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
        SetCurrent(new File(folderPath));
    }

    public void SetCurrent(File folder)
    {
        if(folder == null || !folder.isDirectory())
            return;

        m_CurrentFolder = folder;
        IntoFolder();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        m_ChoosedFile = null;
        m_Form = getActivity().getLayoutInflater().inflate(R.layout.choose_file_layout, null);

        // отримання елементів управління діалога
        m_ListView = (ListView)m_Form.findViewById(R.id.file_item);

        m_Adapter = new ArrayAdapter<String>(m_Form.getContext(), android.R.layout.simple_list_item_1, m_AdapterList);
        m_ListView.setAdapter(m_Adapter);
        m_ListView.setOnItemClickListener(new OnListItemClck());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // встановлення заголовку діалога
//        builder.setTitle("Вибір вогневої системи");
        builder.setView(m_Form);
        // кнопка ВІДМІНА
        builder.setNeutralButton(getString(R.string.cancel_text), new OnDialogBntClck());

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface unused)
    {
        super.onDismiss(unused);

        if(m_ChoosedFile != null && m_Listener != null)
            m_Listener.SetFile(m_ChoosedFile.getAbsolutePath());

    }

    class OnDialogBntClck implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dismiss();
        }
    }

    class OnListItemClck implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            if(position == 0) {
                SetCurrent(m_CurrentFolder.getParentFile());
                return;
            }

            if(m_Files == null)
                return;

            File pickedFile = m_Files.elementAt(position);

            if(pickedFile.isDirectory()){
                SetCurrent(pickedFile);
                return;
            }

            m_ChoosedFile = pickedFile;
            dismiss();
        }
    }

    private void FormList()
    {
        m_AdapterList.clear();
        m_AdapterList.add("../" + m_CurrentFolder.getName());
        File files[] = m_CurrentFolder.listFiles();

        if(files == null)
            return;

        m_Files.clear();
        m_Files.add(m_CurrentFolder);
        for(int i = 0; i < files.length; ++i){
            File fileOrDir = files[i];

            if(fileOrDir.isDirectory()) {
                m_AdapterList.add("[" + fileOrDir.getName() + "]");
                m_Files.add(fileOrDir);
                continue;
            }

            String systemName = m_GunSystemLoader.LoadSystemName(fileOrDir.getAbsolutePath());
            if(systemName == null)
                continue;

            m_AdapterList.add(systemName);
            m_Files.add(fileOrDir);
        }
    }

    private void ChangeAdapter()
    {
        if(m_ListView == null) {
            FormList();
            return;
        }

        m_ListView.setAdapter(null);
        FormList();
        m_ListView.setAdapter(m_Adapter);
    }

    private void GetFile(File pickedFile)
    {

    }

    private void IntoFolder()
    {
        ChangeAdapter();
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

    private File                        m_ChoosedFile;
    private File                        m_CurrentFolder;
    private IGunSystemFileDlgListener   m_Listener;
    private Vector<File>                m_Files;
    private Vector<String>              m_AdapterList;
    private ArrayAdapter<String>        m_Adapter;
    private GunSystemLoader             m_GunSystemLoader;

}
