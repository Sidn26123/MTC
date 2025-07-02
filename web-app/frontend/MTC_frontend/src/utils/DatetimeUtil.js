import { format } from "date-fns";


function formatDate(date, formatStr) {
    return format(date, formatStr);
}

function formatPublishDateTime(date) {
    return format(date, "dd/MM/yyyy HH:mm");
}

function getFormattedTimeForApi(date) {
    // const now = new Date();

    // Lấy phần ISO cơ bản: "YYYY-MM-DDTHH:mm:ss.sssZ"
    const isoString = date.toISOString(); // VD: "2025-05-08T15:09:23.687Z"

    // Tách phần trước dấu "." và phần milliseconds
    const [datePart, milliPartWithZ] = isoString.split(".");
    const milliPart = milliPartWithZ.replace("Z", "");

    // Đảm bảo phần milliseconds có 6 chữ số (SSSSSS)
    const microseconds = milliPart.padEnd(6, "0");

    return `${datePart}.${microseconds}`;
}

function getCurrentFormattedTimeForApi() {
    return getFormattedTimeForApi(new Date());
}

function timeAgo(isoTimeStr) {
    // Cắt microseconds để Date hiểu đúng
    const trimmed = isoTimeStr.slice(0, 23) + "Z";
    const past = new Date(trimmed);
    const now = new Date();

    const diffMs = now - past;
    const diffSec = Math.floor(diffMs / 1000);
    const diffMin = Math.floor(diffSec / 60);
    const diffHour = Math.floor(diffMin / 60);
    const diffDay = Math.floor(diffHour / 24);
    const diffWeek = Math.floor(diffDay / 7);
    const diffMonth = Math.floor(diffDay / 30); // xấp xỉ
    const diffYear = Math.floor(diffDay / 365); // xấp xỉ

    if (diffSec < 10) return "vài giây trước";
    if (diffSec < 60) return `${diffSec} giây trước`;
    if (diffMin < 60) return `${diffMin} phút trước`;
    if (diffHour < 24) return `${diffHour} giờ trước`;
    if (diffDay < 7) return `${diffDay} ngày trước`;
    if (diffWeek < 4) return `${diffWeek} tuần trước`;
    if (diffMonth < 12) return `${diffMonth} tháng trước`;
    return `${diffYear} năm trước`;
}


export { formatDate, formatPublishDateTime,getCurrentFormattedTimeForApi, getFormattedTimeForApi, timeAgo };