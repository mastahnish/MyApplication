package com.example.piotr.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OptionActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button buttonSAVE, buttonDISPLAY, buttonEDIT, buttonDELETE;
    EditText produkt, ilosc, cena, id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        db = new DatabaseHelper(this);
        buttonSAVE = (Button) findViewById(R.id.buttonSAVE);
        buttonDISPLAY = (Button) findViewById(R.id.buttonDISPLAY);
        buttonEDIT = (Button) findViewById(R.id.buttonEDIT);
        buttonDELETE = (Button) findViewById(R.id.buttonDELETE);
        produkt = (EditText) findViewById(R.id.produkt);
        ilosc = (EditText) findViewById(R.id.ilosc);
        cena = (EditText) findViewById(R.id.cena);
        id = (EditText) findViewById(R.id.id);


        buttonDISPLAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteCursor kursor = db.pobierzdane();
                if (kursor.getCount() > 0) {
                    StringBuffer buff = new StringBuffer();
                    while (kursor.moveToNext()) {
                        buff.append("Produkt NR: " + kursor.getString(0) + "\n");
                        buff.append("Produkt: " + kursor.getString(1) + "\n");
                        buff.append("Ilość: " + kursor.getString(2) + "\n");
                        buff.append("Cena: " + kursor.getString(3) + "\n");

                    }
                    Wyswietl("rekord", buff.toString());
                } else {
                    Wyswietl("Brak", "Niestety brak rekordw");
                }
            }
        });


        buttonSAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long productId = 0;

                String product = produkt.getText().toString();
                String quantity = ilosc.getText().toString();
                String price = cena.getText().toString();


                productId = db.wstawdane(product, quantity, price);


                if (productId != -1) {
                    Toast.makeText(OptionActivity.this, "Udało się", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OptionActivity.this, "NIE udało się", Toast.LENGTH_LONG).show();
                }

                sendIntent(productId, product, quantity, price);
            }
        });


        buttonEDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean czysieudalo;
                czysieudalo = db.aktualizuj(id.getText().toString(), produkt.getText().toString(), ilosc.getText().toString(), cena.getText().toString());

                if (czysieudalo) {
                    Toast.makeText(OptionActivity.this, "Udało się", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(OptionActivity.this, "NIE udało się", Toast.LENGTH_LONG).show();


            }
        });

        buttonDELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db.usun(id.getText().toString())) {
                    Toast.makeText(OptionActivity.this, "Udało się", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(OptionActivity.this, "NIE udało się", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void sendIntent(long productId, String product, String quantity, String price) {

        final Intent intent = new Intent();
        intent.setAction("com.example.piotr.myapplication.show.product");
//        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("PRODUCT_ID_KEY", productId);
        intent.putExtra("PRODUCT_KEY", product);
        intent.putExtra("QUANTITY_KEY", quantity);
        intent.putExtra("PRICE_KEY", price);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    public void Wyswietl(String tytul, String wiadomosc) {
        AlertDialog.Builder budowiczy = new AlertDialog.Builder(this);
        budowiczy.setCancelable(true);
        budowiczy.setMessage(wiadomosc);
        budowiczy.setTitle(tytul);
        budowiczy.show();
    }

}

