package com.example.xyzreader.ui.detail;


import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
	private long articleId;

	public ArticleDetailFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
		articleId = ItemsContract.Items.getItemId(getActivity().getIntent().getData());

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
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		data.moveToFirst();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}
}
