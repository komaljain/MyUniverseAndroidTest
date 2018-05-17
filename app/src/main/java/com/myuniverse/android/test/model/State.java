package com.myuniverse.android.test.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class State {

    private List<String> states;

    private Map<String, List<String>> cities;


    public List<String> getStates() {
        return states;
    }

    public void setState(List<String> states) {
        this.states = states;
    }

    public Map<String, List<String>> getCities() {
        return cities;
    }

    public void setCities(Map<String, List<String>> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "State{" +
                "state=" + states +
                ", cities=" + cities +
                '}';
    }
}
