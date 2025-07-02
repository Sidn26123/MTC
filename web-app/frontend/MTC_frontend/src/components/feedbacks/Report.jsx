import { useState } from 'react';


const NovelReport = ({onClose}) => {
    return (
        <>
            {(
                <div>
                    <div className="relative z-10" role="dialog" aria-modal="true">
                        <div className="fixed inset-0 bg-gray-50/40 bg-opacity-75"></div>
                        <div className="fixed inset-0 z-10 overflow-y-auto">
                            <div
                                className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0 ">
                                <div
                                    className="bg-panel p-6 max-w-xs sm:max-w-sm md:max-w-md w-full rounded-xl background-color-lighter bg-opacity-25">
                                    <div className="flex justify-between items-center">
                                        <img className="h-8 w-auto"
                                             src="https://assets.metruyencv.com/build/assets/logo-776b73c9.png"
                                             alt="" />
                                        <h3 className="font-bold text-xl">Yêu cầu &amp; Báo lỗi</h3>
                                        <button type="button"
                                                className="rounded-md bg-panel focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2"
                                                onClick={onClose}
                                        >
                                            <span className="sr-only">Close</span>
                                            <svg className="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none"
                                                 viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor"
                                                 aria-hidden="true">
                                                <path strokeLinecap="round" strokeLinejoin="round"
                                                      d="M6 18 18 6M6 6l12 12"></path>
                                            </svg>
                                        </button>
                                    </div>
                                    <div className="space-y-6 mt-10 mx-2">
                                        <div className="space-y-2">
                                            <div className="flex items-center">
                                                <span
                                                    className="font-medium">Bạn cần hỗ trợ hay báo cáo vấn đề gì?</span>
                                            </div>
                                            <div className="space-y-2">
                                                <div className="relative flex items-start">
                                                    <div className="flex h-6 items-center">
                                                        <input type="radio"
                                                               className="h-4 w-4 border-gray-300 text-primary focus:ring-primary"
                                                               value="0" name="selected" />
                                                    </div>
                                                    <div className="text-sm text-start">
                                                        <span className="ml-3 leading-6">Báo lỗi chương #19625678</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="space-y-2">
                                                <div className="relative flex items-start">
                                                    <div className="flex h-6 items-center">
                                                        <input type="radio"
                                                               className="h-4 w-4 border-gray-300 text-primary focus:ring-primary"
                                                               value="1" name="selected" />
                                                    </div>
                                                    <div className="text-sm text-start">
                                                        <span className="ml-3 leading-6">Báo cáo chương #19625678</span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="space-y-2">
                                                <div className="relative flex items-start">
                                                    <div className="flex h-6 items-center">
                                                        <input type="radio"
                                                               className="h-4 w-4 border-gray-300 text-primary focus:ring-primary"
                                                               value="2" name="selected" />
                                                    </div>
                                                    <div className="text-sm text-start">
                                                    <span
                                                        className="ml-3 leading-6">Báo lỗi chức năng chương #19625678</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="space-y-2">
                                            <div className="flex justify-between items-center">
                                                <span className="font-medium">Tiêu đề</span>
                                            </div>
                                            <div>
                                                <input
                                                    className="h-10 w-full pl-5 pr-10 text-sm placeholder-gray-400 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded-xl"
                                                    placeholder="Tiêu đề" type="text" />
                                            </div>
                                        </div>
                                        <div className="space-y-2">
                                            <div className="flex justify-between items-center">
                                                <span className="font-medium">Nội dung</span>
                                            </div>
                                            <div>
                                            <textarea rows="5"
                                                      className="block w-full p-2 border placeholder-gray-400 border-slate-300 shadow-sm focus:border-primary focus:ring-primary text-sm rounded-xl"
                                                      placeholder="Chi tiết về vấn đề của bạn..."></textarea>
                                            </div>
                                        </div>
                                        <div className="space-y-3">
                                            <div className="flex justify-center">
                                                <button
                                                    className="bg-primary text-white rounded-xl w-1/2 text-xl py-2 disabled:opacity-25">Gửi
                                                    yêu cầu
                                                </button>
                                            </div>
                                            <div className="text-muted italic">
                                                Các vấn đề vi phạm bản quyền xin vui lòng gửi thư đến contact@truyen.onl
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            )
            }

        </>

    )
}

const UserReport = ({onClose}) => {
    return (
        <>
            <div>
                <div className="relative z-10" role="dialog" aria-modal="true">
                    <div className="fixed inset-0 bg-gray-50/75 transition-opacity"></div>
                    <div className="fixed inset-0 z-10 overflow-y-auto">
                        <div
                            className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                            <div className="bg-panel p-6 max-w-xs sm:max-w-sm md:max-w-md w-full rounded-xl background-color-lighter">
                                <div className="flex justify-between items-center">
                                    <img className="h-8 w-auto"
                                         src="https://assets.metruyencv.com/build/assets/logo-776b73c9.png" alt="" />
                                    <h3 className="font-bold text-xl">Yêu cầu &amp; Báo lỗi</h3>
                                    <button type="button"
                                            className="rounded-md bg-panel focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2"
                                        onClick={onClose}
                                    >
                                        <span className="sr-only">Close</span>
                                        <svg className="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none"
                                             viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor"
                                             aria-hidden="true">
                                            <path strokeLinecap="round" strokeLinejoin="round"
                                                  d="M6 18 18 6M6 6l12 12"></path>
                                        </svg>
                                    </button>
                                </div>

                                <div className="space-y-6 mt-10 mx-2">


                                    <div className="space-y-2">
                                        <div className="flex justify-between items-center">
                                            <span className="font-medium">Tiêu đề</span>
                                        </div>
                                        <div>
                                            <input
                                                className="h-10 w-full pl-5 pr-10 text-sm placeholder-gray-400 dark:placeholder-gray-500 border border-slate-300 focus:outline-none focus:ring-1 focus:ring-primary focus:border-transparent rounded-xl form-auto"
                                                placeholder="Tiêu đề" type="text" />
                                        </div>
                                    </div>

                                    <div className="space-y-2">
                                        <div className="flex justify-between items-center">
                                            <span className="font-medium">Nội dung</span>
                                        </div>
                                        <div>
                                            <textarea rows="5"
                                                      className="block w-full p-2 border placeholder-gray-400 dark:placeholder-gray-500 border-slate-300 shadow-sm focus:border-primary focus:ring-primary text-sm rounded-xl form-auto"
                                                      placeholder="Vui lòng cung cấp thông tin chi tiết về vi phạm của bình luận này"></textarea>
                                        </div>
                                    </div>

                                    <div className="space-y-3">
                                        <div className="flex justify-center">
                                            <button
                                                className="bg-primary text-white rounded-xl w-1/2 text-xl py-2 disabled:opacity-25"> Gửi
                                                yêu cầu
                                            </button>
                                        </div>
                                        <div className="text-muted italic">Các vấn đề vi phạm bản quyền xin vui lòng gửi
                                            thư đến contact@truyen.onl
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </>
    )
}

export { NovelReport, UserReport };