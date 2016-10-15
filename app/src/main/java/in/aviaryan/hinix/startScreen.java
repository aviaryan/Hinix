package in.aviaryan.hinix;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import dmax.dialog.SpotsDialog;

import static android.R.attr.value;

/**
 * Created by nilesh on 11/10/16.
 */

public class startScreen extends AppCompatActivity {

    private Button play, instruction;
    private RadioGroup rg;
    private RadioButton rb1,rb2,rb3;
    private  int selectedId=1;
//    static ProgressDialog progressDialog;
    private android.app.AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen1);
        //progressDialog = new ProgressDialog(startScreen.this);
        dialog = new SpotsDialog(startScreen.this);

        play=(Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    BackGroundTask backGroundTask = new BackGroundTask(startScreen.this , dialog) ;
                    backGroundTask.execute() ;

//                Intent i =new Intent(startScreen.this, MainActivity.class);
//                i.putExtra("Level",selectedId);
//                startActivity(i);
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
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(startScreen.this);

                // set title
                alertDialogBuilder.setTitle("Instructions");

                // set dialog message
                alertDialogBuilder
                        .setMessage("1. You are supposed to make as many words as you can from the give set of letters in the grid.\n" +
                                "There is a maximum no. of words possible. Your goal is to make that many words.\n" +
                                "2.A fixed no. of points you get in each game. These points gets deducted based on the difference on the no. of possible words and your score.\n" +
                                "The game ends as soon as these points get over.\n" +
                                "Lastly you can challenge the game to show you all the possible words.\n\n");

                android.app.AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

    }
    public int getSelectedId() {
                return selectedId;
            }
    public void level(){

    }
}
class BackGroundTask extends AsyncTask<Void , Void ,Void> {
        private android.app.AlertDialog dialog;
        private startScreen activity ;
                public BackGroundTask(startScreen activity , android.app.AlertDialog dialog) {
                BackGroundTask.this.activity = activity ;
                BackGroundTask.this.dialog = dialog ;
            }


                @Override
        protected void onPreExecute() {
                //Set Dialog properties here ...

                                dialog.setTitle("Please Wait");
                dialog.setMessage("Loading ... ");
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