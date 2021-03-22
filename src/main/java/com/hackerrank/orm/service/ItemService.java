package com.hackerrank.orm.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackerrank.orm.enums.ItemStatus;
import com.hackerrank.orm.model.Item;
import com.hackerrank.orm.repository.ItemRepository;

@Transactional
@Service
public class ItemService {

	@Autowired
	public ItemRepository itemRepository;

	public void insert(Item item) {
		itemRepository.save(item);
	}

	public Optional<Item> findById(int id) {
		return itemRepository.findById(id);
	}

	public Iterable<Item> findAll() {
		return itemRepository.findAll();
	}

	public Iterable<Item> findByStatus(ItemStatus itemStatus) {
		return itemRepository.findByItemStatus(itemStatus);
	}

	public void updateItem(Item item) {
		itemRepository.save(item);
	}

	public void deleteItem(int id) {
		itemRepository.deleteById(id);
	}

	public void deleteAll() {
		itemRepository.deleteAll();
	}

	public Iterable<Item> findByPage(Integer size, Integer page, String sortBy) {
		Pageable paging = PageRequest.of(page, size, Sort.by(sortBy));
		Page<Item> pagedResult = itemRepository.findAll(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Item>();
		}
	}

}
