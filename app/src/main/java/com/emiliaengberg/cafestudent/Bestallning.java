package com.emiliaengberg.cafestudent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Bestallning {

    private ArrayList<Macka> mBestalldMackaLista;
    private Calendar mTidpunkt;

    //Default constructor
    public Bestallning() {
        mBestalldMackaLista = new ArrayList<>();
        mTidpunkt = Calendar.getInstance();
    }

    //Method for adding object to array
    public void laggTillMacka(Macka macka) {
        mBestalldMackaLista.add(macka);
    }

    //Method for adding calendar data
    public void laggTillTidpunkt(Calendar calendar) {
        mTidpunkt = calendar;
    }

    //Method that returns a string with data about that field in given time and date format
    public String getTidpunkt() {
        //Date- and timeformatters
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm",
                Locale.getDefault());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());

        //String with data about field
        String tidpunkt = "Upphämtning: " + dateFormatter.format(mTidpunkt.getTime()) + " klockan " + timeFormatter.format(mTidpunkt.getTime());
        return tidpunkt;
    }

    //Method that returns a string with data about that field
    public String getMackor() {
        //String with data about field
        String mackor = "Beställda mackor: ";
        if(mBestalldMackaLista.size() != 0) {
            for(int index = 0; index < mBestalldMackaLista.size(); index++) {
                mackor += mBestalldMackaLista.get(index).getName() + " ";
            }
        }
        return mackor;
    }
}
