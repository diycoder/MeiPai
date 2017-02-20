package com.mumu.vcamera.ui.record.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mumu.meipai.R;
import com.mumu.vcamera.po.POThemeSingle;
import com.mumu.vcamera.utils.IsUtils;

import java.util.Observable;
import java.util.Observer;

public class ThemeView extends RelativeLayout implements Observer {

	/** 图标 */
	private ImageView mSelectedIcon;
	private BitmapImageView mIcon;
	/** 标题 */
	private TextView mTitle;
	/** 当前主题 */
	private POThemeSingle mTheme;

	public ThemeView(Context context, POThemeSingle theme) {
		super(context);
		this.mTheme = theme;

		LayoutInflater.from(context).inflate(R.layout.recorder_view_theme_item, this);
		mIcon = (BitmapImageView) findViewById(R.id.icon);
		mSelectedIcon = (ImageView) findViewById(R.id.selected);
		mTitle = (TextView) findViewById(R.id.title);

		mTitle.setText(mTheme.themeDisplayName);

		if (!mTheme.isMV()) {
			// 高级编辑全部变成方的
			// if (mTheme.isWatermark() || mTheme.isSoundEffect() ||
			// mTheme.isFilter() || mTheme.isSpeed())
			mSelectedIcon.setImageResource(R.drawable.record_theme_selected);
		}
		if (mTheme.isEmpty()) {
			mSelectedIcon.setVisibility(View.VISIBLE);
		}
	}

	/** 获取主题图标 */
	public BitmapImageView getIcon() {
		return mIcon;
	}

	@Override
	public void update(Observable observable, Object data) {
		if (data != null && mTheme != null) {
			if (IsUtils.equals(mTheme.themeName, data.toString())) {
				mSelectedIcon.setVisibility(View.VISIBLE);
			} else {
				mSelectedIcon.setVisibility(View.GONE);
			}
		}
	}
}
