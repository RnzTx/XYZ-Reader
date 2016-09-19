package com.example.xyzreader.ui.detail;



import android.database.Cursor;
import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
	private long articleId;
	@BindView(R.id.iv_article_detail_image)ImageView imgArticlePhoto;
	@BindView(R.id.tv_article_body)TextView tvArticleBody;
	@BindView(R.id.toolbar_article_detail)Toolbar toolbar;
	@BindView(R.id.tv_article_info)TextView tvArticleInfo;
	@BindView(R.id.collapsing_toolbar_article_detail)CollapsingToolbarLayout collapsingToolbarLayout;
	ArticleDetailFragment fragment = this;

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

		// home button navigation
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});
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
		long val_article_date = cursor.getLong(ArticleLoader.Query.PUBLISHED_DATE);
		String val_article_author = cursor.getString(ArticleLoader.Query.AUTHOR);
		String val_article_body = cursor.getString(ArticleLoader.Query.BODY);

		tvArticleBody.setText(Html.fromHtml(val_article_body).toString());
		tvArticleInfo.setText(
						new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).format(new Date(val_article_date)).toString()+
						" by "+
						val_article_author
		);
//		collapsingToolbarLayout.setTitle(val_article_title);
		toolbar.setTitle(val_article_title);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}
}
