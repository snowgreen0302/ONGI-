package com.example.ongi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ongi.adapter.DonationAdapter;
import com.example.ongi.model.Donation;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BrowseFragment extends Fragment {

    RecyclerView recyclerView;
    DonationAdapter adapter;

    List<Donation> donationList;

    Set<String> categorySet;

    Set<String> regionSet;

    Spinner spinnerCategory;

    Spinner spinnerRegion;
    EditText edtSearch;

    String selectedCategory = "전체";
    String selectedRegion = "전체";
    String currentKeyword = "";


    public BrowseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view =
                inflater.inflate(
                        R.layout.fragment_browse,
                        container,
                        false);

        recyclerView =
                view.findViewById(R.id.recyclerView);

        spinnerCategory =
                view.findViewById(R.id.spinnerCategory);

        spinnerRegion =
                view.findViewById(R.id.spinnerRegion);

        edtSearch =
                view.findViewById(R.id.edtSearch);

        recyclerView.setLayoutManager(
                new GridLayoutManager(getContext(), 2));

        recyclerView.setPadding(0,0,0,90);
        recyclerView.setClipToPadding(false);

        // 데이터 생성
        donationList =
                DonationRepository
                        .donationList;
        // 전체 목록 표시
        adapter = new DonationAdapter(donationList);
        recyclerView.setAdapter(adapter);

        // Spinner 설정
        categorySet = new LinkedHashSet<>();

        categorySet.add("전체");

        regionSet = new LinkedHashSet<>();

        regionSet.add("전체");

        for(Donation donation : donationList){

            // 카테고리

            categorySet.add(
                    donation.getCategory());

            // 지역

            String address =
                    donation.getAddress();

            if(address != null){

                String[] parts =
                        address.split(" ");

                if(parts.length > 1){

                    regionSet.add(parts[1]);

                }

            }

        }

        List<String> categoryList =

                new ArrayList<>(
                        categorySet);

        ArrayAdapter<String> categoryAdapter =

                new ArrayAdapter<>(

                        requireContext(),

                        android.R.layout.simple_spinner_item,

                        categoryList);

        categoryAdapter.setDropDownViewResource(

                android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(
                categoryAdapter);

        List<String> regionList =

                new ArrayList<>(
                        regionSet);

        ArrayAdapter<String> regionAdapter =

                new ArrayAdapter<>(

                        requireContext(),

                        android.R.layout.simple_spinner_item,

                        regionList);

        regionAdapter.setDropDownViewResource(

                android.R.layout.simple_spinner_dropdown_item);

        spinnerRegion.setAdapter(
                regionAdapter);

        spinnerCategory.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        selectedCategory =
                                parent.getItemAtPosition(position)
                                        .toString();
                        applyFilterAndSearch();
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {

                    }
                });

        spinnerRegion.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        selectedRegion =
                                parent.getItemAtPosition(position)
                                        .toString();

                        applyFilterAndSearch();
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {

                    }
                });

        // 검색 기능
        edtSearch.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        currentKeyword = s.toString();
                        applyFilterAndSearch();
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                });

        return view;
    }

    private void applyFilterAndSearch() {

        List<Donation> filteredList =
                new ArrayList<>();

        for (Donation donation : donationList) {

            boolean categoryMatch =
                    selectedCategory.equals("전체")
                            || donation.getCategory()
                            .contains(selectedCategory);

            boolean regionMatch =

                    selectedRegion.equals("전체")

                            ||

                            donation.getAddress()
                                    .contains(selectedRegion);

            String keyword =
                    currentKeyword.toLowerCase();

            boolean searchMatch =

                    donation.getName()
                            .toLowerCase()
                            .contains(keyword)

                            ||

                            donation.getCategory()
                                    .toLowerCase()
                                    .contains(keyword)

                            ||

                            donation.getAddress()
                                    .toLowerCase()
                                    .contains(keyword);

            if(categoryMatch
                    && regionMatch
                    && searchMatch) {

                filteredList.add(donation);
            }
        }

        adapter = new DonationAdapter(filteredList);
        recyclerView.setAdapter(adapter);
    }

}