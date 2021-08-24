package com.example.taxpro;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FireStoreAPI
{


    private static FirebaseFirestore db= FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static ClassInfo classInfo=ClassInfo.getInstance();
    private static Student student=Student.getInstance();

    private List<String> list;


    public static class Auth
    {

        public static void checkStudentCode(Context context, String studentCode, String password)
        {
            String classCode = studentCode.substring(0,6);
            FireStoreAPI.db.collection("IntegratedManagement")
                    .document(classCode)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            DocumentSnapshot document_ClassCode = task.getResult();
                            if (document_ClassCode.exists())
                            {
                                        student.setClassCode(classCode);
                                        FireStoreAPI.db.collection("IntegratedManagement/"+classCode+"/StudentList")
                                                .document(studentCode)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                                                {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task)
                                                    {
                                                        DocumentSnapshot document_studentCode = task.getResult();
                                                        if (document_studentCode.exists())
                                                        {
                                                            Log.d("???", context.toString());
                                                            student.setStudentCode(document_studentCode.get("StudentCode").toString());
                                                            if(context instanceof LoginActivity)
                                                            {

                                                                student.setRegion(document_studentCode.get("Region").toString());
                                                                student.setSchool(document_studentCode.get("School").toString());
                                                                student.setGrade(document_studentCode.get("Grade").toString());
                                                                student.setName(document_studentCode.get("Name").toString());
                                                                checkEmail(context, classCode, studentCode, password);
                                                            }
                                                            else if(context instanceof StudentCodeActivity)
                                                            {
                                                                context.startActivity(new Intent(context,SignUpActivity.class));
                                                            }


                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(context,"올바르지 않은 학생코드입니다. 정확한 학생코드를 입력해주세요.",Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                });
                            }
                            else
                            {
                                Toast.makeText(context,"올바르지 않은 학급코드입니다. 정확한 학급코드를 입력해주세요.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

        private static void checkEmail(Context context, String classCode, String studentCode, String password)
        {
            FireStoreAPI.db.collection("IntegratedManagement/"+classCode+"/StudentList")
                    .document(studentCode)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.getResult().get("Email") != "")
                            {
                                signIn(context,task.getResult().get("Email").toString(),password);
                            }
                            else
                            {
                                Toast.makeText(context,"해당 학생코드는 등록되어 있지 않습니다. 먼저 회원가입 해주세요.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

        private static void signIn(Context context, String email, String password)
        {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Log.d("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user.isEmailVerified())
                                {
                                    student.setEmail(user.getEmail());
                                    context.startActivity(new Intent(context,MainScreenActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(context,"이메일 인증이 필요합니다.",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else
                                {

                                Log.w("TAG", "signInWithEmail:failure", task.getException());


                            }
                        }
                    });
        }

        public static void signUp(Context context, String email, String password)
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                                registerEmail(email);

                                FirebaseUser user = mAuth.getCurrentUser();

                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                Log.d("TAG", "Email:sent");
                                            }
                                        });

                                context.startActivity(new Intent(context,LoginActivity.class));

                            } else
                                {

                                    Toast.makeText(context, "회원가입 실패!", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });

        }

        private static void registerEmail(String email)
        {
            FireStoreAPI.db.collection("IntegratedManagement/"+student.getClassCode()+"/StudentList")
                    .document(student.getStudentCode())
                    .update("Email",email);

        }

    }

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
