package com.example.WorshipDocument.popup.action;

public interface PopupListener
{
    /**
     * action when click into one of popup list items.
     *
     * @param itemId : id of item just click
     * @param value  : value return when has checkbox selected or not.
     *               anythings other POPUP_TYPE this null
     */
    public void onItemClick(int itemId, Boolean value);

    public void onDismiss();
}