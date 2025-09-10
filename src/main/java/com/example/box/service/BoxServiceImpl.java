package com.example.box.service;

import com.example.box.entity.*;
import com.example.box.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;

    public BoxServiceImpl(BoxRepository boxRepository, ItemRepository itemRepository) {
        this.boxRepository = boxRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Box createBox(Box box) {
        if (box.getWeightLimit() > 500) throw new IllegalArgumentException("weightLimit cannot exceed 500g");
        if (box.getTxref() == null || box.getTxref().length() > 20) throw new IllegalArgumentException("txref invalid");
        return boxRepository.save(box);
    }

    @Override
    public List<Box> getAvailableBoxes() {
        return boxRepository.findAll().stream()
        .filter(b -> b.getBatteryCapacity() >= 25)
        .toList();
    }

    @Override
    @Transactional
    public List<Item> loadItems(String txref, List<Item> itemsToLoad) {
        Box box = boxRepository.findByTxref(txref).orElseThrow(() -> new NoSuchElementException("Box not found"));

        if (box.getBatteryCapacity() < 25) {
            throw new IllegalStateException("Battery below 25% — cannot start loading");
        }

        int currentWeight = box.getItems().stream().mapToInt(Item::getWeight).sum();
        int newWeight = itemsToLoad.stream().mapToInt(Item::getWeight).sum();
        if (currentWeight + newWeight > box.getWeightLimit()) {
            throw new IllegalStateException("Weight exceeds box capacity");
        }

        var namePattern = java.util.regex.Pattern.compile("^[A-Za-z0-9_-]+$");
        var codePattern = java.util.regex.Pattern.compile("^[A-Z0-9_]+$");

        for (Item it : itemsToLoad) {
            if (!namePattern.matcher(it.getName()).matches()) {
                throw new IllegalArgumentException("Invalid item name: " + it.getName());
            }
            if (!codePattern.matcher(it.getCode()).matches()) {
                throw new IllegalArgumentException("Invalid item code: " + it.getCode());
            }
            it.setBox(box);
        }

        box.getItems().addAll(itemsToLoad);
        box.setState(BoxState.LOADED);
        boxRepository.save(box);

        return itemsToLoad.stream().map(itemRepository::save).toList();
    }

    @Override
    public List<Item> getBoxItems(String txref) {
        Box box = boxRepository.findByTxref(txref).orElseThrow(() -> new NoSuchElementException("Box not found"));
        return box.getItems();
    }

    @Override
    public int getBattery(String txref) {
        Box box = boxRepository.findByTxref(txref).orElseThrow(() -> new NoSuchElementException("Box not found"));
        return box.getBatteryCapacity();
    }
}
