package fr.dizzz11.tpea_cours_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "fr.dizzz11.tpea.MESSAGE";

    static final String schoolListId = "hFJRXGSlLIAR5kna8eeyZ";
    static final String restaurantListId = "5ki7WvMrEtLCCjjIycKSHu";
    static final String streetListId = "FKETti2CVVfmBW36eytxH";
    static final String natureListId = "6q6Onjucq5QugAWui98rgu";

    private LinearLayoutCompat schoolCard, restaurantCard, streetCard, natureCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();

        schoolCard = (LinearLayoutCompat) findViewById(R.id.school);
        restaurantCard = (LinearLayoutCompat) findViewById(R.id.restaurant);
        streetCard = (LinearLayoutCompat) findViewById(R.id.street);
        natureCard = (LinearLayoutCompat) findViewById(R.id.nature);

        schoolCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(schoolListId);
            }
        });

        restaurantCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(restaurantListId);
            }
        });

        streetCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(streetListId);
            }
        });

        natureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(natureListId);
            }
        });

    }

    public void startActivity(String message)
    {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
