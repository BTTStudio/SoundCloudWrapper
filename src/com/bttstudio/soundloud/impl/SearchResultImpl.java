/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bttstudio.soundloud.impl;

import com.bttstudio.soundloud.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author martin
 */
public class SearchResultImpl<T extends SoudnCloudData> implements SearchResult {

    private SoundCloudAPIImpl cloudAPIImpl;
    private Class type;
    private List<T> results = new ArrayList<>();

    public SearchResultImpl(SoundCloudAPIImpl cloudAPIImpl, Class type) {
        this.cloudAPIImpl = cloudAPIImpl;
        this.type = type;
    }

    @Override
    public List<T> getResults() {
        return results;
    }

    @Override
    public boolean hasResults() {
        return !results.isEmpty();
    }

    public void doSearch(String q) {
        if (type.getSimpleName().equals(Track.class.getSimpleName())) {
            doSearchTracks(q);
        } else if (type.getSimpleName().equals(User.class.getSimpleName())) {
            doSearchUsers(q);
        }
    }

    private void doSearchTracks(String q) {
        String url = SoundCloudAPIImpl.apiUrl + Endpoints.TRACKS + ".json?client_id=" + cloudAPIImpl.getClientID() + "&q=" + q;
        try {
            JSONArray a = cloudAPIImpl.callServerArray(url);
            for (int i = 0; i < a.length(); i++) {
                JSONObject o = a.getJSONObject(i);
                Track track = new Track();
                track.id = o.getString("id");
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
                results.add((T) track);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doSearchUsers(String q) {
        String url = SoundCloudAPIImpl.apiUrl + Endpoints.USERS + ".json?client_id=" + cloudAPIImpl.getClientID() + "&q=" + q;
        try {
            JSONArray a = cloudAPIImpl.callServerArray(url);
            for (int i = 0; i < a.length(); i++) {
                JSONObject o = a.getJSONObject(i);
                User user = new User();
                user.id = o.getString("id");
                user.permalink = o.getString("permalink");
                user.username = o.getString("username");


                user.avatar_url = o.getString("avatar_url");
                user.permalink_url = o.getString("permalink_url");
                user.full_name = o.getString("full_name");
                user.country = o.getString("country");
                user.city = o.getString("city");
                results.add((T) user);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(SoundCloudAPIImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
