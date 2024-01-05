package quyenvvph20946.fpl.supercomicapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import quyenvvph20946.fpl.supercomicapplication.MainActivity;
import quyenvvph20946.fpl.supercomicapplication.R;
import quyenvvph20946.fpl.supercomicapplication.RetrofitIntance;
import quyenvvph20946.fpl.supercomicapplication.api.UserApi;
import quyenvvph20946.fpl.supercomicapplication.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserApi userApi;
    private User user;
    private EditText ed_username, ed_password;
    private TextView tv_register;
    private Button btn_login;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        ed_username = findViewById(R.id.ed_login_username);
        ed_password = findViewById(R.id.ed_login_password);
        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_login_register);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_username.getText().toString();
                String password = ed_password.getText().toString();

                // Kiểm tra xem các trường đăng nhập có được nhập đầy đủ hay không
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }
                user = new User();
                user.setUsername(username);
                user.setPassword(password);
                userApi = RetrofitIntance.getApiUser();
                Call<User> call = userApi.login(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();
                        if (response.isSuccessful() && user != null) {
                            Log.d("GGGGG", "onResponse: " + user.getUsername());
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            // Lưu thông tin người dùng vào SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userId", user.get_id());
                            // Thêm bất kỳ thông tin người dùng khác mà bạn muốn lưu
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("GGGGG", "onFailure: lỗi đăng nhập");
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}