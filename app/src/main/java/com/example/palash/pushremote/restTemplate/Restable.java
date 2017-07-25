package com.example.palash.pushremote.restTemplate;

import com.example.palash.pushremote.restCallable.RestCallable;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by palash on 22/7/17.
 */

public interface Restable {

    void setup(String base, String apiPath, int apiPort, Object entity, RestCallable callable) throws URISyntaxException;



    String postForObject(Object obj, URI uri);

    String get(URI uri);
}
