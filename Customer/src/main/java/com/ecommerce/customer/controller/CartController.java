package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Cart;
import com.ecommerce.library.entity.Customer;
import com.ecommerce.library.entity.Product;
import com.ecommerce.library.service.CartService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/api")
public class CartController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session){
        if(principal == null){
            return "redirect:/api/login";
        }
        String username = principal.getName();
        CustomerDTO customer = customerService.findByUsername(username);
        Cart shoppingCart = customer.getCart();
        if(shoppingCart == null){
            model.addAttribute("check", "No item in your cart");
            return "cart";
        }
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("subTotal", shoppingCart.getTotalPrices());
        model.addAttribute("shoppingCart", shoppingCart);
        return "cart";
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(
            @RequestParam("id") Long productId,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Principal principal,
            HttpServletRequest request){

        if(principal == null){
            return "redirect:/api/login";
        }
        ProductDTO product = productService.findById(productId);
        String username = principal.getName();
        CustomerDTO customer = customerService.findByUsername(username);

        Cart cart = cartService.addItemToCart(product, quantity, customer);
        return "redirect:" + request.getHeader("Referer");

    }


    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long productId,
                             Model model,
                             Principal principal
    ){

        if(principal == null){
            return "redirect:/api/login";
        }else{
            String username = principal.getName();
            CustomerDTO customer = customerService.findByUsername(username);
            ProductDTO product = productService.findById(productId);
            Cart cart = cartService.updateItemInCart(product, quantity, customer);

            model.addAttribute("shoppingCart", cart);
            return "redirect:/api/cart";
        }

    }


    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItemFromCart(@RequestParam("id") Long productId,
                                     Model model,
                                     Principal principal){
        if(principal == null){
            return "redirect:/api/login";
        }else{
            String username = principal.getName();
            CustomerDTO customer = customerService.findByUsername(username);
            ProductDTO product = productService.findById(productId);
            Cart cart = cartService.deleteItemFromCart(product, customer);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/api/cart";
        }

    }
}