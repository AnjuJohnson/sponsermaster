package com.cutesys.sponsermasterlibrary.Reveal;

import android.view.View;

/**
 * Created by athira on 17/09/15.
 */
public interface OnRevealChangeListener {
    void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView);
    void onSecondaryViewAppeared(FABRevealLayout fabRevealLayout, View secondaryView);
}
