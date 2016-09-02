package com.inclass_03.pizzastore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Krunal on 01-02-2016.
 */
public class OrderActivity extends AppCompatActivity {
    int num_of_top, del_check;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        if(getIntent().getExtras() != null)
        {
            if(getIntent().getExtras().containsKey("num_of_top"))
            {
                num_of_top = getIntent().getExtras().getInt("num_of_top");
            }
            if(getIntent().getExtras().containsKey("del_check"))
            {
                del_check = getIntent().getExtras().getInt("del_check");
            }
        }


    }
}
