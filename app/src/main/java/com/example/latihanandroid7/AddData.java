package com.example.latihanandroid7;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.latihanandroid7.dataController.HttpHandler;

public class AddData extends AppCompatActivity implements View.OnClickListener {

    DatePicker picker;
    EditText etjudul, ettanggal, etdesc, etpenulis;
    Button btnKirim;

    String judul, tanggal, desc, penulis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        etjudul = findViewById(R.id.et_judul);
        etpenulis = findViewById(R.id.et_penulis);
        ettanggal = findViewById(R.id.et_tanggal);
        etdesc = findViewById(R.id.et_keterangan);
        picker = findViewById(R.id.datePicker);
        btnKirim = findViewById(R.id.kirim);

        ettanggal.setText(getCurrentDate());
        ettanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    picker.setVisibility(View.VISIBLE);
                    ettanggal.setText(getCurrentDate());
                } else {
                    picker.setVisibility(View.GONE);
                    ettanggal.setText(getCurrentDate());
                }
            }
        });
        btnKirim.setOnClickListener(this);
    }

    public String getCurrentDate() {
        StringBuilder builder = new StringBuilder();
        String date = String.valueOf(picker.getDayOfMonth());
        String month = String.valueOf(picker.getMonth() + 1);
        String year = String.valueOf(picker.getYear());
        if (date.length() < 2) {
            date = "0" + date;
        }
        if (month.length() < 2) {
            month = "0" + month;
        }
        builder.append(date + "-");
        builder.append(month + "-");
        builder.append(year);
        return builder.toString();
    }

    @Override
    public void onClick(View v) {
        cekInput();
    }

    public void cekInput() {
        judul = etjudul.getText().toString();
        penulis = etpenulis.getText().toString();
        tanggal = ettanggal.getText().toString();
        desc = etdesc.getText().toString();

        if (judul == null || judul.equals("")
                || penulis == null || penulis.equals("")
                || tanggal == null || tanggal.equals("")
                || desc == null || desc.equals("")
        ) {
            Toast.makeText(AddData.this, "Please fill all data", Toast.LENGTH_SHORT).show();
        } else {
            new TambahData().execute();
        }
    }

    private class TambahData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String judul_ = judul.replaceAll(" ", "%20");
            String penulis_ = penulis.replaceAll(" ", "%20");
            String tanggal_ = tanggal.replaceAll(" ", "%20");
            String desc_ = desc.replaceAll(" ", "%20");
            HttpHandler sh = new HttpHandler();
            String url = "http://172.16.10.2/master/login.php?" +
                    "judul=" + judul_ +
                    "&penulis=" + penulis_ +
                    "&tanggal=" + tanggal_ +
                    "&desc=" + desc_;

            String response = sh.makeServiceCall(url);
            Log.e("DATASERVER", "    Data dari server :\n" + response);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(AddData.this, TampilData.class));
            finish();
        }
    }
}
