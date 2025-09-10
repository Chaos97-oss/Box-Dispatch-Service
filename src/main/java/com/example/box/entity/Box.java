package com.example.box.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boxes")
public class Box {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20, nullable = false)
    private String txref;

    @Min(0)
    @Max(500)
    @Column(name = "weight_limit")
    private int weightLimit;

    @Min(0)
    @Max(100)
    @Column(name = "battery_capacity")
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private BoxState state = BoxState.IDLE;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    public Box() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTxref() { return txref; }
    public void setTxref(String txref) { this.txref = txref; }

    public int getWeightLimit() { return weightLimit; }
    public void setWeightLimit(int weightLimit) { this.weightLimit = weightLimit; }

    public int getBatteryCapacity() { return batteryCapacity; }
    public void setBatteryCapacity(int batteryCapacity) { this.batteryCapacity = batteryCapacity; }

    public BoxState getState() { return state; }
    public void setState(BoxState state) { this.state = state; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}
