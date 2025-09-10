package com.example.box.service;

import com.example.box.entity.Box;
import com.example.box.entity.Item;

import java.util.List;

public interface BoxService {
    Box createBox(Box box);
    List<Box> getAvailableBoxes();
    List<Item> loadItems(String txref, List<Item> itemsToLoad);
    List<Item> getBoxItems(String txref);
    int getBattery(String txref);
}
