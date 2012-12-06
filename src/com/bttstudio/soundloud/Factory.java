/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bttstudio.soundloud;

import com.bttstudio.soundloud.impl.SoundCloudAPIImpl;

/**
 *
 * @author martin
 */
public class Factory {
    public static SoundCloudAPI get() {
        return new SoundCloudAPIImpl();
    }
}
