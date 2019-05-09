package com.mvp_0726.project_0726.online_unit.event;


import com.mvp_0726.common.event.BaseEvent;


public class SettingPoliceEvent extends BaseEvent {

    public SettingPoliceEvent(int what) {
        super(what);
    }

    public SettingPoliceEvent(int what, Object data) {
        super(what, data);
    }

    public SettingPoliceEvent() {
    }
}
