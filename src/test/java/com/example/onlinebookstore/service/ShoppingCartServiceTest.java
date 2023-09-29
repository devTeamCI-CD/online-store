package com.example.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.onlinebookstore.config.SqlFilesPaths;
import com.example.onlinebookstore.dto.shoppingcart.CartItemDto;
import com.example.onlinebookstore.dto.shoppingcart.CartItemUpdateRequestDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.CartItemMapper;
import com.example.onlinebookstore.mapper.ShoppingCartMapper;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.cart.CartItemRepository;
import com.example.onlinebookstore.repository.cart.ShoppingCartRepository;
import com.example.onlinebookstore.service.impl.ShoppingCartServiceImpl;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {
    public static final CartItem UPDATE_REQUIRES = new CartItem();
    public static final CartItemDto VALID_UPDATE = new CartItemDto();
    private static final User DEFAULT_USER = new User();
    private static final ShoppingCart VALID_SHOPPING_CART = new ShoppingCart();
    private static final ShoppingCartDto VALID_DTO_RESPONSE = new ShoppingCartDto();
    private static final CartItemUpdateRequestDto UPDATE_REQUEST_DTO
            = new CartItemUpdateRequestDto();
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private UserService userService;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;
    @Mock
    private CartItemMapper cartItemMapper;

    @BeforeEach
    void setUp() {
        DEFAULT_USER.setId(2L);
        DEFAULT_USER.setEmail("email@i.ua");
        DEFAULT_USER.setPassword("password");
        DEFAULT_USER.setFirstName("Arsen");
        DEFAULT_USER.setLastName("Mehdalskyi");

        VALID_SHOPPING_CART.setId(1L);
        VALID_SHOPPING_CART.setUser(DEFAULT_USER);

        VALID_DTO_RESPONSE.setId(VALID_SHOPPING_CART.getId());
        VALID_DTO_RESPONSE.setUserId(VALID_SHOPPING_CART.getUser().getId());
        VALID_DTO_RESPONSE.setCartItems(new HashSet<>());

        UPDATE_REQUEST_DTO.setQuantity(56);

        VALID_UPDATE.setBookId(1L);
        VALID_UPDATE.setQuantity(56);
        VALID_UPDATE.setId(1L);
        VALID_UPDATE.setBookTitle("Title");

        UPDATE_REQUIRES.setId(1L);
        UPDATE_REQUIRES.setShoppingCart(VALID_SHOPPING_CART);
        UPDATE_REQUIRES.setBook(new Book(1L));
        UPDATE_REQUIRES.setQuantity(4);
    }

    @Test
    @Sql(scripts = SqlFilesPaths.CART_INSERT,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Verify get shopping cart method")
    void getShoppingCart_includingPagination_returnShoppingCart() {
        when(userService.getAuthenticatedUser()).thenReturn(DEFAULT_USER);
        when(shoppingCartRepository.findByUserId(anyLong()))
                .thenReturn(Optional.of(VALID_SHOPPING_CART));
        when(shoppingCartMapper.toDto(VALID_SHOPPING_CART)).thenReturn(VALID_DTO_RESPONSE);

        ShoppingCartDto actual = shoppingCartService.getShoppingCart();
        assertNotNull(actual);
        assertEquals(VALID_DTO_RESPONSE, actual);

        verify(userService, times(1)).getAuthenticatedUser();
        verify(shoppingCartRepository, times(1)).findByUserId(anyLong());
    }

    @Test
    @DisplayName("Verify get shopping cart method")
    void updateCartItem_noCartYet_throwsException() {
        when(userService.getAuthenticatedUser()).thenReturn(DEFAULT_USER);
        EntityNotFoundException actual = assertThrows(EntityNotFoundException.class,
                () -> shoppingCartService.updateCartItem(1L, UPDATE_REQUEST_DTO));

        String expectedMessage = "There is no cart belonging to user with id: 2";
        assertEquals(expectedMessage, actual.getMessage());

        verify(userService, times(2)).getAuthenticatedUser();
        verify(shoppingCartRepository).findByUserId(anyLong());
        verifyNoMoreInteractions(userService);
    }

    @Test
    @Sql(scripts = SqlFilesPaths.CART_INSERT,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Verify get shopping cart method")
    void updateCartItem_invalidCartId_throwsException() {
        when(userService.getAuthenticatedUser()).thenReturn(DEFAULT_USER);
        when(shoppingCartRepository.findByUserId(anyLong()))
                .thenReturn(Optional.of(VALID_SHOPPING_CART));
        EntityNotFoundException actual = assertThrows(EntityNotFoundException.class,
                () -> shoppingCartService.updateCartItem(-1L, UPDATE_REQUEST_DTO));

        String expectedMessage = "Can't find cart item with id: -1";
        assertEquals(expectedMessage, actual.getMessage());

        verify(userService).getAuthenticatedUser();
        verify(shoppingCartRepository).findByUserId(anyLong());
        verifyNoMoreInteractions(userService);
    }

    @Test
    @Sql(scripts = SqlFilesPaths.CART_INSERT,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Verify get shopping cart method")
    void updateCartItem_valid_returnUpdatedCartItem() {
        when(userService.getAuthenticatedUser()).thenReturn(DEFAULT_USER);
        when(shoppingCartRepository.findByUserId(anyLong()))
                .thenReturn(Optional.of(VALID_SHOPPING_CART));
        when(cartItemRepository.findByIdAndShoppingcartId(anyLong(), anyLong()))
                .thenReturn(Optional.of(UPDATE_REQUIRES));
        when(cartItemMapper.toDto(any())).thenReturn(VALID_UPDATE);

        assertEquals(VALID_UPDATE, shoppingCartService.updateCartItem(1L, UPDATE_REQUEST_DTO));
        verify(userService).getAuthenticatedUser();
        verify(shoppingCartRepository).findByUserId(anyLong());
        verify(cartItemRepository).findByIdAndShoppingcartId(anyLong(), anyLong());
        verifyNoMoreInteractions(userService);
    }
}
