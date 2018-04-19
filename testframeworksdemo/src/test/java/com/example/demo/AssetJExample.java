package com.example.demo;

import com.example.demo.domain.Comment;
import com.example.demo.domain.Item;
import com.example.demo.service.ItemService;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.hamcrest.Matchers.*;

/**
 * @author Jakub Krhovják-fg8y7n
 * @since 7/3/2017
 */
public class AssetJExample {



    public static final String EXCEPTION_MESSAGE = "Very bad exception!";
    private static final String COMMENT_1 = "Comment_1";
    private static final String COMMENT_2 = "Comment_2";

    private ItemService itemService = new ItemService();

    private Item item1;
    private Item item2;
    private Item item3 = new Item();


    private List<Item> items = createItems();

    @Test
    public void commentNames() throws Exception {
        List<String> commentNames = itemService.getAllCommentName(items);
        org.junit.Assert.assertThat(commentNames, hasSize(3));
        org.junit.Assert.assertThat(commentNames, hasItems(COMMENT_1, COMMENT_2));

        org.assertj.core.api.Assertions.assertThat(commentNames).
                hasSize(3).
                contains(COMMENT_1, COMMENT_2).
                doesNotContainSubsequence("test").
                startsWith(COMMENT_1).
                endsWith(COMMENT_2);
    }

    @Test
    public void items() throws Exception {
        org.junit.Assert.assertThat(items, hasSize(2));
        org.junit.Assert.assertThat(items, hasItems(item1, item2));
        org.junit.Assert.assertThat(items.get(0), equalTo(item1));
        org.junit.Assert.assertThat(items.get(1), equalTo(item2));

        org.assertj.core.api.Assertions.assertThat(items).
                hasSize(2).
                startsWith(item1).
                endsWith(item2).
                doesNotContain(item3);
    }


    @Test
    public void failingTest() throws Exception {
        org.assertj.core.api.Assertions.assertThat(item3.getName()).
                as("Check %s's name", item3.getName()).
                isEmpty();
    }

    @Test
    public void exception() throws Exception {
        assertThatExceptionOfType(RuntimeException.class).
                isThrownBy(() -> itemService.throwException(true)).
                withMessage(EXCEPTION_MESSAGE).
                withMessageEndingWith("!");
    }

    @Test
    public void exception2() throws Exception {
        Throwable thrown = catchThrowable(() ->  itemService.throwException(true));
        assertThat(thrown).
                isInstanceOf(RuntimeException.class).
                hasMessage(EXCEPTION_MESSAGE).
                hasMessageEndingWith("!");
    }

    @Test
    public void nullPointer() throws Exception {
        assertThatNullPointerException()
                .isThrownBy(() -> {throw new NullPointerException("null !");})
                .withMessage("null !");
    }

    @Test
    public void filter() throws Exception {
        org.assertj.core.api.Assertions.assertThat(item2.getComments()).
                filteredOn(comment -> comment.getId() > 1).
                hasSize(1);
    }

    @Test
    public void byField() throws Exception {
        assertThat(item1).isEqualToComparingFieldByFieldRecursively(item2);
    }

    @Test
    public void commons() throws Exception {
        File file  = new File("/home/cor/file");
        file.createNewFile();
        org.assertj.core.api.Assertions.assertThat(file).hasSameContentAs(file);
        assertThat(new Date()).isBetween("2017-01-31", "2017-12-31");
    }

    private List<Item> createItems() {
        List<Item> items = new ArrayList<>();
        item1 = new Item();
        Comment comment1 = new Comment();
        item1.addIComment(comment1);

        item2 = new Item();
        Comment comment2 = new Comment();
        item2.addIComment(comment1);
        item2.addIComment(comment2);

        items.add(item1);
        items.add(item2);
        return items;
    }
}
