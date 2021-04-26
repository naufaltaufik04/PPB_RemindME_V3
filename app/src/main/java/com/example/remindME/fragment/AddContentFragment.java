package com.example.remindME.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.remindME.model.Content;
import com.example.remindME.comunicator.OnAddContentClickListener;
import com.example.remindME.R;
import com.example.remindME.model.ContentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class AddContentFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private ContentViewModel contentViewModel;

    private EditText titleInput;
    private EditText detailsInput;
    private Spinner difficultyInput;
    private TextView dateInput;
    private CheckBox remindMeBox;
    private LocalDate currentDate;
    private int difficultySelected;

    private Button addContentButton;

    public AddContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_content, container, false);

        titleInput = view.findViewById(R.id.titleInput);
        detailsInput = view.findViewById(R.id.detailsInput);
        difficultyInput = view.findViewById(R.id.difficultyInput);
        dateInput = view.findViewById(R.id.dueDateInput);
        addContentButton = view.findViewById(R.id.addContentButton);
        remindMeBox = view.findViewById(R.id.remindMeBox);

        Spinner spinner = (Spinner) view.findViewById(R.id.difficultyInput);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        addContentButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                sendNewContent();
            }
        });

        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNewContent(){
        Content content = null;
        currentDate = LocalDate.now();

        if(!dataIsEmpty() && !dateIsExpired()){
            try {
                content = new Content(
                        titleInput.getText().toString(),
                        detailsInput.getText().toString(),
                        difficultySelected,
                        new SimpleDateFormat("yyyy/MM/dd").parse(dateInput.getText().toString()),
                        new SimpleDateFormat("yyyy/MM/dd").parse(currentDate.getYear() + "/" + currentDate.getMonthValue() + "/" + currentDate.getDayOfMonth()),
                        remindMeBox.isChecked()
                );
//                onAddContentClickListener.onClickListener(content);

                ContentViewModel contentViewModel = new ViewModelProvider(this,
                        new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ContentViewModel.class);
                contentViewModel.getContentByDefault().observe(this, contents -> {
                    // Update the cached copy of the words in the adapter.
                });
                contentViewModel.insert(content);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Toast.makeText(getContext(), "Tugas berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            titleInput.setText("");
            detailsInput.setText("");
            difficultyInput.setSelection(0);
            dateInput.setText("");
            remindMeBox.setChecked(false);
        }
    }

    private boolean dataIsEmpty(){
        boolean isEmpty = true;
        if(!titleInput.getText().toString().isEmpty()
                && !detailsInput.getText().toString().isEmpty()
                && !dateInput.getText().toString().isEmpty()){
            isEmpty = false;
        }
        else{
            Toast.makeText(getContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
        return isEmpty;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean dateIsExpired(){
        boolean isExpired = true;
        Date dueDate = new Date();
        Date today = new Date();

        try {
            dueDate = new SimpleDateFormat("yyyy/MM/dd").parse(dateInput.getText().toString());
            today = new SimpleDateFormat("yyyy/MM/dd").parse(currentDate.getYear() + "/" + currentDate.getMonthValue() + "/" + currentDate.getDayOfMonth());
        }catch (ParseException ex){}

        if(dueDate.after(today) || dueDate.equals(today)){
            isExpired = false;
        }
        else{
            Toast.makeText(getContext(), "Tanggal yang diinputkan sudah terlewatkan", Toast.LENGTH_SHORT).show();
        }
        return isExpired;
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                dateInput.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        difficultySelected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}