package com.faizanfirstproject.letschat.userData;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.faizanfirstproject.letschat.MainActivity;
import com.faizanfirstproject.letschat.R;
import com.faizanfirstproject.letschat.databinding.ActivityUserProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;


public class UserProfile extends AppCompatActivity {
    ActivityUserProfileBinding binding;

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    ActivityResultLauncher<String> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_profile);
        binding=ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // checking user cred
        auth=FirebaseAuth.getInstance();

        storage=FirebaseStorage.getInstance();

        database=FirebaseDatabase.getInstance();

        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                   binding.userImageView.setImageURI(uri);

                   final StorageReference reference=storage.getReference().child("profile").child(auth.getCurrentUser().getUid());
                   reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   database.getReference().child("user").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void unused) {
                                           Toast.makeText(getApplicationContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                                       }
                                   });
                               }
                           });
                       }
                   });
            }
        });

        binding.userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 launcher.launch("image/*");
            }
        });
//        binding.userImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // by this intent we got a reference of one image which we select
//                Intent intent=new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,45);
//            }
//        });
//        binding.userSetUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name=binding.userName.getText().toString();
//                if(name.isEmpty()){
//                    binding.userName.setError("Please type a name");
//                    return;
//                }
//
//                if(selectedImage!=null){
//                    // it basically create folder "ProfileImage" in database and create unique userid and store that data to it
//                    StorageReference reference=storage.getReference()
//                            // by this we are creating folder
//                            .child("ProfileImage")
//                            // by this we are giving name to image as id
//                            .child(Objects.requireNonNull(auth.getUid()));
//
//                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                            if(task.isSuccessful()){
//                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//                                        String imageUri= uri.toString();
//
//                                        String UId= auth.getUid();
//                                        String phoneNumber = Objects.requireNonNull(auth.getCurrentUser()).getPhoneNumber();
//                                        String name = binding.userName.getText().toString();
//
//                                        UserModel user= new UserModel(UId,name,phoneNumber,imageUri);
//
//                                        // adding to database
//                                        database.getReference()
//                                                .child("users")
//                                                .setValue(user)
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override
//                                                    public void onSuccess(Void unused) {
//                                                        Intent intent=new Intent(UserProfile.this, MainActivity.class);
//                                                        startActivity(intent);
//                                                        finish();
//                                                    }
//                                                });
//                                    }
//                                });
//                            }
//                        }
//                    });
//
//                }else{
//                    String uid= auth.getUid();
//                    String phone= auth.getCurrentUser().getPhoneNumber();
//
//                    UserModel user= new UserModel(uid,name,phone,"No Image");
//
//                    database.getReference()
//                            .child("users")
//                            .child(uid)
//                            .setValue(user)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Intent intent =new Intent(UserProfile.this,MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            });
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(data!=null){
//            if(data.getData() != null){
//                binding.userImageView.setImageURI(data.getData());
//                selectedImage = data.getData();
//            }
//        }
    }
}