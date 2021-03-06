package GUIManager.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kurylets.mykola.bc2017.R;

/**
 * Created by samsung on 31.05.2016.
 */
public class AboutDialog extends DialogFragment implements DialogInterface.OnClickListener
{

    public AboutDialog()
    {
        m_DialogBody = null;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // ініціалізація елементів інтерфейсу діалогу вибору вікна
        m_DialogBody = getActivity().getLayoutInflater().inflate(R.layout.about_dialog, null);
        m_Message = (TextView)m_DialogBody.findViewById(R.id.about_message);
        m_Message.setText("Балістичний калькулятор БК2017.\nРозробники:\n\tТимочко Ірина\n\tКурилець Микола");
        AlertDialog.Builder m_AboutDialogBuilder = new AlertDialog.Builder(getActivity());
        m_AboutDialogBuilder.setView(m_DialogBody);
        m_AboutDialogBuilder.setPositiveButton(getString(R.string.ok_text), this);
        return m_AboutDialogBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        if (which == Dialog.BUTTON_POSITIVE) {
            dismiss();
            return;
        }
    }
    private View m_DialogBody;
    private TextView m_Message;
}
