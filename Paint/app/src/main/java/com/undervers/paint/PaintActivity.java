package com.undervers.paint;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;

import com.shitsuma.myapplication.R;

import java.util.HashMap;
import java.util.Map;

public class PaintActivity extends Activity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    private PaintView paintArea;

    private Map <Integer, Integer> colorMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_paint);

        initColorMap();

        radioGroup = (RadioGroup) findViewById(R.id.color_switcher);
        radioGroup.setOnCheckedChangeListener(this);

        paintArea = (PaintView) findViewById(R.id.draw_area);
        setPaintColor();
    }

    private void initColorMap(){
        colorMap = new HashMap<Integer, Integer> ();
        colorMap.put(R.id.green_paint, Color.GREEN);
        colorMap.put(R.id.blue_paint, Color.BLUE);
        colorMap.put(R.id.red_paint, Color.RED);
    }

    private void setPaintColor(){
        int color = colorMap.get(radioGroup.getCheckedRadioButtonId());
        paintArea.changePaintColor(color);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
         setPaintColor();
    }

    public void onClearClick(View v){
        paintArea.clear();
    }
}
