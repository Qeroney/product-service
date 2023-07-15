package com.example.demo.controller.cart;

import com.example.demo.action.addToCart.AddProductAction;
import com.example.demo.controller.cart.dto.CartDto;
import com.example.demo.model.Cart;
import com.example.demo.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.demo.controller.cart.mapper.CartMapper.CART_MAPPER;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final AddProductAction AddProduct;

    @GetMapping("list")
    public List<CartDto> list(){
        return cartService.list()
                .stream()
                .map(CART_MAPPER::toDto)
                .collect(Collectors.toList());
    }
    @PostMapping("add/{productId}")
    public CartDto addProductToCart(@PathVariable UUID productId){
        Cart cart = AddProduct.execute(CART_MAPPER.toAddProductToCart(productId));

        return CART_MAPPER.toDto(cart);
    }
}