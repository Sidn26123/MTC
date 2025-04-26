import { format } from "date-fns";


function formatDate(date, formatStr) {
    return format(date, formatStr);
}

function formatPublishDateTime(date) {
    return format(date, "dd/MM/yyyy HH:mm");
}

export { formatDate, formatPublishDateTime };