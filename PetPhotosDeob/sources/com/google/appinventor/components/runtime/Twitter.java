package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesActivities;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.annotations.androidmanifest.ActionElement;
import com.google.appinventor.components.annotations.androidmanifest.ActivityElement;
import com.google.appinventor.components.annotations.androidmanifest.IntentFilterElement;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kawa.lang.SyntaxForms;
import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@SimpleObject
@UsesActivities(activities = {@ActivityElement(configChanges = "orientation|keyboardHidden", intentFilters = {@IntentFilterElement(actionElements = {@ActionElement(name = "android.intent.action.MAIN")})}, name = "com.google.appinventor.components.runtime.WebViewActivity", screenOrientation = "behind")})
@UsesPermissions(permissionNames = "android.permission.INTERNET")
@DesignerComponent(category = ComponentCategory.SOCIAL, description = "A non-visible component that enables communication with <a href=\"http://www.twitter.com\" target=\"_blank\">Twitter</a>. Once a user has logged into their Twitter account (and the authorization has been confirmed successful by the <code>IsAuthorized</code> event), many more operations are available:<ul><li> Searching Twitter for tweets or labels (<code>SearchTwitter</code>)</li>\n<li> Sending a Tweet (<code>Tweet</code>)     </li>\n<li> Sending a Tweet with an Image (<code>TweetWithImage</code>)     </li>\n<li> Directing a message to a specific user      (<code>DirectMessage</code>)</li>\n <li> Receiving the most recent messages directed to the logged-in user      (<code>RequestDirectMessages</code>)</li>\n <li> Following a specific user (<code>Follow</code>)</li>\n<li> Ceasing to follow a specific user (<code>StopFollowing</code>)</li>\n<li> Getting a list of users following the logged-in user      (<code>RequestFollowers</code>)</li>\n <li> Getting the most recent messages of users followed by the      logged-in user (<code>RequestFriendTimeline</code>)</li>\n <li> Getting the most recent mentions of the logged-in user      (<code>RequestMentions</code>)</li></ul></p>\n <p>You must obtain a Consumer Key and Consumer Secret for Twitter authorization  specific to your app from http://twitter.com/oauth_clients/new", iconName = "images/twitter.png", nonVisible = SyntaxForms.DEBUGGING, version = 4)
@UsesLibraries(libraries = "twitter4j.jar,twitter4jmedia.jar")
/* loaded from: classes.dex */
public final class Twitter extends AndroidNonvisibleComponent implements ActivityResultListener, Component {
    private static final String ACCESS_SECRET_TAG = "TwitterOauthAccessSecret";
    private static final String ACCESS_TOKEN_TAG = "TwitterOauthAccessToken";
    private static final String CALLBACK_URL = "appinventor://twitter";
    private static final String MAX_CHARACTERS = "160";
    private static final String MAX_MENTIONS_RETURNED = "20";
    private static final String URL_HOST = "twitter";
    private static final String WEBVIEW_ACTIVITY_CLASS = WebViewActivity.class.getName();
    private String TwitPic_API_Key;
    private AccessToken accessToken;
    private String consumerKey;
    private String consumerSecret;
    private final ComponentContainer container;
    private final List<String> directMessages;
    private final List<String> followers;
    private final Handler handler;
    private final List<String> mentions;
    private final int requestCode;
    private RequestToken requestToken;
    private final List<String> searchResults;
    private final SharedPreferences sharedPreferences;
    private final List<List<String>> timeline;
    private twitter4j.Twitter twitter;
    private String userName;

    public Twitter(ComponentContainer container) {
        super(container.$form());
        this.consumerKey = "";
        this.consumerSecret = "";
        this.TwitPic_API_Key = "";
        this.userName = "";
        this.container = container;
        this.handler = new Handler();
        this.mentions = new ArrayList();
        this.followers = new ArrayList();
        this.timeline = new ArrayList();
        this.directMessages = new ArrayList();
        this.searchResults = new ArrayList();
        this.sharedPreferences = container.$context().getSharedPreferences("Twitter", 0);
        this.accessToken = retrieveAccessToken();
        this.requestCode = this.form.registerForActivityResult(this);
    }

