package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.wallet.*
import org.json.JSONArray
import org.json.JSONObject

class CheckOutActivity : Activity() {

    private lateinit var paymentsClient: PaymentsClient
    private val LOAD_PAYMENT_DATA_REQUEST_CODE = 991

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        // Initialize Google Pay client
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
            .build()

        paymentsClient = Wallet.getPaymentsClient(this, walletOptions)

        possiblyShowGooglePayButton()

        val addEwalletButton = findViewById<Button>(R.id.btnCheckout)
        addEwalletButton.setOnClickListener {
            requestPayment()
        }
    }

    private fun possiblyShowGooglePayButton() {
        val baseCardPaymentMethod = JSONObject().apply {
            put("type", "CARD")
            put("parameters", JSONObject().apply {
                put("allowedAuthMethods", JSONArray().put("PAN_ONLY").put("CRYPTOGRAM_3DS"))
                put("allowedCardNetworks", JSONArray().put("AMEX").put("DISCOVER").put("JCB").put("MASTERCARD").put("VISA"))
            })
        }

        val isReadyToPayRequestJson = JSONObject().apply {
            put("apiVersion", 2)
            put("apiVersionMinor", 0)
            put("allowedPaymentMethods", JSONArray().put(baseCardPaymentMethod))
        }

        val request = IsReadyToPayRequest.fromJson(isReadyToPayRequestJson.toString())

        paymentsClient.isReadyToPay(request).addOnCompleteListener { task ->
            if (task.isSuccessful && task.result == true) {
                findViewById<Button>(R.id.addEwalletButton).isEnabled = true
            } else {
                Toast.makeText(this, "Google Pay not available", Toast.LENGTH_SHORT).show()
                findViewById<Button>(R.id.addEwalletButton).isEnabled = false
            }
        }
    }


    private fun requestPayment() {
        val allowedPaymentMethods = JSONArray().put(
            JSONObject().apply {
                put("type", "CARD")
                put("parameters", JSONObject().apply {
                    put("allowedAuthMethods", JSONArray().put("PAN_ONLY").put("CRYPTOGRAM_3DS"))
                    put("allowedCardNetworks", JSONArray().put("AMEX").put("DISCOVER").put("JCB").put("MASTERCARD").put("VISA"))
                    put("billingAddressRequired", true)
                    put("billingAddressParameters", JSONObject().apply {
                        put("format", "FULL")
                    })
                })
                put("tokenizationSpecification", JSONObject().apply {
                    put("type", "PAYMENT_GATEWAY")
                    put("parameters", JSONObject().apply {
                        put("gateway", "example")
                        put("gatewayMerchantId", "exampleGatewayMerchantId")
                    })
                })
            }
        )

        val paymentDataRequestJson = JSONObject().apply {
            put("apiVersion", 2)
            put("apiVersionMinor", 0)
            put("allowedPaymentMethods", allowedPaymentMethods)
            put("transactionInfo", JSONObject().apply {
                put("totalPrice", "10.00")
                put("totalPriceStatus", "FINAL")
                put("currencyCode", "USD")
            })
            put("merchantInfo", JSONObject().apply {
                put("merchantName", "Example Merchant")
            })
        }

        val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())
        AutoResolveHelper.resolveTask(
            paymentsClient.loadPaymentData(request),
            this,
            LOAD_PAYMENT_DATA_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val paymentData = PaymentData.getFromIntent(data!!)
                    val info = paymentData?.toJson()
                    Toast.makeText(this, "Payment successful!", Toast.LENGTH_LONG).show()
                    // Handle token from info if needed
                }
                Activity.RESULT_CANCELED -> {
                    Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show()
                }
                AutoResolveHelper.RESULT_ERROR -> {
                    val status = AutoResolveHelper.getStatusFromIntent(data!!)
                    Toast.makeText(this, "Error: ${status?.statusMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
