package GUIManager.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.kurylets.mykola.bc2017.R;


public class SelectModeDialog extends DialogFragment implements DialogInterface.OnClickListener
{

    public interface ISelectModeListener
    {
         public void OnPossitive();
    }


    public SelectModeDialog()
    {
        m_DialogBody = null;
        m_ModeListener = null;
    }

    public void SetListener(ISelectModeListener sml)
    {
        m_ModeListener = sml;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        m_DialogBody = getActivity().getLayoutInflater().inflate(R.layout.select_mode_dialog, null);
        m_DayModeRadio = (RadioButton)m_DialogBody.findViewById(R.id.day_radio_id);
        m_NightModeRadio = (RadioButton)m_DialogBody.findViewById(R.id.night_radio_id);

        AlertDialog.Builder m_ModeDialogBuilder = new AlertDialog.Builder(getActivity());
        m_ModeDialogBuilder.setView(m_DialogBody);

        m_ModeDialogBuilder.setPositiveButton(m_Yes, this);
        m_ModeDialogBuilder.setNegativeButton(m_Cancel, this);


        return m_ModeDialogBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        if(m_ModeListener== null)
            return;

        switch (which)
        {
            case Dialog.BUTTON_POSITIVE:
                m_ModeListener.OnPossitive();
                break;
            case Dialog.BUTTON_NEGATIVE:
                dialog.dismiss();
                break;

        }
    }

    private View m_DialogBody;
    private ISelectModeListener m_ModeListener;

    private RadioButton m_DayModeRadio;
    private RadioButton m_NightModeRadio;

    private final String m_Yes = "Yes";//getString(R.string.yes_text);
    private final String m_Cancel = "Cancel";//getString(R.string.cancel_text);


}
