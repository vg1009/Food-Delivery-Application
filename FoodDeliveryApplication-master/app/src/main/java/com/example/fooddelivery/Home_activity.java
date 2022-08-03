package com.example.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.fooddelivery.model.CartItem;
import com.example.fooddelivery.viewmodels.ShoppingViewModel;

import java.util.List;

public class Home_activity extends AppCompatActivity {
   NavController navController;
   ShoppingViewModel shoppingViewModel;
    private static final String TAG = "Home_activity";
    private int cartQuantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        shoppingViewModel=new ViewModelProvider(this).get(ShoppingViewModel.class);
        shoppingViewModel.getCart().observe(this, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                int quantity=0;
                for(CartItem cartItem: cartItems)
                {
                    quantity+=cartItem.getQuantity();
                }
                cartQuantity=quantity;
                invalidateOptionsMenu();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // you have to do this from yours truly yt vdo no 5 and 6
        navController= Navigation.findNavController(this,R.id.nav_host_fragment);
       NavigationUI.setupActionBarWithNavController(this,navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        final MenuItem menuItem=menu.findItem(R.id.cartFragment);
        View actionView=menuItem.getActionView();

        TextView cartBadgeTextView=actionView.findViewById(R.id.cart_badge_textView);
        cartBadgeTextView.setText(String.valueOf(cartQuantity));
        cartBadgeTextView.setVisibility(cartQuantity==0 ? View.GONE: View.VISIBLE);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item,navController) || super.onOptionsItemSelected(item);
    }
}
