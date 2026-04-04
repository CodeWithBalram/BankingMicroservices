document.getElementById("paymentForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const submitBtn = this.querySelector("button[type='submit']");
    submitBtn.disabled = true;

    try {
        const userId = document.getElementById("userId").value;
        const referenceType = document.getElementById("referenceType").value;
        const referenceId = document.getElementById("referenceId").value;
        const amount = parseFloat(document.getElementById("amount").value);

        // 1️⃣ Get Razorpay key from backend
        const keyResponse = await fetch("/api/config/razorpay");
        const razorpayKey = await keyResponse.text();

        // 2️⃣ Backend API call to create order
        const response = await fetch("/api/payments/create", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                amount: amount,
                currency: "INR",
                userId: userId,
                referenceType: referenceType,
                referenceId: referenceId
            })
        });

        if (!response.ok) throw new Error("Failed to create order");
        const data = await response.json();
        if (!data.orderId) throw new Error("Order ID missing in response");

        // 3️⃣ Razorpay checkout options
        const options = {
            key: razorpayKey,       // backend se fetch hua key
            amount: amount * 100,   // paise
            currency: "INR",
            name: "Ultimate Bank",
            description: `Payment for ${referenceType}`,
            order_id: data.orderId,
            handler: async function(response) {
                alert(`Payment Success!\nPayment ID: ${response.razorpay_payment_id}\nOrder ID: ${response.razorpay_order_id}`);

                // Notify backend webhook to update DB
                try {
                    await fetch("/api/webhook", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({
                            payload: {
                                payment: {
                                    entity: {
                                        id: response.razorpay_payment_id,
                                        order_id: response.razorpay_order_id
                                    }
                                }
                            }
                        })
                    });
                } catch (err) {
                    console.error("Failed to notify backend:", err);
                }
            },
            prefill: {
                name: "User",
                email: "user@example.com",
                contact: "9999999999"
            },
            theme: { color: "#3399cc" }
        };

        const rzp = new Razorpay(options);
        rzp.open();

        rzp.on('payment.failed', function(response) {
            alert(`Payment failed: ${response.error.description}`);
        });

    } catch (err) {
        console.error(err);
        alert(err.message);
    } finally {
        submitBtn.disabled = false;
    }
});