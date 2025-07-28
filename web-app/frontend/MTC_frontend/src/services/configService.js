const CONFIG_KEY = 'readerConfig';

const defaultConfig = {
    'background-color': {
        light: '#f4f4f4',
        dark: '#1e293b',
    },
    color: {
        light: '#000000',
        dark: '#ffffff',
    },
    'font-family': 'Avenir Next',
    'font-size': '24px',
    'line-height': '150%',
    'text-align': 'left',
};

// Lưu cấu hình vào localStorage
export function saveConfig(config) {
    try {
        localStorage.setItem(CONFIG_KEY, JSON.stringify(config));
    } catch (error) {
        console.error('Lỗi khi lưu config vào localStorage:', error);
    }
}

// Lấy cấu hình từ localStorage, nếu không có thì trả default
export function getConfig() {
    try {
        const raw = localStorage.getItem(CONFIG_KEY);
        return raw ? JSON.parse(raw) : { ...defaultConfig };
    } catch (error) {
        console.error('Lỗi khi lấy config từ localStorage:', error);
        return { ...defaultConfig };
    }
}

// Reset về cấu hình mặc định
export function resetConfig() {
    saveConfig(defaultConfig);
    return { ...defaultConfig };
}

// Cập nhật 1 giá trị cụ thể trong config
export function updateConfigValue(key, value, subkey = null) {
    const current = getConfig();
    if (subkey) {
        current[key] = {
            ...(current[key] || {}),
            [subkey]: value,
        };
    } else {
        current[key] = value;
    }
    saveConfig(current);
    return current;
}

// Optional: xuất default để tiện sử dụng
export { defaultConfig };
