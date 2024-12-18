package com.example.bulle;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bulle.R;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;

    private TextView textX;
    private TextView textY;
    private TextView textZ;
    private TextView levelIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textX = findViewById(R.id.axeX);
        textY = findViewById(R.id.axeY);
        textZ = findViewById(R.id.axeZ);
        levelIndicator = findViewById(R.id.sensorTextView);

        // Initialisation du gestionnaire de capteurs
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Enregistrement des capteurs
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Désenregistrement des capteurs
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event == null) return;

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                // Accéléromètre : fournit les axes X, Y, Z
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                textX.setText(String.format("X: %.2f", x));
                textY.setText(String.format("Y: %.2f", y));
                textZ.setText(String.format("Z: %.2f", z));

                // Calcul de l'inclinaison en utilisant X et Y
                double inclination = Math.atan2(y, x) * (180 / Math.PI);
                levelIndicator.setText(String.format("Inclinaison : %.2f°", inclination));
                break;

            case Sensor.TYPE_GYROSCOPE:
                // Gyroscope pour ajouter des informations sur l'orientation
                // Pour l'exemple, nous allons juste afficher la vitesse de rotation
                float rotationX = event.values[0];
                float rotationY = event.values[1];
                float rotationZ = event.values[2];
                // Vous pouvez utiliser ces valeurs pour améliorer le calcul de l'orientation.
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ne pas gérer l'exactitude des capteurs ici
    }
}
