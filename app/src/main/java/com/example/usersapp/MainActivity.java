package com.example.usersapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.example.usersapp.api.UserClient;
import com.example.usersapp.api.UserResponse;
import com.example.usersapp.api.UserService;
import com.example.usersapp.room.DbUser;
import com.example.usersapp.user.User;
import com.example.usersapp.room.UserDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView ageTextView;
    private TextView emailTextView;
    private TextView cityTextView;
    private TextView countryTextView;
    private ImageView pictureImageView;

    private String userId;
    private String pictureUrl;
    private UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                        UserDatabase.class, "user-database")
                .build();

        firstNameTextView = findViewById(R.id.firstName);
        lastNameTextView = findViewById(R.id.lastName);
        ageTextView = findViewById(R.id.age);
        emailTextView = findViewById(R.id.email);
        cityTextView = findViewById(R.id.city);
        countryTextView = findViewById(R.id.country);
        pictureImageView = findViewById(R.id.userImage);

        fetchRandomUser();

        findViewById(R.id.nextUserButton).setOnClickListener(v -> fetchRandomUser());
        findViewById(R.id.addButton).setOnClickListener(v -> addUserToDatabase());
        findViewById(R.id.viewCollectionButton).setOnClickListener(v -> viewCollectionClicked());
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchRandomUser();
    }
    private void fetchRandomUser() {
        findViewById(R.id.nextUserButton).setEnabled(false);
        findViewById(R.id.addButton).setEnabled(false);
        findViewById(R.id.viewCollectionButton).setEnabled(false);
        Retrofit retrofit = UserClient.getClient();
        UserService userService = retrofit.create(UserService.class);

        Call<UserResponse> call = userService.getRandomUser();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User[] users = response.body().getResults();
                    if (users != null && users.length > 0) {
                        displayUserData(users[0]);
                        userId=users[0].login.uuid;
                        pictureUrl = users[0].picture.large;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch user", Toast.LENGTH_SHORT).show();
                }
                findViewById(R.id.nextUserButton).setEnabled(true);
                findViewById(R.id.addButton).setEnabled(true);
                findViewById(R.id.viewCollectionButton).setEnabled(true);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                firstNameTextView.setText("error");
                lastNameTextView.setText("error");
                ageTextView.setText("error");
                emailTextView.setText("error");
                cityTextView.setText("error");
                countryTextView.setText("error");
                Log.e("MainActivity", "Network call failed", t);
                Toast.makeText(MainActivity.this, "Network call failed", Toast.LENGTH_SHORT).show();
                findViewById(R.id.nextUserButton).setEnabled(true);
                findViewById(R.id.addButton).setEnabled(true);
                findViewById(R.id.viewCollectionButton).setEnabled(true);
            }
        });
    }

    private void displayUserData(User user) {
        firstNameTextView.setText(user.name.first);
        lastNameTextView.setText(user.name.last);
        ageTextView.setText(String.valueOf(user.dob.age));
        emailTextView.setText(user.email);
        cityTextView.setText(user.location.city);
        countryTextView.setText(user.location.country);

        Glide.with(this)
                .load(user.picture.large)
                .placeholder(R.drawable.ic_user_placeholder)
                .error(R.drawable.ic_user_placeholder)
                .into(pictureImageView);
    }

    private void addUserToDatabase() {
        String firstName = firstNameTextView.getText().toString();
        String lastName = lastNameTextView.getText().toString();
        int age = Integer.parseInt(ageTextView.getText().toString());
        String email = emailTextView.getText().toString();
        String city = cityTextView.getText().toString();
        String country = countryTextView.getText().toString();

        DbUser user = new DbUser();
        user.firstName = firstName;
        user.lastName = lastName;
        user.age = age;
        user.email = email;
        user.city = city;
        user.country = country;
        user.picture = pictureUrl;
        user.uid = userId;

        AsyncTask.execute(() -> {
            long result = db.userDao().insertUser(user);

            runOnUiThread(() -> {
                if (result == -1) {
                    Toast.makeText(MainActivity.this, "User already exists in database", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "User added to database", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }



    private void viewCollectionClicked() {
        Intent intent = new Intent(MainActivity.this, UsersActivity.class);
        startActivity(intent);
    }
}
