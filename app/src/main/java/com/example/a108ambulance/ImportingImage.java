package com.example.a108ambulance;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImportingImage extends AppCompatActivity {
    private Button select,upload,previous,next;
    private ImageSwitcher imageView;
    private Uri filepath;
    TextView total;
    int PICK_IMAGE_MULTIPLE = 1;
    String image_encoded;
    ArrayList<Uri> mArrayUri;
    int position = 0;
    List<String> imagesEncodedList;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    private int GALLERY = 1,CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importingimage);
        //ActionBar actionBar;
        //actionBar = getSupportActionBar();
        total = findViewById(R.id.txt);
        imageView = findViewById(R.id.imageswitcher);
        select = findViewById(R.id.select);
        upload = findViewById(R.id.upload);
        previous = findViewById(R.id.previous);
        next=findViewById(R.id.next);
        mArrayUri = new ArrayList<Uri>();
        imageView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView1 = new ImageView(getApplicationContext());
                return imageView1;
            }
        });


        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < mArrayUri.size() - 1)
                {
                    position++;
                    imageView.setImageURI(mArrayUri.get(position));
                }else {
                    Toast.makeText(ImportingImage.this,"Last Image Already Shown",Toast.LENGTH_SHORT).show();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>0){
                    position--;
                    imageView.setImageURI(mArrayUri.get(position));
                }
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_MULTIPLE);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();

            }
        });


    }
    /*private void SelectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_MULTIPLE);
        //startActivityForResult(Intent.createChooser(intent,"Select Image From here"),PICK_IMAGE_REQUEST);
       /* AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };

        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();*/
    /*public void choosePhotoFromGallery() {
        Intent intent = new Intent();

        startActivityForResult(Intent.createChooser(intent,"Select Picture"), GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_MULTIPLE && resultCode==RESULT_OK && data!=null )
        {
           if(data.getClipData() != null){
               ClipData clipData = data.getClipData();
               int cout = data.getClipData().getItemCount();
               for (int i = 0; i<cout; i++)
               {
                   Uri imageurl = data.getClipData().getItemAt(i).getUri();
                   mArrayUri.add(imageurl);

               }
               imageView.setImageURI(mArrayUri.get(0));
               position=0;
            }else {
               filepath = data.getData();
               Uri imageurl = data.getData();
               mArrayUri.add(imageurl);
               imageView.setImageURI(mArrayUri.get(0));
               position=0;
           }

        }else {
            Toast.makeText(ImportingImage.this,"You haven't picked image",Toast.LENGTH_SHORT).show();
            /*else if (requestCode == CAMERA) {
            Uri imageurl = data.getData();
            mArrayUri.add(imageurl);
            imageView.setImageURI(mArrayUri.get(0));
        }*/
        }
    }
    private  void UploadImage()
    {
        if(filepath!=null)
        {
            ProgressDialog progressDialog = new ProgressDialog(ImportingImage.this);
            progressDialog.setTitle("Uploading.....");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(ImportingImage.this,"Image Uploaded",Toast.LENGTH_SHORT).show();

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ImportingImage.this,"Failed to add",Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded" + (int) progress + "%");
                        }
                    });

        }
    }
}