package ashutosh.shopit.ui.payment

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import ashutosh.shopit.Constants
import ashutosh.shopit.R
import ashutosh.shopit.api.NetworkResult
import ashutosh.shopit.models.OrderResponse
import ashutosh.shopit.models.UpdateOrderRequest
import com.google.gson.Gson
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import kotlin.properties.Delegates


@AndroidEntryPoint
class PaymentActivity : AppCompatActivity(), PaymentResultWithDataListener {

    private lateinit var dataObj: OrderResponse
    private var paymentType = 0
    private var productId: Int = -1
    private var quantity: Int = -1

    private val paymentViewModel by viewModels<PaymentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val data = intent.getStringExtra("data")
        paymentType = intent.getIntExtra("paymentType", 0)
        productId = intent.getIntExtra("productId", -1)
        quantity = intent.getIntExtra("quantity", -1)
        dataObj = Gson().fromJson(data, OrderResponse::class.java)
        Checkout.preload(applicationContext)
        startPayment(dataObj)

        paymentViewModel.updateOrderResponse.observe(this){
            when(it){
                is NetworkResult.Success -> {
                    Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is NetworkResult.Loading -> {}
            }
        }
    }

    private fun startPayment(data: OrderResponse) {
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()
        co.setKeyID(Constants.razorpayKey)

        try {
            val options = JSONObject()
            options.put("name","ShopIt")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc")
            options.put("currency","INR")
            options.put("order_id", data.id)
            options.put("amount",data.amountDue)//pass amount in currency subunits

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email","shopitanything@gmail.com")
            prefill.put("contact","8287027446")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        if (p1 != null) {
            when (paymentType) {
                1 -> {
                    paymentViewModel.updateOrder(UpdateOrderRequest(p1.paymentId, p1.orderId, p1.signature))
                }
                2 -> {
                    paymentViewModel.updateDirectOrder(productId, quantity, UpdateOrderRequest(p1.paymentId, p1.orderId, p1.signature))
                }
                else -> {
                    Toast.makeText(this, "Something went wrong\nPlease try again!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            Toast.makeText(this, "Something went wrong!\nPlease try again!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
        finish()
    }
}