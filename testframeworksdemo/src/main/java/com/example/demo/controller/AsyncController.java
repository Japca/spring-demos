package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Jakub krhovják (cor on 7/4/17.
 */


@RestController
public class AsyncController {

    @RequestMapping(value = "/async", method = POST)
    public Callable<Integer> stringBody(final @RequestBody Integer sleep) {
        return () -> {
            Thread.sleep(sleep);
            return sleep;
        };
    }
}
