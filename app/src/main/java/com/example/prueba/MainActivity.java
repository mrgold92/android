package com.example.prueba;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    TextView resultado;
    TextView tele;
    TextView mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultado = (TextView) findViewById(R.id.textResultado);
        tele = (TextView) findViewById(R.id.textTelefono);
        mail = (TextView) findViewById(R.id.textEmail);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
                    } else {
                        Uri conractData = data.getData();
                        Cursor c = getContentResolver().query(conractData, null, null, null, null);

                        while (c.moveToNext()) {
                            String id = c.getString(c.getColumnIndex(ContactsContract.Data._ID));
                            resultado.setText(c.getString(c.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)));
                            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Data.HAS_PHONE_NUMBER));


                            if ((Integer.parseInt(hasPhone) == 1)) {
                                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
                                while (phones.moveToNext()) {
                                    tele.setText(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                                }
                                phones.close();
                            }

                            Cursor email = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);

                            while (email.moveToNext()) {
                                mail.setText(email.getString(email.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                            }

                            email.close();

                        }


                    }
                }
                break;

        }
    }

    public void ir(View v) {
        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(i, 1);

    }
}
