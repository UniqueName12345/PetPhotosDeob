package com.google.appinventor.components.runtime;

import android.app.TimePickerDialog;
import android.os.Handler;
import android.text.format.DateFormat;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.Dates;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import java.util.Calendar;

@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "<p>A button that, when clicked on, launches  a popup dialog to allow the user to select a time.</p>", version = 4)
@SimpleObject
/* loaded from: classes.dex */
public class TimePicker extends ButtonBase {
    private Handler androidUIHandler;
    private boolean customTime;
    private Form form;
    private int hour;
    private Calendar instant;
    private int minute;
    private TimePickerDialog time;
    private TimePickerDialog.OnTimeSetListener timePickerListener;

    public TimePicker(ComponentContainer container) {
        super(container);
        this.hour = 0;
        this.minute = 0;
        this.customTime = false;
        this.timePickerListener = new TimePickerDialog.OnTimeSetListener() { // from class: com.google.appinventor.components.runtime.TimePicker.1
            @Override // android.app.TimePickerDialog.OnTimeSetListener
            public void onTimeSet(android.widget.TimePicker view, int selectedHour, int selectedMinute) {
                if (view.isShown()) {
                    TimePicker.this.hour = selectedHour;
                    TimePicker.this.minute = selectedMinute;
                    TimePicker.this.instant = Dates.TimeInstant(TimePicker.this.hour, TimePicker.this.minute);
                    TimePicker.this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.TimePicker.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TimePicker.this.AfterTimeSet();
                        }
                    });
                }
            }
        };
        this.form = container.$form();
        Calendar c = Calendar.getInstance();
        this.hour = c.get(11);
        this.minute = c.get(12);
        this.time = new TimePickerDialog(this.container.$context(), this.timePickerListener, this.hour, this.minute, DateFormat.is24HourFormat(this.container.$context()));
        this.instant = Dates.TimeInstant(this.hour, this.minute);
        this.androidUIHandler = new Handler();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The hour of the last time set using the time picker. The hour is in a 24 hour format. If the last time set was 11:53 pm, this property will return 23.")
    public int Hour() {
        return this.hour;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The minute of the last time set using the time picker")
    public int Minute() {
        return this.minute;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The instant of the last time set using the time picker")
    public Calendar Instant() {
        return this.instant;
    }

    @SimpleFunction(description = "Set the time to be shown in the Time Picker popup. Current time is shown by default.")
    public void SetTimeToDisplay(int hour, int minute) {
        if (hour < 0 || hour > 23) {
            this.form.dispatchErrorOccurredEvent(this, "SetTimeToDisplay", ErrorMessages.ERROR_ILLEGAL_HOUR, new Object[0]);
        } else if (minute < 0 || minute > 59) {
            this.form.dispatchErrorOccurredEvent(this, "SetTimeToDisplay", ErrorMessages.ERROR_ILLEGAL_MINUTE, new Object[0]);
        } else {
            this.time.updateTime(hour, minute);
            this.instant = Dates.TimeInstant(hour, minute);
            this.customTime = true;
        }
    }

    @SimpleFunction(description = "Set the time from the instant to be shown in the Time Picker dialog. Current time is shown by default.")
    public void SetTimeToDisplayFromInstant(Calendar instant) {
        int hour = Dates.Hour(instant);
        int minute = Dates.Minute(instant);
        this.time.updateTime(hour, minute);
        Dates.TimeInstant(hour, minute);
        this.customTime = true;
    }

    @SimpleFunction(description = "Launches the TimePicker dialog.")
    public void LaunchPicker() {
        click();
    }

    @Override // com.google.appinventor.components.runtime.ButtonBase
    public void click() {
        if (!this.customTime) {
            Calendar c = Calendar.getInstance();
            int h = c.get(11);
            int m = c.get(12);
            this.time.updateTime(h, m);
            this.instant = Dates.TimeInstant(this.hour, this.minute);
        } else {
            this.customTime = false;
        }
        this.time.show();
    }

    @SimpleEvent(description = "This event is run when a user has set the time in the popup dialog.")
    public void AfterTimeSet() {
        EventDispatcher.dispatchEvent(this, "AfterTimeSet", new Object[0]);
    }
}
