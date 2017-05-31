package com.cutesys.sponsermasterlibrary.Print;

/**
 * Created by user on 3/15/2017.
 */

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.cutesys.sponsermasterlibrary.R;

class PrintViewUtils {

    /**
     * Initialization of icon for print views.
     *
     * @param context    The Context the view is running in, through which it can access the current
     *                   theme, resources, etc.
     * @param attrs      The attributes of the XML tag that is inflating the view.
     * @param inEditMode Indicates whether this View is currently in edit mode.
     * @return The icon to display.
     */
    static PrintDrawable initIcon(Context context, AttributeSet attrs, boolean inEditMode) {
        PrintDrawable.Builder iconBuilder = new PrintDrawable.Builder(context);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PrintView);

            if (a.hasValue(R.styleable.PrintView_print_iconText)) {
                String iconText = a.getString(R.styleable.PrintView_print_iconText);
                iconBuilder.iconText(iconText);
            }

            if (a.hasValue(R.styleable.PrintView_print_iconCode)) {
                int iconCode = a.getInteger(R.styleable.PrintView_print_iconCode, 0);
                iconBuilder.iconCode(iconCode);
            }

            if (!inEditMode && a.hasValue(R.styleable.PrintView_print_iconFont)) {
                String iconFontPath = a.getString(R.styleable.PrintView_print_iconFont);
                iconBuilder.iconFont(TypefaceManager.load(context.getAssets(), iconFontPath));
            }

            if (a.hasValue(R.styleable.PrintView_print_iconColor)) {
                ColorStateList iconColor = a.getColorStateList(R.styleable.PrintView_print_iconColor);
                iconBuilder.iconColor(iconColor);
            }

            int iconSize = a.getDimensionPixelSize(R.styleable.PrintView_print_iconSize, 0);
            iconBuilder.iconSize(TypedValue.COMPLEX_UNIT_PX, iconSize);

            iconBuilder.inEditMode(inEditMode);

            a.recycle();
        }

        return iconBuilder.build();
    }

    private PrintViewUtils() {
    }

}