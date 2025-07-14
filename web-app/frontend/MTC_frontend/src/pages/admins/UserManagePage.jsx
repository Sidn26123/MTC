
import React from 'react';

const UserManagePage = () => {
  return (
    <div className="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl mt-10">
      <div className="md:flex">
        <div className="p-8">
          <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold">Profile</div>
          <div className="flex items-center">
            <img className="h-16 w-16 rounded-full mr-4" src="https://via.placeholder.com/150" alt="Profile" />
            <div>
              <h1 className="text-xl font-medium text-gray-900">Musharof Chowdhury</h1>
              <p className="text-gray-600">Team Manager | Arizona, United States</p>
            </div>
          </div>
          <div className="mt-4 flex space-x-2">
            <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-facebook"></i></a>
            <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-x"></i></a>
            <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-linkedin"></i></a>
            <a href="#" className="text-gray-400 hover:text-gray-500"><i className="fab fa-instagram"></i></a>
            <button className="ml-auto bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300">Edit</button>
          </div>
        </div>
      </div>
      <div className="p-8 pt-0">
        <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold flex justify-between items-center">
          <span>Personal Information</span>
          <button className="bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300">Edit</button>
        </div>
        <div className="mt-4 grid grid-cols-2 gap-4">
          <div>
            <p className="text-gray-600">First Name</p>
            <p className="text-gray-900">Musharof</p>
          </div>
          <div>
            <p className="text-gray-600">Last Name</p>
            <p className="text-gray-900">Chowdhury</p>
          </div>
          <div>
            <p className="text-gray-600">Email address</p>
            <p className="text-gray-900">randomuser@pimjo.com</p>
          </div>
          <div>
            <p className="text-gray-600">Phone</p>
            <p className="text-gray-900">+09 363 398 46</p>
          </div>
          <div className="col-span-2">
            <p className="text-gray-600">Bio</p>
            <p className="text-gray-900">Team Manager</p>
          </div>
        </div>
        <div className="mt-6">
          <div className="uppercase tracking-wide text-sm text-indigo-500 font-semibold flex justify-between items-center">
            <span>Address</span>
            <button className="bg-gray-200 text-gray-700 px-2 py-1 rounded hover:bg-gray-300">Edit</button>
          </div>
          <div className="mt-4 grid grid-cols-2 gap-4">
            <div>
              <p className="text-gray-600">Country</p>
              <p className="text-gray-900">United States.</p>
            </div>
            <div>
              <p className="text-gray-600">City/State</p>
              <p className="text-gray-900">Phoenix, Arizona, United States.</p>
            </div>
            <div>
              <p className="text-gray-600">Postal Code</p>
              <p className="text-gray-900">ERT 2489</p>
            </div>
            <div>
              <p className="text-gray-600">TAX ID</p>
              <p className="text-gray-900">A54S6834</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

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



