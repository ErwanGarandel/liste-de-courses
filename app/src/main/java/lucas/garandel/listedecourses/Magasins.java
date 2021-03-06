package lucas.garandel.listedecourses;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.MagasinAdapter;
import adapter.MagasinLongAdapter;
import model.Magasin;

public class Magasins extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD = 123;
    public static final int REQUEST_CODE_EDIT = 111;
    public static final String NOM_MAGASIN = "REPONSE";
    public static final String OLD_NOM_MAGASIN = "OLD";   // QUESTION : LA VALEUR EST ELLE IMPORTANTE ???

    // Définition statique des adapters (pour les faire varier)


    ListView liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magasins);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addMag = new Intent(Magasins.this, AjoutMagasin.class);
                addMag.putExtra("requestCode", REQUEST_CODE_ADD);
                startActivityForResult(addMag, REQUEST_CODE_ADD);
            }
        });

        liste = (ListView)findViewById(R.id.listMag);
        /*
            Action au click simple sur un item :
         */

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editMag = new Intent(Magasins.this, AjoutMagasin.class);
                String old = (String)parent.getItemAtPosition(position);
                editMag.putExtra(OLD_NOM_MAGASIN,old);
                editMag.putExtra("requestCode",REQUEST_CODE_EDIT);
                startActivityForResult(editMag, REQUEST_CODE_EDIT);
            }
        });
        // Construct the data source
        ArrayList<Magasin> arrayOfMags = Magasin.getData();
        // Create the adapter to convert the array to views
        MagasinAdapter adapter = new MagasinAdapter(this, arrayOfMags);
        final MagasinLongAdapter longAdapter = new MagasinLongAdapter(this, arrayOfMags);
        // Attach the adapter to a ListView
        liste.setAdapter(adapter);

        /*
            Action au click long sur un item :
         */
        liste.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                liste.setAdapter(longAdapter);
                return true;
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case(Magasins.REQUEST_CODE_ADD):
                switch(resultCode){
                    case (Activity.RESULT_OK):
                        String result = intent.getStringExtra(Magasins.NOM_MAGASIN);

                        //AFFICHAGE DE TEST DU RETOUR D'ACTIVITE
                        Snackbar.make(findViewById(R.id.activity_magasin), "Ajout : "+result, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        //QUESTION : Comment ajouter la valeur ??

                        return;

                    case (Activity.RESULT_CANCELED):
                        return;
                }
            case(Magasins.REQUEST_CODE_EDIT):
                switch(resultCode){
                case (Activity.RESULT_OK):
                    String result = intent.getStringExtra(Magasins.NOM_MAGASIN);

                    //AFFICHAGE DE TEST DU RETOUR D'ACTIVITE
                    Snackbar.make(findViewById(R.id.activity_magasin), "Edit : "+result, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    //QUESTION : Comment editer la valeur ??

                    return;

                case (Activity.RESULT_CANCELED):
                    return;
            }
        }
    }
}
