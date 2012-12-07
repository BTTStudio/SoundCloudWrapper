/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bttstudio.soundloud.impl;

import com.bttstudio.soundloud.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author martin
 */
public class SoundCloudAPIImpl implements SoundCloudAPI {

    public static final String apiUrl = "https://api.soundcloud.com";
    private String clientID;
    private boolean isUserLogedin = false;
    private User currentUser;

    public SoundCloudAPIImpl(String clientID) {
        this.clientID = clientID;
    }


    @Override
    public boolean login(String username, String password) {
        //?
        return true;
    }

    @Override
    public boolean isAnonymous() {
        return !isUserLogedin;
    }

    @Override
    public SearchResult<Track> searchTrack(String keyWords) {
        SearchResultImpl<Track> i = new SearchResultImpl<>(this, Track.class);
        i.doSearch(keyWords);
        return i;
    }

    @Override
    public SearchResult<User> searchUser(String keyWords) {
        SearchResultImpl<User> i = new SearchResultImpl<>(this, User.class);
        i.doSearch(keyWords);
        return i;
    }

    @Override
    public Track getTrack(String id) {
        try {
            JSONObject o = this.callServerObject(apiUrl + Endpoints.TRACK_DETAILS.replaceFirst("%d", id) + ".json?client_id=" + clientID);
            Track track = new Track();
            track.id = id;
            track.permalink = o.getString("permalink");
            track.duration = o.getLong("duration");
            track.streamable = o.getBoolean("streamable");
            track.downloadable = o.getBoolean("downloadable");
            track.title = o.getString("title");
            track.original_format = o.getString("original_format");
            track.artwork_url = o.getString("artwork_url");
            track.permalink_url = o.getString("permalink_url");
            if (track.streamable) {
                track.stream_url = o.getString("stream_url");
            }
            if (track.downloadable) {
                track.download_url = o.getString("download_url");
            }
            track.user_id = o.getString("user_id");

            track.user = getUser(track.user_id);

            return track;
        } catch (MalformedURLException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User getUser(String id) {
        try {
            JSONObject o = this.callServerObject(apiUrl + Endpoints.USER_DETAILS.replaceFirst("%d", id) + ".json?client_id=" + clientID);
            User user = new User();
            user.id = id;
            user.permalink = o.getString("permalink");
            user.username = o.getString("username");


            user.avatar_url = o.getString("avatar_url");
            user.permalink_url = o.getString("permalink_url");
            user.full_name = o.getString("full_name");
            user.country = o.getString("country");
            user.city = o.getString("city");

            return user;
        } catch (MalformedURLException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User me() {
        if (this.isUserLogedin) {
            return this.currentUser;
        }
        return null;
    }

    public String callServer(String url) throws MalformedURLException, ProtocolException, IOException, JSONException {
        URL u = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        HttpURLConnection.setFollowRedirects(true);
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");

        int i;
        byte[] buffer = new byte[256];
        InputStream in = connection.getInputStream();

        String out = "";

        while ((i = in.read(buffer)) != -1) {
            out += new String(buffer, 0, i);
        }

        return out;
    }

    public JSONObject callServerObject(String url) throws MalformedURLException, ProtocolException, IOException, JSONException {
        JSONObject o = new JSONObject(callServer(url));
        return o;
    }

    public JSONArray callServerArray(String url) throws MalformedURLException, ProtocolException, IOException, JSONException {
        JSONArray o = new JSONArray(callServer(url));
        return o;
    }

    public String getClientID() {
        return clientID;
    }
}
