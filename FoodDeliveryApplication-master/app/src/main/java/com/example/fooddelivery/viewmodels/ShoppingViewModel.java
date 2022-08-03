package com.example.fooddelivery.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fooddelivery.model.CartItem;
import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.repository.CartRepo;
import com.example.fooddelivery.repository.ShopRepo;

import java.util.List;

public class ShoppingViewModel extends ViewModel {

    ShopRepo shoprepo=new ShopRepo();
    CartRepo cartrepo=new CartRepo();

    MutableLiveData<Product> mutableProduct=new MutableLiveData<>();
    public LiveData<List<Product>> getProducts(){
       return shoprepo.getProducts();
    }

    public void setProduct(Product product){

        mutableProduct.setValue(product);
    }
    public LiveData<Product> getProduct()
    {
        return mutableProduct;
    }

    public LiveData<List<CartItem>> getCart()
    {
        return cartrepo.getCart();
    }

    public boolean addItemtoCart(Product product)
    {
        return cartrepo.addItemtoCart(product);

    }

    public void removeItemFromCart(CartItem cartItem)
    {
        cartrepo.removeItemFromCart(cartItem);
    }

    public void changedQuantity(CartItem cartItem,int quantity)
    {
        cartrepo.changedQuantity(cartItem,quantity);
    }

    public LiveData<Double> getTotalPrice()
    {
        return cartrepo.getTotalPrice();
    }

    public void resetCart()
    {
        cartrepo.initCart();
    }
}
