package com.project.wisdomfirecontrol.firecontrol.ui.adapter_Rv;

import android.content.Context;
import android.view.ViewGroup;

import com.project.wisdomfirecontrol.common.ui.BaseAdapterLV;
import com.project.wisdomfirecontrol.common.ui.BaseHolderLV;
import com.project.wisdomfirecontrol.firecontrol.model.bean.video.VideoesXBean;
import com.project.wisdomfirecontrol.firecontrol.ui.holder_Rv.VideoListHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/4/28.
 */

public class VideoListAdapter extends BaseAdapterLV<VideoesXBean>{
    public VideoListAdapter(Context context, List<VideoesXBean> listData) {
        super(context, listData);
    }

    @Override
    public BaseHolderLV<VideoesXBean> createViewHolder
            (Context context, ViewGroup parent, VideoesXBean bean, int position) {
        return new VideoListHolder(context,parent,this,position,bean);
    }
}
