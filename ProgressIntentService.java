package clin.com.binding;


import android.Manifest;
import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Un {@link IntentService} que simula un proceso en primer plano
 * <p>
 */
public class ProgressIntentService extends IntentService {
    private static final String TAG = ProgressIntentService.class.getSimpleName();


    public ProgressIntentService() {
        super("ProgressIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Constants.ACTION_RUN_ISERVICE.equals(action)) {
                handleActionRun();
            }
        }
    }

    TimerTask timerTask;

    /**
     * Maneja la acción de ejecución del servicio
     */
    private void handleActionRun() {

        // Se construye la notificación
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_menu_directions)

                .setContentTitle("Coordenadas actuales")
                .setContentText("Procesando...");

        // Bucle de simulación
        final ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        final ActivityManager activityManager =
                (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        boolean stop = true;
        try {
            while (stop) {
                Intent localIntent = new Intent(Constants.ACTION_RUN_SERVICE)
                        .putExtra(Constants.EXTRA_MEMORY, "Procesando");
                ;
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                           if (myLocation == null) {
                               Criteria criteria = new Criteria();
                               criteria.setAccuracy(Criteria.ACCURACY_FINE);
                               criteria.setAltitudeRequired(false);
                               criteria.setBearingRequired(false);
                               criteria.setCostAllowed(true);
                               criteria.setPowerRequirement(Criteria.POWER_LOW);
                               String provider = lm.getBestProvider(criteria, true);
                               if (provider != null) {
                                   myLocation = lm.getLastKnownLocation(provider);
                               }
                           }
                           if (myLocation != null) {
                               Log.d(TAG, myLocation.getLatitude()+" "+myLocation.getLongitude());

                               builder.setContentText(myLocation.getLatitude()+" "+myLocation.getLongitude());
                              localIntent = new Intent(Constants.ACTION_RUN_SERVICE)
                                       .putExtra(Constants.EXTRA_MEMORY, myLocation.getLatitude()+" "+myLocation.getLongitude());

                           }
                       startForeground(1, builder.build());
                       // Emitir el intent a la actividad
                       LocalBroadcastManager.
                               getInstance(ProgressIntentService.this).sendBroadcast(localIntent);
                       Thread.sleep(300);
                   }
                    stopForeground(true);
        } catch (InterruptedException e) {
        e.printStackTrace();
    }
                // Quitar de primer plano

        }

        @Override
        public void onDestroy() {
            Toast.makeText(this, "Servicio destruido...", Toast.LENGTH_SHORT).show();

            // Emisión para avisar que se terminó el servicio
            Intent localIntent = new Intent(Constants.ACTION_PROGRESS_EXIT);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        }

}
