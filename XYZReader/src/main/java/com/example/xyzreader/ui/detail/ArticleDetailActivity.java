package com.example.xyzreader.ui.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ItemsContract;

public class ArticleDetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_detail);
		if (savedInstanceState!=null)
			return;
		else {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.article_detail_container,new ArticleDetailFragment())
					.commit();
		}
	}
}
