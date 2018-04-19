package com.example.demo;

import com.example.demo.service.AtomicIdGenerator;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Jakub krhovják on 7/4/17.
 *
 */
public class TempusFugitTest {

    @Rule
    public ConcurrentRule concurrently = new ConcurrentRule();

    @Rule public RepeatingRule repeatedly = new RepeatingRule();
    private AtomicIdGenerator idGenerator = new AtomicIdGenerator();

    private Set<Long> ids = new HashSet<>(100);

    @Test
    @Concurrent(count = 8)
    @Repeating(repetition = 100)
    public void idsShouldBeUnique() {
        assertTrue(ids.add(idGenerator.nextId()));
    }
}
