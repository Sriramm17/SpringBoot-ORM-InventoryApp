package com.hackerrank.orm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackerrank.orm.enums.ItemStatus;
import com.hackerrank.orm.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	Iterable<Item> findByItemStatus(ItemStatus itemStatus);
}
