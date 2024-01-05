package quyenvvph20946.fpl.supercomicapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import quyenvvph20946.fpl.supercomicapplication.R;
import quyenvvph20946.fpl.supercomicapplication.RetrofitIntance;
import quyenvvph20946.fpl.supercomicapplication.api.UserApi;
import quyenvvph20946.fpl.supercomicapplication.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText ed_username,ed_password,ed_email,ed_fullname,ed_avatarUrl;
    private Button btn_register, btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed_username = findViewById(R.id.register_ed_Username);
        ed_password = findViewById(R.id.register_ed_Password);
        ed_email = findViewById(R.id.register_ed_Email);
        ed_fullname = findViewById(R.id.register_ed_Email);
        ed_avatarUrl = findViewById(R.id.register_ed_avatarUrl);
        btn_register = findViewById(R.id.register_btn_Register);
        btn_back = findViewById(R.id.register_btn_back);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_username.getText().toString();
                String password = ed_password.getText().toString();
                String email = ed_email.getText().toString();
                String fullname = ed_fullname.getText().toString();
                String avatarUrl = ed_avatarUrl.getText().toString();
                if (username.isEmpty()||password.isEmpty()||email.isEmpty()||fullname.isEmpty()||avatarUrl.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    User user = new User(username,password,email,fullname,avatarUrl);
                    UserApi userApi = RetrofitIntance.getApiUser();
                    Call<User> call = userApi.register(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "Register new Account Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Register new Account Failed", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "API connect Failed", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
            }
        });

    }
}