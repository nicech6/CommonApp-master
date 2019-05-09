package com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;


public class BannerAdapter extends PagerAdapter {
	private List<String> adList;
	private Context context;
//	private DisplayImageOptions options;
//	private ImageLoader imageLoader;
	public BannerAdapter(Context mContext, List<String> adList) {
		this.adList = adList;
		this.context = mContext;
//		options = ImgUtils.mImageOptions();
//		imageLoader = ImageLoader.getInstance();
//		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final int pos = position % adList.size();
		ImageView imageView = new ImageView(context);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(params);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//		imageLoader.displayImage(adList.get(pos), imageView, options);
		Glide.with(context).load(adList.get(pos)).into(imageView);
		container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		return imageView;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}


}
