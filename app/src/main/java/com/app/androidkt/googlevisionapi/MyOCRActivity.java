package com.app.androidkt.googlevisionapi;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.ColorInfo;
import com.google.api.services.vision.v1.model.DominantColorsAnnotation;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.ImageProperties;
import com.google.api.services.vision.v1.model.SafeSearchAnnotation;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOCRActivity extends AppCompatActivity {
    CardView mymainbutton ;
    private Bitmap bitmap;
    ImageView imageView;
    CardView qr_scanner,ocr_scanner ;
    Uri imageUri ;
    Bitmap imageBitmap ;
    TextView carIdtxt, companyIdtxt ,carnumbertxt;
    EditText cost , kiloMiters,ekramya,kilom ;
    Button send_to_server_btn ;
    String car_id,company_id , kilo ,costs,ekramyas ,kiloms ;
    ProgressBar p_load ;
    String all_costs ;
    String user_id1 ;
    LinearLayout imagelayout;
    Boolean image = false;
    ProgressBar imageUploadProgress;
    private DataWrap dataWrap;
    EditText price,letre;
    ArrayList<String> texts = new ArrayList<String>();
    private static final String CLOUD_VISION_API_KEY = "AIzaSyANp_13U93QjZBAlX__GxEyOhhBCt6k3To";
    static final int QR_SCANNER_RESULT_CODE = 0;
    private Feature feature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ocractivity);
        imagelayout=findViewById(R.id.imagelayout);
        mymainbutton=findViewById(R.id.button);
        imageView=findViewById(R.id.imageView2);
        imageUploadProgress=findViewById(R.id.imageProgress);
        carnumbertxt = findViewById(R.id.textView7);
    kiloMiters=findViewById(R.id.kiloms);
        send_to_server_btn = findViewById(R.id.send_to_server_btn);
        carIdtxt = findViewById(R.id.carId);
        ekramya=findViewById(R.id.ekramya);

        companyIdtxt = findViewById(R.id.companyId);
        p_load = findViewById(R.id.p_load);
        price=findViewById(R.id.price);
        letre=findViewById(R.id.letre);
        qr_scanner = findViewById(R.id.qr_scanner);

        feature = new Feature();
        feature.setType("TEXT_DETECTION");
        mymainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent newIntent = new Intent(MyOCRActivity.this,cameraactivity.class);
startActivity(newIntent);
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(MyOCRActivity.this);
            }
        });
        qr_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, QR_SCANNER_RESULT_CODE);

                } catch (Exception e) {
                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);
                }
            }
        });
        send_to_server_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if (!image){
    Toast.makeText(MyOCRActivity.this,"برجاء قم بتصوير العداد",Toast.LENGTH_SHORT).show();
}else {
                ekramyas="0";
                car_id = carIdtxt.getText().toString();
                company_id = companyIdtxt.getText().toString();
                kilo = letre.getText().toString();
                costs = price.getText().toString();
                ekramyas="0";
                ekramyas=ekramya.getText().toString();
                kiloms=kiloMiters.getText().toString();

                if (TextUtils.isEmpty(car_id)){
                    carIdtxt.setError("من فضلك قم بقرائة ال QR ");
                }else if (TextUtils.isEmpty(company_id)){
                    companyIdtxt.setError("من فضلك قم بقرائة ال QR ");
                }else if (TextUtils.isEmpty(kilo)){
                    letre.setError("من فضلك أدخل عداد الليترات ");
                }else if (TextUtils.isEmpty(costs)){
                    price.setError("من فضلك أدخل التكلفة ");

                }else {
                //    kiloMiters.setError(null);
//                    cost.setError(null);
//                    kilom.setError(null);

                    p_load.setVisibility(View.VISIBLE);

Double reallllcost = Double.parseDouble(costs)+Double.parseDouble(ekramyas);
                    all_costs = String.valueOf(reallllcost );
                    user_id1 =String.valueOf( PreferenceManager.getInstance(MyOCRActivity.this).fetchInteger("user_id"));
                   try {
                       uploadingimage();
                   } catch(Exception e){System.out.println(e);}
                 //  send_to_server();
                }

            }}
        });
