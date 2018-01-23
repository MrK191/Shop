package com.shop.controllers;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.shop.model.Basket;
import com.shop.model.User;
import com.shop.service.BasketService;
import com.shop.service.PaypalService;
import com.shop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${paypal.client.app}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;
    @Autowired
    private PaypalService paypalService;
    @Autowired
    private BasketService basketService;
    @Autowired
    private UserService userService;

    private Logger log = LoggerFactory.getLogger(getClass());
    private final String paypalSuccessUrl = "/successfull-payment";
    private final String paypalCancelUrl = "/cancel-payment";
    APIContext context;

    @PostMapping(value = "/pay", consumes = "application/json")
    public void pay(HttpServletResponse response, @RequestBody Basket basket) {
        APIContext context = new APIContext(clientId, clientSecret, mode);
        this.context = context;

        try {
            Payment payment = paypalService.createPayment(context,
                paypalSuccessUrl,
                paypalCancelUrl,
                "PLN",
                String.valueOf(basket.getTotalPrice()),
                "payment");

            log.info(payment.getId());
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    response.sendRedirect(links.getHref());
                }
            }
        } catch (PayPalRESTException | IOException e) {
            log.error(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = paypalCancelUrl)
    public ResponseEntity<?> cancelPay() {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.GET, value = paypalSuccessUrl)
    public ResponseEntity successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {

        try {
            Payment createdPayment = paypalService.executePayment(paymentId, payerId, context);
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        User currentUser = userService.getCurrentLoggedInUser();
        basketService.deactivateCurrentBasket(currentUser.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

}
