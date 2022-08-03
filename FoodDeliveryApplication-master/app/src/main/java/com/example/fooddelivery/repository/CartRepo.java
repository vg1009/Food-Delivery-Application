package com.example.fooddelivery.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fooddelivery.model.CartItem;
import com.example.fooddelivery.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartRepo {

    private MutableLiveData<List<CartItem>> mutablecart=new MutableLiveData<>();
    private MutableLiveData<Double> mutableTotalPrice=new MutableLiveData<>();

    public LiveData<List<CartItem>> getCart()
    {
        if(mutablecart.getValue()==null){
            initCart();
        }
        return mutablecart;
    }

    public void initCart()
    {
        mutablecart.setValue(new ArrayList<CartItem>());
        calculateCartTotal();

    }

    public boolean addItemtoCart(Product product)
    {
        if(mutablecart.getValue()==null){
            initCart();
        }

        List<CartItem> cartItemList=new ArrayList<>(mutablecart.getValue());
        for(CartItem cartItem: cartItemList)
        {
            if(cartItem.getProduct().getId().equals(product.getId()))
            {
                if(cartItem.getQuantity()==5)
                {
                    return false;
                }
                int index=cartItemList.indexOf(cartItem);
                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartItemList.set(index,cartItem);

                mutablecart.setValue(cartItemList);
                calculateCartTotal();
                return true;
            }
        }

        CartItem cartItem=new CartItem(product,1);
        cartItemList.add(cartItem);
        mutablecart.setValue(cartItemList);
        calculateCartTotal();
        return true;

    }
    public void removeItemFromCart(CartItem cartItem)
    {
        if(mutablecart.getValue()==null)
        {
            return;
        }
        List<CartItem> cartItemList=new ArrayList<>(mutablecart.getValue());
        cartItemList.remove(cartItem);
        mutablecart.setValue(cartItemList);
        calculateCartTotal();
    }
    public  void changedQuantity(CartItem cartItem,int quantity)
    {
        if(mutablecart.getValue()==null) return;

        List<CartItem> cartItemList=new ArrayList<>(mutablecart.getValue());
        CartItem updatedItem=new CartItem(cartItem.getProduct(),quantity);
        cartItemList.set(cartItemList.indexOf(cartItem),updatedItem);
        mutablecart.setValue(cartItemList);
        calculateCartTotal();
    }

    private void calculateCartTotal(){
        if(mutablecart.getValue()==null) return;
        double total=0.0;
        List<CartItem> cartItemList=mutablecart.getValue();
        for(CartItem cartItem: cartItemList)
        {
            total+=cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        mutableTotalPrice.setValue(total);
    }

    public LiveData<Double> getTotalPrice(){
        if(mutableTotalPrice.getValue()==null)
        {
            mutableTotalPrice.setValue(0.0);
        }
        return mutableTotalPrice;
    }

}


