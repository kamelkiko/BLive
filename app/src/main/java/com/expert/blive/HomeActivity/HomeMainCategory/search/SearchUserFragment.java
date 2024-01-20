package com.expert.blive.HomeActivity.HomeMainCategory.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.ExtraFragments.MessagesFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Popular.PopularMainFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.SearchUserAdapter;
import com.expert.blive.ModelClass.searchUser.SearchUserDetail;
import com.expert.blive.ModelClass.searchUser.SearchUserRoot;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchUserFragment extends Fragment implements SearchUserAdapter.callBackFromSearchUserAdapter {

    private RecyclerView search_recycler;
    private SearchUserAdapter searchUserAdapter;
    private MvvmViewModelClass mvvmViewModelClass;
    private EditText search;
    ImageView back;
    private List<SearchUserDetail> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mvvmViewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_search_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg_gradient);



        findIds(view);
       onclick(view);
        searchUserAdapter = new SearchUserAdapter(SearchUserFragment.this, new ArrayList<>(), getContext());

        search_recycler.setLayoutManager(new GridLayoutManager(getContext(), 1));

        search_recycler.setAdapter(searchUserAdapter);

        getUsersList();
        searchData();
    }

    private void onclick(View view) {
          back.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new PopularMainFragment()).addToBackStack(null).commit());

    }

    private void getUsersList() {

        mvvmViewModelClass.searchUserRootLiveData(requireActivity(),"", CommonUtils.getUserId()).observe(requireActivity(), searchUserRoot -> {

            if (searchUserRoot != null) {

                list = searchUserRoot.getDetails();

                searchUserAdapter.loadData(list);

            }
        });

        }

        private void findIds (View view){

            search_recycler = view.findViewById(R.id.search_recycler);

            search = view.findViewById(R.id.search);
            back = view.findViewById(R.id.back);

            ImageView back = view.findViewById(R.id.back);
            back.setOnClickListener(view1 -> requireActivity().onBackPressed());

        }

        @Override
        public void submit (SearchUserDetail userDetail){

        OtherUserProfileFragment.searchUserDetail = userDetail;
        MessagesFragment.other_userId = userDetail.getId();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new OtherUserProfileFragment()).addToBackStack(null).commit();

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
            List<SearchUserDetail> filteredList = new ArrayList<>();
            for (SearchUserDetail item : list) {
                if (item.getUsername().toLowerCase().contains(text.toLowerCase()) || item.getUsername().toUpperCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            searchUserAdapter.filterList(filteredList);
            searchUserAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.INVISIBLE);
    }
}