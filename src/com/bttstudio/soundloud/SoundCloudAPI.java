/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bttstudio.soundloud;

/**
 *
 * @author martin
 */
public interface SoundCloudAPI {

    public boolean login(String username, String password);

    public boolean isAnonymous();

    public SearchResult<User> searchUser(String keyWords);

    public SearchResult<Track> searchTrack(String keyWords);

    public Track getTrack(String id);

    public User getUser(String id);

    public User me();
}
