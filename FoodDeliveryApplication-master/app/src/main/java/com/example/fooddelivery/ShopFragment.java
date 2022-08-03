package com.example.fooddelivery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddelivery.adapter.ShopListAdapter;
import com.example.fooddelivery.databinding.FragmentShopBinding;
import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.viewmodels.ShoppingViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment implements ShopListAdapter.ShopInterface {

    private static final String TAG = "ShopFragment";

     FragmentShopBinding fragmentshopbinding;
    private ShopListAdapter shoplistAdapter ;
    private ShoppingViewModel shoppingViewModel;
    private NavController navController;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       fragmentshopbinding= FragmentShopBinding.inflate(inflater,container,false);
       return fragmentshopbinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoplistAdapter= new ShopListAdapter(this);
        fragmentshopbinding.shoprecyclerview.setAdapter(shoplistAdapter);
        fragmentshopbinding.shoprecyclerview.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));
        fragmentshopbinding.shoprecyclerview.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.HORIZONTAL));


        shoppingViewModel =new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);
        shoppingViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                shoplistAdapter.submitList(products);
            }
        });

        navController= Navigation.findNavController(view);
    }

    @Override
    public void addItem(Product product) {
        //Log.d(TAG, "addItem: "+product.toString());
       boolean isadded= shoppingViewModel.addItemtoCart(product);
        if(isadded)
        {
            Snackbar.make(requireView(),product.getName()+" Added to cart",Snackbar.LENGTH_LONG)
                    .setAction("Checkout", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navController.navigate(R.id.action_shopFragment_to_cartFragment);
                        }
                    })
            .show();
        }else{
            Snackbar.make(requireView()," Alredy have the max quantity in the cart",Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onItemClick(Product product) {

         //Log.d(TAG, "onItemClick: " + product.toString());
         shoppingViewModel.setProduct(product);
         navController.navigate(R.id.action_shopFragment_to_productDetailFragment);

    }


}
