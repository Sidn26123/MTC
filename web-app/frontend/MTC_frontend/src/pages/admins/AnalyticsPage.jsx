import React from "react";

import DonutAnalystic from "../../components/analytics/DonutAnalystic";
import TableAnalystic from "../../components/analytics/TableAnalystic";

function AnalyticsPage() {
    return (
        <div className="max-w-5xl mx-auto p-8  min-h-screen">
      <h2 className="text-lg font-bold mb-8 text-gray-100">Trang Thống Kê</h2>

      {/* Gọi component 3 lần với title khác nhau */}
      <DonutAnalystic title="thống kê" />
      <TableAnalystic title="Quản lý bối cảnh thế giới" />
      

    </div>
    );
}

export default AnalyticsPage;