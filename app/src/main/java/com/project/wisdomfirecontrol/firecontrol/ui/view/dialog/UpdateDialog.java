package com.project.wisdomfirecontrol.firecontrol.ui.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.wisdomfirecontrol.R;


/**
 * 自定义dialog
 */
public class UpdateDialog {
	Context context;
	Dialogcallback dialogcallback;
	Dialog dialog;
	TextView sure;
	public TextView content;
	EditText editText;
	private float width = 162;
	private float height = 120;
	private LinearLayout ll_container;
	private TextView cancle;
	
	/**
	 * init the dialog
	 * @return
	 */
	public UpdateDialog(Context con) {
		this.context = con;
		dialog = new Dialog(context, R.style.dialog);
		// 透明
		Window window = dialog.getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.alpha = 1.0f;
		// 设置宽度、高度、密度、对齐方式
		float density = getDensity(context);
		params.width = (int) (width * density);
		params.height = (int) (height * density);
//		params.x = 150;// 设置x坐标
//		params.y = -60;// 设置y坐标
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
		dialog.setContentView(R.layout.dialog_update);
		ll_container = (LinearLayout)dialog.findViewById(R.id.ll_container);
		content = (TextView)dialog.findViewById(R.id.content);
		
		sure = (TextView) dialog.findViewById(R.id.ok);
		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogcallback.dialogdo(ll_container);
			}
		});
		
		cancle = (TextView) dialog.findViewById(R.id.cancle);
		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogcallback.dialogcancle();
			}
		});
	}
	/**
	 * 获取显示密度
	 * @param context
	 * @return
	 */
	public float getDensity(Context context) {
		Resources res = context.getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		return dm.density;
	}
	/**
	 * 设定一个interfack接口,使mydialog可以處理activity定義的事情
	 * @author 
	 */
	public interface Dialogcallback {
		public void dialogdo(LinearLayout container);
		public void dialogcancle();
	}
	public void setDialogCallback(Dialogcallback dialogcallback) {
		this.dialogcallback = dialogcallback;
	}
	/**
	 * @category Set The Content of the TextView
	 * */
	public void setContent(String ctm) {
		content.setText(ctm);
	}
	/**
	 * Get the Text of the EditText
	 * */
	public String getText() {
		return editText.getText().toString();
	}
	public void isCanceledOnTouchOutside(boolean flag){
		dialog.setCanceledOnTouchOutside(flag);
	}
	public void show() {
		dialog.show();
	}
	public void hide() {
		dialog.hide();
	}
	public void dismiss() {
		dialog.dismiss();
	}
	
}