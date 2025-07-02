import React from "react";

function ProfilePage() {
    return (
        <>
            <div className={"flex m-4 rounded-lg"}>
                <div className={"flex flex-col background-color-lighter w-1/4 p-4 mr-3 rounded-lg"}>
                    <div className={"flex flex-col items-center gap-y-5"}>
                        <img src="https://flowbite.com/docs/images/logo.svg" className="h-8" alt="Flowbite Logo"/>
                        <span>Sidn2612</span>
                        <span>Tham gia vào 5 năm trước</span>
                        <div className={"flex flex-row items-center justify-between align-center w-full pl-10"}>
                            <div className={"flex w-2/5"}>
                                <span>ĐÃ ĐỌC</span>
                            </div>
                            <div className={"flex flex-col w-3/5"}>
                                <span>1952 truyen</span>
                                <span>130143 chuong</span>
                            </div>
                        </div>
                        <div className={"flex flex-row items-center justify-between align-center w-full pl-10"}>
                            <div className={"flex w-2/5"}>
                                <span>ĐÁNH DẤU</span>
                            </div>
                            <div className={"flex flex-col w-3/5"}>
                                <span>32</span>
                            </div>
                        </div>
                        <div className={"flex flex-row items-center justify-between align-center w-full pl-10"}>
                            <div className={"flex w-2/5"}>
                                <span>ĐỀ CỬ</span>
                            </div>
                            <div className={"flex flex-col w-3/5"}>
                                <span>10</span>
                            </div>
                        </div>
                        <div className={"flex flex-row items-center justify-between align-center w-full pl-10"}>
                            <div className={"flex w-2/5"}>
                                <span>BÌNH LUẬN</span>
                            </div>
                            <div className={"flex flex-col w-3/5"}>
                                <span>32</span>
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