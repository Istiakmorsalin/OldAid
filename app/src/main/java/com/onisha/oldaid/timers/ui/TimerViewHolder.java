/*
 * Copyright 2017 Onisha
 *
 * This file is part of ClockPlus.
 *
 * ClockPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ClockPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ClockPlus.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.onisha.oldaid.timers.ui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.onisha.oldaid.dialogs.AddLabelDialog;
import com.onisha.oldaid.dialogs.AddLabelDialogController;
import com.onisha.oldaid.list.BaseViewHolder;
import com.onisha.oldaid.list.OnListItemInteractionListener;
import com.onisha.oldaid.ringtone.playback.TimerRingtoneService;
import com.onisha.oldaid.timers.data.AsyncTimersTableUpdateHandler;
import com.onisha.oldaid.util.FragmentTagUtils;
import com.onisha.oldaid.util.ProgressBarUtils;
import com.onisha.oldaid.dialogs.AddLabelDialog;
import com.onisha.oldaid.dialogs.AddLabelDialogController;
import com.onisha.oldaid.timers.TimerController;
import com.onisha.oldaid.ringtone.playback.TimerRingtoneService;
import com.onisha.oldaid.timers.data.AsyncTimersTableUpdateHandler;
import com.onisha.oldaid.list.BaseViewHolder;
import com.onisha.oldaid.list.OnListItemInteractionListener;
import com.onisha.oldaid.R;
import com.onisha.oldaid.timers.Timer;
import com.onisha.oldaid.util.FragmentTagUtils;
import com.onisha.oldaid.util.ProgressBarUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Onisha on 7/25/2016.
 */
public class TimerViewHolder extends BaseViewHolder<Timer> {
    private static final String TAG = "TimerViewHolder";

    private final AsyncTimersTableUpdateHandler mAsyncTimersTableUpdateHandler;
    private TimerController mController;
    private ObjectAnimator mProgressAnimator;
    private final Drawable mStartIcon;
    private final Drawable mPauseIcon;
    private final PopupMenu mPopupMenu;
    private final AddLabelDialogController mAddLabelDialogController;

    @Bind(com.onisha.oldaid.R.id.label) TextView mLabel;
    @Bind(com.onisha.oldaid.R.id.duration) CountdownChronometer mChronometer;
    @Bind(com.onisha.oldaid.R.id.seek_bar) SeekBar mSeekBar;
    @Bind(com.onisha.oldaid.R.id.add_one_minute) TextView mAddOneMinute;
    @Bind(com.onisha.oldaid.R.id.start_pause) ImageButton mStartPause;
    @Bind(com.onisha.oldaid.R.id.stop) ImageButton mStop;
    @Bind(com.onisha.oldaid.R.id.menu) ImageButton mMenuButton;

