package com.interjoy.framework;

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

import com.interjoy.CardAndDatabase.DBHelper;
import com.interjoy.CardAndDatabase.FruitCard;
import com.interjoy.FruitsIdentification.MainActivity;
import com.interjoy.FruitsIdentification.R;
import com.interjoy.login.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FrameworkActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private DBHelper myDatebaseHelper;
    private DrawerLayout mdrawerLayout;


    private Fruit[] fruits = {
            new Fruit("Apple", R.drawable.apple,"This is an apple"), new Fruit("Banana", R.drawable.banana,"This is a banana"),
            new Fruit("Orange", R.drawable.orange,"This is an orgnge"), new Fruit("Watermelon", R.drawable.watermelon,"This is a watermelon"),
            new Fruit("Pear", R.drawable.pear,"This is a pear"), new Fruit("Grape", R.drawable.grape,"This is a grape"),
            new Fruit("Pineapple", R.drawable.pineapple,"This is a pineapple"), new Fruit("Strawberry", R.drawable.strawberry,"This is a strawberry"),
            new Fruit("Cherry", R.drawable.cherry,"This is a cherry"), new Fruit("Mango", R.drawable.mango,"This is a mango"),
            new Fruit("Orange",R.drawable.fruit1,"This is an orgnge"),new Fruit("Lemon",R.drawable.fruit2,"This is a lemon"),
            new Fruit("Blueberry",R.drawable.fruit3,"This is a blueberry"),new Fruit("Strawberry",R.drawable.fruit4,"This is a strawberry"),
            new Fruit("Kiwi",R.drawable.fruit5,"This is a kiwi")
    };

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

        //悬浮按钮
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.add_fruit);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FrameworkActivity.this);
                builder.setTitle("二次确认");
                builder.setMessage("Add a fruit card?");
                builder.setPositiveButton("Yes,add one", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FrameworkActivity.this,"请在下一页中添加",Toast.LENGTH_SHORT).show();
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

                Snackbar.make(v,"Add Fruits Card",Snackbar.LENGTH_SHORT).setAction("Undo",

                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(FrameworkActivity.this,
                                        "Undo add fruit card successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        //toolbar工具栏初始化
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //抽屉页初始化
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        //左侧滑出目录的点击事件实现
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.YangPinKu:
                        Toast.makeText(FrameworkActivity.this,"This is the Fruits Library",
                                Toast.LENGTH_SHORT).show();
                        mdrawerLayout.closeDrawers();
                        break;
                    case R.id.ShiBie:
                        //12.30添加水果识别功能
                        Intent intent = new Intent(FrameworkActivity.this, MainActivity.class);
                        startActivity(intent);
                        mdrawerLayout.closeDrawers();
                        break;

                    case R.id.MyAcount:
                        AlertDialog.Builder builder = new AlertDialog.Builder(FrameworkActivity.this);
                        builder.setTitle("My Account Info").setMessage("Name: Ruan /n Mail:1329522082@qq.com")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //留空
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //留空
                            }
                        }).create();
                        builder.show();
                        //Toast.makeText(FrameworkActivity.this,"This is MyAccount page",Toast.LENGTH_SHORT).show();
                        mdrawerLayout.closeDrawers();
                        break;

                    case R.id.object_library:
                        //待添加功能
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(FrameworkActivity.this);
                        builder1.setTitle("Objects Library").setMessage("Coming soon...")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //留空
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //留空
                            }
                        }).create();
                        builder1.show();
                        mdrawerLayout.closeDrawers();
                        break;

                    case R.id.nav_share:
                        //待添加功能
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(FrameworkActivity.this);
                        builder2.setTitle("Share Function[soon coming...]").setMessage("You can share anything to your friends")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //留空
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //留空
                            }
                        }).create();
                        builder2.show();
                        mdrawerLayout.closeDrawers();
                        break;

                    case R.id.nav_contact_us:
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(FrameworkActivity.this);
                        builder3.setTitle("Contact us")
                                .setMessage("please phone 18316299722")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(FrameworkActivity.this,"soon coming...",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(FrameworkActivity.this,"Do nothing",Toast.LENGTH_SHORT).show();
                                    }
                                });
                        builder3.create();
                        builder3.show();
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
        //默认点击抽屉页的栏
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
                Toast.makeText(FrameworkActivity.this,"This is the headview listener",Toast.LENGTH_SHORT).show();
                mdrawerLayout.closeDrawers();
            }
        });


    }
    //界面跳转，跳到水果卡界面
    private void openFriutCard(){
        Intent intent =new Intent();
        intent.setClass(FrameworkActivity.this,FruitCard.class);
        startActivity(intent);
    }

    //初始化显示20个水果卡片
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
            String info = cursor.getString(cursor.getColumnIndex("fruitinfo"));
            Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
            Drawable pic=chage_to_drawable(bmpout);
            Fruit Nfruit = new Fruit(name,in,did,info);
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
                //Toast.makeText(this,"You clicked Backup",Toast.LENGTH_SHORT).show();
                refreshFruits();
                break;

            case R.id.addfirut1:
                //Toast.makeText(this,"You clicked AddNew",Toast.LENGTH_SHORT).show();
                openFriutCard();
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
