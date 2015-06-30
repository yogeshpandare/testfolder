package com.cs639.pace123.inpaceimport;

//import com.cs639.pace.inpace.R;
import com.cs639.pace123.inpaceimport.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOptions extends Activity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_options);


        tv = (TextView) findViewById(R.id.go);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOptions.this, First.class);
                startActivity(intent);
            }
        });


    }


}


