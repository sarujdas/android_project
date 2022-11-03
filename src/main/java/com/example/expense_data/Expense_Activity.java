package com.example.expense_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Expense_Activity extends AppCompatActivity {
    private Button submit;
    private EditText date;
    private EditText type;
    private EditText comment;
    private EditText amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ImageView leftArrow = findViewById(R.id.left_arrow);

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Expense_Activity.this, MainActivity.class));
            }
        });

        date = findViewById(R.id.expense_entry_date);
        type = findViewById(R.id.expense_type);
        comment = findViewById(R.id.expense_comment);
        amount = findViewById(R.id.expense_amount);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> data = new HashMap<>();
                data.put("date", date.getText().toString());
                data.put("type", type.getText().toString());
                data.put("comment", comment.getText().toString());
                data.put("amount", amount.getText().toString());
                FirebaseFirestore.getInstance().collection("Expense").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(Expense_Activity.this, "Expense Added", Toast.LENGTH_SHORT).show();
                        date.setText("");
                        type.setText("");
                        comment.setText("");
                        amount.setText("");
                    }
                });

            }
        });

    }
}