package com.example.latihanandroid7;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.latihanandroid7.dataController.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailData extends AppCompatActivity {

    TextView tvjudul, tvtanggal, tvpenulis, tvdesc;
    Context ctx;
    String datajudul, urldata, judul, penulis, tahun_terbit, keterangan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_data);
        ctx = this;

        Bundle b = getIntent().getExtras();
        if (b != null) {
            datajudul = b.getString("judul");
        } else {
            Toast.makeText(ctx, "Data kosong", Toast.LENGTH_SHORT).show();
        }
        String judul_ = datajudul.replaceAll(" ", "%20");
        urldata = "http://172.16.10.2/master/detaildata.php?judul=" + judul_;

        tvjudul = findViewById(R.id.tvjudul);
        tvtanggal = findViewById(R.id.tvtanggal);
        tvpenulis = findViewById(R.id.tvpenulis);
        tvdesc = findViewById(R.id.tvdesc);
        new getDetailData().execute();
    }

    private class getDetailData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(urldata);
            Log.e("DetailData", "Response From URL : " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject json = new JSONObject(jsonStr);
                    JSONArray data = json.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);

                        judul = c.getString("judul");
                        penulis = c.getString("penulis");
                        keterangan = c.getString("deskripsi");
                        tahun_terbit = c.getString("tahun_terbit");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvjudul.setText(judul);
            tvpenulis.setText(penulis);
            tvdesc.setText(keterangan);
            tvtanggal.setText(tahun_terbit);
        }
    }
}
