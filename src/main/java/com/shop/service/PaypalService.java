package com.shop.service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PaypalService {

    public Payment createPayment(APIContext context,
                                 String successfulPaymentUrl,
                                 String cancelPaymentUrl,
                                 String Currency,
                                 String totalPaymentValue,
                                 String paymentDescription) throws PayPalRESTException {


        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/payment" + cancelPaymentUrl);
        redirectUrls.setReturnUrl("http://localhost:8080/payment" + successfulPaymentUrl);

        Amount amount = new Amount();
        amount.setCurrency(Currency);
        amount.setTotal(totalPaymentValue);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction
            .setDescription(paymentDescription);

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setRedirectUrls(redirectUrls);
        payment.setTransactions(transactions);

        return payment.create(context);
    }

    public Payment executePayment(String paymentId, String payerId, APIContext context) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(context, paymentExecute);
    }

}
