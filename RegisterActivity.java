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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // Firebase Authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register); // Activity layout beállítása

        mAuth = FirebaseAuth.getInstance(); // FirebaseAuth inicializálása

        // Rendszerbarok kezelése, hogy ne takarják el az UI-t
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Regisztrációs metódus
    public void register(View view) {
        // Az EditText-ek lekérése
        EditText emailEditText = findViewById(R.id.username); // Az email mező
        EditText passwordEditText = findViewById(R.id.password); // A jelszó mező

        // A felhasználói adatokat kinyerjük
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Ellenőrizzük, hogy a mezők üresek-e
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Kérlek töltsd ki az összes mezőt!", Toast.LENGTH_SHORT).show();
            return;
        }


        // Regisztráció a Firebase Authentication-nál
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Ha sikeres a regisztráció, a felhasználó adatai
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.putExtra("success_message", "Sikeres regisztráció" + user.getEmail());
                       startActivity(intent);
                        finish();
                    } else {
                        // Ha a regisztráció nem sikerült
                        Toast.makeText(RegisterActivity.this, "Regisztrációs hiba: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
