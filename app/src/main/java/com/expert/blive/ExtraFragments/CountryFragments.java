package com.expert.blive.ExtraFragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.CountryAdapter;
import com.expert.blive.ModelClass.DetailCountry;
import com.expert.blive.R;
import com.google.android.exoplayer2.util.Log;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class CountryFragments extends BottomSheetDialogFragment   {

    private MvvmViewModelClass viewModelClass;
    private  View view;
    private RecyclerView recyclerView;
    private CountryAdapter.GetData getData;
    private EditText search;
    CountryAdapter countryAdapter;
    private List<DetailCountry.Details> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        view = inflater.inflate(R.layout.fragment_country_fragments, container, false);


        return  view;
    }

    public CountryFragments(CountryAdapter.GetData getData) {
        this.getData = getData;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView= view.findViewById(R.id.countryList);
        search= view.findViewById(R.id.search);

        getallCountries();
        searchData();
        view.findViewById(R.id.msgBackImg).setOnClickListener(view1 -> dismiss());




    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            setupRatio(bottomSheetDialog);
        });
        return dialog;
    }
    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = displaySize(requireActivity())[1] * 100 / 100;
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private static int[] displaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Display) display).getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.i("height", String.valueOf(height));
        Log.i(  "width", String.valueOf(width));
        return new int[]{width, height};
    }
    private void getallCountries() {





    viewModelClass.getAllCountries(requireActivity()).observe(requireActivity(), detailCountry -> {
        if (detailCountry.getStatus().equalsIgnoreCase("1")){

            list = detailCountry.getDetails();
            setAdatpter(list);
        }
    });


    }

    private void setAdatpter(List<DetailCountry.Details> details) {

         countryAdapter = new CountryAdapter(details, s -> {
             getData.getData(s);
             dismiss();
         });
        recyclerView.setAdapter(countryAdapter);


    }

    private void searchData () {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search.requestFocus();
                search.setSelection(s.toString().length());
                filter(s.toString());

            }
        });
    }
    private void filter (String text){
        List<DetailCountry.Details> filteredList = new ArrayList<>();
        for (DetailCountry.Details item : list) {
            if (item.getCountry().toLowerCase().contains(text.toLowerCase()) || item.getCountry().toUpperCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
         countryAdapter.filterList(filteredList);
        countryAdapter.notifyDataSetChanged();

    }
}