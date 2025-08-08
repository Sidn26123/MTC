// const API_GATEWAY = 'http://localhost:8889/api/v1';
// const API_GATEWAY = 'http://localhost:8100/';
const API_GATEWAY = 'http://10.252.1.138:8889/api/v1';

const IDENTITY_SERVICE = API_GATEWAY + '/identity';
const NOVEL_SERVICE = API_GATEWAY + '/novel';
const NOVEL_CATEGORY_PREFIX = '/categories';
const USER_SERVICE = API_GATEWAY + '/user';
const FEEDBACK_SERVICE = API_GATEWAY + '/feedback';
const FILE_SERVICE = API_GATEWAY + '/file';


// const NOVEL_STATISTIC = NOVEL_SERVICE + '/novel-statistic';

export const CONFIG = {
    API_GATEWAY: API_GATEWAY,
    IDENTITY_SERVICE: IDENTITY_SERVICE,
    MEDIA: "http://localhost:8889/api/v1/file/files/media/download/",
};


export const API = {
    LOGIN: '/identity/auth/token',
    LOGIN_GOOGLE: '/identity/auth/social-login',
    MY_INFO: '/user/user-profiles',
    INFO_ME: '/user/user-profiles/me',
    GOOGLE_CALLBACK: '/identity/auth/social/callback',
    REGISTER: IDENTITY_SERVICE + '/users/registration',
    IDENTITY_SERVICE: IDENTITY_SERVICE,
    NOVEL: NOVEL_SERVICE,
    NOVEL_STATISTIC : NOVEL_SERVICE + '/novel-statistic',

    CHAPTER: NOVEL_SERVICE + "/chapters",
    FILES: FILE_SERVICE + "/files",
    POLICY: FILE_SERVICE + "/policies",
    CATEGORY: NOVEL_SERVICE + "/categories",
    USER: USER_SERVICE + "/user-profiles",
    FEEDBACK: FEEDBACK_SERVICE,
    FEEDBACK_RATING: FEEDBACK_SERVICE + "/ratings",
    FEEDBACK_COMMENT: FEEDBACK_SERVICE + "/comments",
    FEEDBACK_LIKE: FEEDBACK_SERVICE + "/likes",


}

export const API_CATEGORY = {
    NOVEL_PROGRESS_STATUS: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-progress-status",
    NOVEL_ATTRIBUTES: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-attributes",
    NOVEL_STATE: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-state",
    NOVEL_VISIBILITY: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-visibility",
    // GENRES: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/genres",
    GENRES: NOVEL_SERVICE + "/genres",
    // MAIN_CHARACTER_TRAITS: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/main-character-traits",
    MAIN_CHARACTER_TRAITS: NOVEL_SERVICE  + "/main-character-traits",
    // SECTS: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/sects",
    SECTS: NOVEL_SERVICE  + "/sects",
    // WORLD_SCENES: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/world-scenes",
    WORLD_SCENES: NOVEL_SERVICE +  "/world-scenes",
    NOVEL_TYPES: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-types",

    
};