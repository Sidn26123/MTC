function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];

        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');

        const jsonPayload = decodeURIComponent(
            atob(base64)
                .split('')
                .map(c => `%${('00' + c.charCodeAt(0).toString(16)).slice(-2)}`)
                .join('')
        );
        return JSON.parse(jsonPayload);
    } catch (e) {
        console.error("Error parsing JWT:", e.message);
        return null;
    }
}


const getScopeArray = (token) => {
    const payload = parseJwt(token);
    console.log("payload: ", payload.scope);
    if (!payload || !payload.scope) return [];
   // log("payload.scope: ", payload.scope);
    console.log("payload.scope type: ",  payload.scope);
    console.log("payload.scope.split: ", payload.scope.split(' '));

    return payload.scope.split(' ');
};

const checkHasScope = (token, scope) => {
    const scopes = getScopeArray(token);
    return scopes.includes(scope);
};

const checkHasAnyScope = (token, scopes) => {
    const userScopes = getScopeArray(token);
    return scopes.some(scope => userScopes.includes(scope));
}

const checkHasScopeInArray = (scopes, scopeToCheck) => {
    return scopes.some(scope => scope === scopeToCheck);
}

export { checkHasScope, checkHasAnyScope, checkHasScopeInArray };


export {parseJwt, getScopeArray};

