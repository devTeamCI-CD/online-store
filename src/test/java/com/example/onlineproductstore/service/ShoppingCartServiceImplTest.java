package com.example.onlineproductstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.onlineproductstore.dto.shoppingcart.CreateCartItemRequestDto;
import com.example.onlineproductstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlineproductstore.mapper.CartItemMapper;
import com.example.onlineproductstore.mapper.ShoppingCartMapper;
import com.example.onlineproductstore.model.CartItem;
import com.example.onlineproductstore.model.Product;
import com.example.onlineproductstore.model.ShoppingCart;
import com.example.onlineproductstore.model.User;
import com.example.onlineproductstore.repository.cart.CartItemRepository;
import com.example.onlineproductstore.repository.cart.ShoppingCartRepository;
import com.example.onlineproductstore.repository.product.ProductRepository;
import com.example.onlineproductstore.service.impl.ShoppingCartServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ShoppingCartServiceImplTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private CartItemMapper cartItemMapper;

    @Mock
    private UserService userService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetShoppingCart() {
        // Given
        User user = new User();
        user.setId(1L);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(1L);

        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(shoppingCartRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(shoppingCart));
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(shoppingCartDto);

        // When
        ShoppingCartDto result = shoppingCartService.getShoppingCart();

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testAddToCart() {
        // Given
        CreateCartItemRequestDto requestDto = new CreateCartItemRequestDto();
        requestDto.setProductId(1L);
        requestDto.setQuantity(2);
        User user = new User();
        user.setId(1L);
        Product product = new Product();
        product.setId(1L);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setProduct(product);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(2);
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(1L);

        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(shoppingCartRepository.findByUserId(user.getId()))
                .thenReturn(Optional.of(shoppingCart));
        when(cartItemRepository.save(any())).thenReturn(cartItem);
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(shoppingCartDto);

        // When
        ShoppingCartDto result = shoppingCartService.addToCart(requestDto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
