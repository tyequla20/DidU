package com.example.login10;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivityCal extends AppCompatActivity {

    TextView myList; //, today;
    Button addNewButton, showListButton;

    DatabaseReference reference;
    RecyclerView myPlans;
    ArrayList<PlanItemData> list;
    PlanAdapter planAdapter;

    // 현재 시각 구하기
    Calendar calendar=Calendar.getInstance();
    int cYear=calendar.get(Calendar.YEAR);
    int cMonth=calendar.get(Calendar.MONTH)+1;
    int cDate=calendar.get(Calendar.DATE);
    int cHour=calendar.get(Calendar.HOUR_OF_DAY);
    int cMinute=calendar.get(Calendar.MINUTE);

    private DatePickerDialog.OnDateSetListener callbackMethodDate;
    private TimePickerDialog.OnTimeSetListener callbackMethodTime;

    private  Button btnNavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cal);

        btnNavi=(Button) findViewById(R.id.btn_open);

        btnNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityCal.this,NaviActivity.class);
                startActivity(intent);
            }
        });

        myList=findViewById(R.id.MyList);
        // today=findViewById(R.id.today);
        addNewButton=findViewById(R.id.addNewButton);
        showListButton=findViewById(R.id.showListButton);

//        // Firebase 2개 연결
//        FirebaseDatabase database=FirebaseDatabase.getInstance();
//        FirebaseOptions options=new FirebaseOptions.Builder().setApplicationId("com.example.login10").setApiKey("AIzaSyARFTeQWQXYEmvmp_7tdS9QHmB_XtFTczg").setDatabaseUrl("https://didu1je.firebaseio.com/").build();
//        FirebaseApp.initializeApp(this, options, "second");
//        // Retrieve my other app;
//        FirebaseApp app= FirebaseApp.getInstance("second");
//        // Get the database for the other app
//        FirebaseDatabase secondDatabase=FirebaseDatabase.getInstance(app);

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivityCal.this, AddNewPlanActivity.class);
                startActivity(intent);
            }
        });

        showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivityCal.this, PlanListActivity.class);
                startActivity(intent);
            }
        });

        // today.setText(cYear+" / "+cMonth+" / "+cDate);

        // working with data
        myPlans=findViewById(R.id.MyPlans);
        myPlans.setHasFixedSize(true); //
        myPlans.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<PlanItemData>();

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // get data from FireBase
        reference=FirebaseDatabase.getInstance().getReference().child("DidU1");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    PlanItemData planItemData=dataSnapshot1.getValue(PlanItemData.class);
                    list.add(planItemData);
                }

                planAdapter=new PlanAdapter(MainActivityCal.this, list);
                myPlans.setAdapter(planAdapter);
                planAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        reference = FirebaseDatabase.getInstance().getReference().child("DidU1");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // set code to retrive data and replace layout
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
//                {
//                    PlanItemData planItemData=dataSnapshot1.getValue(PlanItemData.class);
//                    list.add(planItemData);
//                }
//                planAdapter=new PlanAdapter(MainActivityCal.this, list);
//                myPlans.setAdapter(planAdapter);
//                planAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // set code to show error
//                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void OnClickHandlerDate(View view)
    {
        DatePickerDialog dialog=new DatePickerDialog(this, callbackMethodDate, cYear, cMonth, cDate);
        dialog.show();
    }

    public void OnClickHandlerTime(View view)
    {
        TimePickerDialog dialog=new TimePickerDialog(this, callbackMethodTime, cHour, cMinute, true);
        dialog.show();;
    }
}