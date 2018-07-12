package com.zlin.property.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zlin.property.R;
import com.zlin.property.control.FuUiFrameManager;


/**
 * 
 */
public class NavigationMenu extends LinearLayout {

	private View mNavigation;

	private Context mContext;

	public NavigationMenu(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;

		mNavigation = LayoutInflater.from(context).inflate(
				R.layout.fu_view_navigation, this, true);

		RelativeLayout lHome = (RelativeLayout) mNavigation
				.findViewById(R.id.fu_home);
		lHome.setOnClickListener(mOnClick);

		// RelativeLayout lCommun = (RelativeLayout) mNavigation
		// .findViewById(R.id.fu_communion);
		// lCommun.setOnClickListener(mOnClick);

		RelativeLayout lNews = (RelativeLayout) mNavigation
				.findViewById(R.id.fu_news);
		lNews.setOnClickListener(mOnClick);

		// RelativeLayout lJy = (RelativeLayout) mNavigation
		// .findViewById(R.id.fu_jy);
		// lJy.setOnClickListener(mOnClick);

		RelativeLayout lHealth = (RelativeLayout) mNavigation
				.findViewById(R.id.fu_health);
		lHealth.setOnClickListener(mOnClick);

		RelativeLayout lMore = (RelativeLayout) mNavigation
				.findViewById(R.id.fu_more);
		lMore.setOnClickListener(mOnClick);

		mCurrentAction = 1;

	}

	// ==========================================

	private EventClick mEventListen;

	public void setEventListen(EventClick listen) {

		mEventListen = listen;

	}

	private int mCurrentAction; // 1 首页，2资讯 3 健康 4 更多

	private boolean mPointShow;

	public void setMorePoint(boolean point) {

		ImageView lMoreBp = (ImageView) mNavigation
				.findViewById(R.id.ic_fu_more);

		mPointShow = point;

		switch (mCurrentAction) {
		case 4:

			lMoreBp.setImageResource(mPointShow ? R.mipmap.ic_more_select
					: R.mipmap.ic_select_more);

			break;

		default:

			lMoreBp.setImageResource(mPointShow ? R.mipmap.ic_more_un_select
					: R.mipmap.ic_more);

			break;
		}
	}

	// ==================================================

