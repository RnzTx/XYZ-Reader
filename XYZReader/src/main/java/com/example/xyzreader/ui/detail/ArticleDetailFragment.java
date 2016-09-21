package com.example.xyzreader.ui.detail;



import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class ArticleDetailFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<Cursor>,RequestListener<String,GlideDrawable>{
	private long articleId;
	@BindView(R.id.iv_article_detail_image)ImageView imgArticlePhoto;
	@BindView(R.id.tv_article_body)TextView tvArticleBody;
	@BindView(R.id.toolbar_article_detail)Toolbar toolbar;
	@BindView(R.id.tv_article_info)TextView tvArticleInfo;
	@BindView(R.id.tv_article_title)TextView tvArticleTitle;
//	@BindViews({R.id.tv_article_title,R.id.tv_article_info}) List<TextView> articleHeaders;
	@BindView(R.id.article_detail_body_headers)LinearLayout articleHeaders;

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
		Glide.with(this)
			.load(photoUrl)
			.listener(this)
			.into(imgArticlePhoto);
		
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
//		toolbar.setTitle(val_article_title);
		tvArticleTitle.setText(val_article_title);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}

	@Override
	public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
		return false;
	}

	@Override
	public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
	                               boolean isFromMemoryCache, boolean isFirstResource) {
		Bitmap bitmap = ((GlideBitmapDrawable)resource.getCurrent()).getBitmap();
		Palette palette = Palette.from(bitmap).generate();
		int defaultDarkColor = ContextCompat.getColor(getContext(),R.color.cardview_dark_background);

		int darkColor = palette.getDarkMutedColor(defaultDarkColor);
		articleHeaders.setBackgroundColor(darkColor);
		return false;
	}
}
