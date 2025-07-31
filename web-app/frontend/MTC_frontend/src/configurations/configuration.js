// const API_GATEWAY = 'http://localhost:8889/api/v1';
// const API_GATEWAY = 'http://localhost:8100/';
const API_GATEWAY = 'http://localhost:8889/api/v1';

const IDENTITY_SERVICE = API_GATEWAY + '/identity';
const NOVEL_SERVICE = API_GATEWAY + '/novel';
const NOVEL_CATEGORY_PREFIX = '/categories';
const USER_SERVICE = API_GATEWAY + '/user';
const FEEDBACK_SERVICE = API_GATEWAY + '/feedback';
const FILE_SERVICE = API_GATEWAY + '/file';
const PAYMENT_SERVICE = API_GATEWAY + '/payment';

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
    REGISTER: IDENTITY_SERVICE + '/users/registration',
    NOVEL: NOVEL_SERVICE,
    CHAPTER: NOVEL_SERVICE + "/chapters",
    DRAFT: NOVEL_SERVICE + "/drafts",
    FILES: FILE_SERVICE + "/files",
    POLICY: FILE_SERVICE + "/policies",
    CATEGORY: NOVEL_SERVICE + "/categories",
    USER: USER_SERVICE + "/user-profiles",
    FEEDBACK: FEEDBACK_SERVICE,
    REPORT: FEEDBACK_SERVICE + "/reports",
    FEEDBACK_RATING: FEEDBACK_SERVICE + "/ratings",
    FEEDBACK_COMMENT: FEEDBACK_SERVICE + "/comments",
    FEEDBACK_LIKE: FEEDBACK_SERVICE + "/likes",
    BOOKSHELF: NOVEL_SERVICE + "/bookshelfs",
    BOOKMARKED: NOVEL_SERVICE + "/bookshelfs/marked-novels",
    PAYMENT: PAYMENT_SERVICE,
    TRANSACTIONS: PAYMENT_SERVICE + "/transactions",
    WALLET: PAYMENT_SERVICE + "/wallets",


}

export const API_CATEGORY = {
    NOVEL_PROGRESS_STATUS: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-progress-status",
    NOVEL_ATTRIBUTES: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-attributes",
    NOVEL_STATE: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-state",
    NOVEL_VISIBILITY: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-visibility",
    GENRES: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/genres",
    MAIN_CHARACTER_TRAITS: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/main-character-traits",
    SECTS: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/sects",
    WORLD_SCENES: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/world-scenes",
    NOVEL_TYPES: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-types",
};

export const API_ENUMS = {

}