package in.aviaryan.hinix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by nilesh on 11/10/16.
 */

public class startScreen extends AppCompatActivity {

    private Button play;
    private RadioButton rb1;
    private RadioButton rb2;
    public static ProgressDialog progressDialog;
    private  int selectedId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        progressDialog = new ProgressDialog(startScreen.this);



        play=(Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Set progressDialog properties and Intent in BackGroundTask ...

                BackGroundTask backGroundTask = new BackGroundTask(startScreen.this , progressDialog) ;
                backGroundTask.execute() ;
            }
        });
        RadioGroup rg = (RadioGroup) findViewById(R.id.radiogrp);
        rb1=(RadioButton) findViewById(R.id.easy);
        rb2=(RadioButton) findViewById(R.id.moderate);
        //RadioButton rb3 = (RadioButton) findViewById(R.id.hard);
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
        Button commandButton = (Button) findViewById(R.id.instr);
        commandButton.setOnClickListener(new View.OnClickListener() {
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


    public int getSelectedId() {
        return selectedId;
    }

    /**
     * this method is yet to be implemented
     */
    public void level(){
        return;
    }
}


class BackGroundTask extends AsyncTask<Void , Void ,Void> {
    private ProgressDialog dialog;
    private startScreen activity ;

    public BackGroundTask(startScreen activity , ProgressDialog dialog) {
        BackGroundTask.this.activity = activity ;
        BackGroundTask.this.dialog = dialog ;
    }


    @Override
    protected void onPreExecute() {
        //Set Dialog properties here ...

        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading ... ");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    protected Void doInBackground(Void... params) {

        try {
            Thread.sleep(500);

            Intent i =new Intent(BackGroundTask.this.activity , MainActivity.class);
            i.putExtra("Level",BackGroundTask.this.activity.getSelectedId());
            BackGroundTask.this.activity.startActivity(i);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return  null ;
    }




    @Override
    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}