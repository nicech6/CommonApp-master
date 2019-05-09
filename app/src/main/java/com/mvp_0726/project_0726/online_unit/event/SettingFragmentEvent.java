package com.mvp_0726.project_0726.online_unit.event;


import com.mvp_0726.common.event.BaseEvent;


public class SettingFragmentEvent extends BaseEvent {

    public SettingFragmentEvent(int what) {
        super(what);
    }

    public SettingFragmentEvent(int what, Object data) {
        super(what, data);
    }

    public SettingFragmentEvent() {
    }
}
