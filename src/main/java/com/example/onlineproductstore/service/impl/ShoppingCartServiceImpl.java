package com.example.onlineproductstore.service.impl;

import com.example.onlineproductstore.dto.shoppingcart.CartItemDto;
import com.example.onlineproductstore.dto.shoppingcart.CartItemUpdateRequestDto;
import com.example.onlineproductstore.dto.shoppingcart.CreateCartItemRequestDto;
import com.example.onlineproductstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlineproductstore.exception.EntityNotFoundException;
import com.example.onlineproductstore.mapper.CartItemMapper;
import com.example.onlineproductstore.mapper.ShoppingCartMapper;
import com.example.onlineproductstore.model.CartItem;
import com.example.onlineproductstore.model.Product;
import com.example.onlineproductstore.model.ShoppingCart;
import com.example.onlineproductstore.model.User;
import com.example.onlineproductstore.repository.cart.CartItemRepository;
import com.example.onlineproductstore.repository.cart.ShoppingCartRepository;
import com.example.onlineproductstore.repository.product.ProductRepository;
import com.example.onlineproductstore.service.ShoppingCartService;
import com.example.onlineproductstore.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final UserService userService;
    private final ProductRepository productRepository;

    @Override
    public ShoppingCartDto getShoppingCart() {
        User user = userService.getAuthenticatedUser();
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewShoppingCart(user)));
    }

    @Override
    @Transactional
    public ShoppingCartDto addToCart(CreateCartItemRequestDto request) {
        User user = userService.getAuthenticatedUser();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Product with id: " + request.getProductId() + " not found"));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewShoppingCart(user));

        CartItem cartItem = createCartItem(product, shoppingCart, request.getQuantity());
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public CartItemDto updateCartItem(Long cartItemId, CartItemUpdateRequestDto request) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(
                userService.getAuthenticatedUser().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                                "There is no cart belonging to user with id: "
                                        + userService.getAuthenticatedUser().getId()));
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(
                cartItemId, shoppingCart.getId()).orElseThrow(() -> new EntityNotFoundException(
                        "Can't find cart item with id: " + cartItemId));
        cartItem.setQuantity(request.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public Optional<ShoppingCart> findCartByCurrentUser(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    @Override
    public void cleanUp(ShoppingCart shoppingCart) {
        cartItemRepository.deleteAll(shoppingCart.getCartItems());
        shoppingCart.getCartItems().clear();
    }

    private ShoppingCart createNewShoppingCart(User user) {
        ShoppingCart newShoppingCart = new ShoppingCart();
        newShoppingCart.setUser(user);
        return shoppingCartRepository.save(newShoppingCart);
    }

    private CartItem createCartItem(Product product, ShoppingCart shoppingCart, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(quantity);
        return cartItem;
    }
}
