package in.aviaryan.hinix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.R.attr.value;

/**
 * Created by nilesh on 11/10/16.
 */

public class startScreen extends AppCompatActivity {

    private Button play, instruction;
    private RadioGroup rg;
    private RadioButton rb1,rb2,rb3;
    private  int selectedId=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen1);

        play=(Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(startScreen.this, MainActivity.class);
                i.putExtra("Level",selectedId);
                startActivity(i);
            }
        });
        rg = (RadioGroup) findViewById(R.id.radiogrp);
        rb1=(RadioButton) findViewById(R.id.easy);
        rb2=(RadioButton) findViewById(R.id.moderate);
        rb3=(RadioButton) findViewById(R.id.hard);
        //final String value = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

        //rb = (RadioButton) findViewById(selectedId);
       rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==rb1.getId())
                selectedId=1;
                else if(checkedId==rb2.getId())
                    selectedId=2;
                else
                selectedId=3;
                /*Toast.makeText(getBaseContext(), selectedId+"", Toast.LENGTH_SHORT).show();*/
            }
        });
        instruction= (Button) findViewById(R.id.instr);
        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        startScreen.this);

                // set title
                alertDialogBuilder.setTitle("Instructions");

                // set dialog message
                alertDialogBuilder
                        .setMessage("1. You are supposed to make as many words as you can from the given set of letters in the grid.\n" +
                                "2. If the submitted word is a valid one then it it added to the list and the score would be updated.\n" +
                                "3. Lastly you can challenge the game to show you all the possible words.\n\n ");

                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

    }

    public void level(){

    }
}