    @SimpleFunction(description = "Twitter's API no longer supports login via username and password. Use the Authorize call instead.", userVisible = JSONzip.probe)
    public void Login(String username, String password) {
        this.form.dispatchErrorOccurredEvent(this, "Login", ErrorMessages.ERROR_TWITTER_UNSUPPORTED_LOGIN_FUNCTION, new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The user name of the authorized user. Empty if there is no authorized user.")
    public String Username() {
        return this.userName;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ConsumerKey() {
        return this.consumerKey;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The the consumer key to be used when authorizing with Twitter via OAuth.")
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ConsumerSecret() {
        return this.consumerSecret;
    }

    @SimpleProperty(description = "The consumer secret to be used when authorizing with Twitter via OAuth")
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    @Deprecated
    public String TwitPic_API_Key() {
        return this.TwitPic_API_Key;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The API Key for image uploading, provided by TwitPic.")
    @Deprecated
    public void TwitPic_API_Key(String TwitPic_API_Key) {
        this.TwitPic_API_Key = TwitPic_API_Key;
    }

    @SimpleEvent(description = "This event is raised after the program calls <code>Authorize</code> if the authorization was successful.  It is also called after a call to <code>CheckAuthorized</code> if we already have a valid access token. After this event has been raised, any other method for this component can be called.")
    public void IsAuthorized() {
        EventDispatcher.dispatchEvent(this, "IsAuthorized", new Object[0]);
    }

    @SimpleFunction(description = "Redirects user to login to Twitter via the Web browser using the OAuth protocol if we don't already have authorization.")
    public void Authorize() {
        if (this.consumerKey.length() == 0 || this.consumerSecret.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "Authorize", ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET, new Object[0]);
            return;
        }
        if (this.twitter == null) {
            this.twitter = new TwitterFactory().getInstance();
        }
        final String myConsumerKey = this.consumerKey;
        final String myConsumerSecret = this.consumerSecret;
        AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.1
            @Override // java.lang.Runnable
            public void run() {
                if (Twitter.this.checkAccessToken(myConsumerKey, myConsumerSecret)) {
                    Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Twitter.this.IsAuthorized();
                        }
                    });
                    return;
                }
                try {
                    Twitter.this.twitter.setOAuthConsumer(myConsumerKey, myConsumerSecret);
                    RequestToken newRequestToken = Twitter.this.twitter.getOAuthRequestToken(Twitter.CALLBACK_URL);
                    String authURL = newRequestToken.getAuthorizationURL();
                    Twitter.this.requestToken = newRequestToken;
                    Intent browserIntent = new Intent("android.intent.action.MAIN", Uri.parse(authURL));
                    browserIntent.setClassName(Twitter.this.container.$context(), Twitter.WEBVIEW_ACTIVITY_CLASS);
                    Twitter.this.container.$context().startActivityForResult(browserIntent, Twitter.this.requestCode);
                } catch (IllegalStateException e) {
                    Log.e("Twitter", "OAuthConsumer was already set: launch IsAuthorized()");
                    Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Twitter.this.IsAuthorized();
                        }
                    });
                } catch (TwitterException e2) {
                    Log.i("Twitter", "Got exception: " + e2.getMessage());
                    e2.printStackTrace();
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Authorize", ErrorMessages.ERROR_TWITTER_EXCEPTION, e2.getMessage());
                    Twitter.this.DeAuthorize();
                }
            }
        });
    }

    @SimpleFunction(description = "Checks whether we already have access, and if so, causes IsAuthorized event handler to be called.")
    public void CheckAuthorized() {
        final String myConsumerKey = this.consumerKey;
        final String myConsumerSecret = this.consumerSecret;
        AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.2
            @Override // java.lang.Runnable
            public void run() {
                if (Twitter.this.checkAccessToken(myConsumerKey, myConsumerSecret)) {
                    Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Twitter.this.IsAuthorized();
                        }
                    });
                }
            }
        });
    }

    @Override // com.google.appinventor.components.runtime.ActivityResultListener
    public void resultReturned(int requestCode, int resultCode, Intent data) {
        Log.i("Twitter", "Got result " + resultCode);
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Log.i("Twitter", "Intent URI: " + uri.toString());
                final String oauthVerifier = uri.getQueryParameter("oauth_verifier");
                if (this.twitter == null) {
                    Log.e("Twitter", "twitter field is unexpectedly null");
                    this.form.dispatchErrorOccurredEvent(this, "Authorize", ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN, "internal error: can't access Twitter library");
                    new RuntimeException().printStackTrace();
                }
                if (this.requestToken != null && oauthVerifier != null && oauthVerifier.length() != 0) {
                    AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.3
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                AccessToken resultAccessToken = Twitter.this.twitter.getOAuthAccessToken(Twitter.this.requestToken, oauthVerifier);
                                Twitter.this.accessToken = resultAccessToken;
                                Twitter.this.userName = Twitter.this.accessToken.getScreenName();
                                Twitter.this.saveAccessToken(resultAccessToken);
                                Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.3.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        Twitter.this.IsAuthorized();
                                    }
                                });
                            } catch (TwitterException e) {
                                Log.e("Twitter", "Got exception: " + e.getMessage());
                                e.printStackTrace();
                                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Authorize", ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN, e.getMessage());
                                Twitter.this.deAuthorize();
                            }
                        }
                    });
                    return;
                }
                this.form.dispatchErrorOccurredEvent(this, "Authorize", ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED, new Object[0]);
                deAuthorize();
                return;
            }
            Log.e("Twitter", "uri returned from WebView activity was unexpectedly null");
            deAuthorize();
            return;
        }
        Log.e("Twitter", "intent returned from WebView activity was unexpectedly null");
        deAuthorize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveAccessToken(AccessToken accessToken) {
        SharedPreferences.Editor sharedPrefsEditor = this.sharedPreferences.edit();
        if (accessToken == null) {
            sharedPrefsEditor.remove(ACCESS_TOKEN_TAG);
            sharedPrefsEditor.remove(ACCESS_SECRET_TAG);
        } else {
            sharedPrefsEditor.putString(ACCESS_TOKEN_TAG, accessToken.getToken());
            sharedPrefsEditor.putString(ACCESS_SECRET_TAG, accessToken.getTokenSecret());
        }
        sharedPrefsEditor.commit();
    }

    private AccessToken retrieveAccessToken() {
        String token = this.sharedPreferences.getString(ACCESS_TOKEN_TAG, "");
        String secret = this.sharedPreferences.getString(ACCESS_SECRET_TAG, "");
        if (token.length() == 0 || secret.length() == 0) {
            return null;
        }
        return new AccessToken(token, secret);
    }

    @SimpleFunction(description = "Removes Twitter authorization from this running app instance")
    public void DeAuthorize() {
        deAuthorize();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deAuthorize() {
        this.requestToken = null;
        this.accessToken = null;
        this.userName = "";
        twitter4j.Twitter oldTwitter = this.twitter;
        this.twitter = null;
        saveAccessToken(this.accessToken);
        if (oldTwitter != null) {
            oldTwitter.setOAuthAccessToken((AccessToken) null);
        }
    }

    @SimpleFunction(description = "This sends a tweet as the logged-in user with the specified Text, which will be trimmed if it exceeds 160 characters. <p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void Tweet(final String status) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "Tweet", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, "Need to login?");
        } else {
            AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Twitter.this.twitter.updateStatus(status);
                    } catch (TwitterException e) {
                        Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Tweet", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, e.getMessage());
                    }
                }
            });
        }
    }

    @SimpleFunction(description = "This sends a tweet as the logged-in user with the specified Text and a path to the image to be uploaded, which will be trimmed if it exceeds 160 characters. If an image is not found or invalid, only the text will be tweeted.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void TweetWithImage(final String status, final String imagePath) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "TweetWithImage", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, "Need to login?");
        } else {
            AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String cleanImagePath = imagePath;
                        if (cleanImagePath.startsWith("file://")) {
                            cleanImagePath = imagePath.replace("file://", "");
                        }
                        java.io.File imageFilePath = new java.io.File(cleanImagePath);
                        if (imageFilePath.exists()) {
                            StatusUpdate theTweet = new StatusUpdate(status);
                            theTweet.setMedia(imageFilePath);
                            Twitter.this.twitter.updateStatus(theTweet);
                            return;
                        }
                        Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "TweetWithImage", 315, new Object[0]);
                    } catch (TwitterException e) {
                        Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "TweetWithImage", ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, e.getMessage());
                    }
                }
            });
        }
    }

    @SimpleFunction(description = "Requests the 20 most recent mentions of the logged-in user.  When the mentions have been retrieved, the system will raise the <code>MentionsReceived</code> event and set the <code>Mentions</code> property to the list of mentions.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void RequestMentions() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestMentions", ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED, "Need to login?");
        } else {
            AsynchUtil.runAsynchronously(new AnonymousClass6());
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter$6  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass6 implements Runnable {
        List<Status> replies = Collections.emptyList();

        AnonymousClass6() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                try {
                    this.replies = Twitter.this.twitter.getMentionsTimeline();
                    Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Twitter.this.mentions.clear();
                            for (Status status : AnonymousClass6.this.replies) {
                                Twitter.this.mentions.add(status.getUser().getScreenName() + " " + status.getText());
                            }
                            Twitter.this.MentionsReceived(Twitter.this.mentions);
                        }
                    });
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestMentions", ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED, e.getMessage());
                    Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Twitter.this.mentions.clear();
                            for (Status status : AnonymousClass6.this.replies) {
                                Twitter.this.mentions.add(status.getUser().getScreenName() + " " + status.getText());
                            }
                            Twitter.this.MentionsReceived(Twitter.this.mentions);
                        }
                    });
                }
            } catch (Throwable th) {
                Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Twitter.this.mentions.clear();
                        for (Status status : AnonymousClass6.this.replies) {
                            Twitter.this.mentions.add(status.getUser().getScreenName() + " " + status.getText());
                        }
                        Twitter.this.MentionsReceived(Twitter.this.mentions);
                    }
                });
                throw th;
            }
        }
    }

    @SimpleEvent(description = "This event is raised when the mentions of the logged-in user requested through <code>RequestMentions</code> have been retrieved.  A list of the mentions can then be found in the <code>mentions</code> parameter or the <code>Mentions</code> property.")
    public void MentionsReceived(List<String> mentions) {
        EventDispatcher.dispatchEvent(this, "MentionsReceived", mentions);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains a list of mentions of the logged-in user.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>IsAuthorized</code> event.</li> <li> Call the <code>RequestMentions</code> method.</li> <li> Wait for the <code>MentionsReceived</code> event.</li></ol>\nThe value of this property will then be set to the list of mentions (and will maintain its value until any subsequent calls to <code>RequestMentions</code>).")
    public List<String> Mentions() {
        return this.mentions;
    }

    @SimpleFunction
    public void RequestFollowers() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestFollowers", ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED, "Need to login?");
        } else {
            AsynchUtil.runAsynchronously(new AnonymousClass7());
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter$7  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass7 implements Runnable {
        List<User> friends = new ArrayList();

        AnonymousClass7() {
        }

        @Override // java.lang.Runnable
        public void run() {
            long[] iDs;
            try {
                IDs followerIDs = Twitter.this.twitter.getFollowersIDs(-1L);
                for (long id : followerIDs.getIDs()) {
                    this.friends.add(Twitter.this.twitter.showUser(id));
                }
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestFollowers", ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED, e.getMessage());
            } finally {
                Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Twitter.this.followers.clear();
                        for (User user : AnonymousClass7.this.friends) {
                            Twitter.this.followers.add(user.getName());
                        }
                        Twitter.this.FollowersReceived(Twitter.this.followers);
                    }
                });
            }
        }
    }

    @SimpleEvent(description = "This event is raised when all of the followers of the logged-in user requested through <code>RequestFollowers</code> have been retrieved. A list of the followers can then be found in the <code>followers</code> parameter or the <code>Followers</code> property.")
    public void FollowersReceived(List<String> followers2) {
        EventDispatcher.dispatchEvent(this, "FollowersReceived", followers2);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains a list of the followers of the logged-in user.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>IsAuthorized</code> event.</li> <li> Call the <code>RequestFollowers</code> method.</li> <li> Wait for the <code>FollowersReceived</code> event.</li></ol>\nThe value of this property will then be set to the list of followers (and maintain its value until any subsequent call to <code>RequestFollowers</code>).")
    public List<String> Followers() {
        return this.followers;
    }

    @SimpleFunction(description = "Requests the 20 most recent direct messages sent to the logged-in user.  When the messages have been retrieved, the system will raise the <code>DirectMessagesReceived</code> event and set the <code>DirectMessages</code> property to the list of messages.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void RequestDirectMessages() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestDirectMessages", ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED, "Need to login?");
        } else {
            AsynchUtil.runAsynchronously(new AnonymousClass8());
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter$8  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass8 implements Runnable {
        List<DirectMessage> messages = Collections.emptyList();

        AnonymousClass8() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                try {
                    this.messages = Twitter.this.twitter.getDirectMessages();
                    Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Twitter.this.directMessages.clear();
                            for (DirectMessage message : AnonymousClass8.this.messages) {
                                Twitter.this.directMessages.add(message.getSenderScreenName() + " " + message.getText());
                            }
                            Twitter.this.DirectMessagesReceived(Twitter.this.directMessages);
                        }
                    });
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestDirectMessages", ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED, e.getMessage());
                    Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Twitter.this.directMessages.clear();
                            for (DirectMessage message : AnonymousClass8.this.messages) {
                                Twitter.this.directMessages.add(message.getSenderScreenName() + " " + message.getText());
                            }
                            Twitter.this.DirectMessagesReceived(Twitter.this.directMessages);
                        }
                    });
                }
            } catch (Throwable th) {
                Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Twitter.this.directMessages.clear();
                        for (DirectMessage message : AnonymousClass8.this.messages) {
                            Twitter.this.directMessages.add(message.getSenderScreenName() + " " + message.getText());
                        }
                        Twitter.this.DirectMessagesReceived(Twitter.this.directMessages);
                    }
                });
                throw th;
            }
        }
    }

    @SimpleEvent(description = "This event is raised when the recent messages requested through <code>RequestDirectMessages</code> have been retrieved. A list of the messages can then be found in the <code>messages</code> parameter or the <code>Messages</code> property.")
    public void DirectMessagesReceived(List<String> messages) {
        EventDispatcher.dispatchEvent(this, "DirectMessagesReceived", messages);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains a list of the most recent messages mentioning the logged-in user.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>Authorized</code> event.</li> <li> Call the <code>RequestDirectMessages</code> method.</li> <li> Wait for the <code>DirectMessagesReceived</code> event.</li></ol>\nThe value of this property will then be set to the list of direct messages retrieved (and maintain that value until any subsequent call to <code>RequestDirectMessages</code>).")
    public List<String> DirectMessages() {
        return this.directMessages;
    }

    @SimpleFunction(description = "This sends a direct (private) message to the specified user.  The message will be trimmed if it exceeds 160characters. <p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void DirectMessage(final String user, final String message) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "DirectMessage", ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED, "Need to login?");
        } else {
            AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.9
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Twitter.this.twitter.sendDirectMessage(user, message);
                    } catch (TwitterException e) {
                        Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "DirectMessage", ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED, e.getMessage());
                    }
                }
            });
        }
    }

    @SimpleFunction
    public void Follow(final String user) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "Follow", ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED, "Need to login?");
        } else {
            AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Twitter.this.twitter.createFriendship(user);
                    } catch (TwitterException e) {
                        Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "Follow", ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED, e.getMessage());
                    }
                }
            });
        }
    }

    @SimpleFunction
    public void StopFollowing(final String user) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "StopFollowing", ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED, "Need to login?");
        } else {
            AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Twitter.this.twitter.destroyFriendship(user);
                    } catch (TwitterException e) {
                        Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "StopFollowing", ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED, e.getMessage());
                    }
                }
            });
        }
    }

    @SimpleFunction
    public void RequestFriendTimeline() {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "RequestFriendTimeline", ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED, "Need to login?");
        } else {
            AsynchUtil.runAsynchronously(new AnonymousClass12());
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter$12  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass12 implements Runnable {
        List<Status> messages = Collections.emptyList();

        AnonymousClass12() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                try {
                    this.messages = Twitter.this.twitter.getHomeTimeline();
                    Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.12.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Twitter.this.timeline.clear();
                            for (Status message : AnonymousClass12.this.messages) {
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(message.getUser().getScreenName());
                                arrayList.add(message.getText());
                                Twitter.this.timeline.add(arrayList);
                            }
                            Twitter.this.FriendTimelineReceived(Twitter.this.timeline);
                        }
                    });
                } catch (TwitterException e) {
                    Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "RequestFriendTimeline", ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED, e.getMessage());
                    Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.12.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Twitter.this.timeline.clear();
                            for (Status message : AnonymousClass12.this.messages) {
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(message.getUser().getScreenName());
                                arrayList.add(message.getText());
                                Twitter.this.timeline.add(arrayList);
                            }
                            Twitter.this.FriendTimelineReceived(Twitter.this.timeline);
                        }
                    });
                }
            } catch (Throwable th) {
                Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.12.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Twitter.this.timeline.clear();
                        for (Status message : AnonymousClass12.this.messages) {
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(message.getUser().getScreenName());
                            arrayList.add(message.getText());
                            Twitter.this.timeline.add(arrayList);
                        }
                        Twitter.this.FriendTimelineReceived(Twitter.this.timeline);
                    }
                });
                throw th;
            }
        }
    }

    @SimpleEvent(description = "This event is raised when the messages requested through <code>RequestFriendTimeline</code> have been retrieved. The <code>timeline</code> parameter and the <code>Timeline</code> property will contain a list of lists, where each sub-list contains a status update of the form (username message)")
    public void FriendTimelineReceived(List<List<String>> timeline) {
        EventDispatcher.dispatchEvent(this, "FriendTimelineReceived", timeline);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property contains the 20 most recent messages of users being followed.  Initially, the list is empty.  To set it, the program must: <ol> <li> Call the <code>Authorize</code> method.</li> <li> Wait for the <code>IsAuthorized</code> event.</li> <li> Specify users to follow with one or more calls to the <code>Follow</code> method.</li> <li> Call the <code>RequestFriendTimeline</code> method.</li> <li> Wait for the <code>FriendTimelineReceived</code> event.</li> </ol>\nThe value of this property will then be set to the list of messages (and maintain its value until any subsequent call to <code>RequestFriendTimeline</code>.")
    public List<List<String>> FriendTimeline() {
        return this.timeline;
    }

    @SimpleFunction(description = "This searches Twitter for the given String query.<p><u>Requirements</u>: This should only be called after the <code>IsAuthorized</code> event has been raised, indicating that the user has successfully logged in to Twitter.</p>")
    public void SearchTwitter(String query) {
        if (this.twitter == null || this.userName.length() == 0) {
            this.form.dispatchErrorOccurredEvent(this, "SearchTwitter", ErrorMessages.ERROR_TWITTER_SEARCH_FAILED, "Need to login?");
        } else {
            AsynchUtil.runAsynchronously(new AnonymousClass13(query));
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Twitter$13  reason: invalid class name */
    /* loaded from: classes.dex */
    class AnonymousClass13 implements Runnable {
        List<Status> tweets = Collections.emptyList();
        final /* synthetic */ String val$query;

        AnonymousClass13(String str) {
            this.val$query = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.tweets = Twitter.this.twitter.search(new Query(this.val$query)).getTweets();
            } catch (TwitterException e) {
                Twitter.this.form.dispatchErrorOccurredEvent(Twitter.this, "SearchTwitter", ErrorMessages.ERROR_TWITTER_SEARCH_FAILED, e.getMessage());
            } finally {
                Twitter.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Twitter.13.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Twitter.this.searchResults.clear();
                        for (Status tweet : AnonymousClass13.this.tweets) {
                            Twitter.this.searchResults.add(tweet.getUser().getName() + " " + tweet.getText());
                        }
                        Twitter.this.SearchSuccessful(Twitter.this.searchResults);
                    }
                });
            }
        }
    }

    @SimpleEvent(description = "This event is raised when the results of the search requested through <code>SearchSuccessful</code> have been retrieved. A list of the results can then be found in the <code>results</code> parameter or the <code>Results</code> property.")
    public void SearchSuccessful(List<String> searchResults) {
        EventDispatcher.dispatchEvent(this, "SearchSuccessful", searchResults);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "This property, which is initially empty, is set to a list of search results after the program: <ol><li>Calls the <code>SearchTwitter</code> method.</li> <li>Waits for the <code>SearchSuccessful</code> event.</li></ol>\nThe value of the property will then be the same as the parameter to <code>SearchSuccessful</code>.  Note that it is not necessary to call the <code>Authorize</code> method before calling <code>SearchTwitter</code>.")
    public List<String> SearchResults() {
        return this.searchResults;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkAccessToken(String myConsumerKey, String myConsumerSecret) {
        this.accessToken = retrieveAccessToken();
        if (this.accessToken == null) {
            return false;
        }
        if (this.twitter == null) {
            this.twitter = new TwitterFactory().getInstance();
        }
        try {
            this.twitter.setOAuthConsumer(this.consumerKey, this.consumerSecret);
            this.twitter.setOAuthAccessToken(this.accessToken);
        } catch (IllegalStateException e) {
        }
        if (this.userName.trim().length() == 0) {
            try {
                User user = this.twitter.verifyCredentials();
                this.userName = user.getScreenName();
            } catch (TwitterException e2) {
                deAuthorize();
                return false;
            }
        }
        return true;
    }
}
