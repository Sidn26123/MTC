import React from "react";

function NovelFan(){
    return (
        <>
            <div className={"mt-3"}>
                <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-6">
                    <div className="flex space-x-2 border-b border-auto pb-4 border-gray-500">
                        <div className="relative w-11 h-11"><img data-x-bind="UserAvatar(item.user)"
                                                                 className="absolute rounded-full right-1 top-1 w-9 h-9"
                                                                 src="https://static.cdnno.com/user/682422a2e30d73d843efef82e9c814b8/200.jpg?1729986404"
                                                                 alt="Trần Thị Hiền"/><img
                            data-x-show="item.level.frame" data-x-bind:src="item.level.frame" className="absolute"
                            src="https://static.cdnno.com/static/fans/chuong-mon.png"/></div>
                        <div className="flex-col space-y-1">
                            <div className="text-title">Trần Thị Hiền</div>
                            <div className="text-muted text-xs">961,000</div>
                        </div>
                    </div>
                </div>
                <div className="flex justify-center mt-8">
                    <button className="border primary-btn-outline px-4 py-2 primary-text-color rounded" data-x-bind="ShowAll"
                            data-x-text="`Xem hết ${fans.length} người hâm mộ`">Xem hết 45 người hâm mộ
                    </button>
                </div>
            </div>
        </>
    )
}

export default NovelFan;