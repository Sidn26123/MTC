// const API_GATEWAY = 'http://localhost:8889/api/v1';
// const API_GATEWAY = 'http://localhost:8100/';
const API_GATEWAY = 'http://localhost:8889/api/v1';
const MOMO_API = "http://localhost:5000"
const IDENTITY_SERVICE = API_GATEWAY + '/identity';
const NOVEL_SERVICE = API_GATEWAY + '/novel';
const NOVEL_CATEGORY_PREFIX = '/categories';
const USER_SERVICE = API_GATEWAY + '/user';
const FEEDBACK_SERVICE = API_GATEWAY + '/feedback';
const FILE_SERVICE = API_GATEWAY + '/file';
const PAYMENT_SERVICE = API_GATEWAY + '/payment';
const NOTIFICATION_SERVICE = API_GATEWAY + '/notification';

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
    DRAFT: NOVEL_SERVICE + "/drafts",
    FILES: FILE_SERVICE + "/files",
    FILES_CLOUDINARY: FILE_SERVICE + "/cloudinary",
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
    CONTENT_PURCHASED: PAYMENT_SERVICE + "/content-purchases",
    TRANSACTIONS: PAYMENT_SERVICE + "/transactions",
    WALLET: PAYMENT_SERVICE + "/wallets",
    MOMO_PAYMENT: MOMO_API + "/payment",
    NOTIFICATION: NOTIFICATION_SERVICE + "/notifications",
    // NOVEL_STATISTIC : NOVEL_SERVICE + '/novel-statistic',
    // GOOGLE_CALLBACK: '/identity/auth/social/callback',


}

export const API_CATEGORY = {
    NOVEL_PROGRESS_STATUS: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-progress-status",
    NOVEL_ATTRIBUTES: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-attributes",
    NOVEL_STATE: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-state",
    NOVEL_VISIBILITY: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-visibility",
    GENRES: NOVEL_SERVICE  + "/genres",
    MAIN_CHARACTER_TRAITS: NOVEL_SERVICE + "/main-character-traits",
    SECTS: NOVEL_SERVICE + "/sects",
    WORLD_SCENES: NOVEL_SERVICE  + "/world-scenes",
    NOVEL_TYPES: NOVEL_SERVICE + NOVEL_CATEGORY_PREFIX + "/novel-types",
};

export const API_ENUMS = {

}