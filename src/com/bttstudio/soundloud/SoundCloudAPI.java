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
    public void setClientID();
    public boolean login(String username, String password);
    public boolean anonymousLogin();
    
    public SearchResult search(String keyWords);
    public Track getTrack(String id);
    
    public User me();
}
