package com.project.customcalendarminiproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;

import com.project.customcalendarminiproject.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ArrayList<CalendarFragment> calendarFragments;

    private CalendarFragmentAdapter calendarFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addControls();
        addEvents();
    }

    private void addEvents() {
        binding.vpCalendarCells.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0; i< calendarFragments.size(); i++){
                            if(i != position){
                                if(calendarFragments.get(i).getCalendarAdapter() != null){
                                    if(calendarFragments.get(i).getCalendarAdapter().getDateToday() != null){
                                        calendarFragments.get(i).getCalendarAdapter().setDateToday(null);
                                    }
                                }
                            }
                        }
                    }
                });
                thread.start();
                if(position == 1){
                    calendarFragments.add(0, new CalendarFragment(calendarFragments.get(0).getSelectedDate().minusMonths(1)));
                    calendarFragmentAdapter.notifyDataSetChanged();
                    binding.vpCalendarCells.setCurrentItem(2,true);
                }else if(position == calendarFragments.size()-2){
                    calendarFragments.add(new CalendarFragment(calendarFragments.
                            get(calendarFragments.size() - 1)
                            .getSelectedDate()
                            .plusMonths(1)));
                    calendarFragmentAdapter.notifyDataSetChanged();
                }
                binding.txtMonth.setText(monthFromDate(calendarFragments.get(position).getSelectedDate()));
                binding.txtYear.setText(yearFromDate(calendarFragments.get(position).getSelectedDate()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addControls() {
        initViewPagerCalendar();
    }

    private void initViewPagerCalendar() {
        calendarFragments = new ArrayList<>();
        calendarFragments.add(new CalendarFragment(LocalDate.now().minusMonths(2)));
        calendarFragments.add(new CalendarFragment(LocalDate.now().minusMonths(1)));
        calendarFragments.add(new CalendarFragment(LocalDate.now()));
        calendarFragments.add(new CalendarFragment(LocalDate.now().plusMonths(1)));
        calendarFragments.add(new CalendarFragment(LocalDate.now().plusMonths(2)));
        calendarFragmentAdapter = new CalendarFragmentAdapter(getSupportFragmentManager(),calendarFragments);
        binding.vpCalendarCells.setAdapter(calendarFragmentAdapter);
        binding.vpCalendarCells.setCurrentItem(2,true);
        getSupportFragmentManager().executePendingTransactions();
    }

    private String monthFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        return date.format(formatter);
    }

    private String yearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        return date.format(formatter);
    }

    private String dayFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");
        return date.format(formatter);
    }
}