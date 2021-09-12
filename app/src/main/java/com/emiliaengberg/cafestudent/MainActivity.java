package com.emiliaengberg.cafestudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<Macka> mMackaLista = new ArrayList<>();
    private ArrayList<Macka> mKundvagnLista = new ArrayList<>();
    private ArrayList<Bestallning> mBestallningLista = new ArrayList<>();
    private Macka mMacka;
    private Bestallning mBestallning = new Bestallning();
    private Calendar mTidpunktUpphamtning = Calendar.getInstance();
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;
    private Button mDatePickerButton;
    private Button mTimePickerButton;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd",
            Locale.getDefault());
    private final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm",
            Locale.getDefault());
    private Spinner mSpinner;
    private Button mLaggTillButton;
    private Button mLaggBestallningButton;
    private RecyclerView mRecyclerView1, mRecyclerView2, mRecyclerView3;
    private RecyclerAdapterMackor mRecyclerAdapterMackor1, mRecyclerAdapterMackor2;
    private RecyclerAdapterBestallning mRecyclerAdapterBestallning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call method to add sandwiches to the sandwich list
        setupMackorData();

        //Call method to setup TabHost
        setupTabHost();

        //Call method to setup RecyclerView for the first tab
        setupRecyclerViewUtbud();

        //Call method to setup RecyclerView for the second tab
        setupRecyclerViewKundvagn();

        //Call method to setup spinner
        setupSpinnerKundvagn();

        //Update time on time picker buttons
        updateraTidpunktUpphamtning();

        //Method for adding the button for adding content to kundvagn and
        //setting up an onClick listener
        onClickLaggKundvagn();

        //Method for adding the order button and setting up an onClick listener
        onClickLaggBestallning();

        //Call method to set up RecyclerView for third tab
        setupRecyclerViewBestallning();
    }

    private void setupTabHost() {
        //Initiate and setup TabHost
        final TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        //Creates a builder for TabHost and sets the content of the tab as the view set up in
        //activity-main.xml
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("Mackor");
        tabSpec1.setContent(R.id.first_tab);

        //Sets name for first tab and adds it to TabHost
        tabSpec1.setIndicator("Mackor");
        tabHost.addTab(tabSpec1);

        //Creates a builder for TabHost and sets the content of the tab as the view set up in
        //activity-main.xml
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("Kundvagn");
        tabSpec2.setContent(R.id.second_tab);

        //Sets name for second tab and adds it to TabHost
        tabSpec2.setIndicator("Kundvagn");
        tabHost.addTab(tabSpec2);

        //Creates a builder for TabHost and sets the content of the tab as the view set up in
        //activity-main.xml
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("Beställning");
        tabSpec3.setContent(R.id.third_tab);

        //Sets name for second tab and adds it to TabHost
        tabSpec3.setIndicator("Beställning");
        tabHost.addTab(tabSpec3);

        //Sets the first tab as current tab
        tabHost.setCurrentTab(0);

        //Call method to set up DatePicker
        setDate();

        //Call method to set up TimePicker
        setTime();
    }

    private void setupRecyclerViewUtbud() {
        //Sets up RecyclerView for the sandwich list
        mRecyclerView1 = findViewById(R.id.recyclerView_utbud);
        mRecyclerAdapterMackor1 = new RecyclerAdapterMackor(mMackaLista);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this));

        //Adds a divider line between rows for clarity
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mRecyclerAdapterMackor1);
    }

    private void setupRecyclerViewKundvagn() {
        //Sets up RecyclerView for the sandwich list
        mRecyclerView2 = findViewById(R.id.recyclerView_kundvagn);
        mRecyclerAdapterMackor2 = new RecyclerAdapterMackor(mKundvagnLista);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));

        //Adds a divider line between rows for clarity
        mRecyclerView2.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        mRecyclerView2.setAdapter(mRecyclerAdapterMackor2);
    }

    private void setupRecyclerViewBestallning() {
        //Sets up RecyclerView for the sandwich list
        mRecyclerView3 = findViewById(R.id.recyclerView_bestallning);
        mRecyclerAdapterBestallning = new RecyclerAdapterBestallning(mBestallningLista);
        mRecyclerView3.setLayoutManager(new LinearLayoutManager(this));

        //Adds a divider line between rows for clarity
        mRecyclerView3.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        mRecyclerView3.setAdapter(mRecyclerAdapterBestallning);
    }

    private void setupMackorData() {
        //Fills ArrayList with a list of Sandwiches
        mMackaLista.add(new Macka("Ostfralla", "Enkel ostfralla gjord " +
                "på vitt bröd"));
        mMackaLista.add(new Macka("Skink- och ostfralla", "Enkel fralla " +
                "gjord på ljust bröd med ost, skinka och grönsaker"));
        mMackaLista.add(new Macka("Räkmacka", "Gjord på vår goda tekaka " +
                "med majonnäs, ägg och räkor"));
        mMackaLista.add(new Macka("Baguette med kycklingröra", "Baguette " +
                "gjord på fullkornsbröd med kycklingröra och grönsaker"));
        mMackaLista.add(new Macka("Baguette med Tonfiskröra", "Baguette " +
                "gjord på fullkornsbröd med tonfiskröra, ägg och grönsaker"));
        mMackaLista.add(new Macka("Ostbaguette", "Baguette " +
                "gjord på ljust bröd med ost och grönsaker"));

        mMackaLista.add(new Macka("Skink- och ostbaguette", "Baguette " +
                "gjord på ljust bröd med ost, skinka och grönsaker"));
        mMackaLista.add(new Macka("Falafelbaguette", "Baguette " +
                "gjord på ljust bröd med falafel, tzatzikisås och grönsaker"));
    }

    private void setDate() {
        //Set up DatePicker and its onClick listener
        mDatePickerButton = findViewById(R.id.date_picker_btn);
        mDatePickerButton.setInputType(InputType.TYPE_NULL);
        mDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a claendar for holding date and time values
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                mDatePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            //Updates the values for date when user has set their own value from
                            //DatePicker
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                mDatePickerButton.setText(dateFormatter.format(calendar.getTime()));
                                mTidpunktUpphamtning.set(year, month, dayOfMonth);
                            }
                        }, year, month, day);

                //Show DatePicker dialog
                mDatePickerDialog.show();
            }
        });
    }

    private void setTime() {
        //Set up TimePicker and its onClick listener
        mTimePickerButton = findViewById(R.id.time_picker_btn);
        mTimePickerButton.setInputType(InputType.TYPE_NULL);
        mTimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a claendar for holding date and time values
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                //Updates the values for time when user has set their own value from
                //DatePicker
                mTimePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(calendar.MINUTE, minute);
                                mTimePickerButton.setText(timeFormatter.format(calendar.getTime()));
                                mTidpunktUpphamtning.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                mTidpunktUpphamtning.set(calendar.MINUTE, minute);
                            }
                        }, hour, minute, true);

                //Show TimePicker dialog
                mTimePickerDialog.show();
            }
        });
    }

    private void setupSpinnerKundvagn() {
        //Setup spinner for data
        mSpinner = findViewById(R.id.sandwich_spinner);
        mSpinner.setOnItemSelectedListener(this);

        //Setup adapter for spinner
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, mMackaLista);

        //Set the type of appearance for spinner to drop down menu
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set the adapter
        mSpinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Adds the object data for selected item in spinner
        mMacka = mMackaLista.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateraTidpunktUpphamtning() {
        //Updates Date- och TimePicker data in object and buttons
        mTidpunktUpphamtning = Calendar.getInstance();
        mDatePickerButton.setText(dateFormatter.format(mTidpunktUpphamtning.getTime()));
        mTimePickerButton.setText(timeFormatter.format(mTidpunktUpphamtning.getTime()));
    }

    //Method to create and show an alert dialog that confirms button click
    private void bekraftaBestallning() {
        //Sets up an alert dialog with an ok button
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.bestallningsbekraftelse_titel);
        builder.setMessage(R.string.bestallning_tillagd_text);
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void onClickLaggKundvagn() {
        //Add the button for adding content to kundvagn and set up an onClick listener
        mLaggTillButton = findViewById(R.id.lagg_i_kundvagn);
        mLaggTillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add selected item to kundvagn and make a toast informing user that this has happened
                mKundvagnLista.add(mMacka);
                Toast.makeText(getApplicationContext(), mMacka.getName() + " lagd i kundvagnen", Toast.LENGTH_SHORT).show();

                //Clear and update data
                mMacka = new Macka();
                mRecyclerAdapterMackor2.notifyDataSetChanged();
                setupSpinnerKundvagn();
            }
        });
    }

    private void onClickLaggBestallning() {
        //Add the order button and set up an onClick listener
        mLaggBestallningButton = findViewById(R.id.lagg_bestallning);
        mLaggBestallningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check that array is not empty
                if (mKundvagnLista.size() != 0){
                    for (int index = 0; index < mKundvagnLista.size(); index++){
                        //Add macka to kundvagn
                        mBestallning.laggTillMacka(mKundvagnLista.get(index));
                    }
                    //Add data to object
                    mBestallning.laggTillTidpunkt(mTidpunktUpphamtning);
                    mBestallningLista.add(mBestallning);
                    bekraftaBestallning();

                    //Clear array
                    mKundvagnLista.clear();

                    //Update Date- and TimePicker buttons
                    updateraTidpunktUpphamtning();

                    //Create new object
                    mBestallning = new Bestallning();

                    //Update RecyclerViews
                    mRecyclerAdapterMackor2.notifyDataSetChanged();
                    mRecyclerAdapterBestallning.notifyDataSetChanged();
                }
                else {
                    //Toast to inform that no item is added to kundvagn
                    Toast.makeText(getApplicationContext(), R.string.ingen_macka_kundvagn, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}