import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const PaymentSuccess = () => {
  const [orderId, setOrderId] = useState("");
  const [requestId, setRequestId] = useState("");
  const [userId, setUserId] = useState("");
  const [currencyId, setCurrencyId] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const query = new URLSearchParams(window.location.search);
    const orderId = query.get("orderId");
    const requestId = query.get("requestId");
    const extraDataEncoded = query.get("extraData");

    setOrderId(orderId || "");
    setRequestId(requestId || "");

    if (extraDataEncoded) {
      try {
        const decoded = atob(extraDataEncoded);
        const parsed = JSON.parse(decoded);
        setUserId(parsed.userId || "");
        setCurrencyId(parsed.currencyId || "");
      } catch (err) {
        console.error("Lỗi giải mã extraData:", err);
      }
    }
  }, []);

  return (
    <div style={{ padding: "2rem", fontFamily: "Arial" }}>
      <h2 style={{ color: "green" }}>🎉 Thanh toán thành công!</h2>
      <p><strong>Mã đơn hàng:</strong> {orderId}</p>
      <p><strong>Mã giao dịch:</strong> {requestId}</p>
      <p><strong>User ID:</strong> {userId}</p>
      <p><strong>Currency ID:</strong> {currencyId}</p>

      <button
        onClick={() => navigate("/")}
        style={{
          marginTop: "1.5rem",
          padding: "10px 20px",
          backgroundColor: "#007bff",
          color: "white",
          border: "none",
          borderRadius: "5px",
          cursor: "pointer"
        }}
      >
        🔙 Trở về trang chủ
      </button>
    </div>
  );
};

export default PaymentSuccess;
