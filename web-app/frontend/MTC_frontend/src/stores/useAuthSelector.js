import authStore from './useAuthStore';

export const useAuth = () =>
    authStore((state) => ({
        token: state.token,
        refreshToken: state.refreshToken,
        isAuthenticated: state.isAuthenticated,
        roles: state.roles,
        userId: state.userId,
    }));

export const useAuthToken = () => authStore((state) => state.token);

export const useAuthActions = () => authStore((state) => state.actions);

export const useIsAuthenticated = () =>
    authStore((state) => state.isAuthenticated);
