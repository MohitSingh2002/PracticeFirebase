package com.mohitsingh.practicefirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

//        getAllDocumentsFromACollection();

//        deleteDocument();

//        deleteFields();

//        batchedWrites();

//        saveAccounts();

//        transaction("a", "b", 100);

//        queries();

//        setupSnapshotListener();

        multipleDocumentSnapshot();

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

    private void deleteDocument() {

        db.collection("users").document("oFtz5G9uKHdXg4SIFqUl")
                .delete()
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

    private void deleteFields() {

        db.collection("users").document("mohit_singh")
                .update("age", FieldValue.delete())
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

    private void batchedWrites() {

        WriteBatch writeBatch = db.batch();

        User mohit = new User("Mohit", "Bikaner", "Rajasthan", 18);
        User singh = new User("Singh", "Jaipur", "Rajasthan", 18);

        writeBatch.set(db.collection("users").document("mohit"), mohit);
        writeBatch.set(db.collection("users").document("singh"), singh);

        writeBatch.commit()
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

    private void saveAccounts() {

        Account a = new Account("a", 100);
        Account b = new Account("b", 200);
        Account c = new Account("c", 300);

        WriteBatch writeBatch = db.batch();

        writeBatch.set(db.collection("users").document(a.name), a);
        writeBatch.set(db.collection("users").document(b.name), b);
        writeBatch.set(db.collection("users").document(c.name), c);

        writeBatch.commit();

    }

    private void transaction(String fromAccount, String toAccount, int amountToBeSend) {

        db.runTransaction(new Transaction.Function<Integer>() {
            @Nullable
            @Override
            public Integer apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                Account from = transaction.get(db.collection("users").document(fromAccount)).toObject(Account.class);

                if (from.balance < amountToBeSend) {
                    return -1;
                }

                transaction.update(db.collection("users").document(fromAccount), "balance", FieldValue.increment(-amountToBeSend));
                transaction.update(db.collection("users").document(toAccount), "balance", FieldValue.increment(amountToBeSend));

                return from.balance - amountToBeSend;
            }
        })
                .addOnSuccessListener(new OnSuccessListener<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        Toast.makeText(MainActivity.this, "" + integer, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failure !!!!!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void queries() {

        db.collection("users")
                .whereEqualTo("name", "Mohit Singh")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Log.d("TAG_5", documentSnapshot.getId() + " " + documentSnapshot.get("name"));
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Failure !!!!!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void setupSnapshotListener() {

        db.collection("users").document("mohit")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (value != null && value.exists()) {
                            Log.d("TAG_6", "Current Data : " + value.getData().toString());
                        }

                    }
                });

    }

    private void multipleDocumentSnapshot() {

        db.collection("users")
                .whereEqualTo("name", "Mohit Singh")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        List<String> allIds = new ArrayList<>();
                        for (QueryDocumentSnapshot query : value) {
                            allIds.add(query.getId());
                        }

                        Log.d("TAG_7", "All ID's : " + allIds);

                    }
                });

    }

}
