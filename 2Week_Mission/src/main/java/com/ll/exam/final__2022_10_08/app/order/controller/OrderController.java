package com.ll.exam.final__2022_10_08.app.order.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.exam.final__2022_10_08.app.base.exception.OrderNotEnoughRestCashException;
import com.ll.exam.final__2022_10_08.app.base.rq.Rq;
import com.ll.exam.final__2022_10_08.app.cartitem.service.CartItemService;
import com.ll.exam.final__2022_10_08.app.member.entity.Member;
import com.ll.exam.final__2022_10_08.app.member.service.MemberService;
import com.ll.exam.final__2022_10_08.app.order.entity.Order;
import com.ll.exam.final__2022_10_08.app.order.service.OrderService;
import com.ll.exam.final__2022_10_08.app.security.dto.MemberContext;
import com.ll.exam.final__2022_10_08.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final RestTemplate restTemplate;
    private final OrderService orderService;

    private final ObjectMapper objectMapper;
    private final MemberService memberService;
    private final Rq rq;

    private final String SECRET_KEY = "test_sk_dP9BRQmyarYaNnnkNDXrJ07KzLNk";
    @GetMapping("/list")
    public String showList(Model model){
        Member member = rq.getMember();
        Order order = orderService.getOrder(member);
        long restCash = member.getRestCash();
        model.addAttribute("actorRestCash", restCash);
        model.addAttribute("order",order);
        return "/order/list";
    }

    @PostMapping("/create")
    public String create(String ids){

        Member member = rq.getMember();
        orderService.createFromSelectCart(member,ids);
        return "redirect:/order/list";
    }

    @PostMapping("/makeOrder")
    public String makeOrder(){
        Member member = rq.getMember();
        orderService.createFromCart(member);
        return "redirect:/order/list";
    }


    @PostConstruct
    private void init() {
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {
            }
        });
    }

    @PostMapping("/{orderId}/payByRestCashOnly")
    public String payByRestCashOnly(@PathVariable long orderId){
        Member actor = rq.getMember();
        Order order = orderService.findForPrintById(orderId).get();
        orderService.payByRestCashOnly(order);
        long restCash = actor.getRestCash();
        actor.setRestCash(restCash - order.calculatePayPrice());
        rq.modifyMemberContext(actor);
        return "redirect:/order/list?msg=%s".formatted(Ut.url.encode("????????? ?????????????????????."));
    }


    @RequestMapping("/{id}/success")
    public String confirmPayment(
            @PathVariable long id,
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Long amount,
            Model model
    ) throws Exception {

        Order order = orderService.findForPrintById(id).get();

        long orderIdInputed = Long.parseLong(orderId.split("__")[1]);

        // ????????? ????????? ?????? ????????? ???????????? ??????(??????)??? ????????? ???????????? ??????
        if ( id != orderIdInputed ) {
            throw new OrderNotEnoughRestCashException();
        }
        HttpHeaders headers = new HttpHeaders();
        // headers.setBasicAuth(SECRET_KEY, ""); // spring framework 5.2 ?????? ???????????? ??????
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(amount));

        // ??? ????????? ?????????????????? + ??????????????? ?????? ?????? ?????? check ?????? ????????????.
        Member actor = rq.getMember();
        // ????????? ?????? ???(?????????)??? ?????????
        long restCash = actor.getRestCash();
        // ????????? ?????????.
        // order.calculatePayPrice() ??? ?????? ??????
        // amount ????????? ?????? (??????????????? ????????? ??????)
        long payPriceRestCash = order.calculatePayPrice() - amount;

        // ????????? ??????????????? ????????? ?????? ???????????? ?????? ??????
        if (payPriceRestCash > restCash) {
            throw new OrderNotEnoughRestCashException();
        }

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            orderService.payByTossPayments(order, payPriceRestCash);
            // ?????? ?????? ??????
            actor.setRestCash(restCash - payPriceRestCash);
            rq.modifyMemberContext(actor);
            return "redirect:/order/list?msg=%s".formatted(Ut.url.encode("????????? ?????????????????????."));
        } else {
            JsonNode failNode = responseEntity.getBody();
            model.addAttribute("message", failNode.get("message").asText());
            model.addAttribute("code", failNode.get("code").asText());
            return "redirect:/order/list?msg=%s".formatted(Ut.url.encode("????????? ?????? ???????????????."));
        }
    }
}
