import { getToken, removeToken, setToken } from "./localStorageService";
import {useAuthRoles} from '../stores/authStore.js';

import httpClient from "../configurations/httpClient";

function useHasScope(scope) {
    const roles = useAuthRoles();
    return roles?.some((role) => role === scope) ?? false;
}

const hasScope = (scopes, scopeToCheck) => {
    if (!scopes || scopes.length === 0) {
        return false;
    }
    return scopes.some((scope) => scope === scopeToCheck);
}


export {useHasScope, hasScope};