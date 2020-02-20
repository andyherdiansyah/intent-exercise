package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullname, email, password, confirm_password, homepage, about;
    private Bitmap bitmap;
    private ImageView image_profile;
    private static final String TAG = RegisterActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.text_fullname);
        email = findViewById(R.id.text_email);
        password = findViewById(R.id.text_password);
        confirm_password = findViewById(R.id.text_confirm_password);
        homepage = findViewById(R.id.text_homepage);
        about = findViewById(R.id.text_about);
        image_profile = findViewById(R.id.image_profile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }

        if (requestCode == 1) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    image_profile.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleOk(View view) {
        String fullnameText = fullname.getText().toString();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String confirmText = confirm_password.getText().toString();
        String homepageText = homepage.getText().toString();
        String aboutText = about.getText().toString();

        if(!(fullnameText).equals("") && !(emailText).equals("") && !(passwordText).equals("") && !(confirmText).equals("") && !(homepageText).equals("") && !(aboutText).equals("") && bitmap!= null){
            if((passwordText).equals(confirmText)){
                Intent intent = new Intent(this,ProfileActivity.class);
                intent.putExtra("FULLNAME_KEY", fullnameText);
                intent.putExtra("EMAIL_KEY", emailText);
                intent.putExtra("PASSWORD_KEY", passwordText);
                intent.putExtra("CONFIRM_KEY", confirmText);
                intent.putExtra("HOMEPAGE_KEY", homepageText);
                intent.putExtra("ABOUT_KEY", aboutText);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Password and Confirm password must same !",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Please insert all data !",Toast.LENGTH_SHORT).show();
        }
    }

    public void handleChangeAvatar(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
}