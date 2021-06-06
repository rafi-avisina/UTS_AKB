package com.example.a10118328_mrafiavisina_uts;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
/*      TANGGAL PENGERJAAN : JUMAT, 5 JUNI 2021
        NIM : 10118328
        NAMA : MUHAMMAD RAFI AVISINA
        KELAS : IF-8*/

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    DBHelper helper;
    ListView listView;
    LayoutInflater inflaterr;
    View dialogView;
    TextView Tv_Nama, Tv_JK, Tv_Tanggal, Tv_IsiKegiatan;

    public ListFragment() {
        // Required empty public constructor
    }

    public void setListView(){
        Cursor cursor = helper.allData();
        CostumCursorAdapter customCursorAdapter = new CostumCursorAdapter(requireContext(), cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onResume() {
        setListView();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        helper = new DBHelper(requireContext());

        listView = view.findViewById(R.id.list_data);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView getId = (TextView)view.findViewById(R.id.listID);
//                final long id = Long.parseLong(getId.getText().toString());
                final Cursor cur = helper.oneData(id);
                cur.moveToFirst();

                final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Pilih Opsi");

                //Add a list
                String[] options = {"Lihat Data", "Edit Data", "Hapus Data"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                final AlertDialog.Builder viewData = new AlertDialog.Builder(requireContext());
                                inflaterr = getLayoutInflater();
                                dialogView = inflaterr.inflate(R.layout.view_data, null);
                                viewData.setView(dialogView);
                                viewData.setTitle("Lihat Data");


                                Tv_Nama = (TextView)dialogView.findViewById(R.id.tv_Nama);
                                Tv_Tanggal = (TextView)dialogView.findViewById(R.id.tv_Tanggal);
                                Tv_JK = (TextView)dialogView.findViewById(R.id.tv_JK);
                                Tv_IsiKegiatan = (TextView)dialogView.findViewById(R.id.tv_IsiKegiatan);

                                Tv_Nama.setText("Nama: " + cur.getString(cur.getColumnIndex(DBHelper.row_nama)));
                                Tv_Tanggal.setText("Tanggal Kegiatan: " + cur.getString(cur.getColumnIndex(DBHelper.row_tglkegiatan)));
                                Tv_JK.setText("Jenis Kelamin: " + cur.getString(cur.getColumnIndex(DBHelper.row_jk)));
                                Tv_IsiKegiatan.setText("Isi Kegiatan: " + cur.getString(cur.getColumnIndex(DBHelper.row_isikegiatan)));

                                viewData.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                viewData.show();
                        }
                        switch (which){
                            case 1:
                                Intent iddata = new Intent(requireContext(), EditActivity.class);
                                iddata.putExtra(DBHelper.row_id, id);
                                startActivity(iddata);
                        }
                        switch (which){
                            case 2:
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(requireContext());
                                builder1.setMessage("Data ini akan dihapus.");
                                builder1.setCancelable(true);
                                builder1.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        helper.deleteData(id);
                                        Toast.makeText(requireContext(), "Data Terhapus", Toast.LENGTH_SHORT).show();
                                        setListView();
                                    }
                                });
                                builder1.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alertDialog = builder1.create();
                                alertDialog.show();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }
}