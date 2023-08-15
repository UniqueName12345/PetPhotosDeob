package com.google.appinventor.components.runtime;

import android.app.DatePickerDialog;
import android.os.Handler;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.Dates;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "<p>A button that, when clicked on, launches a popup dialog to allow the user to select a date.</p>", version = 4)
@SimpleObject
/* loaded from: classes.dex */
public class DatePicker extends ButtonBase {
    private Handler androidUIHandler;
    private boolean customDate;
    private DatePickerDialog date;
    private DatePickerDialog.OnDateSetListener datePickerListener;
    private int day;
    private Form form;
    private Calendar instant;
    private int javaMonth;
    private String[] localizedMonths;
    private int month;
    private int year;

    public DatePicker(ComponentContainer container) {
        super(container);
        this.localizedMonths = new DateFormatSymbols().getMonths();
        this.customDate = false;
        this.datePickerListener = new DatePickerDialog.OnDateSetListener() { // from class: com.google.appinventor.components.runtime.DatePicker.1
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(android.widget.DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                if (datePicker.isShown()) {
                    DatePicker.this.year = selectedYear;
                    DatePicker.this.javaMonth = selectedMonth;
                    DatePicker.this.month = DatePicker.this.javaMonth + 1;
                    DatePicker.this.day = selectedDay;
                    DatePicker.this.date.updateDate(DatePicker.this.year, DatePicker.this.javaMonth, DatePicker.this.day);
                    DatePicker.this.instant = Dates.DateInstant(DatePicker.this.year, DatePicker.this.month, DatePicker.this.day);
                    DatePicker.this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.DatePicker.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DatePicker.this.AfterDateSet();
                        }
                    });
                }
            }
        };
        this.form = container.$form();
        Calendar c = Calendar.getInstance();
        this.year = c.get(1);
        this.javaMonth = c.get(2);
        this.month = this.javaMonth + 1;
        this.day = c.get(5);
        this.instant = Dates.DateInstant(this.year, this.month, this.day);
        this.date = new DatePickerDialog(this.container.$context(), this.datePickerListener, this.year, this.javaMonth, this.day);
        this.androidUIHandler = new Handler();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "the Year that was last picked using the DatePicker")
    public int Year() {
        return this.year;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "the number of the Month that was last picked using the DatePicker. Note that months start in 1 = January, 12 = December.")
    public int Month() {
        return this.month;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns the name of the Month that was last picked using the DatePicker, in textual format.")
    public String MonthInText() {
        return this.localizedMonths[this.javaMonth];
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "the Day of the month that was last picked using the DatePicker.")
    public int Day() {
        return this.day;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "the instant of the date that was last picked using the DatePicker.")
    public Calendar Instant() {
        return this.instant;
    }

    @SimpleFunction(description = "Allows the user to set the date to be displayed when the date picker opens.\nValid values for the month field are 1-12 and 1-31 for the day field.\n")
    public void SetDateToDisplay(int year, int month, int day) {
        int jMonth = month - 1;
        try {
            GregorianCalendar cal = new GregorianCalendar(year, jMonth, day);
            cal.setLenient(false);
            cal.getTime();
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, "SetDateToDisplay", ErrorMessages.ERROR_ILLEGAL_DATE, new Object[0]);
        }
        this.date.updateDate(year, jMonth, day);
        this.instant = Dates.DateInstant(year, month, day);
        this.customDate = true;
    }

    @SimpleFunction(description = "Allows the user to set the date from the instant to be displayed when the date picker opens.")
    public void SetDateToDisplayFromInstant(Calendar instant) {
        int year = Dates.Year(instant);
        int month = Dates.Month(instant);
        int day = Dates.Day(instant);
        this.date.updateDate(year, month, day);
        Dates.DateInstant(year, month, day);
        this.customDate = true;
    }

    @SimpleFunction(description = "Launches the DatePicker dialog.")
    public void LaunchPicker() {
        click();
    }

    @Override // com.google.appinventor.components.runtime.ButtonBase
    public void click() {
        if (!this.customDate) {
            Calendar c = Calendar.getInstance();
            int year = c.get(1);
            int jMonth = c.get(2);
            int day = c.get(5);
            this.date.updateDate(year, jMonth, day);
            this.instant = Dates.DateInstant(year, jMonth + 1, day);
        } else {
            this.customDate = false;
        }
        this.date.show();
    }

    @SimpleEvent(description = "Event that runs after the user chooses a Date in the dialog")
    public void AfterDateSet() {
        EventDispatcher.dispatchEvent(this, "AfterDateSet", new Object[0]);
    }
}
