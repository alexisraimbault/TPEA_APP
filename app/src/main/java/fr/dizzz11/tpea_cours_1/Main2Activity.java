package fr.dizzz11.tpea_cours_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class Main2Activity extends AppCompatActivity {
    private ImageView imageView, btnChoose, btnNext, btnQuestion;
    private TextView queryResult, objective_text;


    private Uri filePath;

    private String currentPhotoPath;

    private String objective;
    private boolean firstObj;

    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;

    private static final String TARGET_URL =
            "https://vision.googleapis.com/v1/images:annotate?";
    private static final String API_KEY =
            "key=AIzaSyCDegq0Vjo1F7FikkqYEbtiFp2WGlrAEyM";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    private static final String OBJ_VALUE = "objValue";

    private String schoolListId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            getSupportActionBar().hide();

            btnChoose = (ImageView) findViewById(R.id.btnChoose);
            btnNext = (ImageView) findViewById(R.id.btnNext);
            imageView = (ImageView) findViewById(R.id.imgView);
            btnQuestion = (ImageView) findViewById(R.id.btnQuestion);
            queryResult = (TextView) findViewById(R.id.text_view_id);
            objective_text = (TextView) findViewById(R.id.text_view_objective);
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            Intent intent = getIntent();
            schoolListId = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setObj();
            }
        });

        btnQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuestionActivity();
            }
        });

        if (savedInstanceState != null) {
            setObj(savedInstanceState.getString(OBJ_VALUE));
        }else{
            setObj();
        }

    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(OBJ_VALUE, this.objective);
    }


    private void chooseImage() {

        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/

        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        filePath = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);

        startActivityForResult(intent, 100);*/



        /**/



        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File pictureFile = null;
            try {
                pictureFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        pictureFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }


    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK )
        {
            File imgFile = new  File(currentPhotoPath);
            if(imgFile.exists())            {
                imageView.setImageURI(Uri.fromFile(imgFile));
            }
            uploadImage();
        }else{
            queryResult.setText("erreur on photo data reception");
        }
    }

    private void uploadImage() {

        if(currentPhotoPath != null)
        {
            //loading.smoothToShow();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            File imgFile = new  File(currentPhotoPath);
            ref.putFile(Uri.fromFile(imgFile))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setTitle("Analysing...");
                            //Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Main2Activity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                               @Override
                                               public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                   double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                                           .getTotalByteCount());
                                                   progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                               }
                                           }
                    )
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            /*if (!task.isSuccessful()){
                                throw task.getException();
                            }*/
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downUri = task.getResult();
                        try{
                            URL serverUrl = new URL(TARGET_URL + API_KEY);
                            URLConnection urlConnection = serverUrl.openConnection();
                            HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
                            httpConnection.setRequestMethod("POST");
                            httpConnection.setRequestProperty("Content-Type", "application/json");

                            BufferedWriter httpRequestBodyWriter = new BufferedWriter(new
                                    OutputStreamWriter(httpConnection.getOutputStream()));
                            httpRequestBodyWriter.write
                                    ("{\"requests\":  [{ \"features\":  [ {\"type\": \"LABEL_DETECTION\""
                                            +"}], \"image\": {\"source\": { \"imageUri\":"
                                            +" \""+downUri+ "\"}}}]}");
                            httpRequestBodyWriter.close();

                            String response = httpConnection.getResponseMessage();

                            if (httpConnection.getInputStream() == null) {
                                System.out.println("No stream");
                                return;
                            }

                            Scanner httpResponseScanner = new Scanner (httpConnection.getInputStream());
                            String resp = "";
                            while (httpResponseScanner.hasNext()) {
                                String line = httpResponseScanner.nextLine();
                                resp += line;
                                Log.d("test", line);
                                //System.out.println(line);  //  alternatively, print the line of response
                            }
                            httpResponseScanner.close();
                            queryResult.setText(resp);
                            JSONObject json = new JSONObject(resp);
                            JSONArray arr = json.getJSONArray("responses");
                            JSONArray arr1 = ((JSONObject)arr.get(0)).getJSONArray("labelAnnotations");
                            ArrayList<String> labels = new ArrayList<String>();
                            for(int i = 0; i < arr1.length(); i++) {
                                labels.add(arr1.getJSONObject(i).getString("description"));
                            }
                            if(checkPhoto(labels, objective)){
                                progressDialog.dismiss();
                                queryResult.setText("BRAVO !");
                                btnNext.setVisibility(View.VISIBLE);
                            }
                            else{
                                progressDialog.dismiss();
                                queryResult.setText("ESSAIE ENCORE...");
                            }
                        }
                        catch (Exception e)
                        {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }


                    }
                }
            });

            ;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public String getObjective(){
        ArrayList<String> listObj = getList(schoolListId);
        Collections.shuffle(listObj);
        return listObj.get(0);
    }

    public ArrayList<String> getList(String dbItemId)
    {
        CDAClient client = CDAClient.builder()
                .setSpace("ir9614n98s3l")
                .setToken("GqGpKNBtfkOqvBa8hC_bjy6xICvpBmKnKw24kKInEhY")
                .build();

        CDAEntry entry = client.fetch(CDAEntry.class).one(dbItemId);
        String[] wordArr = entry.getField("items").toString().split(",");
        ArrayList<String> wordList = new ArrayList<String>();
        for(String s : wordArr)
            wordList.add(s);
        return wordList;
    }

    public ArrayList<String> getLexicList(String word, int nb)
    {
        try{
            URL serverUrl = new URL("https://api.datamuse.com/words?ml=" + word + "&max=" + nb);
            URLConnection urlConnection = serverUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "application/json");

            String response = httpConnection.getResponseMessage();

            if (httpConnection.getInputStream() == null) {
                System.out.println("No stream");
                return null;//TODO
            }

            Scanner httpResponseScanner = new Scanner (httpConnection.getInputStream());
            String resp = "";
            while (httpResponseScanner.hasNext()) {
                String line = httpResponseScanner.nextLine();
                resp += line;
                Log.d("test", line);
                //System.out.println(line);  //  alternatively, print the line of response
            }
            httpResponseScanner.close();
            queryResult.setText(resp);
            JSONArray arr = new JSONArray(resp);
            ArrayList<String> labels = new ArrayList<String>();
            for(int i = 0; i < arr.length(); i++)
            {
                labels.add(arr.getJSONObject(i).getString("word"));
            }
            return labels;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkPhoto(ArrayList<String> googleResp, String word){
        ArrayList<String> lexic = getLexicList(word, 30);
        for(String label : googleResp)
        {
            if(objective.equals(label.toLowerCase()))
                return true;
            for(String lex : lexic)
                if(label.toLowerCase().equals(lex.toLowerCase()))
                    return true;
        }
        return false;
    }

    public void setObj(){
        this.objective = getObjective();
        objective_text.setText(objective);
        btnNext.setVisibility(View.GONE);
        queryResult.setText("");
        imageView.setImageDrawable(null);
    }

    public void setObj(String obj){
        this.objective = obj;
        objective_text.setText(objective);
        btnNext.setVisibility(View.GONE);
        queryResult.setText("");
        imageView.setImageDrawable(null);
    }

    public void startQuestionActivity() {
        Intent intent = new Intent(this, Main3Activity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE, objective);
        startActivity(intent);
    }
}
