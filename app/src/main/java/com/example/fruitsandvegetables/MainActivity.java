package com.example.fruitsandvegetables;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fruitsandvegetables.identification.IdentificationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private DBHelper myDatebaseHelper;
    private DrawerLayout mdrawerLayout;


    private Fruit[] fruits = {new Fruit("Apple", R.drawable.apple), new Fruit("Banana", R.drawable.banana),
            new Fruit("Orange", R.drawable.orange), new Fruit("Watermelon", R.drawable.watermelon),
            new Fruit("Pear", R.drawable.pear), new Fruit("Grape", R.drawable.grape),
            new Fruit("Pineapple", R.drawable.pineapple), new Fruit("Strawberry", R.drawable.strawberry),
            new Fruit("Cherry", R.drawable.cherry), new Fruit("Mango", R.drawable.mango)};

    private List<Fruit> fruitList = new ArrayList<>();

    private FruitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatebaseHelper = new DBHelper(this, "DAILY.db", null, 1);
        SQLiteDatabase db = myDatebaseHelper.getWritableDatabase();
        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });


        FloatingActionButton deletefloatingactionbutton = (FloatingActionButton)findViewById(R.id.delete_fruit);
        deletefloatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("二次确认");
                builder.setMessage("Do you really want to delete this fruit card?");
                builder.setPositiveButton("Yes,delete it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No,No,No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                builder.create();
                builder.show();

                Snackbar.make(v,"Fruit Data Deleted",
                        Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"Fruit Data Restored",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.add_fruit);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("二次确认");
                builder.setMessage("Do you really want to add a fruit card?");
                builder.setPositiveButton("Yes,add one", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Added Successfully",Toast.LENGTH_SHORT).show();
                        openFriutCard();
                    }
                });
                builder.setNegativeButton("No,No,No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                builder.create();
                builder.show();

                Snackbar.make(v,"Fruit Added",Snackbar.LENGTH_SHORT).setAction("Undo",

                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,
                                        "Undo add fruit successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        //左侧滑出目录的点击事件实现
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.YangPinKu:
                        Toast.makeText(MainActivity.this,"This is the NavigationView YangPinKu",
                                Toast.LENGTH_SHORT).show();
                        mdrawerLayout.closeDrawers();
                        break;
                    case R.id.ShiBie:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("This is the main function");
                        builder.setMessage("We will spare no effort to finish it");
                        builder.setPositiveButton("Sure ,we will", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this,"soon coming!",Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Still we will", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    //
                            }
                        });
                        builder.create();
                        builder.show();
                        mdrawerLayout.closeDrawers();
                        break;
                    case R.id.MyAcount:
                        Toast.makeText(MainActivity.this,"This is MyAccount page",Toast.LENGTH_SHORT).show();
                        mdrawerLayout.closeDrawers();
                        break;

                    case R.id.object_identification:

                        //12.29添加
                        Intent intent = new Intent(MainActivity.this, IdentificationActivity.class);
                        startActivity(intent);
                        mdrawerLayout.closeDrawers();
                        break;
                    case R.id.object_library:
                        //待添加
                        mdrawerLayout.closeDrawers();
                        break;

                    case R.id.nav_share:
                        //待添加
                        mdrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_contact_us:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("Contact us")
                                .setMessage("please phone 18316299722")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,"soon coming...",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,"Do nothing",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder1.create();
                                builder1.show();
                        mdrawerLayout.closeDrawers();
                        break;

                        default:
                            break;
                }
                return true;
            }
        });



        mdrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        navigationView.setCheckedItem(R.id.YangPinKu);
        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mdrawerLayout.closeDrawers();
                return true;
            }
        });*/


        //左侧滑出栏的点击事件
        View headview = navigationView.getHeaderView(0);
        headview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"This is the headview listener",Toast.LENGTH_SHORT).show();
                mdrawerLayout.closeDrawers();
            }
        });


    }
    //界面跳转，跳到水果卡界面
    private void openFriutCard(){
        Intent intent =new Intent();
        intent.setClass(MainActivity.this,FruitCard.class);
        startActivity(intent);
    }
    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    public void Query(){
        SQLiteDatabase db = myDatebaseHelper.getWritableDatabase();
        Cursor cursor = db.query("Photo", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            byte[] in = cursor.getBlob(cursor.getColumnIndex("fruitimage"));
            long did=cursor.getLong(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("fruitname"));
            Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
            Drawable pic=chage_to_drawable(bmpout);
            Fruit Nfruit = new Fruit(name,pic,did);
            fruitList.add(Nfruit);
        }
        cursor.close();
        db.close();
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        Query();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    public Drawable chage_to_drawable(Bitmap bp)
    {
        //因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
        Bitmap bm=bp;
        BitmapDrawable bd= new BitmapDrawable(this.getResources(), bm);
        return bd;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                mdrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this,"You clicked Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.addfirut1:
                Toast.makeText(this,"You clicked AddNew",Toast.LENGTH_SHORT).show();
                openFriutCard();
                break;
            case R.id.delete:
                Toast.makeText(this,"You clicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting://(其实已经改成了下线功能，只是懒得改id)
                //Toast.makeText(this,"You clicked Setting",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("com.example.fruitsandvegetables.FORCE_OFFLINE");
                sendBroadcast(intent);
                break;
            default:
                    break;
        }
        return true;
    }
}
