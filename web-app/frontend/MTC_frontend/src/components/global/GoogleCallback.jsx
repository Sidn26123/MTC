import { useEffect, useRef } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { parseJwt, getScopeArray } from '../../utils/JWTUtils.js';
import useUserStore from '../../stores/authStore.js';
import {showSuccess} from '../../utils/ToastUtils.js';
import { API } from '../../configurations/configuration.js';
import api from '../../middlewares/axios.js';
// import { showSuccess } from '../../utils/NotificationUtil.js'; // Đừng quên bật lại

function GoogleCallback() {
  debugger;
  const navigate = useNavigate();
  const location = useLocation();
  const setUser = useUserStore((state) => state.setUser);
  const calledRef = useRef(false); // ✅ Đảm bảo chỉ gọi 1 lần
  debugger;

  useEffect(() => {
    debugger;
    const handleGoogleCallback = async () => {
      const urlParams = new URLSearchParams(location.search);
      const code = urlParams.get('code');
      debugger;

      if (calledRef.current) return;
      calledRef.current = true;
      debugger;
      if (!code) {
        console.error('Mã code không tồn tại trong URL!');
        navigate('/login');
        return;
      }
      debugger;
      try {
        debugger;
        const response = await axios.get(`http://localhost:8100/identity/auth/social/callback?code=${code}`);
        const { token, refreshToken, authenticated } = response.data.data;
        debugger;
        if (!authenticated || !token) {
          console.error('Xác thực thất bại!');
          navigate('/login');
          return;
        }
        debugger;
        localStorage.setItem('jwtToken', token);
        localStorage.setItem('refreshToken', refreshToken);
        debugger;
        const jwtPayload = parseJwt(token);
        const email = jwtPayload?.email;
        const userId = jwtPayload?.user_id;
        debugger;
        if (!email) {
          console.error('Không thể đọc email từ token!');
          navigate('/login');
          return;
        }
        
        debugger;
        // Nếu bạn cần lấy profile thì bật lại phần này
        // const profileResponse = await api.get(`${API.MY_INFO}?username=${encodeURIComponent(email)}`);
        // const profileResponse = await axios.get(`http://localhost:8089/user-profiles/byId?userId=${encodeURIComponent(userId)}`);
        const profileResponse = await axios.get(
          `http://localhost:8089/user-profiles/byId?userId=${encodeURIComponent(userId)}`,
          {
            headers: {
              Authorization: `Bearer ${token}`
            }
          }
        );
        debugger;
        const user = profileResponse.data?.result;
        debugger;
        // if (user) {
        //   setUser(user);
        //   const roles = getScopeArray(token);
        //   debugger;
        //   const { login: loginAccount } = require('../../stores/authStore.js').useAuthActions();
        //   debugger;
        //   loginAccount({
        //     token,
        //     refreshToken,
        //     roles,
        //     userId,
        //   });
        // }

        showSuccess('Đăng nhập thành công!');
        debugger;
        navigate('/');
        debugger;
      } catch (err) {
        
        console.error('Lỗi trong quá trình xử lý callback:', err);
        navigate('/login');
      }
    };

    handleGoogleCallback();
  }, [location, navigate]);

  return <div>Đang xử lý đăng nhập...</div>;
}

export default GoogleCallback;




// import { useEffect } from 'react';
// import axios from 'axios';
// import { useNavigate, useLocation } from 'react-router-dom';
// import { getScopeArray, parseJwt } from '../../utils/JWTUtils.js';
// import { useUserStore } from '../../stores/authStore.js'; // Sửa lại import này
// import { useAuthActions } from '../../stores/authStore.js';
// import api from '../../middlewares/axios.js';
// import { API } from '../../configurations/configuration.js';
// // import { showSuccess } from '../../utils/NotificationUtil.js'; // Nếu bạn đang dùng hàm này

