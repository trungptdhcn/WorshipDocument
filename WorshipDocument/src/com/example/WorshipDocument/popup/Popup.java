package com.example.WorshipDocument.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.example.WorshipDocument.R;
import com.example.WorshipDocument.popup.action.PopupListener;
import com.example.WorshipDocument.popup.common.PopupLocationType;
import com.example.WorshipDocument.popup.common.PopupSetting;

import java.util.List;

/**
 * User: AnhNT
 * Date: 2/20/14
 * Time: 10:13 PM
 */
public class Popup
{
    private PopupWindow popupWindow;
    private PopupListener popupListener;
    private ScrollView rootView;
    private LinearLayout contentView;
    private LayoutInflater lInf;

    private Context context;

    public Popup(Context context, String[] arrayItem)
    {
        this.context = context;

        this.popupWindow = new PopupWindow(context);
        this.popupWindow.setOutsideTouchable(true);
        this.popupWindow.setFocusable(true);
        this.popupWindow.setBackgroundDrawable(new BitmapDrawable());

        this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                popupListener.onDismiss();
            }
        });

        lInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInf.inflate(R.layout.popup_layout, null);
        rootView = (ScrollView) view.findViewById(R.id.popup_svRootView);
        contentView = (LinearLayout) view.findViewById(R.id.popup_llContentView);

        for (int i = 0; i < arrayItem.length; i++)
        {
            addItemTitleOnly(new PopupItem(i, arrayItem[i], null));
            addSperator();
        }
    }

    public Popup(Context context, int popupType, List<PopupItem> listItem)
    {
        this.context = context;
        this.popupWindow = new PopupWindow(context);
        this.popupWindow.setOutsideTouchable(true);
        this.popupWindow.setFocusable(true);
        this.popupWindow.setBackgroundDrawable(new BitmapDrawable());

        this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                popupListener.onDismiss();
            }
        });

        lInf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInf.inflate(R.layout.popup_layout, null);
        rootView = (ScrollView) view.findViewById(R.id.popup_svRootView);
        contentView = (LinearLayout) view.findViewById(R.id.popup_llContentView);
        switch (popupType)
        {
            case PopupSetting.STRING_ONLY_TYPE:
                for (PopupItem item : listItem)
                {
                    addItemTitleOnly(item);
                    addSperator();
                }
                break;
            case PopupSetting.STRING_VALUE_TYPE:
                for (PopupItem item : listItem)
                {
                    addItemTitleValue(item);
                    addSperator();
                }
                break;
            case PopupSetting.STRING_CHECKBOX_TYPE:
                for (PopupItem item : listItem)
                {
                    addItemTitleCheckbox(item);
                    addSperator();
                }
                break;
        }
    }

    /**
     * for the case popup has only Title.
     *
     * @param item
     */
    public void addItemTitleOnly(final PopupItem item)
    {
        TextView tvTitle = (TextView) lInf.inflate(R.layout.popup_item_string_only, null);
        tvTitle.setText(item.getItemTitle());
        tvTitle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popupListener.onItemClick(item.getId(), null);
                popupWindow.dismiss();
            }
        });

        contentView.addView(tvTitle);
        tvTitle.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        resizePopupWithTextOnly(tvTitle);
    }

    /**
     * for the case popup has Title with one value align right of each item .
     * value can be String or number, type return will be String.
     *
     * @param item
     */
    public void addItemTitleValue(final PopupItem item)
    {
        View view = lInf.inflate(R.layout.popup_item_string_value, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.popup_item_tvTitle);
        tvTitle.setText(item.getItemTitle());
        TextView tvValue = (TextView) view.findViewById(R.id.popup_item_tvValue);
        tvValue.setText(item.getItemValue());

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popupListener.onItemClick(item.getId(), null);
                popupWindow.dismiss();
            }
        });

        contentView.addView(view);
        tvTitle.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tvValue.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        resizePopupWithTextValue(tvTitle, tvValue);
    }

    /**
     * for the case popup has Title with one Checkbox align right of each item.
     * type of item value is boolean.
     *
     * @param item
     */
    public void addItemTitleCheckbox(final PopupItem item)
    {
        final View view = lInf.inflate(R.layout.popup_item_string_checkbox, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.popup_item_tvTitle);
        tvTitle.setText(item.getItemTitle());
        final CheckBox cbValue = (CheckBox) view.findViewById(R.id.popup_item_cbValue);
        cbValue.setChecked(Boolean.parseBoolean(item.getItemValue()));
        cbValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                popupListener.onItemClick(item.getId(), cbValue.isChecked());
                popupWindow.dismiss();
            }
        });
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                cbValue.setChecked(!cbValue.isChecked());
                item.setItemValue(String.valueOf(cbValue.isChecked()));
                popupListener.onItemClick(item.getId(), cbValue.isChecked());
                popupWindow.dismiss();
            }
        });

        contentView.addView(view);
        tvTitle.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        cbValue.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        resizePopupWithTextCheckbox(tvTitle, cbValue);
    }

    public void addSperator()
    {
        ImageView view = new ImageView(context);
        view.setBackgroundResource(R.drawable.line);
        contentView.addView(view);
    }

    /**
     * method showBelow: show popup below an parent component above.
     *
     * @param viewAbove    : this is component above of popup you want
     * @param locationType : popup location align, it can be align LEFT,
     *                     align RIGHT or align CENTER, using PopupLocationType.class
     */
    public void showBelow(final View viewAbove, final int locationType)
    {
        viewAbove.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        contentView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        popupWindow.setContentView(rootView);
        if (popupWindow.getWidth() < getScreenWidth() / 2 + 30)
        {
            popupWindow.setWidth(getScreenWidth() / 2 + 30);
        }

        int height = contentView.getMeasuredHeight();
        if (height > getScreenHeight() - getLocationY(viewAbove) - viewAbove.getMeasuredHeight())
        {
            height = getScreenHeight() - getLocationY(viewAbove) - viewAbove.getMeasuredHeight() - 10;
        }
        if (height > getScreenHeight() / 2)
        {
            height = getScreenHeight() / 2;
        }
        popupWindow.setHeight(height);

        viewAbove.post(new Runnable()
        {
            public void run()
            {
                int POPUP_TOP = getLocationY(viewAbove) + viewAbove.getMeasuredHeight();
                switch (locationType)
                {
                    case PopupLocationType.LEFT:
                        popupWindow.showAtLocation(viewAbove, Gravity.LEFT | Gravity.TOP, 10, POPUP_TOP);
                        break;
                    case PopupLocationType.CENTER:
                        popupWindow.showAtLocation(viewAbove, Gravity.LEFT | Gravity.TOP, (getScreenWidth() - popupWindow.getWidth()) / 2, POPUP_TOP);
                        break;
                    case PopupLocationType.RIGHT:
                        popupWindow.showAtLocation(viewAbove, Gravity.LEFT | Gravity.TOP, getScreenWidth() - popupWindow.getWidth() - 10, POPUP_TOP);
                        break;
                }
            }
        });
    }

    /**
     * method showAbove: show popup above an parent component below.
     *
     * @param viewBelow    : this is component above of popup you want
     * @param locationType : popup location align, it can be align LEFT,
     *                     align RIGHT or align CENTER, using PopupLocationType.class
     */
    public void showAbove(final View viewBelow, final int locationType)
    {
        viewBelow.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        contentView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        popupWindow.setContentView(rootView);
        if (popupWindow.getWidth() < getScreenWidth() / 2 + 30)
        {
            popupWindow.setWidth(getScreenWidth() / 2 + 30);
        }
        if (contentView.getMeasuredHeight() > getLocationY(viewBelow) - getStatusBarHeight())
        {
            popupWindow.setHeight(getLocationY(viewBelow) - getStatusBarHeight());
        }

        viewBelow.post(new Runnable()
        {
            public void run()
            {
                int POPUP_TOP = getLocationY(viewBelow) - popupWindow.getHeight();
                switch (locationType)
                {
                    case PopupLocationType.LEFT:
                        popupWindow.showAtLocation(viewBelow, Gravity.LEFT | Gravity.TOP, 10, POPUP_TOP);
                        break;
                    case PopupLocationType.CENTER:
                        popupWindow.showAtLocation(viewBelow, Gravity.LEFT | Gravity.TOP, (getScreenWidth() - popupWindow.getWidth()) / 2, POPUP_TOP);
                        break;
                    case PopupLocationType.RIGHT:
                        popupWindow.showAtLocation(viewBelow, Gravity.LEFT | Gravity.TOP, getScreenWidth() - popupWindow.getWidth() - 10, POPUP_TOP);
                        break;
                }
            }
        });
    }

    public void setOnItemClickListener(PopupListener popupListener)
    {
        this.popupListener = popupListener;
    }

    public void dismiss()
    {
        if (popupWindow != null && isShowing())
        {
            popupWindow.dismiss();
        }
    }

    public boolean isShowing()
    {
        return popupWindow.isShowing();
    }

    public void resizePopupWithTextOnly(TextView tvTitle)
    {
        if (popupWindow.getWidth() < tvTitle.getMeasuredWidth())
        {
            popupWindow.setWidth(tvTitle.getMeasuredWidth());
        }
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void resizePopupWithTextValue(TextView tvTitle, TextView tvValue)
    {
        if (popupWindow.getWidth() < tvTitle.getMeasuredWidth() + tvValue.getMeasuredWidth())
        {
            popupWindow.setWidth(tvTitle.getMeasuredWidth() + tvValue.getMeasuredWidth());
        }
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void resizePopupWithTextCheckbox(TextView tvTitle, CheckBox cbValue)
    {
        if (popupWindow.getWidth() < tvTitle.getMeasuredWidth() + cbValue.getMeasuredWidth())
        {
            popupWindow.setWidth(tvTitle.getMeasuredWidth() + cbValue.getMeasuredWidth());
        }
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void resizePopupWithTextRadio(TextView tvTitle, RadioButton rbValue)
    {
        if (popupWindow.getWidth() < tvTitle.getMeasuredWidth() + rbValue.getMeasuredWidth())
        {
            popupWindow.setWidth(tvTitle.getMeasuredWidth() + rbValue.getMeasuredWidth());
        }
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public int getScreenWidth()
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public int getScreenHeight()
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public int getLocationY(View view)
    {
        int[] locationXY = new int[2];
        view.getLocationOnScreen(locationXY);
        return locationXY[1];
    }

    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
