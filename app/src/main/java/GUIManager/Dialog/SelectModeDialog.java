package GUIManager.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kurylets.mykola.bc2017.R;


public class SelectModeDialog extends DialogFragment implements DialogInterface.OnClickListener {

    public interface ISelectModeListener
    {
         void OnPossitive(int res);
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
        // ініціалізація елементів інтерфейсу діалогу вибору вікна
        m_DialogBody = getActivity().getLayoutInflater().inflate(R.layout.select_mode_dialog, null);
        m_DayModeRadio = (RadioButton) m_DialogBody.findViewById(R.id.day_radio_id);
        m_NightModeRadio = (RadioButton) m_DialogBody.findViewById(R.id.night_radio_id);
        m_RadioGroup = (RadioGroup) m_DialogBody.findViewById(R.id.mode_group_id);

        SetCurrentModeRadio();
        AlertDialog.Builder m_ModeDialogBuilder = new AlertDialog.Builder(getActivity());
        m_ModeDialogBuilder.setView(m_DialogBody);


        m_ModeDialogBuilder.setPositiveButton(getString(R.string.yes_text), this);
        m_ModeDialogBuilder.setNegativeButton(getString(R.string.cancel_text), this);


        return m_ModeDialogBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        if (m_ModeListener == null)
            return;

        if(which == Dialog.BUTTON_NEGATIVE){
            dismiss();
            return;
        }

        switch (m_RadioGroup.getCheckedRadioButtonId())
        {
            case R.id.day_radio_id:
                m_CurrMode = DAY_MODE;
                break;
            case R.id.night_radio_id:
                m_CurrMode = NIGHT_MODE;
                break;
        }

        m_ModeListener.OnPossitive(m_CurrMode);
    }

    // отримати активний RadioButton та присвоїти відповідний режим
    public int GetMode()
    {
        return m_CurrMode;
    }

    public void SetMode(int id)
    {
        m_CurrMode = id;
    }

//   встановити  RadioButton  з поточним режимом
    private boolean SetCurrentModeRadio()
    {
        int currentId = m_CurrMode;
        switch (currentId) {
            case DAY_MODE:
                m_DayModeRadio.setChecked(true);
                break;
            case NIGHT_MODE:
                m_NightModeRadio.setChecked(true);
                break;
            default:
                m_DayModeRadio.setChecked(true);
        }
        return true;
    }


    private View                m_DialogBody;
    private ISelectModeListener m_ModeListener;

    private RadioButton         m_DayModeRadio;
    private RadioButton         m_NightModeRadio;
    private RadioGroup          m_RadioGroup;

    private int                 m_CurrMode;
    private final int           DAY_MODE = 0;
    private final int           NIGHT_MODE = 1;
}

