package com.project.wisdomfirecontrol.common.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.project.wisdomfirecontrol.R;

/**
 * 扩展的编辑框, 输入内容后，右侧有清除小图标，点击可清空编辑框输入内容
 * 
 * @author LMX
 */
public class EditLayout extends RelativeLayout {

	/** 清除按钮的背景图片 */
	private static int ICON_CLEAR = 0;

	private static int BG_EDIT_TEXT = Color.WHITE;
	
	private ImageView mBtnClear;
	private ImageView mIvDrawableRight;
	
	private EditText mEditText;
	private Context context;

	private TextWatcher mTextWatcher;
	
	private boolean mUseBtnClear = true;
	
	/**
	 * 按钮焦点改变
	 */
	public OnFocusChangeListener mOnEditTextOnFocusChangeListener
			= new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (!mUseBtnClear)
				return ;
			
			if (hasFocus) {
				String text = mEditText.getText().toString().trim();
				if (!TextUtils.isEmpty(text)) {
					mBtnClear.setVisibility(View.VISIBLE);
				}
			} else {
				mBtnClear.setVisibility(View.GONE);
			}
		}
	};

	/** 设置编辑框内容改变监听器 */
	public void setOnTextChangedListener(TextWatcher textWatcher) {
		this.mTextWatcher = textWatcher;
	}

	public EditLayout(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public EditLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		setGravity(Gravity.CENTER_VERTICAL);

		addEditText();
		addClearBtn();
		bindListener();

		updateTheme();
	}
	
	public void disableClearBtnMode() {
		mUseBtnClear = false;
	}

	private int dp2px(int dp) {
		return (int) (dp * getContext().getResources().getDisplayMetrics().density);
	}
	
	private void addEditText() {
		mEditText = new EditText(context);
		mEditText.setSingleLine();
		mEditText.setHintTextColor(Color.parseColor("#ff8F8F8F"));
		mEditText.setTextSize(16);
		mEditText.setOnFocusChangeListener(mOnEditTextOnFocusChangeListener);
		// mEditText.setCursorVisible(false);

		LayoutParams param = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		param.addRule(RelativeLayout.CENTER_VERTICAL);
		addView(mEditText, param);
	}

	public void setClearBtnBG(int btnClearRes) {
		mBtnClear.setBackgroundResource(btnClearRes);
	}

	private void addClearBtn() {
		mBtnClear = new ImageView(context);
		mBtnClear.setVisibility(View.INVISIBLE);
		mBtnClear.setBackgroundResource(R.drawable.selector_btn_clear);

		LayoutParams param = new LayoutParams(
				dp2px(20), dp2px(20));
		param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		param.addRule(RelativeLayout.CENTER_VERTICAL);
		param.rightMargin = dp2px(5);
		addView(mBtnClear, param);
	}

	/**
	 * 设置清空按钮的右边距
	 * @param marginRight
	 */
	public void setClearBtnMarginRight(int marginRight) {
		if (mBtnClear.getLayoutParams() != null) {
			LayoutParams param = (LayoutParams) mBtnClear
					.getLayoutParams();
			param.rightMargin = marginRight;
		}
	}

	public void addDrawableRight(int res) {
		if (mIvDrawableRight == null) {
			mIvDrawableRight = new ImageView(context);
			mIvDrawableRight.setBackgroundResource(res);

			LayoutParams param = new LayoutParams(
					dp2px(25), dp2px(25));
			param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			param.addRule(RelativeLayout.CENTER_VERTICAL);
			param.rightMargin = dp2px(5);
			addView(mIvDrawableRight, param);
		}
	}

	/** 设置hint提示语的字体颜色值 */
	public void setHintColor(int color) {
		if (mEditText != null) {
			mEditText.setHintTextColor(color);
		}
	}

	public View getBtnClear() {
		return mBtnClear;
	}
	
	private void bindListener() {
		if (mUseBtnClear) {
			mBtnClear.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					mEditText.setText("");
				}
			});
		}

		mEditText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mEditText.setCursorVisible(true);
			}
		});

		mEditText.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (mTextWatcher != null)
					mTextWatcher.onTextChanged(s, start, before, count);
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				if (mTextWatcher != null)
					mTextWatcher.beforeTextChanged(s, start, count, after);
			}

			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					if (mUseBtnClear)
						mBtnClear.setVisibility(View.GONE);
					
					if (mIvDrawableRight != null) {
						mIvDrawableRight.setVisibility(VISIBLE);
					}
					
					if (mOnDataClearListener != null)
						mOnDataClearListener.onDataClear();
					
				} else {
					if (mUseBtnClear)
						mBtnClear.setVisibility(View.VISIBLE);
					
					if (mIvDrawableRight != null) {
						mIvDrawableRight.setVisibility(GONE);
					}
				}

				if (mTextWatcher != null)
					mTextWatcher.afterTextChanged(s);
			}
		});
	}
	
	/**
	 * 编辑框数据清空监听器
	 */
	public interface OnDataClearListener {
		public void onDataClear();
	}
	
	private OnDataClearListener mOnDataClearListener;
	
	public void setOnDataClearListener(OnDataClearListener onDataClearListener) {
		this.mOnDataClearListener = onDataClearListener;
	}
	
	public void updateTheme() {
		Drawable bg = getBackground();
		if (bg == null) {
			mEditText.setBackgroundColor(BG_EDIT_TEXT);
		} else {
			mEditText.setBackgroundColor(Color.TRANSPARENT);
		}

		if (ICON_CLEAR != 0)
			mBtnClear.setBackgroundResource(ICON_CLEAR);

		mEditText.setPadding(dp2px(10), 0, dp2px(35), 0);
		mEditText.setGravity(Gravity.CENTER_VERTICAL);
	}

	public void hideClearBtn() {
		mBtnClear.setVisibility(View.INVISIBLE);
	}
	
	public void setTextColor(int color) {
		mEditText.setTextColor(color);
	}

	public void setTextSize(int size) {
		mEditText.setTextSize(size);
	}

	public String getText() {
		return mEditText.getText().toString().trim();
	}

	public void setText(String text) {
		mEditText.setText(text);
	}

	public void setHint(String hint) {
		mEditText.setHint(hint);
	}

	public void setSelection(int index) {
		mEditText.setSelection(index);
	}

	public EditText getEditText() {
		return mEditText;
	}
	
	public void setSingleline() {
		mEditText.setSingleLine();
	}
	
	public void setPasswordStyle() {
		mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
	}
}













