package activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.csaladireceptknyv.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Sikeres regisztráció üzenetének ellenőrzése
        String successMessage = getIntent().getStringExtra("success_message");
        if (successMessage != null) {
            Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show();
        }

        mAuth = FirebaseAuth.getInstance();
        // Ellenőrizzük, hogy a felhasználó be van-e jelentkezve
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Ha nincs bejelentkezve, átirányítjuk a Bejelentkezés képernyőre (vagy Regisztráció)
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class); // Vagy a regisztrációs képernyő
            startActivity(intent);
            finish(); // Az aktív képernyőt bezárjuk
        } else {
            // Ha be van jelentkezve, megjelenítjük a fő képernyőt
            getIntent().getStringExtra("success_message");
            if (successMessage != null) {
                Toast.makeText(this, successMessage, Toast.LENGTH_LONG).show();
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void login(View view) {
        EditText userName = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);


        String userNamestr = userName.getText().toString();
        String passwordstr = password.getText().toString();

        Intent intent = new Intent(MainActivity.this, cookBookActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }


}