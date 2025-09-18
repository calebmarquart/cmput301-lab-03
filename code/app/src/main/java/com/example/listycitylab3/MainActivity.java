package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayAdapter<City> cityAdapter;

    private Integer selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        City[] cities = {
                new City("Edmonton", "AB"),
                new City("Vancouver", "BC"),
                new City("Toronto", "ON"),
                new City("Hamilton", "ON"),
                new City("Denver", "CO"),
                new City("Los Angeles", "CA")
        };

        ArrayList<City> citiesArray = new ArrayList<>(Arrays.asList(cities));
        
        ListView cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, citiesArray);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(),
                    "Add City");
        });

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            City localCity = cityAdapter.getItem(position);
            AddCityFragment fragment = AddCityFragment.newInstance(position, localCity);
            fragment.show(getSupportFragmentManager(), "Edit City");
        });
    }

    @Override
    public void addCity(City newCity) {
        if (selectedPosition == -1) {
            cityAdapter.add(newCity);
        } else {
            City city = cityAdapter.getItem(selectedPosition);

            if (city != null) {
                city.setName(newCity.getName());
                city.setProvince(newCity.getProvince());
            }

            selectedPosition = -1;
        }

        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissDialog() {
        selectedPosition = -1;
    }
}