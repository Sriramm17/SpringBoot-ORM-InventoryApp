package com.hackerrank.orm.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.orm.enums.ItemStatus;
import com.hackerrank.orm.model.Item;
import com.hackerrank.orm.service.ItemService;

@RestController
@RequestMapping("/app")
public class ItemController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ItemService itemService;

	@Autowired
	ObjectMapper objectMapper;

	@PostMapping(value = "/item")
	public ResponseEntity<Item> addItem(@RequestBody Item item) throws Exception {
		Optional<Item> item1 = itemService.findById(item.getItemId());
		if (item1.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			itemService.insert(item);
			logger.info("inserted item into DB {} ", objectMapper.writeValueAsString(item));
			return new ResponseEntity<>(item, HttpStatus.CREATED);
		}
	}

	@PutMapping(value = "/item/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody Item item) throws Exception {
		Optional<Item> item1 = itemService.findById(id);
		if (item1.isPresent()) {
			itemService.updateItem(item);
			logger.info("updated item in DB {} ", objectMapper.writeValueAsString(item));
			return new ResponseEntity<>(item, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/item/{id}")
	public ResponseEntity<Item> deleteItem(@PathVariable int id) {
		Optional<Item> item = itemService.findById(id);
		if (item.isPresent()) {
			itemService.deleteItem(id);
			logger.info("deleted item with id {} in DB ", id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/item")
	public ResponseEntity<Item> deleteAll() {
		logger.info("deleted all items in DB");
		itemService.deleteAll();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/item")
	public ResponseEntity<Iterable<Item>> getAllItem() {
		Iterable<Item> item = itemService.findAll();
		logger.info("obtained all items in DB");
		return new ResponseEntity<>(item, HttpStatus.OK);
	}

	@GetMapping("/item/{id}")
	public ResponseEntity<Optional<Item>> searchByID(@PathVariable int id) throws Exception {
		Optional<Item> item = itemService.findById(id);
		if (item.isPresent()) {
			logger.info("obtained item {} in DB for id {} ", objectMapper.writeValueAsString(item), id);
			return new ResponseEntity<>(item, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/item", params = "itemStatus")
	public ResponseEntity<Iterable<Item>> searchByStatus(@RequestParam("itemStatus") ItemStatus ItemStatus) {
		Iterable<Item> item = itemService.findByStatus(ItemStatus);
		return new ResponseEntity<>(item, HttpStatus.OK);
	}

	@GetMapping(value = "/item/paging")
	public ResponseEntity<Iterable<Item>> searchByPage(@RequestParam("pageSize") Integer size,
			@RequestParam("page") Integer page, @RequestParam("sortBy") String sortBy) {
		Iterable<Item> item = itemService.findByPage(size, page, sortBy);
		return new ResponseEntity<>(item, HttpStatus.OK);
	}

}
