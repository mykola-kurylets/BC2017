package GUIManager.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kurylets.mykola.bc2017.R;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;

/**
 * Created by samsung on 20.05.2016.
 */
public class CalculationFragment extends Fragment
{

    public  CalculationFragment()
    {
        m_CalcEvent = new CalculationEvent();
        m_UserInput = new InputData();
        m_UserOutput = new OutputData();
        m_FrListener = null;
    }

    public CalculationFragment(ICalculationFragmentListener calcListener)
    {
        this();
        m_FrListener = calcListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        InitControls(view);
        return view;
    }

    public interface ICalculationFragmentListener
    {
        public boolean OnCalculate(InputData inD, OutputData outD);
    }

    public class  CalculationEvent implements View.OnClickListener, View.OnKeyListener
    {
        @Override
        public void onClick(View v)
        {
            ExecudeCalculate();
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event)
        {
            //TODO відфільтрувати непотрібні клавіші
//            ExecudeCalculate();
            return false;
        }

    }

    private boolean ExecudeCalculate()
    {
        if(m_FrListener == null )
            return false;
        GatherInputData();
        if(!m_FrListener.OnCalculate(m_UserInput, m_UserOutput))
           return false;
        PutOutputData();
        return true;
    }

    private void GatherInputData()
    {
        m_UserInput.SetDistance(GetDouble(m_DistanceInput));
        m_UserInput.SetTemperature(GetDouble(m_TemperatureInput));
        m_UserInput.SetPressure(GetDouble(m_PressureInput));
        m_UserInput.SetWindSpeed(GetDouble(m_WindSpeedInput));
    }

    private void PutOutputData()
    {

    }

    private String GetString(EditText userEdit )
    {
        if(userEdit == null)
            return null;
        Editable input = userEdit.getText();
        if(input == null)
            return null;
        String str = input.toString();
        if(str == null || str.length() == 0 )
            return null;
        return  str;
    }

    private double GetDouble(EditText userEdit)
    {
        String str = GetString(userEdit);
        if(str == null)
           return Double.MAX_VALUE;
        return Double.parseDouble(str);
    }

    private void InitControls(View view)
    {

        m_DistanceInput = (EditText)view.findViewById(R.id.distance_value_id);
        m_TemperatureInput = (EditText)view.findViewById(R.id.temperature_value_id);
        m_PressureInput = (EditText)view.findViewById(R.id.pressure_value_id);
        m_WindSpeedInput = (EditText)view.findViewById(R.id.speed_value_id);
        m_WindDirectionSelect = (Spinner)view.findViewById(R.id.direction_id);
        m_VertSightOutput = (TextView)view.findViewById(R.id.vrtc_sight_id);
        m_VertErrorOutput = (TextView)view.findViewById(R.id.vrtc_correction_id);
        m_HorSightOutput = (TextView)view.findViewById(R.id.hrst_sight_id);
        m_HorErrorOutput = (TextView)view.findViewById(R.id.hrst_correction_id);
        m_CalcButton = (Button)view.findViewById(R.id.calculate_button);

        m_DistanceInput.setOnKeyListener(m_CalcEvent );
        m_TemperatureInput.setOnKeyListener(m_CalcEvent );
        m_PressureInput.setOnKeyListener(m_CalcEvent );
        m_WindSpeedInput.setOnKeyListener(m_CalcEvent );
        m_CalcButton.setOnClickListener(m_CalcEvent);
    }

    private EditText m_DistanceInput;
    private EditText m_TemperatureInput;
    private EditText m_PressureInput;
    private EditText m_WindSpeedInput;
    private Spinner m_WindDirectionSelect;
    private TextView m_VertSightOutput;
    private TextView m_VertErrorOutput;
    private TextView m_HorSightOutput;
    private TextView m_HorErrorOutput;
    private Button m_CalcButton;
    private CalculationEvent m_CalcEvent;

    private InputData m_UserInput;
    private OutputData m_UserOutput;

    private ICalculationFragmentListener m_FrListener;
}
