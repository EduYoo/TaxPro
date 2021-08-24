package com.example.taxpro;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FireStoreAPI
{
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private ClassInfo classInfo=ClassInfo.getInstance();
    private Student student=Student.getInstance();

    private List<String> list;

    void getListOfSavingProduct()
    {
        db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/INFO/")
                .document("Banking")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            list=(ArrayList<String>)task.getResult().get("ListOfSavingProduct");

                            for (String item:list)
                            {
                                classInfo.getListOfSavingProduct().add(item);
                            }
                        }
                        else
                        {

                        }
                    }
                });
    }

    void getSavingProduct(FireStoreGetCallback<Double> callback, String saving)
    {
        db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/INFO/Banking/")
                .document("saving")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            callback.callback(Double.valueOf(task.getResult().get("Rate").toString()));
                        }
                        else
                        {

                        }
                    }
                });


    }

}
