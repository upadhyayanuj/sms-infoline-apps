package com.anujupadhyay.smsinfoline.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anujupadhyay.smsinfoline.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentAddCourseType extends Fragment {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    EditText editText;
    Button button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_course_type, container,false);
        editText = view.findViewById(R.id.course_type_name);
        button = view.findViewById(R.id.btn_course_type);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TypeName = editText.getText().toString();
                if(TextUtils.isEmpty(TypeName)){
                    Toast.makeText(getContext(), "Please Enter Course Type", Toast.LENGTH_SHORT).show();
                }else{
                    CreateCourseType(TypeName);
                }
            }
        });
        return view;
    }

    private void CreateCourseType(final String Typename) {
        databaseReference.child("Courses").child(Typename).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    editText.setText("");
                    Toast.makeText(getContext(), "Course Type Added Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    String msg = task.getException().toString();
                    Toast.makeText(getContext(), "Error : "+msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
