package com.inclass_03.pizzastore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button add_topping_btn;

    TableRow topping_row1, topping_row2;
    TableLayout topping_table;
    ProgressBar topProgress;
    int i=0 ;
    ArrayList <String> top_list;
    Button checkout_btn;
    CheckBox c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_topping_btn = (Button) findViewById(R.id.add_topping_btn);
        topping_row1 =  (TableRow) findViewById(R.id.topping_row_1);
        topping_row2 =  (TableRow) findViewById(R.id.topping_row_2);
        topping_table = (TableLayout) findViewById(R.id.topping_table_id);
        topProgress = (ProgressBar)findViewById(R.id.top_progress);
        c = (CheckBox) findViewById(R.id.delivery_check);


        checkout_btn = (Button) findViewById(R.id.checkout_button);


        topping_row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        top_list = new ArrayList<String>();



        final CharSequence[] topping_list={"Bacon","Cheese","Garlic", "Green Pepper", "Mushroom", "Olives", "Onions", "Red Pepper"};

        add_topping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose a Topping").
                        setItems(topping_list, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                topProgress.setMax(100);

                                if (i < topProgress.getMax())
                                {
                                    i = i+10;
                                    topProgress.setProgress(i);
                                }


                                if(top_list.size() < 10) {

                                    TableRow t1;

                                    if (topping_row1.getChildCount() < 5) {
                                        t1 = topping_row1;
                                    } else {
                                        t1 = topping_row2;
                                    }

                                    Log.d("Test Top", topping_list[which].toString());
                                    if (topping_list[which].toString() == "Bacon") {
                                        ImageView iv = new ImageView(MainActivity.this);
                                        iv.setLayoutParams(new TableRow.LayoutParams(120, 120));
                                        iv.setImageResource(R.drawable.bacon);
                                        t1.addView(iv);
                                        top_list.add("Bacon");
                                    }
                                    if (topping_list[which].toString() == "Cheese") {
                                        ImageView iv = new ImageView(MainActivity.this);
                                        iv.setLayoutParams(new TableRow.LayoutParams(120, 120));
                                        iv.setImageResource(R.drawable.cheese);
                                        t1.addView(iv);
                                        top_list.add("Cheese");
                                    }
                                    if (topping_list[which].toString() == "Garlic") {
                                        ImageView iv = new ImageView(MainActivity.this);
                                        iv.setLayoutParams(new TableRow.LayoutParams(120, 120));
                                        iv.setImageResource(R.drawable.garlic);
                                        t1.addView(iv);
                                        top_list.add("Garlic");
                                    }
                                    if (topping_list[which].toString() == "Green Pepper") {
                                        ImageView iv = new ImageView(MainActivity.this);
                                        iv.setLayoutParams(new TableRow.LayoutParams(120, 120));
                                        iv.setImageResource(R.drawable.green_pepper);
                                        t1.addView(iv);
                                        top_list.add("Green Pepper");
                                    }
                                    if (topping_list[which].toString() == "Mushroom") {
                                        ImageView iv = new ImageView(MainActivity.this);
                                        iv.setLayoutParams(new TableRow.LayoutParams(120, 120));
                                        iv.setImageResource(R.drawable.mushroom);
                                        t1.addView(iv);
                                        top_list.add("Mushroom");
                                    }
                                    if (topping_list[which].toString() == "Olives") {
                                        ImageView iv = new ImageView(MainActivity.this);
                                        iv.setLayoutParams(new TableRow.LayoutParams(120, 120));
                                        iv.setImageResource(R.drawable.olives);
                                        t1.addView(iv);
                                        top_list.add("Olives");
                                    }
                                    if (topping_list[which].toString() == "Red Pepper") {
                                        ImageView iv = new ImageView(MainActivity.this);
                                        iv.setLayoutParams(new TableRow.LayoutParams(120, 120));
                                        iv.setImageResource(R.drawable.red_pepper);
                                        t1.addView(iv);
                                        top_list.add("Red Pepper");
                                    }
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Can add upto 10 toppings", Toast.LENGTH_LONG).show();
                                }



                            }
                        });


                final AlertDialog simpleAlert = builder.create();
                builder.create().show();
            }
        });

        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), OrderActivity.class);

                int del_check;

                if(c.isChecked())
                    del_check = 1;
                else
                    del_check = 0;

                i.putExtra("num_of_top", (int) top_list.size());
                i.putExtra("del_check", (int)del_check);
                startActivity(i);

            }
        });


    }
}


