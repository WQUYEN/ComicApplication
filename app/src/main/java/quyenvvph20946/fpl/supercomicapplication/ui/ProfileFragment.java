package quyenvvph20946.fpl.supercomicapplication.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import quyenvvph20946.fpl.supercomicapplication.R;
import quyenvvph20946.fpl.supercomicapplication.RetrofitIntance;
import quyenvvph20946.fpl.supercomicapplication.ui.SplashActivity;
import quyenvvph20946.fpl.supercomicapplication.api.UserApi;
import quyenvvph20946.fpl.supercomicapplication.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private TextView tv_username, tv_fullname, tv_email;
    private TextView dl_tv_username, dl_tv_password, dl_tv_email, dl_tv_fullname,dl_tv_avatarUrl;
    private ImageView img_avatar;
    private Button btn_edit, btn_logout;
    private Context context;
    private UserApi userApi;
    private User userData;
    private SharedPreferences sharedPreferences;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        context = getActivity();
        userApi = RetrofitIntance.getApiUser();

        tv_username = view.findViewById(R.id.profile_tv_username);
        tv_fullname = view.findViewById(R.id.profile_tv_fullname);
        tv_email = view.findViewById(R.id.profile_tv_email);
        btn_edit = view.findViewById(R.id.profile_btn_edit);
        btn_logout = view.findViewById(R.id.profile_btn_logout);
        img_avatar =view.findViewById(R.id.img_profile_avatar);

        // Khởi tạo sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //Lấy comicId
        userId = sharedPreferences.getString("userId", "");

        getUserById();
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit User");

                // Inflate the layout for the dialog
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_user, null);
                builder.setView(view);

                dl_tv_username = view.findViewById(R.id.dialog_profile_ed_Username);
                dl_tv_password = view.findViewById(R.id.dialog_profile_ed_Password);
                dl_tv_email = view.findViewById(R.id.dialog_profile_ed_Email);
                dl_tv_fullname = view.findViewById(R.id.dialog_profile_ed_Fullname);
                dl_tv_avatarUrl = view.findViewById(R.id.dialog_profile_ed_AvatarUrl);

                // Set initial values for the EditText fields
                dl_tv_username.setText(userData.getUsername());
                dl_tv_password.setText(userData.getPassword());
                dl_tv_email.setText(userData.getEmail());
                dl_tv_fullname.setText(userData.getFullname());
                dl_tv_avatarUrl.setText(userData.getAvatarUrl());

                // Set the positive button action (Save button)
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the updated values from the EditText fields
                        String username = dl_tv_username.getText().toString();
                        String password = dl_tv_password.getText().toString();
                        String email = dl_tv_email.getText().toString();
                        String fullname = dl_tv_fullname.getText().toString();
                        String avatarUrl = dl_tv_avatarUrl.getText().toString();
                        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || fullname.isEmpty() || avatarUrl.isEmpty()) {
                            Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            User user = new User(username, password, email, fullname,avatarUrl);
                            Call<User> callUpdate = userApi.updateUsers(userId, user);
                            callUpdate.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                                    getUserById();
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });

                // Set the negative button action (Cancel button)
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });

                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent1);
            }
        });

        return view;
    }
    private void getUserById(){
        Call<User> callUserById = userApi.getUserById(userId);
        callUserById.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userData = response.body();
                tv_username.setText(userData.getUsername());
                tv_fullname.setText(userData.getFullname());
                tv_email.setText(userData.getEmail());
                Picasso.get()
                        .load(userData.getAvatarUrl())
                        .error(R.drawable.ic_account_circle)
                        .into(img_avatar);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}