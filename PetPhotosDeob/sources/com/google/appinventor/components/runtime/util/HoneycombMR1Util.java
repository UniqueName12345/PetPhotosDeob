package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import java.io.InputStream;

/* loaded from: classes.dex */
public class HoneycombMR1Util {
    private HoneycombMR1Util() {
    }

    public static Uri getContentUri() {
        return ContactsContract.Contacts.CONTENT_URI;
    }

    public static Uri getPhoneContentUri() {
        return ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    }

    public static Uri getDataContentUri() {
        return ContactsContract.Data.CONTENT_URI;
    }

    public static String[] getContactProjection() {
        String[] contactProjection = {"_id", "display_name", "photo_thumb_uri", "photo_uri"};
        return contactProjection;
    }

    public static String[] getNameProjection() {
        String[] nameProjection = {"contact_id", "display_name", "photo_thumb_uri", "data1"};
        return nameProjection;
    }

    public static String[] getDataProjection() {
        String[] dataProjection = {"mimetype", "data1", "data2", "data1", "data2"};
        return dataProjection;
    }

    public static String[] getEmailAdapterProjection() {
        String[] emailAdapterProjection = {"_id", "display_name", "data1", "mimetype"};
        return emailAdapterProjection;
    }

    public static int getIdIndex(Cursor contactCursor) {
        return contactCursor.getColumnIndex("_id");
    }

    public static int getContactIdIndex(Cursor contactCursor) {
        return contactCursor.getColumnIndex("contact_id");
    }

    public static int getNameIndex(Cursor contactCursor) {
        return contactCursor.getColumnIndex("display_name");
    }

    public static int getThumbnailIndex(Cursor contactCursor) {
        return contactCursor.getColumnIndex("photo_thumb_uri");
    }

    public static int getPhotoIndex(Cursor contactCursor) {
        return contactCursor.getColumnIndex("photo_uri");
    }

    public static int getPhoneIndex(Cursor dataCursor) {
        return dataCursor.getColumnIndex("data1");
    }

    public static int getEmailIndex(Cursor dataCursor) {
        return dataCursor.getColumnIndex("data1");
    }

    public static int getMimeIndex(Cursor dataCursor) {
        return dataCursor.getColumnIndex("mimetype");
    }

    public static String getPhoneType() {
        return "vnd.android.cursor.item/phone_v2";
    }

    public static String getEmailType() {
        return "vnd.android.cursor.item/email_v2";
    }

    public static String getDisplayName() {
        return "display_name";
    }

    public static String getEmailAddress() {
        return "data1";
    }

    public static String getDataMimeType() {
        return "mimetype";
    }

    public static Cursor getDataCursor(String id, Activity activityContext, String[] dataProjection) {
        Cursor dataCursor = activityContext.getContentResolver().query(ContactsContract.Data.CONTENT_URI, dataProjection, "contact_id=? AND (mimetype=? OR mimetype=?)", new String[]{id, "vnd.android.cursor.item/phone_v2", "vnd.android.cursor.item/email_v2"}, null);
        return dataCursor;
    }

    public static InputStream openContactPhotoInputStreamHelper(ContentResolver cr, Uri contactUri) {
        return ContactsContract.Contacts.openContactPhotoInputStream(cr, contactUri);
    }

    public static String getTimesContacted() {
        return "times_contacted";
    }
}
