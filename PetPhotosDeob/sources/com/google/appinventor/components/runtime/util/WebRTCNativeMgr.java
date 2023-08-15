package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.util.Log;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.ReplForm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

/* loaded from: classes.dex */
public class WebRTCNativeMgr {
    private static final boolean DEBUG = true;
    private static final String LOG_TAG = "AppInvWebRTC";
    private static final CharsetDecoder utf8Decoder = Charset.forName("UTF-8").newDecoder();
    private ReplForm form;
    private List<PeerConnection.IceServer> iceServers;
    private PeerConnection peerConnection;
    private String rCode;
    private String rendezvousServer;
    private String rendezvousServer2;
    private TreeSet<String> seenNonces = new TreeSet<>();
    private boolean haveOffer = false;
    private volatile boolean keepPolling = true;
    private volatile boolean haveLocalDescription = false;
    private boolean first = true;
    private Random random = new Random();
    private DataChannel dataChannel = null;
    Timer timer = new Timer();
    SdpObserver sdpObserver = new SdpObserver() { // from class: com.google.appinventor.components.runtime.util.WebRTCNativeMgr.1
        public void onCreateFailure(String str) {
            Log.d(WebRTCNativeMgr.LOG_TAG, "onCreateFailure: " + str);
        }

        public void onCreateSuccess(SessionDescription sessionDescription) {
            try {
                Log.d(WebRTCNativeMgr.LOG_TAG, "sdp.type = " + sessionDescription.type.canonicalForm());
                Log.d(WebRTCNativeMgr.LOG_TAG, "sdp.description = " + sessionDescription.description);
                new DataChannel.Init();
                if (sessionDescription.type == SessionDescription.Type.OFFER) {
                    Log.d(WebRTCNativeMgr.LOG_TAG, "Got offer, about to set remote description (again?)");
                    WebRTCNativeMgr.this.peerConnection.setRemoteDescription(WebRTCNativeMgr.this.sdpObserver, sessionDescription);
                } else if (sessionDescription.type == SessionDescription.Type.ANSWER) {
                    Log.d(WebRTCNativeMgr.LOG_TAG, "onCreateSuccess: type = ANSWER");
                    WebRTCNativeMgr.this.peerConnection.setLocalDescription(WebRTCNativeMgr.this.sdpObserver, sessionDescription);
                    WebRTCNativeMgr.this.haveLocalDescription = true;
                    JSONObject offer = new JSONObject();
                    offer.put("type", "answer");
                    offer.put("sdp", sessionDescription.description);
                    JSONObject response = new JSONObject();
                    response.put("offer", offer);
                    WebRTCNativeMgr.this.sendRendezvous(response);
                }
            } catch (Exception e) {
                Log.e(WebRTCNativeMgr.LOG_TAG, "Exception during onCreateSuccess", e);
            }
        }

        public void onSetFailure(String str) {
        }

        public void onSetSuccess() {
        }
    };
    PeerConnection.Observer observer = new PeerConnection.Observer() { // from class: com.google.appinventor.components.runtime.util.WebRTCNativeMgr.2
        public void onAddStream(MediaStream mediaStream) {
        }

        public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreamArr) {
        }

        public void onDataChannel(DataChannel dataChannel) {
            Log.d(WebRTCNativeMgr.LOG_TAG, "Have Data Channel!");
            Log.d(WebRTCNativeMgr.LOG_TAG, "v5");
            WebRTCNativeMgr.this.dataChannel = dataChannel;
            dataChannel.registerObserver(WebRTCNativeMgr.this.dataObserver);
            WebRTCNativeMgr.this.keepPolling = false;
            WebRTCNativeMgr.this.timer.cancel();
            Log.d(WebRTCNativeMgr.LOG_TAG, "Poller() Canceled");
            WebRTCNativeMgr.this.seenNonces.clear();
        }

        public void onIceCandidate(IceCandidate iceCandidate) {
            try {
                Log.d(WebRTCNativeMgr.LOG_TAG, "IceCandidate = " + iceCandidate.toString());
                if (iceCandidate.sdp == null) {
                    Log.d(WebRTCNativeMgr.LOG_TAG, "IceCandidate is null");
                } else {
                    Log.d(WebRTCNativeMgr.LOG_TAG, "IceCandidateSDP = " + iceCandidate.sdp);
                }
                JSONObject response = new JSONObject();
                response.put("nonce", WebRTCNativeMgr.this.random.nextInt(Form.MAX_PERMISSION_NONCE));
                JSONObject jsonCandidate = new JSONObject();
                jsonCandidate.put("candidate", iceCandidate.sdp);
                jsonCandidate.put("sdpMLineIndex", iceCandidate.sdpMLineIndex);
                jsonCandidate.put("sdpMid", iceCandidate.sdpMid);
                response.put("candidate", jsonCandidate);
                WebRTCNativeMgr.this.sendRendezvous(response);
            } catch (Exception e) {
                Log.e(WebRTCNativeMgr.LOG_TAG, "Exception during onIceCandidate", e);
            }
        }

