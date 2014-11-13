package com.example.taranair.rpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;


public class DestinationScreen extends Activity {

    Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_screen);
        editButton = (Button) findViewById(R.id.get_edit_view_button);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_destination_screen, menu);

        return true;
    }

    public void sendMessage2(View view)
    {
        EditText edit_text = (EditText) findViewById(R.id.edit_text);
        String edit_text_value = edit_text.getText().toString();

        //textView.setText(edit_text_value+"\n");
        Intent intent = new Intent(DestinationScreen.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("SearchText", edit_text_value);
        intent.putExtras(bundle);
        startActivity(intent);


    }

/*
    private void find_and_modify_text_view() {
        Button get_edit_view_button = (Button) findViewById(R.id.get_edit_view_button);
        get_edit_view_button.setOnClickListener(get_edit_view_button_listener);
    }

    private Button.OnClickListener get_edit_view_button_listener = new Button.OnClickListener() {
        public void onClick(View v) {
            EditText edit_text = (EditText) findViewById(R.id.edit_text);
            CharSequence edit_text_value = edit_text.getText();
            setTitle("EditText:" + edit_text_value);


        }
    };
    */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
