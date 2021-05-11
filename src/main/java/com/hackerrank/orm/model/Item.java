package com.hackerrank.orm.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.hackerrank.orm.enums.ItemStatus;

import lombok.Data;

@Data
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int item121212Id;
	private String itemN121212ame;
	private String itemEnt121212eredByUser;
	@CreationTimestamp
	private Timestamp itemEnteredDate;
	private double itemBuyingPrice;
	private double itemSellingPrice;
	private Date itemLastModifiedDate = new Date();
	private String itemLastModifiedByUser;
	@Enumerated(EnumType.STRING)
	private ItemStatus itemStatus = ItemStatus.AVAILABLE;
}
