package com.example.demo.controller;

import com.example.demo.domain.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jakub krhovják on 7/3/17.
 */
@RestController
public class DataController {

	private static final String UNDERSCORE = "_";

	private List<Data> items = new LinkedList<>();

	public DataController() {
		items.add(createData("Item"));
		items.add(createData("Data"));
	}

	@RequestMapping(value = "/data")
	public List<Data> data(@RequestParam(value = "dummy") String dummy) {
		return items;
	}

	private Data createData(String name) {
		Data data = new Data(name);
		data.put(name + UNDERSCORE + data.getId() + 1, data.getId());
		data.put(name + UNDERSCORE + data.getId() + 2, data.getId());
		return data;
	}


	@RequestMapping(value = "/schema")
	public String schema() {
		return "{ \"id\" : \"1\", \"array\":" + new ArrayList<>() + "}";
	}


}
