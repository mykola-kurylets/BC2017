package GUIManager.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kurylets.mykola.bc2017.R;

import GUIManager.GUIManager;

/**
 * Created by samsung on 25.05.2016.
 */
public class WindDirectionAdapter extends BaseAdapter
{
    public WindDirectionAdapter(Context context, int[] direction)
    {
        this.context = context;
        this.direction = direction;
        inflater = (LayoutInflater.from(context));;
    }

    @Override
    public int getCount()
    {
        return direction.length;
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        view = inflater.inflate(R.layout.spinner_item, null);
        ImageView icon = (ImageView) view.findViewById(R.id.direction_arrow_id);
        icon.setImageResource(direction[i]);
        return view;
    }

    private Context context;
    private int direction[];
    private LayoutInflater inflater;

}
