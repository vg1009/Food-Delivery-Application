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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddelivery.adapter.CartListAdapter;
import com.example.fooddelivery.databinding.FragmentCartBinding;
import com.example.fooddelivery.model.CartItem;
import com.example.fooddelivery.viewmodels.ShoppingViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements CartListAdapter.CartInterface {


    NavController navController;
    private static final String TAG = "CartFragment";
    ShoppingViewModel shoppingViewModel;
    FragmentCartBinding fragmentCartBinding;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCartBinding=FragmentCartBinding.inflate(inflater,container,false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController= Navigation.findNavController(view);
        final CartListAdapter cartListAdapter=new CartListAdapter(this);
        fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
        fragmentCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL));

        shoppingViewModel=new ViewModelProvider(requireActivity()).get(ShoppingViewModel.class);
        shoppingViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {

                cartListAdapter.submitList(cartItems);
                fragmentCartBinding.button3.setEnabled(cartItems.size() >0);
            }
        });



        shoppingViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                 fragmentCartBinding.textView8.setText("Total : ₹"+aDouble.toString());
            }
        });
        fragmentCartBinding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.action_cartFragment_to_orderFragment);
            }
        });
    }

    @Override
    public void deleteItem(CartItem cartItem) {

           shoppingViewModel.removeItemFromCart(cartItem);
    }

    @Override
    public void changedQuantity(CartItem cartItem, int quantity) {

        shoppingViewModel.changedQuantity(cartItem,quantity);
    }
}
