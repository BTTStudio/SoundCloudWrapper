/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bttstudio.soundloud;

import java.util.List;

/**
 *
 * @author martin
 */
public interface SearchResult<T extends SoudnCloudData> extends Iterable<T> {

    public List<T> getResults();

    public boolean hasResults();
}
