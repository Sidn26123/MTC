// import React from 'react';
// import WorldSceneManage from '../../components/admin/WorldSceneManage';
// import SectManage from '../../components/admin/SectManage';
// import MainCharacterManage from '../../components/admin/MainCharacterManage';
// import CategoryManage from '../../components/admin/CategoryManage';

// const CategoryManagePage = () => {
//   return (
//     <div className="max-w-5xl mx-auto p-8  min-h-screen">
//       <h2 className="text-lg font-bold mb-8 text-gray-100">Trang Quản Lý Danh Mục</h2>

//       {/* Gọi component 3 lần với title khác nhau */}
//       <CategoryManage title="Quản lý thể loại" />
//       <WorldSceneManage title="Quản lý bối cảnh thế giới" />
//       <SectManage title="Quản lý lưu phái" />
//       <MainCharacterManage title="Quản lý tính cách nhân vật chính" />
      

//     </div>
//   );
// };

// export default CategoryManagePage;


import React from 'react';
import WorldSceneManage from '../../components/admin/WorldSceneManage';
import SectManage from '../../components/admin/SectManage';
import MainCharacterManage from '../../components/admin/MainCharacterManage';
import CategoryManage from '../../components/admin/CategoryManage';

const CategoryManagePage = () => {
  return (
    <div className="max-w-6xl mx-auto p-4 sm:p-6 md:p-8 min-h-screen">
      <h2 className="text-lg font-bold mb-8 text-gray-100">Trang Quản Lý Danh Mục</h2>

      {/* Grid responsive */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <CategoryManage title="Quản lý thể loại" />
        <WorldSceneManage title="Quản lý bối cảnh thế giới" />
        <SectManage title="Quản lý lưu phái" />
        <MainCharacterManage title="Quản lý tính cách nhân vật chính" />
      </div>
    </div>
  );
};

export default CategoryManagePage;
