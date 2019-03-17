package com.example.latihanandroid7;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.latihanandroid7.dataController.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TampilData extends AppCompatActivity {

    ArrayList<HashMap<String, String>> datalist;
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataview);
        datalist = new ArrayList<>();
        lv = findViewById(R.id.lv);
        new GetDataFromServer().execute();
    }

    private class GetDataFromServer extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String url = "http://172.16.10.2/master/showdata.php";

            String jsonStr = sh.makeServiceCall(url);
            Log.e("DATASERVER", "    Data dari server :\n" + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject c = array.getJSONObject(i);
                        String judul = c.getString("judul");
                        String penulis = c.getString("penulis");
                        String tahun = c.getString("tahun_terbit");
                        HashMap<String, String> buku = new HashMap<>();

                        buku.put("judul", judul);
                        buku.put("penulis", penulis);
                        buku.put("tahun_terbit", tahun);
                        datalist.add(buku);
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
            ListAdapter adapter = new SimpleAdapter(
                    getApplicationContext(),
                    datalist,
                    R.layout.for_data_list,
                    new String[]{"judul", "penulis", "tahun_terbit"},
                    new int[]{R.id.tv_judul, R.id.tv_penulis, R.id.tv_terbit});
            lv.setAdapter(adapter);
        }
    }
}
