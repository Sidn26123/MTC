export const getUserName = (user) => {
    return user.firstName + " " + user.lastName;
}

export const getFullPathOfAvatar = (path) => {
    return "http://localhost:8889/api/v1/file/files/media/download/" + path;
}