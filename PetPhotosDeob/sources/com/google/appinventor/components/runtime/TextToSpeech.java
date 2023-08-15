package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.collect.Maps;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.ExternalTextToSpeech;
import com.google.appinventor.components.runtime.util.ITextToSpeech;
import com.google.appinventor.components.runtime.util.InternalTextToSpeech;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.MEDIA, description = "The TextToSpeech component speaks a given text aloud.  You can set the pitch and the rate of speech. <p>You can also set a language by supplying a language code.  This changes the pronunciation of words, not the actual language spoken.  For example, setting the language to French and speaking English text will sound like someone speaking English (en) with a French accent.</p> <p>You can also specify a country by supplying a country code. This can affect the pronunciation.  For example, British English (GBR) will sound different from US English (USA).  Not every country code will affect every language.</p> <p>The languages and countries available depend on the particular device, and can be listed with the AvailableLanguages and AvailableCountries properties.</p>", iconName = "images/textToSpeech.png", nonVisible = SyntaxForms.DEBUGGING, version = 5)
@SimpleObject
/* loaded from: classes.dex */
public class TextToSpeech extends AndroidNonvisibleComponent implements Component, OnStopListener, OnResumeListener, OnDestroyListener {
    private static final String LOG_TAG = "TextToSpeech";
    private YailList allCountries;
    private YailList allLanguages;
    private String country;
    private ArrayList<String> countryList;
    private boolean isTtsPrepared;
    private String iso2Country;
    private String iso2Language;
    private String language;
    private ArrayList<String> languageList;
    private float pitch;
    private boolean result;
    private float speechRate;
    private final ITextToSpeech tts;
    private static final java.util.Map<String, Locale> iso3LanguageToLocaleMap = Maps.newHashMap();
    private static final java.util.Map<String, Locale> iso3CountryToLocaleMap = Maps.newHashMap();

    static {
        initLocaleMaps();
    }

