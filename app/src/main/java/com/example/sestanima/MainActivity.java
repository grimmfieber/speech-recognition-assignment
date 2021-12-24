package com.example.sestanima;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

//Görev: kullanıcı telefona bir dediğinde ekrana X iki dediğinde O basan program.

public class MainActivity extends AppCompatActivity {

    ImageView speechButton;
    EditText speechText;
    TextView indicator;



    private static final int RECOGNIZER_RESULT = 1;


    //voice inputu ve handling
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK){ //eğer tanınmış ve result sağlanmış ise
            //voice intentinden text arrayi eldesi
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            speechText.setText(result.get(0).toString()); // speectext'in textine resultı string olarak kaydediyoruz.


            if(result.get(0).equals("bir") || Integer.parseInt(result.get(0)) == 1 ){ //birse ekrana X'i arayacak
                String number = "05412188611";
                Uri call = Uri.parse("tel:" + number);
                Intent callIntent1 = new Intent(Intent.ACTION_CALL, call);
                startActivity(callIntent1);
            }
            else if(result.get(0).equals("iki") || Integer.parseInt(result.get(0)) == 2){ //ikiyse ekrana X basacak
                String number = "05350224783";
                Uri call = Uri.parse("tel:" + number);
                Intent callIntent2 = new Intent(Intent.ACTION_CALL, call);
                startActivity(callIntent2);
            }
            else{
                indicator.setText("Bir veya iki demediniz.");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //activity_main'de designını yaptığımız contentleri variablelar ile eşliyoruz
        speechButton = findViewById(R.id.imageView);
        speechText = findViewById(R.id.editText);
        indicator = findViewById(R.id.indicator);


        speechButton.setOnClickListener(new View.OnClickListener() { //konuşma buttonuna tıklandığında
            @Override
            public void onClick(View v) {

                Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); //Kullanıcıdan konuşmasını isteyen intent nesnesi
                // ve bir speech recognizer aracılığıyla gönderecek bir intent initializationı
                speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); // dil modeli olacağını ve bunun free form olacağını belirtiyoruz
                //diğer bir form seçimi web_search olabilirdi
                speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Metne dönüşmesi için konuşun."); //Ekrana çıkacak prompt ve metni
                startActivityForResult(speechIntent,RECOGNIZER_RESULT); //result almak üzere speech intent ile activity başlatıp sonucu RECOGNIZER_RESULT'a veriyoruz.
            }
        });
    }
}