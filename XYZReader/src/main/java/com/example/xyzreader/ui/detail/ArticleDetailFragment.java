package com.example.xyzreader.ui.detail;



import android.database.Cursor;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
	private long articleId;
	@BindView(R.id.img_article_detail)ImageView imgArticlePhoto;
	@BindView(R.id.txt_article_title)TextView tvArticleTitle;
	@BindView(R.id.txt_article_body)TextView tvArticleBody;
	@BindView(R.id.toolbar_article_detail)Toolbar toolbar;
	public ArticleDetailFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
		articleId = ItemsContract.Items.getItemId(getActivity().getIntent().getData());
		getLoaderManager().initLoader(0,null,this);
		ButterKnife.bind(this,rootView);
		return rootView;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (articleId>0)
			return ArticleLoader.newInstanceForItemId(getActivity(),articleId);
		else
			return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (cursor==null || !cursor.moveToFirst())
			return;

		String photoUrl = cursor.getString(ArticleLoader.Query.PHOTO_URL);
		Glide.with(this).load(photoUrl).into(imgArticlePhoto);
		String val_article_title = cursor.getString(ArticleLoader.Query.TITLE);
		String val_article_date = cursor.getString(ArticleLoader.Query.PUBLISHED_DATE);
		String val_article_author = cursor.getString(ArticleLoader.Query.AUTHOR);
		String val_article_body = cursor.getString(ArticleLoader.Query.BODY);

		tvArticleBody.setText(val_article_body);
		toolbar.setTitle(val_article_title);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}
}
