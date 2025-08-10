
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

let useAuthStore = create(
    persist(
        (set, get) => ({
            token: null,
            refreshToken: null,
            isAuthenticated: false,
            roles: null,
            userId: null,
        }),
        {
            name: 'auth-storage',
            // loại bỏ các hàm để tránh bị lưu vào localStorage
            partialize: (state) => ({
                token: state.token,
                refreshToken: state.refreshToken,
                isAuthenticated: state.isAuthenticated,
                roles: state.roles,
                userId: state.userId,
            }),
        }
    )
);

// ✅ Thêm actions vào store ngoài persist
const login = ({ token, refreshToken, roles, userId }) =>
    useAuthStore.setState({
        token,
        refreshToken,
        isAuthenticated: true,
        roles,
        userId,
    });

const logout = () =>
    useAuthStore.setState({
        token: null,
        refreshToken: null,
        isAuthenticated: false,
        roles: null,
        userId: null,
    });

const setToken = (token) =>
    useAuthStore.setState({ token });

const setRefreshToken = (refreshToken) =>
    useAuthStore.setState({ refreshToken });

const checkHasScope = (scope) => {
    const roles = useAuthStore.getState().roles;
    if (!roles) return false;
    return roles.includes(scope);
};

// ✅ Export store & hooks
export default useAuthStore;

export const useIsAuthenticated = () =>
    useAuthStore((state) => state.isAuthenticated);

export const useAuthToken = () =>
    useAuthStore((state) => state.token);

export const useAuthRefreshToken = () =>
    useAuthStore((state) => state.refreshToken);

export const useAuthRoles = () =>
    useAuthStore((state) => state.roles);

export const useAuthActions = () => ({
    login,
    logout,
    setToken,
    setRefreshToken,
    checkHasScope,
});
