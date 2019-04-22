package com.ekattorit.messmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ekattorit.messmanagement.session.Store;
import com.ekattorit.messmanagement.session.mySession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout parent;
    TextView username,mealRate,totalmeal,totalbazar;
    String urlserver = "http://10.0.2.2:8080/MessServer/AllMeal";
    ArrayList<memberModel> memberList = null;
    String dAm[][];
    int allmeal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        if (mySession.user == null) {
            startActivity(new Intent(DashBoard.this, Login.class));
        }
        Intent intent = getIntent();
        String check = intent.getStringExtra("check");
        memberList = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);


        username = (TextView) headerView.findViewById(R.id.username);
        username.setText(mySession.user);


        mealRate = (TextView) headerView.findViewById(R.id.mealrate);
        mealRate.setText("MealRate: "+String.valueOf(mySession.mealrate));

        totalmeal = (TextView) headerView.findViewById(R.id.totalmeal);
        totalmeal.setText("TotalMeal: "+String.valueOf(mySession.allmeal));

        totalbazar = (TextView) headerView.findViewById(R.id.totalbazar);
        totalbazar.setText("TotalBazar: "+String.valueOf(mySession.allbazar));

        ScrollView scView = (ScrollView) findViewById(R.id.nli);

        parent = new LinearLayout(getApplicationContext());
        parent.setOrientation(LinearLayout.VERTICAL);

        scView.addView(parent);

        getData();


    }

    private void createView() {
        int index = 0;
        for (int i = 0; i <= dAm.length; i++) { //Loop for date

            LinearLayout li = new LinearLayout(getApplicationContext());
            parent.addView(li);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(1, 1, 1, 1);

            TextView tv = new TextView(getApplicationContext());
            tv.setWidth(285);
            tv.setHeight(100);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(20);
            tv.setGravity(Gravity.CENTER);
            if (i == 0) {
                tv.setText("Day");
            } else {
                tv.setText(dAm[index][0]);
                //index++;
            }
            //tv.setLayoutParams(params);
            //tv.setPadding(5, 0, 5, 0);
            tv.setBackgroundColor(Color.parseColor("#424242"));
            li.addView(tv);

            int mindex = 1;

            for (int j = 0; j < memberList.size(); j++) { //loop for person
                TextView tv1 = new TextView(getApplicationContext());
                tv1.setWidth(285);
                tv.setHeight(100);
                tv1.setTextSize(20);
                tv1.setGravity(Gravity.CENTER);
                if (i == 0) {
                    tv1.setText(memberList.get(j).name);
                    tv1.setTextColor(Color.parseColor("#ffffff"));
                    tv1.setBackgroundColor(Color.parseColor("#3498DB"));
                } else if (i == 31) {
                    tv.setText("Total");
                    tv1.setTextColor(Color.parseColor("#ffffff"));
                    tv1.setBackgroundColor(Color.parseColor("#3498DB"));
                    tv1.setText(String.valueOf(allmeal));
                } else {
                    tv1.setTextColor(Color.parseColor("#000000"));
                    tv1.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv1.setText(dAm[index][mindex]);
                    String currentmeal = dAm[index][mindex];
                    int a = Character.getNumericValue(currentmeal.charAt(0));
                    int b = Character.getNumericValue(currentmeal.charAt(2));

                    allmeal+=a+b;
                    mindex++;
                    //index++;
                }
                //tv1.setLayoutParams(params);
                //tv1.setPadding(5, 0, 5, 0);
                tv1.setLayoutParams(params);
                li.addView(tv1);
            }
            if(i==0){

            }else{
            index++;}
        }
            mySession.allmeal=allmeal;
        Toast.makeText(this,"Total meal"+allmeal,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit from App?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_meal) {

        } else if (id == R.id.nav_bazar) {
            startActivity(new Intent(DashBoard.this, DemoAllBazar.class));
        } else if (id == R.id.nav_member) {
            startActivity(new Intent(DashBoard.this, AllMember.class));
        } else if (id == R.id.nav_payment) {
            startActivity(new Intent(DashBoard.this, AllPayment.class));
        } else if (id == R.id.nav_meal_add) {

            startActivity(new Intent(DashBoard.this, AddMeal.class));

        } else if (id == R.id.nav_bazar_add) {
            startActivity(new Intent(DashBoard.this,AddBazar.class));
        } else if (id == R.id.nav_member_add) {

        } else if (id == R.id.nav_payment_add) {
            startActivity(new Intent(DashBoard.this,AddPayment.class));
        } else if (id == R.id.nav_logout) {
            mySession.user = null;
            startActivity(new Intent(DashBoard.this, Login.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getData() {

        StringRequest request = new StringRequest(Request.Method.POST, urlserver, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                // Toast.makeText(DashBoard.this,"GetData Called", Toast.LENGTH_LONG).show();
                if (s.contains("null")) {
                    Toast.makeText(DashBoard.this, s, Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(DashBoard.this,s, Toast.LENGTH_LONG).show();

                    String meal[] = s.split("@");
                    if (meal[1] != null) {

                        String daywise[] = meal[1].split("-");
                        dAm = new String[daywise.length][daywise[0].split(",").length];
                        int i = 0;
                        for (String today : daywise) {
                            String dateAndMeal[] = today.split(",");
                            for (int j = 0; j < dateAndMeal.length; j++) {
                                dAm[i][j] = dateAndMeal[j];
                                //Toast.makeText(DashBoard.this,dAm[i][j], Toast.LENGTH_LONG).show();
                            }
                            i++;
                        }
                    }
                    String nameanddata[] = s.split("@");
                    String singleNameRow = nameanddata[0];

                    String names[] = singleNameRow.split("--");

                    for (int i = 1; i < names.length; i++) {
                        if (names[i] != null) {
                            memberList.add(new memberModel(names[i].trim(), "0191"));
                            // Toast.makeText(DashBoard.this, names[i], Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DashBoard.this, "null", Toast.LENGTH_LONG).show();
                        }
                        if (Store.check == 0) {
                            // Toast.makeText(DashBoard.this, "check zero", Toast.LENGTH_LONG).show();
                            Store.list = memberList;
                            Store.check = 1;
                        }
                    }
                    // Toast.makeText(DashBoard.this,"list Added", Toast.LENGTH_LONG).show();
                    //Store.list = memberList;
                    createView();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(DashBoard.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                ;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("db", mySession.dbname);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(DashBoard.this);
        rQueue.add(request);

        return "";
    }
}
