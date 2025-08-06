// import React from "react";

// import DonutAnalystic from "../../components/analytics/DonutAnalystic";
// import TableAnalystic from "../../components/analytics/TableAnalystic";
// import DonutGenreAnalystic from "../../components/analytics/DonutGenreAnalystic";
// import DonutSectAnalystic from "../../components/analytics/DonutSectAnaLystic";
// import DonutCharacterTraitAnalystic from "../../components/analytics/DonutCharacterTraitAnalystic";
// import NovelApprovedAnalystic from "../../components/analytics/NovelApprovedAnalystic";

// function AnalyticsPage() {
//     return (
//         <div className="max-w-5xl mx-auto p-8  min-h-screen">
//       <h2 className="text-lg font-bold mb-8 text-gray-100">Trang Thống Kê</h2>

//       {/* Gọi component 3 lần với title khác nhau */}
//       {/* <DonutAnalystic title="thống kê" /> */}
//       <DonutSectAnalystic title="thống kê sect" />
//       <DonutGenreAnalystic title="thống kê genre" />
//       <DonutCharacterTraitAnalystic title="thống kê genre" />
//       <TableAnalystic title="Quản lý bối cảnh thế giới" />
//       <NovelApprovedAnalystic title="Thống kê truyện đã duyệt" />
      
      

//     </div>
//     );
// }

// export default AnalyticsPage;


import React from "react";

import DonutAnalystic from "../../components/analytics/DonutAnalystic";
import TableAnalystic from "../../components/analytics/TableAnalystic";
import DonutGenreAnalystic from "../../components/analytics/DonutGenreAnalystic";
import DonutSectAnalystic from "../../components/analytics/DonutSectAnaLystic";
import DonutCharacterTraitAnalystic from "../../components/analytics/DonutCharacterTraitAnalystic";
import NovelApprovedAnalystic from "../../components/analytics/NovelApprovedAnalystic";

function AnalyticsPage() {
  return (
    <div className="max-w-6xl mx-auto p-4 sm:p-6 md:p-8 min-h-screen">
      <h2 className="text-lg font-bold mb-8 text-gray-100">Trang Thống Kê</h2>

      {/* Nhóm biểu đồ nhỏ - responsive grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
        <DonutSectAnalystic title="Thống kê lưu phái" />
        <DonutGenreAnalystic title="Thống kê thể loại" />
        <DonutCharacterTraitAnalystic title="Thống kê tính cách nhân vật" />
      </div>

      {/* Các bảng lớn - luôn full width */}
      <div className="space-y-8">
        <TableAnalystic title="Thống kê bối cảnh thế giới" />
        <NovelApprovedAnalystic title="Thống kê truyện đã duyệt" />
      </div>
    </div>
  );
}

export default AnalyticsPage;