// function GoogleCallback() {
//   const navigate = useNavigate();
//   const location = useLocation();
//   const setUser = useUserStore((state) => state.setUser);
//   const { login: loginAccount } = useAuthActions();

//   const fetchUserInfo = async (email) => {
//     try {
//       const response = await api.get(`${API.MY_INFO}?username=${encodeURIComponent(email)}`);
//       return response.data;
//     } catch (err) {
//       console.error('Lỗi khi lấy user info:', err);
//     }
//   };

//   useEffect(() => {
//     const handleCallback = async () => {
//       const urlParams = new URLSearchParams(location.search);
//       const code = urlParams.get('code');

//       if (!code) {
//         console.error('Không tìm thấy code từ Google');
//         navigate('/login');
//         return;
//       }

//       try {
//         // Gửi code đến backend để xử lý đăng nhập
//         const response = await axios.get(`http://localhost:8100/identity/auth/social/callback?code=${code}`, {
//           withCredentials: true, // nếu backend cần cookie/session
//         });

//         const { token, refreshToken, authenticated } = response.data.data;

//         if (authenticated) {
//           localStorage.setItem('jwtToken', token);
//           localStorage.setItem('refreshToken', refreshToken);

//           const jwtData = parseJwt(token);

//           // const profile = await fetchUserInfo(jwtData.email);
//           // setUser(profile?.result); // an toàn hơn khi dùng optional chaining

//           // const scope = getScopeArray(token);
//           // loginAccount({
//           //   token: token,
//           //   refreshToken: refreshToken,
//           //   roles: scope,
//           //   userId: jwtData.userId,
//           // });

//           // showSuccess("Đăng nhập thành công");
//           navigate('/');
//         } else {
//           console.error('Authentication failed');
//           navigate('/login');
//         }
//       } catch (error) {
//         console.error('Lỗi khi xử lý callback từ Google:', error);
//         navigate('/login');
//       }
//     };

//     handleCallback();
//   }, [location, navigate]);

//   return <div>Đang xử lý đăng nhập...</div>;
// }

// export default GoogleCallback;



// // src/components/GoogleCallback.jsx
// import { useEffect } from 'react';
// import axios from 'axios';
// import { redirect, useNavigate } from 'react-router';
// import {  useLocation } from 'react-router-dom';
// import { getScopeArray, parseJwt } from '../../utils/JWTUtils.js';
// import authStore, { useAuthActions } from '../../stores/authStore.js';
// import api from '../../middlewares/axios.js';
// import { API } from '../../configurations/configuration.js';

// function GoogleCallback() {
//   const navigate = useNavigate();
//   const location = useLocation();
//   const setUser = useUserStore((state) => state.setUser);
//   const { login: loginAccount } = useAuthActions();

//   const fetchUserInfo = async (email) => {
//         try {
//             var myInfoStr = API.MY_INFO + "?username=" + encodeURIComponent(email);
//             const response = await api.get(myInfoStr);
//             return response.data;
//         } catch (err) {
//             console.error('Lỗi khi lấy user info:', err);
//         }
//     };

//   useEffect(() => {
//     const handleCallback = async () => {
//       // Lấy code từ query parameters
//       const urlParams = new URLSearchParams(location.search);
//       const code = urlParams.get('code');

//       if (code) {
//         try {
//           // Gửi code đến /social/callback
//           debugger;
//           const response = await axios.get(`http://localhost:8100/identity/auth/social/callback?code=${code}`);
//           const { data } = response;
//           const { token, refreshToken, authenticated } = data.data;
//           debugger;
//           if (authenticated) {
//             // Lưu token và refreshToken
//             debugger;
//             localStorage.setItem('jwtToken', token);
//             localStorage.setItem('refreshToken', refreshToken);

//             const jwtData = parseJwt(token);

//             // const profile = await fetchUserInfo(jwtData.email);
//             // setUser(profile.result);
//             // const scope = getScopeArray(token);
//             // loginAccount({
//             //                 token: token,
//             //                 refreshToken: null,
//             //                 roles: scope,
//             //                 userId: jwtData.userId,
//             //             })
//               showSuccess("Đăng nhập thành công");

