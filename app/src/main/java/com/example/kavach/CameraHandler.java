package com.example.kavach;
import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraHandler {

    private ImageCapture imageCapture;
    private StorageReference storageRef;
   private  SmsHandler smsHandler;
    private ExecutorService cameraExecutor;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private FirebaseStorage storage;
    private LifecycleOwner lifecycleOwner;

    public CameraHandler(Context context, LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(context);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        startCamera(context);
    }

    public void startCamera(Context context) {
        cameraProviderFuture.addListener(() -> {
            try {
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                Preview preview = new Preview.Builder().build();

                imageCapture = new ImageCapture.Builder().build();

                // Bind the camera use cases
                Camera camera = cameraProviderFuture.get().bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(context));
    }

    public void capturePhoto(Context context) {
        File outputDirectory = context.getExternalFilesDir(null);
        File photoFile = new File(outputDirectory, "IMG_" + System.currentTimeMillis() + ".jpg");

        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(
                outputFileOptions,
                cameraExecutor,
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                        String savedUri = photoFile.getAbsolutePath();
                        uploadImageToFirebase(photoFile, context);
                        Log.d("CameraActivity", "Image saved: " + savedUri);
                    }

                    @Override
                    public void onError(ImageCaptureException exception) {
                        Log.e("CameraActivity", "Error capturing image: " + exception.getMessage());
                    }
                }
        );
    }

    public void uploadImageToFirebase(File imageFile, Context context) {
        Uri fileUri = Uri.fromFile(imageFile);
        StorageReference ref = storageRef.child(imageFile.getName());
        Log.d("CameraActivity", "Image uploading from : " + fileUri.toString());

        UploadTask uploadTask = ref.putFile(fileUri);

        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            // Continue with the task to get the download URL
            return ref.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                Log.d("Firebase", "Image download URL: " + downloadUri);
                smsHandler.sendSMS(context, downloadUri.toString(), Manifest.permission.SEND_SMS);
                // You can use the download URL as needed
            } else {
                Log.e("Firebase", "Upload failed: " + task.getException());
            }
        });
    }
}
