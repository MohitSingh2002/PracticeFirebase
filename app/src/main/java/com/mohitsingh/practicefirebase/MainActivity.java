package com.mohitsingh.practicefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setupAddData();

//        setDocument();

//        dataTypes();

//        customObjects();

//        updateADocument();

//        incrementAValue();

//        getData();

//        getCustomObject();

//        getMultipleDocuments();

        getAllDocumentsFromACollection();


    }

    private void setupAddData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Mohit Singh");
        map.put("age", 18);
        map.put("college", "Jecrc University");
        map.put("language", "Marwari");

        db.collection("users")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, documentReference.getId(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void setDocument() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Mohit Singh");
        map.put("age", 18);
        map.put("city", "Bikaner");
        map.put("state", "Rajasthan");

        db.collection("users").document("mohit_singh")
                .set(map, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void dataTypes() {
        HashMap<String, Object> dataTypesHashMap = new HashMap<>();
        dataTypesHashMap.put("string", "Mohit Singh");
        dataTypesHashMap.put("number", 18);
        dataTypesHashMap.put("boolean", true);
        dataTypesHashMap.put("time stamp", new Timestamp(new Date()));
        dataTypesHashMap.put("list", Arrays.asList(10, 20, 30, 40, 50));

        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 1);
        map.put("d", 4);
        map.put("e", 5);

        dataTypesHashMap.put("map", map);

        db.collection("users").document("dataTypes")
                .set(dataTypesHashMap, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void customObjects() {
        User mohit = new User("Mohit Singh", "Bikaner", "Rajasthan", 18);

        db.collection("users").document(mohit.name.trim() + mohit.city + mohit.state + mohit.age)
                .set(mohit, SetOptions.merge());

    }

    private void updateADocument() {

        db.collection("users").document("dataTypes")
                .update(
                        "string", "Mohit",
                        "map.c", 3
                        );

    }

    private void incrementAValue() {

        db.collection("users").document("dataTypes")
                .update("number", FieldValue.increment(1));

    }

    private void getData() {
        DocumentReference documentReference = db.collection("users").document("mohit_singh");

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                Toast.makeText(MainActivity.this, documentSnapshot.getData().toString(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No Such Document!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Failure!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void getCustomObject() {
        DocumentReference documentReference = db.collection("users").document("Mohit SinghBikanerRajasthan18");

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);

                        Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_LONG).show();

                    }
                });

    }


    private void getMultipleDocuments() {

        db.collection("users")
                .whereEqualTo("name", "Mohit Singh")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Log.d("TAG_1", snapshot.getId() + " - " + snapshot.getData());
                            }
                        } else {
                            Log.d("TAG_2", "Failure!");
                        }
                    }
                });

    }


    private void getAllDocumentsFromACollection() {

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Log.d("TAG_3", snapshot.getId() + " - " + snapshot.getData());
                            }
                        } else {
                            Log.d("TAG_4", "Failure !!!!!");
                        }
                    }
                });

    }

}
