package com.example.a10118328_mrafiavisina_uts;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
/*      TANGGAL PENGERJAAN : JUMAT, 5 JUNI 2021
        NIM : 10118328
        NAMA : MUHAMMAD RAFI AVISINA
        KELAS : IF-8*/

public class AddActivity extends AppCompatActivity {

    com.example.a10118328_mrafiavisina_uts.DBHelper helper;
    EditText TxNama, txIsi_Kegiatan, txTglKegiatan;
    Spinner SpJK;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        helper = new com.example.a10118328_mrafiavisina_uts.DBHelper(this);

        id = getIntent().getLongExtra(com.example.a10118328_mrafiavisina_uts.DBHelper.row_id, 0);

        txTglKegiatan= (EditText)findViewById(R.id.txTglKegiatan_Add);
        TxNama = (EditText)findViewById(R.id.txNama_Add);
        SpJK = (Spinner)findViewById(R.id.spJK_Add);
        txIsi_Kegiatan = (EditText)findViewById(R.id.txIsi_kegiatan_Add);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        txTglKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                txTglKegiatan.setText(dateFormatter.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_add:
                String tanggal = txTglKegiatan.getText().toString().trim();
                String nama = TxNama.getText().toString().trim();
                String jk = SpJK.getSelectedItem().toString().trim();
                String kegiatan = txIsi_Kegiatan.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(com.example.a10118328_mrafiavisina_uts.DBHelper.row_tglkegiatan, tanggal);
                values.put(com.example.a10118328_mrafiavisina_uts.DBHelper.row_nama, nama);
                values.put(com.example.a10118328_mrafiavisina_uts.DBHelper.row_jk, jk);
                values.put(com.example.a10118328_mrafiavisina_uts.DBHelper.row_isikegiatan, kegiatan);

                if (  tanggal.equals("") || nama.equals("") || kegiatan.equals("")){
                    Toast.makeText(com.example.a10118328_mrafiavisina_uts.AddActivity.this, "Data ini tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else{
                    helper.insertData(values);
                    Toast.makeText(com.example.a10118328_mrafiavisina_uts.AddActivity.this, "Data Sudah Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
