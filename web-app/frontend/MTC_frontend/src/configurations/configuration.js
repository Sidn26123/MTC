const API_GATEWAY = 'http://localhost:8889/api/v1';
const IDENTITY_SERVICE = API_GATEWAY + '/identity';
const NOVEL_SERVICE = API_GATEWAY + '/novel';
const USER_SERVICE = API_GATEWAY + '/user';
const FEEDBACK_SERVICE = API_GATEWAY + '/feedback';
const FILE_SERVICE = API_GATEWAY + '/file';
export const CONFIG = {
    API_GATEWAY: API_GATEWAY,
    IDENTITY_SERVICE: IDENTITY_SERVICE,
    MEDIA: "http://localhost:8889/api/v1/file/files/media/download/",
};


export const API = {
    LOGIN: '/identity/auth/token',
    MY_INFO: '/user/user-profiles',
    INFO_ME: '/user/user-profiles/me',
    REGISTER: IDENTITY_SERVICE + '/users/registration',
    NOVEL: NOVEL_SERVICE,
    CHAPTER: NOVEL_SERVICE + "/chapters",
    FILES: FILE_SERVICE + "/files",
}