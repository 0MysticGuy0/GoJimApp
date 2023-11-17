package com.mygy.gojimapp.mainPage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.mygy.gojimapp.MainActivity;
import com.mygy.gojimapp.R;
import com.mygy.gojimapp.data.User;

import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    private ImageView tempIco;
    private Uri selectedIcoUri = null;
    private User usr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        usr = MainActivity.user;

        ImageButton backBtn = findViewById(R.id.settings_backBtn);
        backBtn.setOnClickListener(v -> {
            finish();
        });

        if(usr != null) {
            ImageView ico = findViewById(R.id.settings_ico);
            ico.setImageURI(usr.getIconUri());
            ico.setOnClickListener(v -> {
                tempIco = ico;
                ImagePicker.with(this)
                        .crop(1,1)	    			//Crop image(Optional), Check Customization for more option
                        .compress(512)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(512, 512)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            });

            TextView name = findViewById(R.id.settings_name);
            name.setText(usr.getName());

            TextView birthDateTxt = findViewById(R.id.settings_birthDateTxt);
            birthDateTxt.setText(usr.getFormattedBirthDate());

            ImageButton birthDateBtn = findViewById(R.id.settings_birthDateBtn);
            final Date[] selectedDate = new Date[1];
            birthDateBtn.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate[0] = new Date(year - 1900, month, dayOfMonth);
                        birthDateTxt.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
            });

            Button saveBtn = findViewById(R.id.settings_saveBtn);
            saveBtn.setOnClickListener(v -> {
                if(name.getText().toString().length() > 0) {
                    usr.setName(name.getText().toString());
                }else{
                    name.setText(usr.getName());
                    //Toast.makeText(this, "Имя не введено!", Toast.LENGTH_SHORT).show();
                }
                if (selectedDate[0] != null) {
                    usr.setBirthDate(selectedDate[0]);
                }else{
                    birthDateTxt.setText(usr.getFormattedBirthDate());
                    //Toast.makeText(this, "Дата рождения не введена!", Toast.LENGTH_SHORT).show();
                }
                    tempIco = ico;
                if (selectedIcoUri != null) {
                    usr.setIconUri(selectedIcoUri);
                }else{
                    ico.setImageURI(usr.getIconUri());
                    //Toast.makeText(this, "Фото профиля не выбрано!", Toast.LENGTH_SHORT).show();
                }
                    finish();
            });
        }else{
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            tempIco.setImageURI(uri);
            selectedIcoUri = uri;
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}