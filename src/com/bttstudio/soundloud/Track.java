/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bttstudio.soundloud;

/**
 *
 * @author martin
 */
public class Track implements SoudnCloudData {

    public String id;
    public String user_id;
    public long duration;
    public String permalink;
    public String permalink_url;
    public String artwork_url;
    public boolean streamable;
    public boolean downloadable;
    public String title;
    public String stream_url;
    public String download_url;
    public User user;
    public String original_format;
}