	OnClickListener mOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {

			clearCurrentView();

			switch (v.getId()) {
			case R.id.fu_home:

				mCurrentAction = 1;

				ImageView lHomeBp = (ImageView) mNavigation
						.findViewById(R.id.ic_fu_home);
				lHomeBp.setImageResource(R.mipmap.ic_select_home);

				TextView lHomeTv = (TextView) mNavigation
						.findViewById(R.id.tv_fu_home);
				lHomeTv.setTextColor(mContext.getResources().getColor(
						R.color.bg_withe));

				mEventListen.onEventClick(FuUiFrameManager.FU_MAIN_HOME);

				break;
			case R.id.fu_news:

				mCurrentAction = 2;

				ImageView lNewsBp = (ImageView) mNavigation
						.findViewById(R.id.ic_fu_news);
				lNewsBp.setImageResource(R.mipmap.ic_select_news);

				TextView lNewsTv = (TextView) mNavigation
						.findViewById(R.id.tv_fu_news);
				lNewsTv.setTextColor(mContext.getResources().getColor(
						R.color.bg_withe));


				break;
			case R.id.fu_health:

				mCurrentAction = 3;

				ImageView lHealthBp = (ImageView) mNavigation
						.findViewById(R.id.ic_fu_health);
				lHealthBp.setImageResource(R.mipmap.ic_select_health);

				TextView lHealthTv = (TextView) mNavigation
						.findViewById(R.id.tv_fu_health);
				lHealthTv.setTextColor(mContext.getResources().getColor(
						R.color.bg_withe));
				mEventListen.onEventClick(FuUiFrameManager.FU_MINE);


				break;
			case R.id.fu_more:

				mCurrentAction = 4;

				ImageView lMoreBp = (ImageView) mNavigation
						.findViewById(R.id.ic_fu_more);

				TextView lMoreTv = (TextView) mNavigation
						.findViewById(R.id.tv_fu_more);
				lMoreTv.setTextColor(mContext.getResources().getColor(
						R.color.bg_withe));

				if (mPointShow) {

					lMoreBp.setImageResource(R.mipmap.ic_more_select);

				} else {

					lMoreBp.setImageResource(R.mipmap.ic_select_more);

				}

				break;

			default:
				break;
			}
		}
	};

	public void setCurrentBtnState(int action) {

		clearCurrentView();

		switch (action) {
		case FuUiFrameManager.FU_MAIN_HOME:

			ImageView lHomeBp = (ImageView) mNavigation
					.findViewById(R.id.ic_fu_home);
			lHomeBp.setImageResource(R.mipmap.ic_select_home);

			TextView lHomeTv = (TextView) mNavigation
					.findViewById(R.id.tv_fu_home);
			lHomeTv.setTextColor(mContext.getResources().getColor(
					R.color.bg_withe));

			break;


		}

	}

	/**
	 */
	private void clearCurrentView() {

		ImageView lHomeBp = (ImageView) mNavigation
				.findViewById(R.id.ic_fu_home);
		lHomeBp.setImageResource(R.mipmap.ic_home);

		TextView lHomeTv = (TextView) mNavigation
				.findViewById(R.id.tv_fu_home);
		lHomeTv.setTextColor(mContext.getResources().getColor(
				R.color.bg_bottom_menu_tv_no));

		// ImageView lCommuBp = (ImageView) mNavigation
		// .findViewById(R.id.ic_fu_communion);
		// lCommuBp.setImageResource(R.drawable.ic_chat);
		//
		// TextView lCommuTv = (TextView) mNavigation
		// .findViewById(R.id.tv_fu_communion);
		// lCommuTv.setTextColor(mContext.getResources().getColor(
		// R.color.bg_bottom_menu_tv_no));

		ImageView lNewsBp = (ImageView) mNavigation
				.findViewById(R.id.ic_fu_news);
		lNewsBp.setImageResource(R.mipmap.ic_news);
		//
		TextView lNewsTv = (TextView) mNavigation
				.findViewById(R.id.tv_fu_news);
		lNewsTv.setTextColor(mContext.getResources().getColor(
				R.color.bg_bottom_menu_tv_no));

		// ImageView lJyBp = (ImageView)
		// mNavigation.findViewById(R.id.ic_fu_jy);
		// lJyBp.setImageResource(R.drawable.ic_jy);
		//
		// TextView lJyTv = (TextView) mNavigation.findViewById(R.id.tv_fu_jy);
		// lJyTv.setTextColor(mContext.getResources().getColor(
		// R.color.bg_bottom_menu_tv_no));

		ImageView lHealthBp = (ImageView) mNavigation
				.findViewById(R.id.ic_fu_health);
		lHealthBp.setImageResource(R.mipmap.ic_health);

		TextView lHealthTv = (TextView) mNavigation
				.findViewById(R.id.tv_fu_health);
		lHealthTv.setTextColor(mContext.getResources().getColor(
				R.color.bg_bottom_menu_tv_no));

		ImageView lMoreBp = (ImageView) mNavigation
				.findViewById(R.id.ic_fu_more);

		TextView lMoreTv = (TextView) mNavigation
				.findViewById(R.id.tv_fu_more);
		lMoreTv.setTextColor(mContext.getResources().getColor(
				R.color.bg_bottom_menu_tv_no));

		if (mPointShow) {

			lMoreBp.setImageResource(R.mipmap.ic_more_un_select);

		} else {

			lMoreBp.setImageResource(R.mipmap.ic_more);

		}
	}

	// -===================================================

	public interface EventClick {

		public void onEventClick(int event);
	}

}