try {
    Intent intent = getIntent();

      imageUri = intent.getData();

    // bitmap = (Bitmap) data.getExtras().get("data");

   // imageView.setImageURI(imageUri);

    Picasso.get().load(imageUri).resize(100,70).centerCrop().into(imageView);

    imagelayout.setVisibility(View.VISIBLE);

    try {

        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String contents = sh.getString("contents", "");
        getCarIdAndCompanyIdFromString(contents);
        image =true;

    } catch (IOException e) {
        e.printStackTrace();
    }

    callCloudVision(bitmap, feature);
}catch (Exception e){
    System.out.println(e.getMessage());
}


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QR_SCANNER_RESULT_CODE) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");


                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                myEdit.putString("contents", contents);


                myEdit.commit();
                getCarIdAndCompanyIdFromString(contents);


            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                // bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
                imageView.setImageURI(resultUri);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                callCloudVision(bitmap, feature);
                //     populatetext(texts);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    private void callCloudVision(final Bitmap bitmap, final Feature feature) {
        imageUploadProgress.setVisibility(View.VISIBLE);
        final List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
        annotateImageReq.setImage(getImageEncodeImage(bitmap));
        annotateImageRequests.add(annotateImageReq);


        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {

                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();

                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    VisionRequestInitializer requestInitializer = new VisionRequestInitializer(CLOUD_VISION_API_KEY);

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);

                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(annotateImageRequests);

                    Vision.Images.Annotate annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);
                    annotateRequest.setDisableGZipContent(true);
                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                   System.out.println("response"+response);

                    return convertResponseToString(response);
                } catch (GoogleJsonResponseException e) {
                    Log.d("Myocr", "failed to make API request because " + e.getContent());
                } catch (IOException e) {
                    Log.d("Myocr", "failed to make API request because of other IOException " + e.getMessage());
                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {
              //  price.setText(result);
                imageUploadProgress.setVisibility(View.INVISIBLE);
            }
        }.execute();
    }

    private Image getImageEncodeImage(Bitmap bitmap) {
        Image base64EncodedImage = new Image();
        // Convert the bitmap to a JPEG
        // Just in case it's a format that Android understands but Cloud Vision
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Base64 encode the JPEG
        base64EncodedImage.encodeContent(imageBytes);
        return base64EncodedImage;
    }
    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        AnnotateImageResponse imageResponses = response.getResponses().get(0);

        List<EntityAnnotation> entityAnnotations;
        String api="TEXT_DETECTION";
        String message = "";
        switch (api) {
            case "LANDMARK_DETECTION":
                entityAnnotations = imageResponses.getLandmarkAnnotations();
                message = formatAnnotation(entityAnnotations);
                break;
            case "LOGO_DETECTION":
                entityAnnotations = imageResponses.getLogoAnnotations();
                message = formatAnnotation(entityAnnotations);
                break;
            case "SAFE_SEARCH_DETECTION":
                SafeSearchAnnotation annotation = imageResponses.getSafeSearchAnnotation();
                message = getImageAnnotation(annotation);
                break;
            case "IMAGE_PROPERTIES":
                ImageProperties imageProperties = imageResponses.getImagePropertiesAnnotation();
                message = getImageProperty(imageProperties);
                break;
            case "LABEL_DETECTION":
                entityAnnotations = imageResponses.getLabelAnnotations();
                message = formatAnnotation(entityAnnotations);
                break;
            case "TEXT_DETECTION":
                try {
                    entityAnnotations = imageResponses.getTextAnnotations();
                    message = formatAnnotationForText(entityAnnotations);
                    Toast.makeText(MyOCRActivity.this, "message"+message, Toast.LENGTH_SHORT).show();
                    break;
                }catch (Exception e ){
                    System.out.print(e.toString());
                    break; }

        }
        //     Toast.makeText(MainActivity.this,"this "+getDetectedTexts(response),Toast.LENGTH_SHORT).show();
        return message;
    }
    private String getImageAnnotation(SafeSearchAnnotation annotation) {
        return String.format("adult: %s\nmedical: %s\nspoofed: %s\nviolence: %s\n",
                annotation.getAdult(),
                annotation.getMedical(),
                annotation.getSpoof(),
                annotation.getViolence());
    }

    private String getImageProperty(ImageProperties imageProperties) {
        String message = "";
        DominantColorsAnnotation colors = imageProperties.getDominantColors();
        for (ColorInfo color : colors.getColors()) {
            message = message + "" + color.getPixelFraction() + " - " + color.getColor().getRed() + " - " + color.getColor().getGreen() + " - " + color.getColor().getBlue();
            message = message + "\n";
        }
        return message;
    }

    private String formatAnnotation(List<EntityAnnotation> entityAnnotation) {
        String message = "";

        if (entityAnnotation != null) {
            for (EntityAnnotation entity : entityAnnotation) {
                message = message + "    " + entity.getDescription() + " " + entity.getScore();
                message += "\n";
            }
        } else {
            message = "Nothing Found";
        }
        return message;
    }
    @SuppressLint("SetTextI18n")
    private String formatAnnotationForText(List<EntityAnnotation> entityAnnotation) {
        String message = "";
texts.clear();
        if (entityAnnotation != null) {
          //  System.out.print(entityAnnotation.toString());
            for (EntityAnnotation entity : entityAnnotation) {
                texts.add(entity.getDescription());
                message = message+  "    " + entity.getDescription() ;
           //     System.out.print(message);
                //     message += "\n";
            }
        } else {
            message = "Nothing Found";
        }
        System.out.println(texts+"array");
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                double intValue;
                ArrayList<Double> cars = new ArrayList<Double>();
                cars.clear();
                for(int i = 0 ;i<texts.size();i++ ){

                    try {
                        intValue = Double.parseDouble(texts.get(i));
                        cars.add(intValue);
                    } catch (NumberFormatException e) {
                        System.out.println("Input String cannot be parsed to Integer.");
                    }
                }
                try {
                    price.setText(cars.get(0)+"");


                }catch (Exception e){Toast.makeText(MyOCRActivity.this,"برجاء التقاط صورة أوضح",Toast.LENGTH_SHORT).show();}
               try {
                   letre.setText(cars.get(1)+"");

               }catch (Exception e){Toast.makeText(MyOCRActivity.this,"برجاء التقاط صورة أوضح",Toast.LENGTH_SHORT).show();}
            }
        });

        return message;
    }
    private void populatetext(ArrayList<String> texts) {




    }
    private void getCarIdAndCompanyIdFromString(String url) {
        char [] url_characters = url.toCharArray();
        StringBuilder carId = new StringBuilder();
        StringBuilder companyId = new StringBuilder();
        StringBuilder carNumber = new StringBuilder();
        carIdtxt.setError(null);
        companyIdtxt.setError(null);

        boolean isFirst = true;
        boolean is_second = true;
        boolean first_number = true ;
        boolean second_number = true ;
        for(char c : url_characters){
            if(Character.isDigit(c)&&isFirst){
                carId.append(c);
                first_number = false ;
            }else if (Character.isDigit(c)&&!isFirst&&is_second){
                companyId.append(c);
                second_number = false ;
            }
            else if (Character.isDigit(c)&&!is_second) {

                carNumber.append(c);

            }else {
                if (!first_number){
                    isFirst =false ;
                }
                if (!second_number){
                    is_second = false ;
                }
            }

        }

        carIdtxt.setText(carId);
        companyIdtxt.setText(companyId);
        carnumbertxt.setText(carNumber);
        Toast.makeText(this, carNumber, Toast.LENGTH_SHORT).show();

    }
    private void send_to_server() {

        String url = sheared.main_url + "petrol";
         StringRequest getRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(MyOCRActivity.this, response, Toast.LENGTH_SHORT).show();
                            p_load.setVisibility(View.INVISIBLE);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")){
                                AlertDialog alertDialog = new AlertDialog.Builder(MyOCRActivity.this).create();
                                alertDialog.setMessage("تم ارسال البيانات بنجاح");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "موافق",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            Intent restaractivity = new Intent(MyOCRActivity.this,MyOCRActivity.class);
                                          startActivity(restaractivity);
//                                                carIdtxt.setText("");
//                                                companyIdtxt.setText("");
////                                                kiloMiters.setText("");
//                                                cost.setText("");
////                                                kilom.setText("");
////                                                ekramya.setText("");
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                   //     Toast.makeText(MyOCRActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        p_load.setVisibility(View.INVISIBLE);
                        Log.d("ERROR","error => "+error.toString());

                    }
                }
        )
        {

            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("carId",car_id);
                params.put("companyId",company_id);
                params.put("litre",kilo);
                params.put("ekramyat",ekramyas);
                params.put("all_costs",all_costs);
                params.put("kiloNumbers","0");
                params.put("pound",costs);
                params.put("user_id",user_id1);
                params.put("all_kilometers",kiloms);


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AppKey",getResources().getString(R.string.app_key));
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(getRequest);

    }
    public void uploadingimage( ) {
        ProgressDialog dialog = ProgressDialog.show(MyOCRActivity.this, "برجاء الانتظار",
                "جاري رفع البيانات...", true);

        dialog.show();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Request request = chain.request().newBuilder().addHeader("AppKey", getResources().getString(R.string.app_key)).build();

                return chain.proceed(request);

            }
        });
            Retrofit retrofit = new Retrofit.Builder().baseUrl(sheared.main_url)
                    .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        String  path=RealPathUtil.getRealPath(MyOCRActivity.this,imageUri);

            File file = new File(path);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

            RequestBody carId = RequestBody.create(MediaType.parse("multipart/form-data"),car_id);
            RequestBody companyId = RequestBody.create(MediaType.parse("multipart/form-data"), company_id);
        RequestBody litre = RequestBody.create(MediaType.parse("multipart/form-data"), kilo);
        RequestBody ekramyat = RequestBody.create(MediaType.parse("multipart/form-data"), ekramyas);
        Double reallllcost = Double.parseDouble(costs)+Double.parseDouble(ekramyas);
        all_costs = String.valueOf(reallllcost );
        RequestBody all_costs = RequestBody.create(MediaType.parse("multipart/form-data"),reallllcost.toString());
        RequestBody pound = RequestBody.create(MediaType.parse("multipart/form-data"), costs);
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), user_id1);
        RequestBody all_kilometers = RequestBody.create(MediaType.parse("multipart/form-data"), kiloms);

        Api apiService = retrofit.create(Api.class);
            Call<WaqudResponsePojo> call = apiService.addvalues( carId, companyId,litre,ekramyat,all_costs,pound,user_id,all_kilometers,body);
            call.enqueue(new Callback<WaqudResponsePojo>() {
                @Override
                public void onResponse(Call<WaqudResponsePojo> call, retrofit2.Response<WaqudResponsePojo> response) {
                    if (response.isSuccessful()) {
                        p_load.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                        System.out.println(response.body().getMsg());
                        if (response.body().getStatus().toString().equals("200")) {
                            Toast.makeText(getApplicationContext(), "Customer Added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"تم رفع البيانات" ,Toast.LENGTH_SHORT).show();
                            Intent restaractivity = new Intent(MyOCRActivity.this,MyOCRActivity.class);
                            startActivity(restaractivity);
                        }
                    }
                }

                @Override
                public void onFailure(Call<WaqudResponsePojo> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

                }

            });



    }
 }