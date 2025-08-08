
import React from 'react';

import { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";

import { getProfileById, getMyInfo, updateUserProfile ,
        updatePassword, checkPassword} from '../../services/userService.js';

import { useUser } from "../../stores/userStores.js";

import { logOut } from '../../services/authenticationService.js';

import useUserStore from '../../stores/userStores.js';

const UserManagePage = () => {
  const setUser = useUserStore((state) => state.setUser);

  const navigate = useNavigate(); 

  const [userInfo, setUserInfo] = useState(null);

  const user = useUser();
  console.log('User from store:', user);
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
  firstName: "",
  lastName: "",
  gender: "",
});
  const [showPasswordModal, setShowPasswordModal] = useState(false);

  const [passwordData, setPasswordData] = useState({
    currentPassword: "",
    newPassword: "",
    confirmPassword: "",
  });

  const [passwordError, setPasswordError] = useState("");


  const fetchUserData = async () => {
    try {
      const userData = await getMyInfo();
      setUserInfo(userData.data.result);
      console.log('------User Data:', userData.data?.result);
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  const openModal = () => {
    setFormData({
      // username: userInfo?.username || "",
      firstName: userInfo?.firstName || "",
      lastName: userInfo?.lastName || "",
      // email: userInfo?.email || "",
      gender: userInfo?.gender || "",
    });
    setShowModal(true);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSave = async (id) => {
    console.log("Saving info:", formData);
    // Call the updateUserProfile function with the formData

    const updateUserData = await updateUserProfile(id, formData);
    fetchUserData(); // Refresh user data after update
    // TODO: Gửi dữ liệu cập nhật lên server tại đây
    setShowModal(false);
  };

  const handleChangePassword = async (userId) => {
    setPasswordError("");

    if (passwordData.newPassword !== passwordData.confirmPassword) {
      setPasswordError("Mật khẩu mới và xác nhận không khớp.");
      return;
    }

    if (passwordData.newPassword.length < 8) {
    setPasswordError("❌ Mật khẩu mới phải có ít nhất 8 ký tự.");
    return;
  }

    const res = await checkPassword(userId, passwordData.currentPassword);

    if (!res.result) {
      setPasswordError("❌ Mật khẩu hiện tại không đúng.");
      return;
    }



    // TODO: Gửi API đổi mật khẩu
    console.log("Đổi mật khẩu:", passwordData);
    const updatePasswordResponse = await updatePassword(userId, 
       passwordData.newPassword
    );


    // Reset & đóng
    setPasswordData({
      currentPassword: "",
      newPassword: "",
      confirmPassword: "",
    });
    setShowPasswordModal(false);
  };

  const handleLogout = (event)=> {
          event.preventDefault();
          setUser(null);
          logOut();
          // showSuccess("Đăng xuất thành công");
          navigate("/"); // hoặc trang home nếu cần
      }


  useEffect(() => {
    // Fetch user data when the component mounts


    fetchUserData();
    // openModal(); // Open modal to edit user info
  }, []);

  return (
    // <div className="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl mt-10">
    <div className="max-w-md mx-auto rounded-xl border border-white overflow-hidden md:max-w-2xl mt-10">
      {/* // <div className="max-w-md mx-auto rounded-xl overflow-hidden md:max-w-2xl mt-10"> */}
      {/* // <div className="max-w-md mx-auto rounded-xl border border-white p-4 mt-10"> */}
      <div className="md:flex">
        <div className="p-8">
          <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold">Profile</div>
          <div className="flex items-center">
            <img className="h-16 w-16 rounded-full mr-4" src={userInfo?.avatarPath || "https://react-demo.tailadmin.com/images/user/owner.jpg"} alt="Profile" />
            <div>
              <h1 className="text-xl font-medium 	text-white">{userInfo?.username}</h1>
              <p className="	text-white">Admin</p>
            </div>
          </div>
          <div className="mt-4 flex space-x-2">
            {/* <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-facebook"></i></a>
            <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-x"></i></a>
            <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-linkedin"></i></a>
            <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-instagram"></i></a> */}
            {/* <button className="ml-auto bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300">Edit</button> */}
          </div>
        </div>
      </div>
      <div className="p-8 pt-0">
        <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold flex justify-between items-center">
          <span>Personal Information</span>
          <button className="bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300" onClick={openModal}>Edit</button>

          {showModal && (
            <div className="fixed inset-0 flex items-center justify-center backdrop-blur z-50">
              <div className="bg-gray-700 rounded-lg shadow-lg p-6 w-[400px] max-w-full">
                <h2 className="text-xl font-bold mb-4">Chỉnh sửa thông tin</h2>

                <div className="space-y-3">
                  {/* <div>
                <label className="block font-medium text-gray-200">Username</label>
                <input
                  type="text"
                  name="username"
                  value={formData.username}
                  onChange={handleChange}
                  className="w-full border px-3 py-1 rounded text-gray-100"
                />
              </div> */}
                  <div>
                    <label className="block font-medium text-gray-200">First Name</label>
                    <input
                      type="text"
                      name="firstName"
                      value={formData.firstName}
                      onChange={handleChange}
                      className="w-full border px-3 py-1 rounded text-gray-100"
                    />
                  </div>
                  <div>
                    <label className="block font-medium text-gray-200">Last Name</label>
                    <input
                      type="text"
                      name="lastName"
                      value={formData.lastName}
                      onChange={handleChange}
                      className="w-full border px-3 py-1 rounded text-gray-100"
                    />
                  </div>
                  {/* <div>
                <label className="block font-medium">Email</label>
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  className="w-full border px-3 py-1 rounded"
                />
              </div> */}
                  <div>
                    <label className="block font-medium text-gray-200">Gender</label>
                    <select
                      name="gender"
                      value={formData.gender}
                      onChange={handleChange}
                      className="w-full border px-3 py-1 rounded text-gray-100"
                    >
                      <option value="MALE">-- Chọn giới tính --</option>
                      <option value="MALE">Male</option>
                      <option value="FEMALE">Female</option>
                      {/* <option value="OTHER">Khác</option> */}
                    </select>
                  </div>
                </div>

                <div className="mt-6 flex justify-end space-x-2">
                  <button
                    className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                    onClick={() => setShowModal(false)}
                  >
                    Hủy
                  </button>
                  <button
                    className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                    onClick={() => handleSave(userInfo?.id)}
                  >
                    Lưu
                  </button>
                </div>
              </div>
            </div>
          )}



        </div>
        <div className="mt-4 grid grid-cols-2 gap-4">
          <div>
            <p className="text-gray-300">First Name</p>
            <p className="text-white">{userInfo?.firstName}</p>
          </div>
          <div>
            <p className="text-gray-300">Last Name</p>
            <p className="text-white">{userInfo?.lastName}</p>
          </div>
          <div>
            <p className="text-gray-300">Email address</p>
            <p className="text-white">{userInfo?.email}</p>
          </div>
          {/* <div>
            <p className="text-gray-300">Date of birth</p>
            <p className="text-white">{new Date(userInfo?.dateOfBirth).toLocaleDateString("vi-VN")}</p>
 
          </div> */}
          <div className="col-span-2">
            <p className="text-gray-300">Gender</p>
            <p className="text-white">{userInfo?.gender}</p>
          </div>
        </div>

        <div className="flex justify-between items-center px-4 mt-4">
          <button
            className="bg-gray-200 text-gray-700 px-3 py-1 rounded hover:bg-gray-300"
            onClick={() => setShowPasswordModal(true)}
          >
            Đổi mật khẩu
          </button>
          {showPasswordModal && (
            <div className="fixed inset-0 flex items-center justify-center backdrop-blur z-50">
              <div className="bg-gray-700 rounded-lg shadow-lg p-6 w-[400px] max-w-full">
                <h2 className="text-xl font-bold mb-4 text-white">Đổi mật khẩu</h2>

                <div className="space-y-3">
                  <div>
                    <label className="block font-medium text-gray-200">Mật khẩu hiện tại</label>
                    <input
                      type="password"
                      value={passwordData.currentPassword}
                      onChange={(e) => setPasswordData({ ...passwordData, currentPassword: e.target.value })}
                      className="w-full border px-3 py-1 rounded text-gray-100"
                    />
                  </div>
                  <div>
                    <label className="block font-medium text-gray-200">Mật khẩu mới</label>
                    <input
                      type="password"
                      value={passwordData.newPassword}
                      onChange={(e) => setPasswordData({ ...passwordData, newPassword: e.target.value })}
                      className="w-full border px-3 py-1 rounded text-gray-100"
                    />
                  </div>
                  <div>
                    <label className="block font-medium text-gray-200">Nhập lại mật khẩu mới</label>
                    <input
                      type="password"
                      value={passwordData.confirmPassword}
                      onChange={(e) => setPasswordData({ ...passwordData, confirmPassword: e.target.value })}
                      className="w-full border px-3 py-1 rounded text-gray-100"
                    />
                  </div>

                  {passwordError && (
                    <p className="text-red-500 text-sm font-medium">{passwordError}</p>
                  )}
                </div>

                <div className="mt-6 flex justify-end space-x-2">
                  <button
                    className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                    onClick={() => setShowPasswordModal(false)}
                  >
                    Hủy
                  </button>
                  <button
                    className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                    onClick={() => handleChangePassword(userInfo?.userId)}
                  >
                    Lưu
                  </button>
                </div>
              </div>
            </div>
          )}


          <button
            className="bg-gray-200 text-gray-700 px-3 py-1 rounded hover:bg-gray-300"
            onClick={(event) => handleLogout(event)}
          >
            Đăng xuất
          </button>
        </div>

        {/* <div className="mt-6">
          <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold flex justify-between items-center">
            <span>Address</span>
            <button className="bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300">Edit</button>
          </div>
          <div className="mt-4 grid grid-cols-2 gap-4">
            <div>
              <p className="text-gray-300">Country</p>
              <p className="text-white">United States.</p>
            </div>
            <div>
              <p className="text-gray-300">City/State</p>
              <p className="text-white">Phoenix, Arizona, United States.</p>
            </div>
            <div>
              <p className="text-gray-300">Postal Code</p>
              <p className="text-white">ERT 2489</p>
            </div>
            <div>
              <p className="text-gray-300">TAX ID</p>
              <p className="text-white">A54S6834</p>
            </div>
          </div>
        </div> */}
      </div>
    </div>
  );
};

   
//   return (
//     // <div className="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl mt-10">
//     <div className="max-w-md mx-auto rounded-xl border border-white overflow-hidden md:max-w-2xl mt-10">
//     {/* // <div className="max-w-md mx-auto rounded-xl overflow-hidden md:max-w-2xl mt-10"> */}
//     {/* // <div className="max-w-md mx-auto rounded-xl border border-white p-4 mt-10"> */}
//       <div className="md:flex">
//         <div className="p-8">
//           <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold">Profile</div>
//           <div className="flex items-center">
//             <img className="h-16 w-16 rounded-full mr-4" src="https://react-demo.tailadmin.com/images/user/owner.jpg" alt="Profile" />
//             <div>
//               <h1 className="text-xl font-medium 	text-white">Musharof Chowdhury</h1>
//               <p className="	text-white">Team Manager | Arizona, United States</p>
//             </div>
//           </div>
//           <div className="mt-4 flex space-x-2">
//             <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-facebook"></i></a>
//             <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-x"></i></a>
//             <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-linkedin"></i></a>
//             <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-instagram"></i></a>
//             <button className="ml-auto bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300">Edit</button>
//           </div>
//         </div>
//       </div>
//       <div className="p-8 pt-0">
//         <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold flex justify-between items-center">
//           <span>Personal Information</span>
//           <button className="bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300">Edit</button>
//         </div>
//         <div className="mt-4 grid grid-cols-2 gap-4">
//           <div>
//             <p className="text-gray-300">First Name</p>
//             <p className="text-white">Musharof</p>
//           </div>
//           <div>
//             <p className="text-gray-300">Last Name</p>
//             <p className="text-white">Chowdhury</p>
//           </div>
//           <div>
//             <p className="text-gray-300">Email address</p>
//             <p className="text-white">randomuser@pimjo.com</p>
//           </div>
//           <div>
//             <p className="text-gray-300">Phone</p>
//             <p className="text-white">+09 363 398 46</p>
//           </div>
//           <div className="col-span-2">
//             <p className="text-gray-300">Bio</p>
//             <p className="text-white">Team Manager</p>
//           </div>
//         </div>
//         <div className="mt-6">
//           <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold flex justify-between items-center">
//             <span>Address</span>
//             <button className="bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300">Edit</button>
//           </div>
//           <div className="mt-4 grid grid-cols-2 gap-4">
//             <div>
//               <p className="text-gray-300">Country</p>
//               <p className="text-white">United States.</p>
//             </div>
//             <div>
//               <p className="text-gray-300">City/State</p>
//               <p className="text-white">Phoenix, Arizona, United States.</p>
//             </div>
//             <div>
//               <p className="text-gray-300">Postal Code</p>
//               <p className="text-white">ERT 2489</p>
//             </div>
//             <div>
//               <p className="text-gray-300">TAX ID</p>
//               <p className="text-white">A54S6834</p>
//             </div>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

export default UserManagePage;

// import ProfileCard from "../components/userProfileAdmin/ProfileCard.jsx";
// import PersonalInfoCard from "../components/userProfileAdmin/PersonalInfoCard.jsx";  
// import AddressCard from "../components/userProfileAdmin/AddressCard.jsx";
// import {ProfileCard} from "../../components/userProfileAdmin/ProfileCard.jsx";
// // "../src/components/userProfileAdmin/ProfileCard.jsx";
// import PersonalInfoCard from "../../components/userProfileAdmin/PersonalInfoCard.jsx";
// import AddressCard from "../../components/userProfileAdmin/AddressCard.jsx";
// import ProfileCard from "../../components/userProfileAdmin/ProfileCard.jsx";
// import PersonalInfoCard from "../../components/userProfileAdmin/PersonalInfoCard.jsx";
// import AddressCard from "../../components/userProfileAdmin/AddressCard.jsx";


// function UserManagePage() {
//     return (
//     <div className="flex min-h-screen w-full bg-gray-100/40 dark:bg-gray-800/40">
      

//         {/* Page Content */}
//         <main className="flex flex-1 flex-col gap-4 p-4 md:gap-8 md:p-6">
//           {/* Page title + breadcrumb */}
//           <div className="flex items-center justify-between">
//             <h1 className="text-2xl font-semibold">Profile</h1>
//             <div className="flex items-center gap-2 text-sm text-gray-500 dark:text-gray-400">
//               <Link href="#" className="hover:underline">
//                 Home
//               </Link>
//               <span>/</span>
//               <span>Profile</span>
//             </div>
//           </div>

//           {/* Profile Sections */}
//           <ProfileCard />
//           <PersonalInfoCard />
//           <AddressCard />
//         </main>
//     </div>
//   );
// }

// export default UserManagePage;



