/*
    Assignment HW 01
    Aneesha Velagapudi
    Geneva Davis
    Krunal Gosalia


 */

package com.hw01.computercalculator;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView seek_value, res_price, status_text;
    SeekBar tip_seek;
    RadioGroup storage_rg, memory_rg;
    int storage_size, memory_size, num_of_accessories, del_opt_value;
    double final_cost, budget_val, tip_per;
    Switch delivery_switch;
    CheckBox mouse_chk, coolPad_chk, flashDr_chk, carryCase_chk;
    EditText budget_txt;
    GradientDrawable gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_main);
        gd = new GradientDrawable();

        budget_txt = (EditText) findViewById(R.id.budget_val);


        tip_seek = (SeekBar) findViewById(R.id.seekBar);
        seek_value = (TextView) findViewById(R.id.seek_value);
        seek_value.setText("15%");
        tip_per = 15;

        res_price = (TextView) findViewById(R.id.res_price_text);
        res_price.setText("Price: $0.00");

        status_text = (TextView) findViewById(R.id.status_val);

        memory_rg = (RadioGroup) findViewById(R.id.memory_radioGroup);
        storage_rg = (RadioGroup) findViewById(R.id.storage_radioGroup);

        mouse_chk = (CheckBox) findViewById(R.id.mouse_acc_check);
        coolPad_chk = (CheckBox) findViewById(R.id.coolPad_acc_check);
        flashDr_chk = (CheckBox) findViewById(R.id.flashDr_acc_check);
        carryCase_chk = (CheckBox) findViewById(R.id.carryCase_acc_check);
        num_of_accessories = 0;

        delivery_switch = (Switch) findViewById(R.id.delivery_switch);
        del_opt_value = 1;

        delivery_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    del_opt_value = 1;
                else
                    del_opt_value = 0;

            }
        });

        tip_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seek_value.setText(progress * 5 + "%");
                tip_per = progress * 5;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button calc_btn = (Button) findViewById(R.id.calc_button);

        calc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String budget_string = budget_txt.getText().toString();

                if(budget_string.isEmpty())
                {
                    //Log.d("Error Gen", "Empty String");
                    setError(1);
                    budget_txt.setError("Enter a dollar amount");

                }
                else
                {
                    budget_val = Double.parseDouble(budget_string);

                    if(budget_val > 0)
                    {

                        gd.setShape(GradientDrawable.RECTANGLE);
                        gd.setCornerRadius(15.0f); // border corner radius


                        budget_val = Double.parseDouble(budget_string);
                        switch (memory_rg.getCheckedRadioButtonId()) {
                            case R.id.memory_2GB_radio:
                                memory_size = 2;
                                break;

                            case R.id.memory_4GB_radio:
                                memory_size = 4;
                                break;

                            case R.id.memory_8GB_radio:
                                memory_size = 8;
                                break;

                            case R.id.memory_16GB_radio:
                                memory_size = 16;
                                break;
                        }

                        switch (storage_rg.getCheckedRadioButtonId()) {
                            case R.id.storage_250GB_radio:
                                storage_size = 250;
                                break;

                            case R.id.storage_500GB_radio:
                                storage_size = 500;
                                break;

                            case R.id.storage_750GB_radio:
                                storage_size = 750;
                                break;

                            case R.id.storage_1TB_radio:
                                storage_size = 1000;
                                break;
                        }

                        num_of_accessories = 0;

                        if (mouse_chk.isChecked())
                            num_of_accessories++;
                        if (coolPad_chk.isChecked())
                            num_of_accessories++;
                        if (flashDr_chk.isChecked())
                            num_of_accessories++;
                        if (carryCase_chk.isChecked())
                            num_of_accessories++;


                        final_cost = ((10 * memory_size + 0.75 * storage_size + 20 * num_of_accessories) * (1 + tip_per / 100)) + 5.95 * del_opt_value;

                        /*
                            Log.d("Values", "Memory - " + memory_size + " Storage - " + storage_size + " Acc " + num_of_accessories + " tip - " + tip_per + " Delivery - " + del_opt_value);

                            Log.d("Values", (10 * memory_size) + "");
                            Log.d("Values", (0.75 * storage_size) + "");
                            Log.d("Values", (20 * num_of_accessories) + "");
                            Log.d("Values", (1 + tip_per / 100) + "");
                            Log.d("Values", (5.95 * del_opt_value) + "");
                        */
                        DecimalFormat df2 = new DecimalFormat(".##");

                        res_price.setText("Price: $" + df2.format(final_cost));

                        if (final_cost > budget_val) {

                            status_text.setText("Over budget");
                            gd.setCornerRadius(15.0f);
                            gd.setColor(Color.RED);
                            status_text.setBackground(gd);

                        } else {
                            status_text.setText("Within budget");
                            gd.setCornerRadius(15.0f);
                            gd.setColor(Color.GREEN);
                            status_text.setBackground(gd);
                        }
                    }
                    else
                    {
                        //Log.d("Error Gen", "Negative Value");
                        setError(2);
                        budget_txt.setError("Invalid amount");
                    }

                }




            }

            private void setError(int i) {
                if(i==1)
                {
                    status_text.setText("Enter a dollar amount");
                    gd.setCornerRadius(15.0f);
                    gd.setColor(Color.rgb(255, 165, 0));
                    status_text.setBackground(gd);
                }
                else if(i==2)
                {
                    status_text.setText("Invalid amount");
                    gd.setCornerRadius(15.0f);
                    gd.setColor(Color.rgb(255, 165, 0));
                    status_text.setBackground(gd);
                }
                else
                {
                    Log.d("Test","Test");
                }

            }
        });

        Button reset_btn = (Button) findViewById(R.id.reset_button);

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mouse_chk.setChecked(false);
                coolPad_chk.setChecked(false);
                flashDr_chk.setChecked(false);
                carryCase_chk.setChecked(false);

                memory_rg.check(R.id.memory_2GB_radio);
                storage_rg.check(R.id.storage_250GB_radio);

                res_price.setText("Price: $0.00");
                budget_txt.setText("");

                seek_value.setText("15%");
                tip_seek.setProgress(3);

                status_text.setText("");
                gd.setColor(Color.rgb(255, 255, 255));
                status_text.setBackground(gd);

                delivery_switch.setChecked(true);


                

                /*

                    Fill the contents here

                 */

            }
        });




    }


}