//             debugger;
//             // Chuyển hướng đến trang chính
//             // navigate('/');
//             window.location.href = '/'
//           } else {
//             console.error('Authentication failed');
//             navigate('/login');
//           }
//         } catch (error) {
//           console.error('Google callback failed:', error);
//           navigate('/login');
//         }
//       }
//     };

//     handleCallback();
//   }, [location, navigate]);

//   return <div>Đang xử lý đăng nhập...</div>;
// }

// export default GoogleCallback;







// import { useEffect } from 'react';
// import { useLocation, useNavigate } from 'react-router-dom';
// import axios from 'axios';
// import { parseJwt, getScopeArray } from '../../utils/JWTUtils.js';
// import  useUserStore  from '../../stores/authStore.js';
// import { API } from '../../configurations/configuration.js';
// import api from '../../middlewares/axios.js';
// // import { showSuccess } from '../../utils/NotificationUtil.js';

// function GoogleCallback() {
//   debugger;
//   const navigate = useNavigate();
//   const location = useLocation();
//   const setUser = useUserStore((state) => state.setUser);
//   debugger;
//   useEffect(() => {
//     debugger;
//     let called = false;
//     debugger;
//     const handleGoogleCallback = async () => {
//       debugger;
//       const urlParams = new URLSearchParams(location.search);
//       const code = urlParams.get('code');
//       debugger;
//       if (called) return;
//       called = true;
//       debugger;
//       if (!code) {
//         console.error('Mã code không tồn tại trong URL!');
//         navigate('/login');
//         return;
//       }

//       try {
//         debugger;
//         // Gửi code lên backend để đổi token
//         const response = await axios.get(`http://localhost:8100/identity/auth/social/callback?code=${code}`);
//         const { token, refreshToken, authenticated } = response.data.data;
//         debugger;
//         if (!authenticated || !token) {
//           console.error('Xác thực thất bại!');
//           navigate('/login');
//           return;
//         }
//         debugger;
//         // Lưu token vào localStorage
//         localStorage.setItem('jwtToken', token);
//         localStorage.setItem('refreshToken', refreshToken);
//         debugger;
//         // Decode token để lấy thông tin người dùng
//         const jwtPayload = parseJwt(token);
//         const email = jwtPayload?.email;
//         debugger;
//         if (!email) {
//           console.error('Không thể đọc email từ token!');
//           navigate('/login');
//           return;
//         }
//         debugger;
//         // Gọi API lấy thông tin chi tiết người dùng
//       //  http://localhost:8100//user-profiles
//         // const profileResponse = await api.get(`${API.MY_INFO}?username=${encodeURIComponent(email)}`);
//         // const profileResponse = await api.get(`http://localhost:8089/user-profiles/username=${encodeURIComponent(email)}`);
//         // const user = profileResponse.data?.result;
//         // debugger;
//         // if (user) {
//         //   setUser(user);
//         //   debugger;  
//         //   // Cập nhật auth state
//         //   const roles = getScopeArray(token);
//         //   const { login: loginAccount } = require('../../stores/authStore.js').useAuthActions();
//         //   debugger;
//         //   loginAccount({
//         //     token,
//         //     refreshToken,
//         //     roles,
//         //     userId: jwtPayload.userId,
//         //   });
//         //   debugger;
//           showSuccess('Đăng nhập thành công!');
//           navigate('/');
//         // } else {
//         //   console.error('Không tìm thấy thông tin người dùng!');
//         //   navigate('/login');
//         // }

//       } catch (err) {
//         console.error('Lỗi trong quá trình xử lý callback:', err);
//         navigate('/login');
//       }
//     };

//     handleGoogleCallback();
//   }, [location, navigate]);

//   return <div>Đang xử lý đăng nhập...</div>;
// }

// export default GoogleCallback;
