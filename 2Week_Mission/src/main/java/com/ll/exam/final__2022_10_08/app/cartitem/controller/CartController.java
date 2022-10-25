package com.ll.exam.final__2022_10_08.app.cartitem.controller;

import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.cartitem.entity.CartItem;
import com.ll.exam.final__2022_10_08.app.cartitem.service.CartItemService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.product.entity.Product;
import com.ll.exam.final__2022_10_08.app.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartController {
    private final Rq rq;
    private final CartItemService cartItemService;

    private final ProductService productService;
    @GetMapping("/list")
    public String getList(Model model){
        Member member = rq.getMember();
        List<CartItem> cartItems = cartItemService.getItemsByBuyer(member);
        model.addAttribute("cartItems",cartItems);
        return "/cart/list";
    }

    @Transactional
    @PostMapping("/remove/{cartItemId}")
    public String removeItem(@PathVariable long cartItemId){
        Member member = rq.getMember();
        CartItem cartItem = cartItemService.findByMemberAndCartItem(cartItemId, member.getId()).orElse(null);
        cartItemService.removeItem(cartItem);
        return "redirect:/cart/list";
    }

    @Transactional
    @PostMapping("/removeItems")
    public String removeItems(String ids){
        Member member = rq.getMember();
        String[] idsBits = ids.split(",");
        for (String idsBit : idsBits){
            CartItem cartItem = cartItemService.findByMemberAndCartItem(Integer.parseInt(idsBit), member.getId()).orElse(null);
            cartItemService.removeItem(cartItem);
        }
        return "redirect:/cart/list";
    }

    @Transactional
    @GetMapping("/add/{productId}")
    public String addCartItem(@PathVariable long productId){
        Member member = rq.getMember();
        Product product = productService.findById(productId).orElse(null);
        cartItemService.addItem(member,product);
        return "redirect:/product/list";
    }
}
