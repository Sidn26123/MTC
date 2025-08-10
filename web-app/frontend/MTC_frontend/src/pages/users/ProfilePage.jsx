import React, { useEffect } from 'react';
import useUserStore, { useProfile, useSetProfile } from '../../stores/userStores.js';
import { getProfileById } from '../../services/userService.js';
import { getFullPathOfAvatar, getUserName } from '../../utils/ProfileUtils.js';

function ProfilePage() {
    const profile = useProfile();
    const setProfile = useSetProfile();
    const user = useUserStore((state) => state.user);
    useEffect(() => {
        getProfileById(user.userId).then(r => {
            console.log("Profile fetched:", r);
            setProfile(r.data.result || {});
        })
    }, [])
    return (
        <>
            <div className={"flex m-4 rounded-lg"}>
                <div className={"flex flex-col background-color-lighter w-1/4 p-4 mr-3 rounded-lg"}>
                    <div className={"flex flex-col items-center gap-y-5"}>
                        <img src={profile?.avatarPath && getFullPathOfAvatar(profile.avatarPath)} className="h-8" alt="Flowbite Logo"/>
                        <span>{getUserName(profile)}</span>
                        {/*<span>Tham gia vào 5 năm trước</span>*/}
                        <div className={"flex flex-row items-center justify-between align-center w-full pl-10"}>
                            <div className={"flex w-2/5"}>
                                <span>ĐÃ ĐỌC</span>
                            </div>
                            <div className={"flex flex-col w-3/5"}>
                                <span>{profile?.readNovelCount + 5} truyện</span>
                                <span>{profile?.readChapterCount + 6} chương</span>
                            </div>
                        </div>
                        <div className={"flex flex-row items-center justify-between align-center w-full pl-10"}>
                            <div className={"flex w-2/5"}>
                                <span>ĐÁNH DẤU</span>
                            </div>
                            <div className={"flex flex-col w-3/5"}>
                                <span>{profile?.markedNovelCount}</span>
                            </div>
                        </div>
                        <div className={"flex flex-row items-center justify-between align-center w-full pl-10"}>
                            <div className={"flex w-2/5"}>
                                <span>ĐỀ CỬ</span>
                            </div>
                            <div className={"flex flex-col w-3/5"}>
                                <span>{profile?.recommendedNovelCount + 1}</span>
                            </div>
                        </div>
                        <div className={"flex flex-row items-center justify-between align-center w-full pl-10"}>
                            <div className={"flex w-2/5"}>
                                <span>BÌNH LUẬN</span>
                            </div>
                            <div className={"flex flex-col w-3/5"}>
                                <span>{profile?.commentedCount + 5}</span>
                            </div>
                        </div>
                        <div className={"flex flex-row items-center justify-between align-center w-full pl-10"}>
                            <div className={"flex w-2/5"}>
                                <span>ĐÁNH GIÁ</span>
                            </div>
                            <div className={"flex flex-col w-3/5"}>
                                <span>5</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div className={"flex flex-col background-color-lighter w-3/4 p-4 rounded-lg"}>
                    b
                </div>
            </div>
        </>
    );
}

export default ProfilePage;