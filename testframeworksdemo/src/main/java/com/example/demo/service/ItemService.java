package com.example.demo.service;

import com.example.demo.domain.Comment;
import com.example.demo.domain.Item;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by Jakub krhovják on 7/4/17.
 *
 */
@Service
public class ItemService {

    public List<String> getAllCommentName(List<Item> items) {
        return items.stream().
                map(Item::getComments).
                flatMap(List::stream).
                map(Comment::getName).
                collect(toList());

    }

    public String throwException(boolean exception) throws Exception {
        if(exception) {
            throw new RuntimeException("Very bad exception!");
        }
       return "No exception!";
    }
}
