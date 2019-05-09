package com.project.wisdomfirecontrol.common.base;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

/**
 * 封装ui相关的操作
 *
 * @author LMX
 */
public interface IUIOperation extends OnClickListener {

	/** 返回activity的布局文件 */
	int getLayoutRes();
	
	/** 查找子控件 */
	void initView() ;
	
	/** 设置监听器 */
	void initListener() ;
	
	/** 初始化数据 */
	void initData() ;

	void initTitleHeight(LinearLayout linearLayout);
	
	/**
	 * 由子类处理点击事件
	 * @param v 点击控件
	 * @param id 点击控件的id
	 */
	void onClick(View v, int id) ;
}

