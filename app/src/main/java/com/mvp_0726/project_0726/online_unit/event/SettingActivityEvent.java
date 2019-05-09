package com.mvp_0726.project_0726.online_unit.event;


import com.mvp_0726.common.event.BaseEvent;


public class SettingActivityEvent extends BaseEvent {

    public SettingActivityEvent(int what) {
        super(what);
    }

    public SettingActivityEvent(int what, Object data) {
        super(what, data);
    }

    public SettingActivityEvent() {
    }
}