        public void onIceCandidatesRemoved(IceCandidate[] iceCandidateArr) {
        }

        public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
        }

        public void onIceConnectionReceivingChange(boolean z) {
        }

        public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
            Log.d(WebRTCNativeMgr.LOG_TAG, "onIceGatheringChange: iceGatheringState = " + iceGatheringState);
        }

        public void onRemoveStream(MediaStream mediaStream) {
        }

        public void onRenegotiationNeeded() {
        }

        public void onSignalingChange(PeerConnection.SignalingState signalingState) {
            Log.d(WebRTCNativeMgr.LOG_TAG, "onSignalingChange: signalingState = " + signalingState);
        }
    };
    DataChannel.Observer dataObserver = new DataChannel.Observer() { // from class: com.google.appinventor.components.runtime.util.WebRTCNativeMgr.3
        public void onBufferedAmountChange(long j) {
        }

        public void onMessage(DataChannel.Buffer buffer) {
            try {
                String input = WebRTCNativeMgr.utf8Decoder.decode(buffer.data).toString();
                Log.d(WebRTCNativeMgr.LOG_TAG, "onMessage: received: " + input);
                WebRTCNativeMgr.this.form.evalScheme(input);
            } catch (CharacterCodingException e) {
                Log.e(WebRTCNativeMgr.LOG_TAG, "onMessage decoder error", e);
            }
        }

        public void onStateChange() {
        }
    };

    public WebRTCNativeMgr(String rendezvousServer, String rendezvousResult) {
        this.rendezvousServer = null;
        this.rendezvousServer2 = null;
        this.iceServers = new ArrayList();
        this.rendezvousServer = rendezvousServer;
        rendezvousResult = (rendezvousResult.isEmpty() || rendezvousResult.startsWith("OK")) ? "{\"rendezvous2\" : \"rendezvous.appinventor.mit.edu\",\"iceservers\" : [{ \"server\" : \"stun:stun.l.google.com:19302\" },{ \"server\" : \"turn:turn.appinventor.mit.edu:3478\",\"username\" : \"oh\",\"password\" : \"boy\"}]}" : "{\"rendezvous2\" : \"rendezvous.appinventor.mit.edu\",\"iceservers\" : [{ \"server\" : \"stun:stun.l.google.com:19302\" },{ \"server\" : \"turn:turn.appinventor.mit.edu:3478\",\"username\" : \"oh\",\"password\" : \"boy\"}]}";
        try {
            JSONObject resultJson = new JSONObject(rendezvousResult);
            this.rendezvousServer2 = resultJson.getString("rendezvous2");
            JSONArray iceServerArray = resultJson.getJSONArray("iceservers");
            this.iceServers = new ArrayList(iceServerArray.length());
            for (int i = 0; i < iceServerArray.length(); i++) {
                JSONObject jsonServer = iceServerArray.getJSONObject(i);
                PeerConnection.IceServer.Builder builder = PeerConnection.IceServer.builder(jsonServer.getString("server"));
                Log.d(LOG_TAG, "Adding iceServer = " + jsonServer.getString("server"));
                if (jsonServer.has("username")) {
                    builder.setUsername(jsonServer.getString("username"));
                }
                if (jsonServer.has("password")) {
                    builder.setPassword(jsonServer.getString("password"));
                }
                this.iceServers.add(builder.createIceServer());
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "parsing iceServers:", e);
        }
    }

    public void initiate(ReplForm form, Context context, String code) {
        this.form = form;
        this.rCode = code;
        PeerConnectionFactory.initializeAndroidGlobals(context, false);
        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();
        PeerConnectionFactory factory = new PeerConnectionFactory(options);
        PeerConnection.RTCConfiguration rtcConfig = new PeerConnection.RTCConfiguration(this.iceServers);
        rtcConfig.continualGatheringPolicy = PeerConnection.ContinualGatheringPolicy.GATHER_CONTINUALLY;
        this.peerConnection = factory.createPeerConnection(rtcConfig, new MediaConstraints(), this.observer);
        this.timer.schedule(new TimerTask() { // from class: com.google.appinventor.components.runtime.util.WebRTCNativeMgr.4
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                WebRTCNativeMgr.this.Poller();
            }
        }, 0L, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Poller() {
        try {
            if (this.keepPolling) {
                Log.d(LOG_TAG, "Poller() Called");
                Log.d(LOG_TAG, "Poller: rendezvousServer2 = " + this.rendezvousServer2);
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpGet request = new HttpGet("http://" + this.rendezvousServer2 + "/rendezvous2/" + this.rCode + "-s");
                HttpResponse response = defaultHttpClient.execute(request);
                StringBuilder sb = new StringBuilder();
                BufferedReader rd = null;
                try {
                    BufferedReader rd2 = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    while (true) {
                        try {
                            String line = rd2.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                        } catch (Throwable th) {
                            th = th;
                            rd = rd2;
                            if (rd != null) {
                                rd.close();
                            }
                            throw th;
                        }
                    }
                    if (rd2 != null) {
                        rd2.close();
                    }
                    if (!this.keepPolling) {
                        Log.d(LOG_TAG, "keepPolling is false, we're done!");
                        return;
                    }
                    String responseText = sb.toString();
                    Log.d(LOG_TAG, "response = " + responseText);
                    if (responseText.equals("")) {
                        Log.d(LOG_TAG, "Received an empty response");
                        return;
                    }
                    JSONArray jsonArray = new JSONArray(responseText);
                    Log.d(LOG_TAG, "jsonArray.length() = " + jsonArray.length());
                    int i = 0;
                    while (i < jsonArray.length()) {
                        Log.d(LOG_TAG, "i = " + i);
                        Log.d(LOG_TAG, "element = " + jsonArray.optString(i));
                        JSONObject element = (JSONObject) jsonArray.get(i);
                        if (!this.haveOffer) {
                            if (!element.has("offer")) {
                                i++;
                            } else {
                                JSONObject offer = (JSONObject) element.get("offer");
                                String sdp = offer.optString("sdp");
                                String type = offer.optString("type");
                                this.haveOffer = true;
                                Log.d(LOG_TAG, "sdb = " + sdp);
                                Log.d(LOG_TAG, "type = " + type);
                                Log.d(LOG_TAG, "About to set remote offer");
                                Log.d(LOG_TAG, "Got offer, about to set remote description (maincode)");
                                this.peerConnection.setRemoteDescription(this.sdpObserver, new SessionDescription(SessionDescription.Type.OFFER, sdp));
                                this.peerConnection.createAnswer(this.sdpObserver, new MediaConstraints());
                                Log.d(LOG_TAG, "createAnswer returned");
                                i = -1;
                                i++;
                            }
                        } else {
                            if (element.has("nonce")) {
                                if (!this.haveLocalDescription) {
                                    Log.d(LOG_TAG, "Incoming candidate before local description set, punting");
                                    return;
                                } else if (element.has("offer")) {
                                    i++;
                                    Log.d(LOG_TAG, "skipping offer, already processed");
                                } else if (element.isNull("candidate")) {
                                    i++;
                                } else {
                                    String nonce = element.optString("nonce");
                                    JSONObject candidate = (JSONObject) element.get("candidate");
                                    String sdpcandidate = candidate.optString("candidate");
                                    String sdpMid = candidate.optString("sdpMid");
                                    int sdpMLineIndex = candidate.optInt("sdpMLineIndex");
                                    if (!this.seenNonces.contains(nonce)) {
                                        this.seenNonces.add(nonce);
                                        Log.d(LOG_TAG, "new nonce, about to add candidate!");
                                        Log.d(LOG_TAG, "candidate = " + sdpcandidate);
                                        IceCandidate iceCandidate = new IceCandidate(sdpMid, sdpMLineIndex, sdpcandidate);
                                        this.peerConnection.addIceCandidate(iceCandidate);
                                        Log.d(LOG_TAG, "addIceCandidate returned");
                                    }
                                }
                            }
                            i++;
                        }
                    }
                    Log.d(LOG_TAG, "exited loop");
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Caught IOException: " + e.toString(), e);
        } catch (JSONException e2) {
            Log.e(LOG_TAG, "Caught JSONException: " + e2.toString(), e2);
        } catch (Exception e3) {
            Log.e(LOG_TAG, "Caught Exception: " + e3.toString(), e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRendezvous(final JSONObject data) {
        AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.util.WebRTCNativeMgr.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    data.put("first", WebRTCNativeMgr.this.first);
                    data.put("webrtc", true);
                    data.put("key", WebRTCNativeMgr.this.rCode + "-r");
                    if (WebRTCNativeMgr.this.first) {
                        WebRTCNativeMgr.this.first = false;
                        data.put("apiversion", SdkLevel.getLevel());
                    }
                    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://" + WebRTCNativeMgr.this.rendezvousServer2 + "/rendezvous2/");
                    try {
                        Log.d(WebRTCNativeMgr.LOG_TAG, "About to send = " + data.toString());
                        post.setEntity(new StringEntity(data.toString()));
                        defaultHttpClient.execute(post);
                    } catch (IOException e) {
                        Log.e(WebRTCNativeMgr.LOG_TAG, "sendRedezvous IOException", e);
                    }
                } catch (Exception e2) {
                    Log.e(WebRTCNativeMgr.LOG_TAG, "Exception in sendRendezvous", e2);
                }
            }
        });
    }

    public void send(String output) {
        try {
            if (this.dataChannel == null) {
                Log.w(LOG_TAG, "No Data Channel in Send");
            } else {
                ByteBuffer bbuffer = ByteBuffer.wrap(output.getBytes("UTF-8"));
                DataChannel.Buffer buffer = new DataChannel.Buffer(bbuffer, false);
                this.dataChannel.send(buffer);
            }
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "While encoding data to send to companion", e);
        }
    }
}
