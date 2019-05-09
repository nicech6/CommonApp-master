package com.project.wisdomfirecontrol.firecontrol.ui.Interface;


import com.project.wisdomfirecontrol.firecontrol.ui.view.dialog.WaitDialog;

public interface DialogControl {

	public abstract void hideWaitDialog();

	public abstract WaitDialog showWaitDialog();

	public abstract WaitDialog showWaitDialog(int resid);

	public abstract WaitDialog showWaitDialog(String text);
}