    public TimerViewHolder(ViewGroup parent, OnListItemInteractionListener<Timer> listener,
                           AsyncTimersTableUpdateHandler asyncTimersTableUpdateHandler) {
        super(parent, com.onisha.oldaid.R.layout.item_timer, listener);
        Log.d(TAG, "New TimerViewHolder");
        mAsyncTimersTableUpdateHandler = asyncTimersTableUpdateHandler;
        mStartIcon = ContextCompat.getDrawable(getContext(), com.onisha.oldaid.R.drawable.ic_start_24dp);
        mPauseIcon = ContextCompat.getDrawable(getContext(), com.onisha.oldaid.R.drawable.ic_pause_24dp);

        // TODO: This is bad! Use a Controller/Presenter instead...
        // or simply pass in an instance of FragmentManager to the ctor.
        AppCompatActivity act = (AppCompatActivity) getContext();
        mAddLabelDialogController = new AddLabelDialogController(
                act.getSupportFragmentManager(),
                new AddLabelDialog.OnLabelSetListener() {
                    @Override
                    public void onLabelSet(String label) {
                        mController.updateLabel(label);
                    }
                });

        // The item layout is inflated in the super ctor, so we can safely reference our views.
        mPopupMenu = new PopupMenu(getContext(), mMenuButton);
        mPopupMenu.inflate(com.onisha.oldaid.R.menu.menu_timer_viewholder);
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case com.onisha.oldaid.R.id.action_delete:
                        mController.deleteTimer();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBind(final Timer timer) {
        super.onBind(timer);
        Log.d(TAG, "Binding TimerViewHolder");
        // TOneverDO: create before super
        mController = new TimerController(timer, mAsyncTimersTableUpdateHandler);
        // Items that are not in view will not be bound. If in one orientation the item was in view
        // and in another it is out of view, then the callback for that item will not be restored
        // for the new orientation.
        mAddLabelDialogController.tryRestoreCallback(makeTag(com.onisha.oldaid.R.id.label));
        Log.d(TAG, "timer.label() = " + timer.label());
        bindLabel(timer.label());
        bindChronometer(timer);
        bindButtonControls(timer);
        bindProgressBar(timer);
    }

    @OnClick(com.onisha.oldaid.R.id.start_pause)
    void startPause() {
        mController.startPause();
    }

    @OnClick(com.onisha.oldaid.R.id.add_one_minute)
    void addOneMinute() {
        mController.addOneMinute();
    }

    @OnClick(com.onisha.oldaid.R.id.stop)
    void stop() {
        mController.stop();
        getContext().stopService(new Intent(getContext(), TimerRingtoneService.class));
    }

    @OnClick(com.onisha.oldaid.R.id.label)
    void openLabelEditor() {
        mAddLabelDialogController.show(mLabel.getText(), makeTag(com.onisha.oldaid.R.id.label));
    }

    @OnClick(com.onisha.oldaid.R.id.menu)
    void openMenu() {
        mPopupMenu.show();
    }

    private void bindLabel(String label) {
        if (!label.isEmpty()) {
            mLabel.setText(label);
        }
    }

    private void bindChronometer(Timer timer) {
        // In case we're reusing a chronometer instance that could be running:
        // If the Timer instance is not running, this just guarantees the chronometer
        // won't tick, regardless of whether it was running.
        // If the Timer instance is running, we don't care whether the chronometer is
        // also running, because we call start() right after. Stopping it just
        // guarantees that, if it was running, we don't deliver another set of
        // concurrent messages to its handler.
        mChronometer.stop();

        // TODO: I think we can simplify all this to just:
        // mChronometer.setDuration(timer.timeRemaining())
        // if we make the modification to the method as
        // described in the Timer class.
        if (!timer.hasStarted()) {
            // Set the initial text
            mChronometer.setDuration(timer.duration());
        } else if (timer.isRunning()) {
            // Re-initialize the base
            mChronometer.setBase(timer.endTime());
            // Previously stopped, so no old messages will interfere.
            mChronometer.start();
        } else {
            // Set the text as last displayed before we stopped.
            // When you call stop() on a Chronometer, it freezes the current text shown,
            // so why do we need this? While that is sufficient for a static View layout,
            // VH recycling will reuse the same Chronometer widget across multiple VHs,
            // so we would have invalid data across those VHs.
            // If a new VH is created, then the chronometer it contains will be in its
            // uninitialized state. We will always need to set the Chronometer's base
            // every time VHs are bound/recycled.
            mChronometer.setDuration(timer.timeRemaining());
        }
    }

    private void bindButtonControls(Timer timer) {
        mStartPause.setImageDrawable(timer.isRunning() ? mPauseIcon : mStartIcon);
        int visibility = timer.hasStarted() ? View.VISIBLE : View.INVISIBLE;
        mAddOneMinute.setVisibility(visibility);
        mStop.setVisibility(visibility);
    }

    private void bindProgressBar(Timer timer) {
        final long timeRemaining = timer.timeRemaining();
        double ratio = (double) timeRemaining / timer.duration();

        // In case we're reusing an animator instance that could be running
        if (mProgressAnimator != null && mProgressAnimator.isRunning()) {
            mProgressAnimator.end();
        }

        if (!timer.isRunning()) {
            // If our scale were 1, then casting ratio to an int will ALWAYS
            // truncate down to zero.
//            mSeekBar.setMax(100);
//            final int progress = (int) (100 * ratio);
//            mSeekBar.setProgress(progress);
            ProgressBarUtils.setProgress(mSeekBar, ratio);
//            mSeekBar.getThumb().mutate().setAlpha(progress == 0 ? 0 : 255);
        } else {
//            mSeekBar.getThumb().mutate().setAlpha(255);
            mProgressAnimator = ProgressBarUtils.startNewAnimator(
                    mSeekBar, ratio, timeRemaining);
        }
        mSeekBar.getThumb().mutate().setAlpha(timeRemaining <= 0 ? 0 : 255);
    }

    private String makeTag(@IdRes int viewId) {
        return FragmentTagUtils.makeTag(TimerViewHolder.class, viewId, getItemId());
    }
}
