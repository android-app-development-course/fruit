package com.interjoy.framework;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.interjoy.FruitsIdentification.R;

public class FruitActivity extends AppCompatActivity {

    public static final String FRUIT_NAME = "fruitname";

    public static final String FRUIT_IMAGE_ID = "fruit_image_id";

    public static final String FRUIT_INFO="fruitinfo";

    public static final String FRUIT_IMAGE = "fruitimage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUIT_NAME);
        int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
        String fruitinfo = intent.getStringExtra(FRUIT_INFO);
        byte[] in = intent.getByteArrayExtra(FRUIT_IMAGE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView = (ImageView) findViewById(R.id.fruit_image_view);
        TextView fruitContentText = (TextView) findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(fruitName);
        if(fruitImageId==0)
        {
            Bitmap fruitimage = BitmapFactory.decodeByteArray(in, 0, in.length);
            fruitImageView.setImageBitmap(fruitimage);
        }
        else Glide.with(this).load(fruitImageId).into(fruitImageView);
        String fruitContent = generateFruitContent(fruitinfo);
        fruitContentText.setText(fruitContent);
    }

    private String generateFruitContent(String fruitinfo) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            fruitContent.append(fruitinfo);
            fruitContent.append("\n");
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}