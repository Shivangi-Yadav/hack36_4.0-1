
   package com.rachit2525.jeevika;

           import android.app.Activity;
           import android.content.Intent;
           import android.os.Bundle;
           import android.util.Log;
           import android.view.View;
           import android.widget.AdapterView;
           import android.widget.ArrayAdapter;
           import android.widget.Button;
           import android.widget.EditText;
           import android.widget.Spinner;
           import android.widget.Toast;

           import androidx.annotation.NonNull;
           import androidx.appcompat.app.AppCompatActivity;

           import com.google.android.gms.tasks.OnFailureListener;
           import com.google.android.gms.tasks.OnSuccessListener;
           import com.google.firebase.firestore.FirebaseFirestore;

           import java.util.HashMap;
           import java.util.Map;

public class DeleteActivity extends AppCompatActivity {

    EditText areaEditText;
    EditText phoneEditText;
    EditText nameEditText;
    Button deleteButton;
    Spinner jobCategorySpinner;

    String phNumber;
    String areaCode;
    String name;
    String jobSelected;

    FirebaseFirestore db;

    private static final String KEY_PHONE = "phone";
    private static final String KEY_ZipCODE = "zipCode";
    private static final String KEY_NAME = "name";
    private static final String KEY_JOB = "job";
    private static final String TAG = "DeleteActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        jobCategorySpinner = findViewById(R.id.jobCategorySpinner);

        areaEditText = findViewById(R.id.areaEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        nameEditText = findViewById(R.id.nameEditText);
        deleteButton = findViewById(R.id.deleteButton);


        ArrayAdapter<CharSequence> jobAdapter =
                ArrayAdapter.createFromResource(this, R.array.jobs, android.R.layout.simple_spinner_item);
        jobAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobCategorySpinner.setAdapter(jobAdapter);
        jobCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jobSelected = adapterView.getItemAtPosition(i).toString();
                System.out.println(jobSelected + "**************************************************************************************");
//                Toast.makeText(adapterView.getContext(), jobSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                jobSelected = "General Helper";
            }
        });


        db = FirebaseFirestore.getInstance();


        System.out.println(jobSelected + "********1******************************************************************************");


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                areaCode = areaEditText.getText().toString();
                phNumber = phoneEditText.getText().toString();
                name = nameEditText.getText().toString();

                Map<String, String> applicantDetails = new HashMap<>();
                applicantDetails.put(KEY_PHONE, phNumber);
                applicantDetails.put(KEY_NAME, name);
                applicantDetails.put(KEY_ZipCODE, areaCode);
                applicantDetails.put(KEY_JOB, jobSelected);

//                Toast.makeText(RegisterActivity.this, "User Added in Database!", Toast.LENGTH_SHORT).show();
                System.out.println(areaCode + "********222******************************************************************************");
                System.out.println(phNumber + "********333333******************************************************************************");
                System.out.println(name + "********444444444444******************************************************************************");
                //db.collection(jobSelected).document(areaCode).collection(phNumber).document(name).set(applicantDetails)
                //db.collection(jobSelected).document(phNumber).set(applicantDetails)
                //db.collection(jobSelected).document(areaCode).collection(phNumber).document(name).set(applicantDetails)

                db.collection(jobSelected).document(phNumber)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(DeleteActivity.this, "Job deleted! Deletion Successful :)", Toast.LENGTH_SHORT).show();

                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DeleteActivity.this, "Error! Please Try Again :(", Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "Error deleting document", e);


                            }
                        });
                Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}


