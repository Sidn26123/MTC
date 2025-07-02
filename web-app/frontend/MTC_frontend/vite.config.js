// import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'
// // https://vite.dev/config/
// export default defineConfig({
//     content: ["./src/**/*.{js,jsx,ts,tsx}"],
//     plugins: [
//         react(), tailwindcss(),],
//     theme: {
//         extend: {
//             colors: {
//                 backgroundColor: "#272729",
//                 yellowText: "#896B29",
//                 primary: "#896B29",
//                 background: {
//                     light: "#F8FAFC",  // Màu nền sáng
//                     DEFAULT: "#272729", // Màu nền chính
//                     dark: "#272729",  // Màu nền tối
//                 },
//                 text: {
//                   basic: "#BFC2C3"
//                 },
//                 // primary: {
//                 //     light: "#93C5FD",
//                 //     DEFAULT: "#3B82F6",
//                 //     dark: "#1E40AF",
//                 // },
//                 status: {
//                     success: "#22C55E",
//                     warning: "#FACC15",
//                     error: "#EF4444",
//                     info: "#3B82F6",
//                 },
//                 level: {
//                     level1: "#FAB387",
//                     level2: "#F9E2AF",
//                     level3: "#A6E3A1",
//                 },
//                 neutral: {
//                     light: "#E5E7EB",
//                     DEFAULT: "#6B7280",
//                     dark: "#374151",
//                 },
//             },
//         },
//     },
// })
//
/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}"
    ],
    theme: {
        extend: {
            colors: {
                primary: "#896B29", // Xanh dương đậm
                secondary: "#E11D48", // Đỏ hồng
            },
        },
    },
    plugins: [
        react(), tailwindcss()],
};

