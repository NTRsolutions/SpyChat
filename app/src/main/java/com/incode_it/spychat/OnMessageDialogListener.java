package com.incode_it.spychat;

public interface OnMessageDialogListener {
    void onSetTime();

    void onDeleteMessage();

    void onReSendMessage();

    void onApplyTime(int hour, int minute);
}