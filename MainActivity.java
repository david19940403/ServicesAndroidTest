package clin.com.binding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import clin.com.binding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    User user = new User("Test", "User");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setUser(user);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{

            Intent intent = new Intent(this, ProgressIntentService.class);
            intent.setAction(Constants.ACTION_RUN_ISERVICE);
            startService(intent);

        }

        // Filtro de acciones que serán alertadas
        IntentFilter filter = new IntentFilter(
                Constants.ACTION_RUN_ISERVICE);
        filter.addAction(Constants.ACTION_RUN_SERVICE);
        filter.addAction(Constants.ACTION_RUN_LOCATIONSERVICE);
        // Crear un nuevo ResponseReceiver
        ResponseReceiver receiver =
                new ResponseReceiver();
        // Registrar el receiver y su filtro
        LocalBroadcastManager.getInstance(this).registerReceiver(
                receiver,
                filter);

    }
    // Broadcast receiver que recibe las emisiones desde los servicios
    private class ResponseReceiver extends BroadcastReceiver {
        // Sin instancias
        private ResponseReceiver() {

        }
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.ACTION_RUN_SERVICE:
                    user.setFirstName(intent.getStringExtra(Constants.EXTRA_MEMORY).split(" ")[0]);
                    user.setLastName(intent.getStringExtra(Constants.EXTRA_MEMORY).split(" ")[1]);
                    break;
                case Constants.ACTION_RUN_LOCATIONSERVICE:
                    break;
            }
        }
    }
    }

