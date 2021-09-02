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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FireStoreService
{


    private static FirebaseFirestore db= FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static ClassInfo classInfo=ClassInfo.getInstance();
    private static Student student=Student.getInstance();


    public static class Auth
    {

        public static void checkStudentCode(Context context, String studentCode, String password)
        {
            if (studentCode.length()<=6)
            {
                Toast.makeText(context,"유효하지 않은 학생코드입니다. 정확한 학생코드를 입력해주세요.",Toast.LENGTH_SHORT).show();
            }
            else
            {
                String classCode = studentCode.substring(0,6);
                db.collection("IntegratedManagement")
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
                                    db.collection("IntegratedManagement/"+classCode+"/StudentList")
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
                                                        student.setRegion(document_studentCode.get("Region").toString());
                                                        student.setSchool(document_studentCode.get("School").toString());
                                                        student.setGrade(document_studentCode.get("Grade").toString());
                                                        student.setName(document_studentCode.get("Name").toString());
                                                        student.setNumber(document_studentCode.get("Number").toString());
                                                        student.setCreditScore(400);

                                                        checkEmail(context, classCode, studentCode, password);

                                                            /*
                                                            if(context instanceof LoginActivity)
                                                            {
                                                                checkEmail(context, classCode, studentCode, password);
                                                            }
                                                            else if(context instanceof StudentCodeActivity)
                                                            {
                                                                context.startActivity(new Intent(context,SignUpActivity.class));
                                                            }*/


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


        }

        private static void checkEmail(Context context, String classCode, String studentCode, String password)
        {
            db.collection("IntegratedManagement/"+classCode+"/StudentList")
                    .document(studentCode)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (context instanceof LoginActivity)
                            {
                                if (task.getResult().get("Email") != "")
                                {
                                    getJobInfo();
                                    signIn(context,task.getResult().get("Email").toString(),password);
                                }
                                else
                                {
                                    Toast.makeText(context,"해당 학생코드는 등록되어 있지 않습니다. 먼저 회원가입 해주세요.",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else if (context instanceof StudentCodeActivity)
                            {
                                if (task.getResult().get("Email") == "")
                                {
                                    context.startActivity(new Intent(context,SignUpActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(context,"해당 학생코드는 등록되어 있습니다. 로그인 해주세요.",Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context,LoginActivity.class));
                                }

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

                                enrollStudent();
                                Bank.openAccount(new Account("OrdinaryAccount"), new AccountLog(false,0, LocalDate.now().toString(), LocalTime.now().toString()),student.getNumber(),student.getName());



                                context.startActivity(new Intent(context,LoginActivity.class));

                            }
                            else
                            {

                                Toast.makeText(context, "회원가입 실패!", Toast.LENGTH_SHORT).show();


                            }
                        }
                    });

        }

        private static void registerEmail(String email)
        {
            FireStoreService.db.collection("IntegratedManagement/"+student.getClassCode()+"/StudentList")
                    .document(student.getStudentCode())
                    .update("Email",email);

        }

        private static void enrollStudent()
        {
            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/INFO/Student/StudentList/")
                    .document(student.getNumber()+student.getName())
                    .set(student);
        }

        private static void getJobInfo()
        {
            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/INFO/Student/StudentList/")
                    .document(student.getNumber()+student.getName())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.isSuccessful())
                            student.setJob(task.getResult().get("job").toString());
                            else
                            {

                            }
                        }
                    });
        }

    }

    public static class Class
    {
        public static void getClassInfo()
        {
            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/")
                    .document(student.getClassCode())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                HashMap<String,String> map=(HashMap<String,String>)task.getResult().get("StudentMap");
                                classInfo.setStudentMap(map);

                                classInfo.setTheNumberOfStudent(Integer.valueOf(task.getResult().get("TheNumberOfStudent").toString()));

                            }
                            else
                            {

                            }
                        }
                    });
        }
    }

    public static class Bank
    {
        private static List<String> list;

        public static void openAccount(Account account, AccountLog log, String number, String name)
        {
            switch (account.getAccountType())
            {
                case "OrdinaryAccount":
                    db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/INFO/Banking/OrdinaryAccount/")
                            .document(student.getNumber()+student.getName())
                            .set(account);

                    addAccountLog(account, log, number, name);
                    break;

                default:

            }

        }

        public static void addAccountLog(Account account, AccountLog log, String number, String name)
        {
            switch (account.getAccountType())
            {
                case "OrdinaryAccount":
                    db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+
                            "/INFO/Banking/OrdinaryAccount/"+number+name+"/Logs")
                            .document(log.getDateOfTransaction()+"_"+log.getTimeOfTransaction())
                            .set(log);
                    break;
                case "SavingsAccount":
                    db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+
                            "/INFO/Banking/SavingsAccount/"+number+name+"/Logs")
                            .document(log.getDateOfTransaction()+"_"+log.getTimeOfTransaction())
                            .set(log);
                    break;
                default:

            }

        }

        public static void deposit(Account account, double amount, String number, String name)
        {
            switch (account.getAccountType())
            {
                case "OrdinaryAccount":
                            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+
                            "/INFO/Banking/OrdinaryAccount/")
                                    .document(number+name)
                                    .update("balance", FieldValue.increment(amount));
                    break;
                default:
            }

        }

        public static void withdraw(Account account, double amount, String number, String name)
        {
            switch (account.getAccountType())
            {
                case "OrdinaryAccount":
                    db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+
                            "/INFO/Banking/OrdinaryAccount/")
                            .document(number+name)
                            .update("balance", FieldValue.increment(-amount));

                    break;
                default:
            }

        }


        public static void enrollSaving(Context context, Saving saving)
        {
            String document = String.valueOf(saving.getNumber())+saving.getName();
            DocumentReference documentSnapshot=
                    db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/INFO/Banking/SavingsAccount/")
                            .document(document);

            documentSnapshot
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.getResult().exists())
                            {
                                Toast.makeText(context, "이미 등록된 학생입니다!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                documentSnapshot.set(saving);
                                addAccountLog(saving,new AccountLog(true,0,LocalDate.now().toString(),LocalTime.now().toString()),saving.getNumber(),saving.getName());
                            }

                        }
                    });

        }

        public static void seeSavingState(FireStoreGetCallback<Saving> callback)
        {
            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/INFO/Banking/SavingsAccount/")

                    .whereEqualTo("closeOrNot", false)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                for (QueryDocumentSnapshot document:task.getResult())
                                {
                                    try
                                    {
                                        callback.callback(document.toObject(Saving.class));
                                    }
                                    catch (ParseException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            else
                            {

                            }
                        }
                    });

        }


        public static void closeSaving(String number, String name)
        {
            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/INFO/Banking/SavingsAccount/")
                    .document(number+name)
                    .update("closeOrNot", true);
        }


        public static void getListOfSavingProduct()
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

        public static void getSavingProduct(FireStoreGetCallback<Double> callback, String saving)
        {
            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/INFO/Banking/ListOfSavingProduct/")
                    .document(saving)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {
                            if (task.isSuccessful())
                            {
                                try
                                {
                                    callback.callback(Double.valueOf(task.getResult().get("rate").toString()));
                                }
                                catch (ParseException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            else
                            {

                            }
                        }
                    });


        }
    }

    public static class Investment
    {

    }

    public static class CreditRating
    {
        public static void rateCreditScore(int score, String number, String name)
        {
            db.collection(student.getRegion()+"/"+student.getSchool()+"/"+student.getGrade()+"/"+student.getClassCode()+"/INFO/Student/StudentList/")
                    .document(number+name)
                    .update("creditScore",FieldValue.increment(score));
        }
    }

}
