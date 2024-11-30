package com.pawgress.main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pawgress.R;
import com.pawgress.model.DataRepository;
import com.pawgress.model.Subtask;
import com.pawgress.model.Task;
import com.pawgress.model.TaskDifficulty;
import com.pawgress.model.TaskStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {
    private EditText taskNameEditText;
    private TextView taskDueDate;
    private EditText editSubject;
    private RadioGroup difficultyGroup;
    private DataRepository dataRepository;
    private TextView subtask1;
    private TextView subtask2;
    private TextView subtask3;
    private int currentTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setupToolbar();

        taskNameEditText = findViewById(R.id.editTaskName);
        taskDueDate = findViewById(R.id.taskDueDate);
        editSubject = findViewById(R.id.editSubject); // Assuming there's an EditText for description
        difficultyGroup = findViewById(R.id.optionsDiff);
        subtask1 = findViewById(R.id.subtask1);
        subtask2 = findViewById(R.id.subtask2);
        subtask3 = findViewById(R.id.subtask3);

        taskDueDate.setOnClickListener(v -> showDatePicker());

        Button saveButton = findViewById(R.id.saveChanges);
        saveButton.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        // Get the task name from the EditText
        String taskName = taskNameEditText.getText().toString();

        // Get the selected RadioButton's ID
        int selectedRadioButtonId = difficultyGroup.getCheckedRadioButtonId();
        TaskDifficulty difficulty = TaskDifficulty.MEDIUM; // Default to MEDIUM

        if (selectedRadioButtonId == R.id.easyDifficulty) {
            difficulty = TaskDifficulty.EASY;
        } else if (selectedRadioButtonId == R.id.mediumDifficulty) {
            difficulty = TaskDifficulty.MEDIUM;
        } else if (selectedRadioButtonId == R.id.hardDifficulty) {
            difficulty = TaskDifficulty.HARD;
        }

        // Get the due date
        String dueDate = taskDueDate.getText().toString();

        // Parse the due date
        LocalDate parsedDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            parsedDate = LocalDate.parse(dueDate, formatter);
        } catch (DateTimeParseException e) {
            Toast.makeText(this, "Invalid date format. Please use dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the task description
        String subject = editSubject.getText().toString();

        // Create a new Task object
        Task newTask = new Task();
        newTask.setName(taskName);
        newTask.setDifficulty(difficulty);
        newTask.setDueDate(parsedDate); // Store it as a String, or convert it to a Date object if needed
        newTask.setSubject(subject);
        newTask.setStatus(TaskStatus.TO_DO);

        // Save subtasks
        String name1 = subtask1.getText().toString();
        if (!TextUtils.isEmpty(name1)) {
            newTask.getSubtasks().add(new Subtask(newTask, name1));
        }
        String name2 = subtask2.getText().toString();
        if (!TextUtils.isEmpty(name2)) {
            newTask.getSubtasks().add(new Subtask(newTask, name2));
        }
        String name3 = subtask3.getText().toString();
        if (!TextUtils.isEmpty(name3)) {
            newTask.getSubtasks().add(new Subtask(newTask, name3));
        }

        // Save the task in the DataRepository
        DataRepository.getInstance().addTask(newTask);

        // Show a success message
        Toast.makeText(this, "Task saved successfully!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarBackTitle);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(R.string.add_task);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDatePicker() {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Show the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    // Set the selected date on the TextView
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    taskDueDate.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }
}
