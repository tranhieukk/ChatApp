package com.uiresource.cookit;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uiresource.cookit.recycler.CommentsAdapter;
import com.uiresource.cookit.recycler.ItemComment;
import com.uiresource.cookit.utils.CircleGlide;

import java.util.ArrayList;
import java.util.List;

public class Detail extends BaseActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private int Position;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewComments;
    private CommentsAdapter mAdapterComments;
    private CoordinatorLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        rootView = (CoordinatorLayout) findViewById(R.id.rootview);
        setupToolbar(R.id.toolbar, "", android.R.color.white, android.R.color.transparent, R.drawable.ic_arrow_back);
        Intent intent = this.getIntent();
        Position= Integer.parseInt(intent.getStringExtra("vitri").toString());
        Toast.makeText(Detail.this,""+ Position,Toast.LENGTH_LONG).show();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);



        recyclerViewComments = (RecyclerView) findViewById(R.id.recyclerComment);

        mAdapterComments = new CommentsAdapter(generateComments(), this);
        LinearLayoutManager mLayoutManagerComment = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewComments.setLayoutManager(mLayoutManagerComment);
        recyclerViewComments.setItemAnimator(new DefaultItemAnimator());
        recyclerViewComments.setAdapter(mAdapterComments);


        final ImageView imageComment = (ImageView) findViewById(R.id.iv_user);
        Glide.with(this)
                .load(Uri.parse("https://randomuser.me/api/portraits/women/75.jpg"))
                .transform(new CircleGlide(this))
                .into(imageComment);

        final ImageView image = (ImageView) findViewById(R.id.image);
        Glide.with(this).load(Uri.parse("https://images.pexels.com/photos/140831/pexels-photo-140831.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb")).into(image);

    }


    public List<ItemComment> generateComments(){
        List<ItemComment> itemList = new ArrayList<>();
        String username[] = {"LAURA MAGNAGO"};
        String date[] = {".27-01-2017"};
        String comments[] = {"Made this for a BBQ today and it was amazing. Bought 2 Madiera cakes from Tesco and cut them Into wedges. Poured the coffee over the top. And used 75ml of Ameretti instead of masala in the cream. Will be making again next week for a gathering and probably many more times! :)"};
        String userphoto[] = {"https://randomuser.me/api/portraits/women/20.jpg"};
        String img1[] = {"https://images.pexels.com/photos/8382/pexels-photo.jpg?h=350&auto=compress&cs=tinysrgb"};
        String img2[] = {"https://images.pexels.com/photos/140831/pexels-photo-140831.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb"};

        for (int i = 0; i<username.length; i++){
            ItemComment comment = new ItemComment();
            comment.setUsername(username[i]);
            comment.setUserphoto(userphoto[i]);
            comment.setDate(date[i]);
            comment.setComments(comments[i]);
            comment.setImg1(img1[i]);
            comment.setImg2(img2[i]);
            itemList.add(comment);
        }
        return itemList;
    }


}

