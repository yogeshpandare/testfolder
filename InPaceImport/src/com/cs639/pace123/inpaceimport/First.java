package com.cs639.pace123.inpaceimport;

import com.cs639.pace123.inpaceimport.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class First extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Proximity coming soon", Toast.LENGTH_LONG);
        toast.show();


        Button b = (Button)findViewById(R.id.foundFirst);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FirstFound.class);
                startActivity(intent);
            }
        });


    }
}