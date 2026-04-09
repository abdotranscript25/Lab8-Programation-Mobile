package com.example.labthreadsasynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    // ========== DÉCLARATION DES COMPOSANTS ==========
    private TextView texteStatut;
    private ProgressBar barreProgression;
    private ImageView imageView;
    private Handler gestionnaireUI;

    // Composants pour le calcul dynamique
    private EditText edtNombre1, edtNombre2;
    private RadioGroup radioGroupOperation;
    private Button btnCalculDynamique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Liaison des vues
        texteStatut = findViewById(R.id.txtStatus);
        barreProgression = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.img);

        Button btnChargerImage = findViewById(R.id.btnLoadThread);
        Button btnAfficherToast = findViewById(R.id.btnToast);

        // Liaison des composants calcul dynamique
        edtNombre1 = findViewById(R.id.edtNombre1);
        edtNombre2 = findViewById(R.id.edtNombre2);
        radioGroupOperation = findViewById(R.id.radioGroupOperation);
        btnCalculDynamique = findViewById(R.id.btnCalculDynamique);

        gestionnaireUI = new Handler(Looper.getMainLooper());

        // Bouton Toast (test réactivité)
        btnAfficherToast.setOnClickListener(v ->
                Toast.makeText(getApplicationContext(), "🍞 UI réactive !", Toast.LENGTH_SHORT).show()
        );

        // Bouton Charger image (Thread classique)
        btnChargerImage.setOnClickListener(v -> chargerImageAvecThread());

        // Bouton Calcul dynamique (AsyncTask)
        btnCalculDynamique.setOnClickListener(v -> lancerCalculDynamique());
    }

    // ==============================================================
    // PARTIE 1 : THREAD CLASSIQUE (Chargement image)
    // ==============================================================

    private void chargerImageAvecThread() {
        barreProgression.setVisibility(View.VISIBLE);
        barreProgression.setProgress(0);
        texteStatut.setText("📥 Statut : Chargement de l'image...");

        new Thread(() -> {
            // 1. Simulation de la progression (0 à 100%)
            for (int i = 0; i <= 100; i++) {
                final int progression = i;

                // Mettre à jour l'UI depuis le Worker Thread
                gestionnaireUI.post(() -> {
                    barreProgression.setProgress(progression);
                    texteStatut.setText("📥 Chargement image... " + progression + "%");
                });

                try {
                    Thread.sleep(15); // Pause de 15ms entre chaque incrément
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 2. Chargement réel de l'image (simulé ici)
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_test);

            // 3. Affichage final
            gestionnaireUI.post(() -> {
                imageView.setImageBitmap(bitmap);
                barreProgression.setVisibility(View.INVISIBLE);
                texteStatut.setText("✅ Statut : Image chargée avec succès !");
            });
        }).start();
    }

    // ==============================================================
    // PARTIE 2 : CALCUL DYNAMIQUE (AsyncTask)
    // ==============================================================

    private void lancerCalculDynamique() {
        String strNombre1 = edtNombre1.getText().toString().trim();
        String strNombre2 = edtNombre2.getText().toString().trim();

        if (TextUtils.isEmpty(strNombre1) || TextUtils.isEmpty(strNombre2)) {
            Toast.makeText(this, "Veuillez saisir deux nombres !", Toast.LENGTH_SHORT).show();
            return;
        }

        BigInteger nombre1 = new BigInteger(strNombre1);
        BigInteger nombre2 = new BigInteger(strNombre2);

        int operationId = radioGroupOperation.getCheckedRadioButtonId();
        String operation = "";
        if (operationId == R.id.radioAddition) operation = "+";
        else if (operationId == R.id.radioSoustraction) operation = "-";
        else if (operationId == R.id.radioMultiplication) operation = "*";
        else if (operationId == R.id.radioDivision) operation = "/";

        new CalculDynamiqueAsyncTask().execute(nombre1, nombre2, operation);
    }

    // ==============================================================
    // ASYNCTASK POUR CALCUL DYNAMIQUE
    // ==============================================================

    private class CalculDynamiqueAsyncTask extends AsyncTask<Object, Integer, String> {

        private BigInteger nombre1, nombre2;
        private String operation;
        private BigInteger resultat;

        @Override
        protected void onPreExecute() {
            barreProgression.setVisibility(View.VISIBLE);
            barreProgression.setProgress(0);
            texteStatut.setText(" Calcul dynamique en cours...");
        }

        @Override
        protected String doInBackground(Object... params) {
            nombre1 = (BigInteger) params[0];
            nombre2 = (BigInteger) params[1];
            operation = (String) params[2];

            // Exécution du calcul
            if (operation.equals("+")) {
                resultat = nombre1.add(nombre2);
            } else if (operation.equals("-")) {
                resultat = nombre1.subtract(nombre2);
            } else if (operation.equals("*")) {
                resultat = nombre1.multiply(nombre2);
            } else if (operation.equals("/")) {
                if (nombre2.equals(BigInteger.ZERO)) {
                    return "ERREUR: Division par zéro !";
                }
                resultat = nombre1.divide(nombre2);
            }

            // Simulation de progression (pour voir la barre)
            for (int i = 0; i <= 100; i++) {
                publishProgress(i);
                try { Thread.sleep(15); } catch (InterruptedException e) { }
            }

            return formaterResultat();
        }

        @Override
        protected void onProgressUpdate(Integer... valeurs) {
            barreProgression.setProgress(valeurs[0]);
            texteStatut.setText(" Calcul... " + valeurs[0] + "%");
        }

        @Override
        protected void onPostExecute(String resultatTexte) {
            barreProgression.setVisibility(View.INVISIBLE);
            texteStatut.setText("✅ " + resultatTexte);
        }

        private String formaterResultat() {
            String resultatStr = resultat.toString();
            if (resultatStr.length() > 50) {
                resultatStr = resultatStr.substring(0, 47) + "...";
            }
            return "Calcul terminé ! " + nombre1 + " " + operation + " " + nombre2 +
                    " = " + resultatStr + " (" + resultat.toString().length() + " chiffres)";
        }
    }
}