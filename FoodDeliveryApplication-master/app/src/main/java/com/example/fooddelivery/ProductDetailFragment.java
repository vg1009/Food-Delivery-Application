package com.example.fooddelivery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddelivery.databinding.FragmentProductDetailBinding;
import com.example.fooddelivery.viewmodels.ShoppingViewModel;


public class ProductDetailFragment extends Fragment {


   FragmentProductDetailBinding fragmentProductDetailBinding;
   ShoppingViewModel shoppingViewModel;

    public ProductDetailFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       fragmentProductDetailBinding=FragmentProductDetailBinding.inflate(inflater,container,false);
       return fragmentProductDetailBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoppingViewModel =new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);
        fragmentProductDetailBinding.setShoppingViewModel(shoppingViewModel);
    }
}
