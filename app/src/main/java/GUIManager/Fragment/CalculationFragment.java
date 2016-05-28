package GUIManager.Fragment;



import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.kurylets.mykola.bc2017.R;
import com.kurylets.mykola.bcmodule.InputData;
import com.kurylets.mykola.bcmodule.OutputData;
import com.kurylets.mykola.bcmodule.WindDirections;

/**
 * Created by samsung on 20.05.2016.
 */
public class CalculationFragment extends Fragment implements AdapterView.OnItemSelectedListener
{
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }


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

    public class  CalculationEvent implements View.OnClickListener,  TextView.OnEditorActionListener
    {
        @Override
        public void onClick(View v)
        {
            ExecudeCalculate();
        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
        {
            if (actionId == EditorInfo.IME_ACTION_DONE)
            {
                ExecudeCalculate();
            }
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

    private void GatherInputData() {
        m_UserInput.SetDistance(GetDouble(m_DistanceInput));
        m_UserInput.SetTemperature(GetDouble(m_TemperatureInput));
        m_UserInput.SetPressure(GetDouble(m_PressureInput));
        m_UserInput.SetWindSpeed(GetDouble(m_WindSpeedInput));

        WindDirections wd = WindDirections.e0;
        switch (m_WindDirectionSelect.getSelectedItemPosition())
        {
            case 0:
                wd = WindDirections.e0;
                break;
            case 1:
                wd = WindDirections.e45;
                break;
            case 2:
                wd = WindDirections.e90;
                break;
            case 3:
                wd = WindDirections.e135;
                break;
            case 4:
                wd = WindDirections.e180;
                break;
            case 5:
                wd = WindDirections.e225;
                break;
            case 6:
                wd = WindDirections.e270;
                break;
            case 7:
                wd = WindDirections.e315;
                break;
        }

        m_UserInput.SetWindDirection(wd);
    }

    private void PutOutputData()
    {
        m_VertSightOutput.setText(String.valueOf(m_UserOutput.GetVerticalSight()));
        m_VertErrorOutput.setText(String.format("%.2f", (Double) m_UserOutput.GetVerticalDeviation()));
        String str = m_UserOutput.GetHorizontalSight();
        if(str == null || str.isEmpty())
            str =  getString(R.string.error_text);
        m_HorSightOutput.setText(str);
        m_HorErrorOutput.setText(String.format("%.2f", (Double) m_UserOutput.GetHorizontalDeviation()));
    }

    private String GetString(EditText userEdit )
    {
        if(userEdit == null)
            return null;
        Editable input = userEdit.getText();
        if(input == null)
            return null;
        String str = input.toString();
        if(str == null || str.isEmpty() )
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

        m_DistanceInput.setOnEditorActionListener(m_CalcEvent);
        m_TemperatureInput.setOnEditorActionListener(m_CalcEvent);
        m_PressureInput.setOnEditorActionListener(m_CalcEvent);
        m_WindSpeedInput.setOnEditorActionListener(m_CalcEvent);
        m_CalcButton.setOnClickListener(m_CalcEvent);
        m_WindDirectionSelect.setOnItemSelectedListener(this);

        WindDirectionAdapter customAdapter=new WindDirectionAdapter(getActivity().getApplicationContext(), m_ArrowImages);
        m_WindDirectionSelect.setAdapter(customAdapter);
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

    private int[] m_ArrowImages = { R.drawable.arrow0
            , R.drawable.arrow45
            , R.drawable.arrow90
            , R.drawable.arrow135
            , R.drawable.arrow180
            , R.drawable.arrow225
            , R.drawable.arrow270
            , R.drawable.arrow315
    };
}


