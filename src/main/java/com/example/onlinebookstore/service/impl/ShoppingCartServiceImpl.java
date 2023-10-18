package com.example.onlinebookstore.service.impl;

import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.CartItemUpdateRequestDto;
import com.example.onlinebookstore.dto.shoppingcart.CreateCartItemRequestDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.CartItemMapper;
import com.example.onlinebookstore.mapper.ShoppingCartMapper;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.book.BookRepository;
import com.example.onlinebookstore.repository.cart.CartItemRepository;
import com.example.onlinebookstore.repository.cart.ShoppingCartRepository;
import com.example.onlinebookstore.service.ShoppingCartService;
import com.example.onlinebookstore.service.UserService;
import java.util.Optional;
import java.util.Set;

import jakarta.transaction.Transactional;
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
    private final BookRepository bookRepository;

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
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book with id: " + request.getBookId() + " not found"));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewShoppingCart(user));

        CartItem cartItem = createCartItem(book, shoppingCart, request.getQuantity());
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
        CartItem cartItem = cartItemRepository.findByIdAndShoppingcartId(
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

    private CartItem createCartItem(Book book, ShoppingCart shoppingCart, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(quantity);
        return cartItem;
    }
}
