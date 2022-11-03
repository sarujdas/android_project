package com.example.expense_data;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton add, add1, add2, add1Text, Add2Text;
    Animation addOpen, addClose, rotateForward, rotateBackward;
    private ListView list;
    private Button btn;
    private List<String> name_list = new ArrayList<>();
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        add = (FloatingActionButton) findViewById(R.id.add);
        add1 = (FloatingActionButton) findViewById(R.id.add_income);
        add2 = (FloatingActionButton) findViewById(R.id.add_expense);
//        list = findViewById(R.id.list);
        btn = findViewById(R.id.View_list);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection(("Expense")+("Income")).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        name_list.clear();
                        for(DocumentSnapshot ds:value){
                            name_list.add(ds.getString("amount") + ":" + ds.getString("type") + ":" + ds.getString("date"));
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_selectable_list_item, name_list);
                        adapter.notifyDataSetChanged();
                        list.setAdapter(adapter);
                    }
                });
            }
        });

        addOpen = AnimationUtils.loadAnimation(this, R.anim.floating_anime_open);
        addClose = AnimationUtils.loadAnimation(this, R.anim.floating_anime_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateAdd();
            }
        });
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateAdd();
                Toast.makeText(MainActivity.this, "Add Your Income", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Income_Activity.class));
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateAdd();
                Toast.makeText(MainActivity.this, "Add Your Expense", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Expense_Activity.class));
            }
        });
    }

    private void animateAdd(){
        if(isOpen){
            add.startAnimation(rotateForward);
            add1.startAnimation(addClose);
            add2.startAnimation(addClose);
            add1.setClickable(false);
            add2.setClickable(false);
            isOpen=false;
        }else{
            add.startAnimation(rotateBackward);
            add1.startAnimation(addOpen);
            add2.startAnimation(addOpen);
            add1.setClickable(true);
            add2.setClickable(true);
            isOpen=true;
        }
    }
}