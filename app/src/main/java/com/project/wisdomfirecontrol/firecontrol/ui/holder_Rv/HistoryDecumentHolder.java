package com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;

import java.util.List;

import com.project.wisdomfirecontrol.GlideApp;
import com.project.wisdomfirecontrol.R;
import com.project.wisdomfirecontrol.common.ui.BaseAdapterRV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderRV;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.BaseProtocol;
import com.project.wisdomfirecontrol.firecontrol.model.protocol.CommonProtocol;
import com.project.wisdomfirecontrol.firecontrol.ui.utils.Unit_StringUtils;

/**
 * Created by Administrator on 2018/3/23.
 */

public class HistoryDecumentHolder extends BaseHolderRV<String> implements BaseProtocol.OnHttpCallback {

    private ImageView iv_photo;
    private ImageView iv_delete;
    private StringBuffer stringBuffer;
    private String stringImg;
    private boolean isDeleteAble = true;
    private String pid;

    public HistoryDecumentHolder(Context context, ViewGroup parent, BaseAdapterRV<String> adapter, int viewType) {
        super(context, parent, adapter, viewType, R.layout.rv_item_history_decument);
    }

    @Override
    public void onFindViews(View itemView) {
        iv_photo = itemView.findViewById(R.id.iv_photo);
        iv_delete = itemView.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDeleteAble) {//此时为增加动画效果，刷新部分数据源，防止删除错乱
                    isDeleteAble = false;//初始值为true,当点击删除按钮以后，休息0.5秒钟再让他为
                    //true,起到让数据源刷新完成的作用

                    if (adapter.listData.size() >= 1) {
                        adapter.listData.remove(position);//删除数据源
                        adapter.notifyItemRemoved(position);//刷新被删除的地方
//                        adapter.notifyDataSetChanged();
                        adapter.notifyItemRangeChanged(position, adapter.getItemCount());//刷新被删除数据，以及其后面的数据
                        updataListDatas(adapter.listData);
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);//休息
                                isDeleteAble = true;//可点击按钮
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }).start();
                }
            }
        });
    }

    /*上传剩余图片地址*/
    private void updataListDatas(List<String> listData) {
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer();
        }
        for (int i = 0; i < listData.size(); i++) {
            if (i == (listData.size() - 1)) {
                stringBuffer.append(listData.get(i));
            } else {
                stringBuffer.append(listData.get(i)).append(",");
            }
        }
        stringImg = stringBuffer.toString();

        pid = Unit_StringUtils.getUserPid(context);
        Log.d("tag", "updataListDatas: " + stringImg + " +++ " + pid);
        CommonProtocol commonProtocol = new CommonProtocol();
        commonProtocol.deleteOrganImg(this, pid, stringImg, "true");

    }

    @Override
    protected void onRefreshView(String bean, int position) {
        if (!TextUtils.isEmpty(bean)) {
            iv_photo.setTag(R.id.imageid, bean);
            if (iv_photo.getTag(R.id.imageid) != null && bean == iv_photo.getTag(R.id.imageid)) {
                RequestBuilder<Bitmap> bitmapRequestBuilder = GlideApp.with(context)
                        .asBitmap()//指定Bitmap类型的RequestBuilder
                        .load(bean)//网络URL
                        .error(R.drawable.image_nosee)//异常图片
                        .placeholder(R.drawable.image_login)//占位图片
                        .fallback(R.drawable.image_nosee);//当url为空时，显示图片

                bitmapRequestBuilder.into(iv_photo);
            }
        }
    }

    @Override
    protected void onItemClick(View itemView, final int position, String bean) {
        super.onItemClick(itemView, position, bean);
    }

    @Override
    public void onHttpSuccess(int reqType, Message obj) {

    }

    @Override
    public void onHttpError(int reqType, String error) {

    }
}