    private static void initLocaleMaps() {
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            try {
                String iso3Country = locale.getISO3Country();
                if (iso3Country.length() > 0) {
                    iso3CountryToLocaleMap.put(iso3Country, locale);
                }
            } catch (MissingResourceException e) {
            }
            try {
                String iso3Language = locale.getISO3Language();
                if (iso3Language.length() > 0) {
                    iso3LanguageToLocaleMap.put(iso3Language, locale);
                }
            } catch (MissingResourceException e2) {
            }
        }
    }

    public TextToSpeech(ComponentContainer container) {
        super(container.$form());
        this.pitch = 1.0f;
        this.speechRate = 1.0f;
        this.result = false;
        Language("");
        Country("");
        boolean useExternalLibrary = SdkLevel.getLevel() < 4;
        Log.v(LOG_TAG, "Using " + (useExternalLibrary ? "external" : "internal") + " TTS library.");
        ITextToSpeech.TextToSpeechCallback callback = new ITextToSpeech.TextToSpeechCallback() { // from class: com.google.appinventor.components.runtime.TextToSpeech.1
            @Override // com.google.appinventor.components.runtime.util.ITextToSpeech.TextToSpeechCallback
            public void onSuccess() {
                TextToSpeech.this.result = true;
                TextToSpeech.this.AfterSpeaking(true);
            }

            @Override // com.google.appinventor.components.runtime.util.ITextToSpeech.TextToSpeechCallback
            public void onFailure() {
                TextToSpeech.this.result = false;
                TextToSpeech.this.AfterSpeaking(false);
            }
        };
        this.tts = useExternalLibrary ? new ExternalTextToSpeech(container, callback) : new InternalTextToSpeech(container.$context(), callback);
        this.form.registerForOnStop(this);
        this.form.registerForOnResume(this);
        this.form.registerForOnDestroy(this);
        this.form.setVolumeControlStream(3);
        this.isTtsPrepared = false;
        this.languageList = new ArrayList<>();
        this.countryList = new ArrayList<>();
        this.allLanguages = YailList.makeList((List) this.languageList);
        this.allCountries = YailList.makeList((List) this.countryList);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Result() {
        return this.result;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Sets the language for TextToSpeech. This changes the way that words are pronounced, not the actual language that is spoken.  For example setting the language to and speaking English text with sound like someone speaking English with a French accent.")
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_TEXT_TO_SPEECH_LANGUAGES)
    public void Language(String language) {
        Locale locale;
        switch (language.length()) {
            case 2:
                locale = new Locale(language);
                this.language = locale.getLanguage();
                break;
            case 3:
                locale = iso3LanguageToLocale(language);
                this.language = locale.getISO3Language();
                break;
            default:
                locale = Locale.getDefault();
                this.language = locale.getLanguage();
                break;
        }
        this.iso2Language = locale.getLanguage();
    }

    private static Locale iso3LanguageToLocale(String iso3Language) {
        Locale mappedLocale = iso3LanguageToLocaleMap.get(iso3Language);
        if (mappedLocale == null) {
            mappedLocale = iso3LanguageToLocaleMap.get(iso3Language.toLowerCase(Locale.ENGLISH));
        }
        return mappedLocale == null ? Locale.getDefault() : mappedLocale;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Sets the Pitch for TextToSpeech The values should be between 0 and 2 where lower values lower the tone of synthesized voice and greater values raise it.")
    @DesignerProperty(defaultValue = "1.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_FLOAT)
    public void Pitch(float pitch) {
        if (pitch < 0.0f || pitch > 2.0f) {
            Log.i(LOG_TAG, "Pitch value should be between 0 and 2, but user specified: " + pitch);
            return;
        }
        this.pitch = pitch;
        ITextToSpeech iTextToSpeech = this.tts;
        if (pitch == 0.0f) {
            pitch = 0.1f;
        }
        iTextToSpeech.setPitch(pitch);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns current value of Pitch")
    public float Pitch() {
        return this.pitch;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Sets the SpeechRate for TextToSpeech. The values should be between 0 and 2 where lower values slow down the pitch and greater values accelerate it.")
    @DesignerProperty(defaultValue = "1.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_FLOAT)
    public void SpeechRate(float speechRate) {
        if (speechRate < 0.0f || speechRate > 2.0f) {
            Log.i(LOG_TAG, "speechRate value should be between 0 and 2, but user specified: " + speechRate);
            return;
        }
        this.speechRate = speechRate;
        ITextToSpeech iTextToSpeech = this.tts;
        if (speechRate == 0.0f) {
            speechRate = 0.1f;
        }
        iTextToSpeech.setSpeechRate(speechRate);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns current value of SpeechRate")
    public float SpeechRate() {
        return this.speechRate;
    }

    @SimpleProperty
    public String Language() {
        return this.language;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Country code to use for speech generation.  This can affect the pronounciation.  For example, British English (GBR) will sound different from US English (USA).  Not every country code will affect every language.")
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_TEXT_TO_SPEECH_COUNTRIES)
    public void Country(String country) {
        Locale locale;
        switch (country.length()) {
            case 2:
                locale = new Locale(country);
                this.country = locale.getCountry();
                break;
            case 3:
                locale = iso3CountryToLocale(country);
                this.country = locale.getISO3Country();
                break;
            default:
                locale = Locale.getDefault();
                this.country = locale.getCountry();
                break;
        }
        this.iso2Country = locale.getCountry();
    }

    private static Locale iso3CountryToLocale(String iso3Country) {
        Locale mappedLocale = iso3CountryToLocaleMap.get(iso3Country);
        if (mappedLocale == null) {
            mappedLocale = iso3CountryToLocaleMap.get(iso3Country.toUpperCase(Locale.ENGLISH));
        }
        return mappedLocale == null ? Locale.getDefault() : mappedLocale;
    }

    @SimpleProperty
    public String Country() {
        return this.country;
    }

    @SimpleProperty(description = "List of the languages available on this device for use with TextToSpeech.  Check the Android developer documentation under supported languages to find the meanings of these abbreviations.")
    public YailList AvailableLanguages() {
        prepareLanguageAndCountryProperties();
        return this.allLanguages;
    }

    @SimpleProperty(description = "List of the country codes available on this device for use with TextToSpeech.  Check the Android developer documentation under supported languages to find the meanings of these abbreviations.")
    public YailList AvailableCountries() {
        prepareLanguageAndCountryProperties();
        return this.allCountries;
    }

    public void prepareLanguageAndCountryProperties() {
        if (!this.isTtsPrepared) {
            if (!this.tts.isInitialized()) {
                this.form.dispatchErrorOccurredEvent(this, LOG_TAG, ErrorMessages.ERROR_TTS_NOT_READY, new Object[0]);
                Speak("");
                return;
            }
            getLanguageAndCountryLists();
            this.isTtsPrepared = true;
        }
    }

    private void getLanguageAndCountryLists() {
        Locale[] availableLocales;
        if (SdkLevel.getLevel() >= 4) {
            for (Locale locale : Locale.getAvailableLocales()) {
                int res = this.tts.isLanguageAvailable(locale);
                if (res != -2) {
                    String tempLang = locale.getLanguage();
                    String tempCountry = locale.getISO3Country();
                    if (!tempLang.equals("") && !this.languageList.contains(tempLang)) {
                        this.languageList.add(tempLang);
                    }
                    if (!tempCountry.equals("") && !this.countryList.contains(tempCountry)) {
                        this.countryList.add(tempCountry);
                    }
                }
            }
            Collections.sort(this.languageList);
            Collections.sort(this.countryList);
            this.allLanguages = YailList.makeList((List) this.languageList);
            this.allCountries = YailList.makeList((List) this.countryList);
        }
    }

    @SimpleFunction
    public void Speak(String message) {
        BeforeSpeaking();
        Locale loc = new Locale(this.iso2Language, this.iso2Country);
        this.tts.speak(message, loc);
    }

    @SimpleEvent
    public void BeforeSpeaking() {
        EventDispatcher.dispatchEvent(this, "BeforeSpeaking", new Object[0]);
    }

    @SimpleEvent(description = "Event to raise after the message is spoken. The result will be true if the message is spoken successfully, otherwise it will be false.")
    public void AfterSpeaking(boolean result) {
        EventDispatcher.dispatchEvent(this, "AfterSpeaking", Boolean.valueOf(result));
    }

    @Override // com.google.appinventor.components.runtime.OnStopListener
    public void onStop() {
        this.tts.onStop();
    }

    @Override // com.google.appinventor.components.runtime.OnResumeListener
    public void onResume() {
        this.tts.onResume();
    }

    @Override // com.google.appinventor.components.runtime.OnDestroyListener
    public void onDestroy() {
        this.tts.onDestroy();
    }
}
